package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.AllCodesBean;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.MsgCountBean;
import com.sj.yinjiaoyun.shituwang.bean.PersonalBean;
import com.sj.yinjiaoyun.shituwang.bean.UnreadNoticeBean;
import com.sj.yinjiaoyun.shituwang.event.EmptyEvent;
import com.sj.yinjiaoyun.shituwang.event.InfoEvent;
import com.sj.yinjiaoyun.shituwang.event.NoticeEvent;
import com.sj.yinjiaoyun.shituwang.fragment.StudentMessageFragment;
import com.sj.yinjiaoyun.shituwang.fragment.StudentPersonalCenterFragment;
import com.sj.yinjiaoyun.shituwang.fragment.StudentRecommendFragment;
import com.sj.yinjiaoyun.shituwang.fragment.TeacherPersonalCenterFragment;
import com.sj.yinjiaoyun.shituwang.fragment.TeacherRecommendFragment;
import com.sj.yinjiaoyun.shituwang.fragment.TeachterMessageFragment;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DialogUtils;
import com.sj.yinjiaoyun.shituwang.utils.GetJsonDataUtil;
import com.sj.yinjiaoyun.shituwang.utils.MyHandler;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopPortrait;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.utils.UpdateVersionManager;
import com.sj.yinjiaoyun.shituwang.utils.permissionUtils;
import com.sj.yinjiaoyun.shituwang.xmppmanager.CheckConnectionListener;
import com.sj.yinjiaoyun.shituwang.xmppmanager.HeartbeatPackage;
import com.sj.yinjiaoyun.shituwang.xmppmanager.MyChatManagerListener;
import com.sj.yinjiaoyun.shituwang.xmppmanager.XmppConnectionManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.packet.Message;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private static final String SELECT_ITEM = "radioGroup";
    private static final String POSITION = "position";
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.bt_message_count)
    Button btMessageCount;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    //版本更新的管理类
    private UpdateVersionManager updateVersionManager;
    //记录选中的下标
    private int position;
    private static final int FRAGMENT_RECOMMEND = 0;
    private static final int FRAGMENT_MESSAGE = 1;
    private static final int FRAGMENT_MY = 2;
    /**
     * 学生端的Fragment
     */
    private StudentRecommendFragment studentRecommendFragment;
    private StudentMessageFragment studentMessageFragment;
    private StudentPersonalCenterFragment studentPersonalCenterFragment;
    /**
     * 老师端的Fragment
     */
    private TeacherRecommendFragment teacherRecommendFragment;
    private TeachterMessageFragment teachterMessageFragment;
    private TeacherPersonalCenterFragment teacherPersonalCenterFragment;
    //个人画像的对话框
    private PopPortrait popPortrait;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    public static Map<String, Children> map = new HashMap<>();
    //IM链接状态的监听器
    private CheckConnectionListener checkConnectionListener;
    //    private MyPacketListener myPacketListener;
    //私聊的消息监听器
    private MyChatManagerListener myChatManagerListener;
    public static DatagramSocket ds = null;
    //登录的id
    private int id;
    //登录IM的id和密码
    private String jid;
    private String MDPwd;
    //点击两次的时间判断
    private long exitTime;
    //开工作线程连接IM
    private Thread thread;
    private Handler handler = new MyHandler(MainActivity.this);

    public void toastShort() {
        ToastUtil.showShortToast(MainActivity.this, "登录服务器失败");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtils.verifyStoragePermissions(this);
        ButterKnife.bind(this);
        init();
        loginIm();
        showDefaultFragment(savedInstanceState);
    }

    /**
     * 展示默认的Fragment
     *
     * @param savedInstanceState
     */
    private void showDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            switch (loginId) {
                //学生登录
                case 1:
                    studentRecommendFragment = (StudentRecommendFragment) getSupportFragmentManager().findFragmentByTag(StudentRecommendFragment.class.getName());
                    studentMessageFragment = (StudentMessageFragment) getSupportFragmentManager().findFragmentByTag(StudentMessageFragment.class.getName());
                    studentPersonalCenterFragment = (StudentPersonalCenterFragment) getSupportFragmentManager().findFragmentByTag(StudentPersonalCenterFragment.class.getName());
                    // 恢复 recreate 前的位置
                    displayFragment(savedInstanceState.getInt(POSITION));
                    radioGroup.check(savedInstanceState.getInt(SELECT_ITEM));
                    break;
                //老师登录
                case 2:
                    teacherRecommendFragment = (TeacherRecommendFragment) getSupportFragmentManager().findFragmentByTag(TeacherRecommendFragment.class.getName());
                    teachterMessageFragment = (TeachterMessageFragment) getSupportFragmentManager().findFragmentByTag(TeachterMessageFragment.class.getName());
                    teacherPersonalCenterFragment = (TeacherPersonalCenterFragment) getSupportFragmentManager().findFragmentByTag(TeacherPersonalCenterFragment.class.getName());
                    // 恢复 recreate 前的位置
                    displayFragment(savedInstanceState.getInt(POSITION));
                    radioGroup.check(savedInstanceState.getInt(SELECT_ITEM));
                    break;
            }
        } else {
            //检查版本只检查一次
            updateVersionManager = new UpdateVersionManager(MainActivity.this);
            updateVersionManager.checkHttpVersion();
            displayFragment(FRAGMENT_RECOMMEND);
        }
    }

    /**
     * 展示Fragment
     *
     * @param index
     */
    private void displayFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_RECOMMEND:
                if (loginId == 1) {
                    if (studentRecommendFragment == null) {
                        studentRecommendFragment = StudentRecommendFragment.newInstance();
                        ft.add(R.id.fragment_container, studentRecommendFragment, StudentRecommendFragment.class.getName());
                    } else {
                        ft.show(studentRecommendFragment);
                    }
                } else if (loginId == 2) {
                    if (teacherRecommendFragment == null) {
                        teacherRecommendFragment = TeacherRecommendFragment.newInstance();
                        ft.add(R.id.fragment_container, teacherRecommendFragment, TeacherRecommendFragment.class.getName());
                    } else {
                        ft.show(teacherRecommendFragment);
                    }
                }
                break;
            case FRAGMENT_MESSAGE:
                if (loginId == 1) {
                    if (studentMessageFragment == null) {
                        studentMessageFragment = StudentMessageFragment.newInstance();
                        ft.add(R.id.fragment_container, studentMessageFragment, StudentMessageFragment.class.getName());
                    } else {
                        ft.show(studentMessageFragment);
                    }
                } else if (loginId == 2) {
                    if (teachterMessageFragment == null) {
                        teachterMessageFragment = TeachterMessageFragment.newInstance();
                        ft.add(R.id.fragment_container, teachterMessageFragment, TeachterMessageFragment.class.getName());
                    } else {
                        ft.show(teachterMessageFragment);
                    }
                }
                break;
            case FRAGMENT_MY:
                if (loginId == 1) {
                    if (studentPersonalCenterFragment == null) {
                        studentPersonalCenterFragment = StudentPersonalCenterFragment.newInstance();
                        ft.add(R.id.fragment_container, studentPersonalCenterFragment, StudentPersonalCenterFragment.class.getName());
                    } else {
                        ft.show(studentPersonalCenterFragment);
                    }
                } else if (loginId == 2) {
                    if (teacherPersonalCenterFragment == null) {
                        teacherPersonalCenterFragment = TeacherPersonalCenterFragment.newInstance();
                        ft.add(R.id.fragment_container, teacherPersonalCenterFragment, TeacherPersonalCenterFragment.class.getName());
                    } else {
                        ft.show(teacherPersonalCenterFragment);
                    }
                }
                break;
        }
        ft.commit();
    }

    /**
     * 影藏Fragment
     *
     * @param ft
     */
    private void hideFragment(FragmentTransaction ft) {
        //如果不为空则影藏起来
        switch (loginId) {
            case 1:
                if (studentRecommendFragment != null) {
                    ft.hide(studentRecommendFragment);
                }
                if (studentMessageFragment != null) {
                    ft.hide(studentMessageFragment);
                }
                if (studentPersonalCenterFragment != null) {
                    ft.hide(studentPersonalCenterFragment);
                }
                break;
            case 2:
                if (teacherRecommendFragment != null) {
                    ft.hide(teacherRecommendFragment);
                }
                if (teachterMessageFragment != null) {
                    ft.hide(teachterMessageFragment);
                }
                if (teacherPersonalCenterFragment != null) {
                    ft.hide(teacherPersonalCenterFragment);
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate 时记录当前位置
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, radioGroup.getCheckedRadioButtonId());
    }

    /**
     * 获取未读消息数量
     */
    private void getMsgCount() {
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverJID", jid);
        HttpClient.post(this, Api.FIND_MSG_COUNT, map, new CallBack<MsgCountBean>() {
            @Override
            public void onSuccess(MsgCountBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    if (result.getData() > 0) {
                        btMessageCount.setVisibility(View.VISIBLE);
                    } else {
                        btMessageCount.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }


    private void init() {
        //获取登录的id
        loginId = PreferencesUtils.getSharePreInt(MainActivity.this, Const.LOGINID);
        id = PreferencesUtils.getSharePreInt(MainActivity.this, Const.ID);
        if (loginId == 1) {//学生登录
            jid = "5stu_" + id;
        } else if (loginId == 2) {//老师登录
            jid = "5tch_" + id;
        }
        Logger.d("loginId=" + loginId);
        //模拟登录
        GetRequest();
        popPortrait = new PopPortrait(MainActivity.this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        displayFragment(FRAGMENT_RECOMMEND);
                        break;
                    case R.id.rb2:
                        displayFragment(FRAGMENT_MESSAGE);
                        break;
                    case R.id.rb3:
                        displayFragment(FRAGMENT_MY);
                        break;
                }
            }
        });
        saveCodes();
    }


    @Override
    protected void onResume() {
        super.onResume();
      //获取回话的总数
        getMsgCount();
        //获取通知的数量
        findUnreadNotice();


    }

    /**
     * 登录IM
     */
    private void loginIm() {
        myChatManagerListener = new MyChatManagerListener();
        checkConnectionListener = new CheckConnectionListener(MainActivity.this, myChatManagerListener);
        Runnable loginRun = new Runnable() {
            @Override
            public void run() {
                MDPwd = PreferencesUtils.getSharePreStr(MainActivity.this, Const.PWD);
                Logger.d("用户名：" + jid + ";加密密码：" + MDPwd);
                MyApplication.xmppConnection = XmppConnectionManager.OnLogin(jid, MDPwd, checkConnectionListener, myChatManagerListener);
                if (MyApplication.xmppConnection.isAuthenticated()) {
                    HeartbeatPackage.newInstens();
                    //登录成功
                    Logger.d("IM登录服务器成功");
                } else {
                    //如果登录失败，自动销毁Service
                    Logger.d("IM登录服务器失败");
                    handler.sendEmptyMessage(0);
                }
            }
        };
        try {
            ds = new DatagramSocket();
            thread = new Thread(loginRun);
            thread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    /**
     * 存储字典
     */
    private void saveCodes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonData = GetJsonDataUtil.getJson(MainActivity.this, "allcode.json");//获取assets目录下的json文件数据
                MyApplication.allCodesBean = new Gson().fromJson(jsonData, AllCodesBean.class);
                if (MyApplication.allCodesBean != null) {
                    map.clear();
                    for (Children children : MyApplication.allCodesBean.getData()) {
                        map.put(children.getCode(), children);
                        dataConversion(children.getChildren());
                    }
                    Logger.d("转换成map数量:" + map.size());
                    MyApplication.map = map;
                }
            }
        }).start();

    }

    /**
     * 发送网络请求登录
     */
    private void GetRequest() {
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
            return;
        }
        String url = "";
        if (loginId == 2) {
            url = Api.TEACHER_LOGIN;
        } else if (loginId == 1) {
            url = Api.STUDENT_LOGIN;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", PreferencesUtils.getSharePreStr(this, Const.PHONE));
        map.put("password", PreferencesUtils.getSharePreStr(this, Const.PASSWORD));
        map.put("platform ", "2");
        HttpClient.post(this, url, map, new CallBack<PersonalBean>() {
            @Override
            public void onSuccess(PersonalBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    //登录成功之后返回的id
                    PreferencesUtils.putSharePre(MainActivity.this, Const.TOKEN, result.getData().getToken());
                } else {
                    ToastUtil.showShortToast(MainActivity.this, "账号或密码错误,即将跳转登录界面");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }


        });
    }

    /**
     * 查找未读的通知数
     */
    private void findUnreadNotice() {
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverId", String.valueOf(id));
        if (loginId == 1) {
            map.put("receiverType", "0");
        } else if (loginId == 2) {
            map.put("receiverType", "1");
        }
        HttpClient.post(this, Api.FIND_UNREAD_NOTICE, map, new CallBack<UnreadNoticeBean>() {
            @Override
            public void onSuccess(UnreadNoticeBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    if (result.getData() > 0) {
                        ivNotice.setVisibility(View.VISIBLE);
                    } else {
                        ivNotice.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 字典数据解析砖转换成map
     *
     * @param list
     */
    private void dataConversion(List<Children> list) {
        for (Children children : list) {
            if (children.getChildren() != null) {
                map.put(children.getCode(), children);
                dataConversion(children.getChildren());
            } else {
                map.put(children.getCode(), children);
            }
        }
    }

    /**
     * 显示个人画像
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
          final View  rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
            if (!PreferencesUtils.getSharePreBoolean(MainActivity.this, Const.ISSHOW)) {
                popPortrait.pop(rootview);
                PreferencesUtils.putSharePre(MainActivity.this, Const.ISSHOW, true);
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * IM服务器被挤下线
     *
     * @param emptyEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EmptyEvent emptyEvent) {
        if (MyApplication.xmppConnection != null) {
            if (!MyApplication.xmppConnection.isAuthenticated()) {
                if (emptyEvent.isLogin) {
                    DialogUtils.showNormalDialog(MainActivity.this, checkConnectionListener);
                }
            }
        }

    }

    /**
     * 展示会话列表的消息数量
     *
     * @param noticeEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NoticeEvent noticeEvent) {
        Logger.d("MainActivity:getNoticeCount" + noticeEvent.getMessageCount());
        if (noticeEvent.getMessageCount() > 0) {
            btMessageCount.setVisibility(View.VISIBLE);
        } else {
            btMessageCount.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 接受系统推送的通知
     *
     * @param infoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(InfoEvent infoEvent) {
        if (Message.Type.chat == infoEvent.getType()) {
            if (!"C01".equals(infoEvent.getInfoType())) {
                return;
            }
            if (!jid.equals(infoEvent.getTo())) {
                return;
            }
            //接受到系统推送的通知提示
            ivNotice.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 程序退出
     * 1、关闭线程
     * 2.清除Handler
     * 3.关闭IM和心跳包
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!thread.isInterrupted()) {
            thread.interrupt();
            thread = null;
        }
        handler.removeCallbacksAndMessages(null);
        //断开服务器的链接
        if (MyApplication.xmppConnection != null) {
            MyApplication.xmppConnection.removeConnectionListener(checkConnectionListener);
            MyApplication.xmppConnection.disconnect();
            //关掉心跳包
            HeartbeatPackage.isRunning = false;
            MyApplication.xmppConnection = null;
        }
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }

    /**
     * 拦截android物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) >= 2000) {
                ToastUtil.showShortToast(MainActivity.this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                if (MyApplication.xmppConnection != null) {
                    //移除连接监听
                    MyApplication.xmppConnection.removeConnectionListener(checkConnectionListener);
                    MyApplication.xmppConnection.disconnect();
                    MyApplication.xmppConnection = null;

                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @OnClick(R.id.bt_message_count)
    public void onClick() {
        displayFragment(FRAGMENT_MESSAGE);
    }
}

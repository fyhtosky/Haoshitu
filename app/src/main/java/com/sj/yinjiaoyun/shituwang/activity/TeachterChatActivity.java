package com.sj.yinjiaoyun.shituwang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.TeachterChatAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ChatHeadBean;
import com.sj.yinjiaoyun.shituwang.bean.ChatMsgBean;
import com.sj.yinjiaoyun.shituwang.bean.DBmsgBean;
import com.sj.yinjiaoyun.shituwang.bean.MsgBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.bean.TableExchangeBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.event.AdapterSendMsgEvent;
import com.sj.yinjiaoyun.shituwang.event.MsgEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.EditViewUtil;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.DropdownListView;
import com.sj.yinjiaoyun.shituwang.view.MyEdittext;
import com.sj.yinjiaoyun.shituwang.xmppmanager.XmppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

public class TeachterChatActivity extends AppCompatActivity implements DropdownListView.OnRefreshListenerHeader {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_phone)
    Button btPhone;
    @BindView(R.id.bt_weixin)
    Button btWeixin;
    @BindView(R.id.bt_agree)
    Button btAgree;
    @BindView(R.id.bt_invite)
    Button btInvite;
    @BindView(R.id.bt_out)
    Button btOut;
    @BindView(R.id.listview)
    DropdownListView listview;
    @BindView(R.id.input)
    MyEdittext input;
    @BindView(R.id.chat_activity)
    RelativeLayout chatActivity;
    @BindView(R.id.rl_no_message)
    RelativeLayout rlNoMessage;
    @BindView(R.id.ll_label)
    LinearLayout llLabel;
    @BindView(R.id.send)
    Button send;
    private String stuId = "";
    private String tchId = "";
    private String userContactId = "";
    //分页
    private String createTime;
    //每页的数量
    private int row = 20;
    //数据的总数
    private int total = 0;
    //聊天記錄的數據源
    private List<ChatMsgBean.DataBean.RowsBean> list = new ArrayList<>();
    private TeachterChatAdapter teachterChatAdapter;
    //登录的id
    private int id;
    //对象转换成json
    private Gson gson = new Gson();
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //学生档案的信息
    private StudentRecordBean.DataBean stuDataBean;
    //xmpp发送消息封装的实体类
    private MsgBean msgBean;
    //DB保存消息的实体类
    private DBmsgBean dBmsgBean;
    //点击标签之前获去交换详情的id
    private int exchangeId;
    //标示卡片的操作状态
//    1:接受  2：拒绝
    private String optFlag = "";
    //记录三个状态是否可点(默认可点的)
    private boolean isPhone = false;
    private boolean isWeixin = false;
    private boolean isAgree = false;
    private boolean isInvited = false;
    private boolean isOut = false;
    //根据消息后获取跟师学习的id
    private String studyId="";
    //消息的类型
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachter_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //过滤掉输入法中的表情
         EditViewUtil.setProhibitEmoji(input,TeachterChatActivity.this);
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(TeachterChatActivity.this, Const.ID);
        tchId = "5tch_" + id;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stuId = bundle.getString("stuId");
            dataBean = (TeacherDetailBean.DataBean) bundle.getSerializable("dataBean");
            stuDataBean = (StudentRecordBean.DataBean) bundle.getSerializable("stuDataBean");
            userContactId = bundle.getString("userContactId");
            Logger.d("传值操作的数据：学生的id:" + stuId + "  老师的id:" + tchId + "  会话的id:" + userContactId);
            if (dataBean != null) {
                tvTitle.setText(stuDataBean.getRealName());
                teachterChatAdapter = new TeachterChatAdapter(TeachterChatActivity.this, list, dataBean, stuDataBean);
                listview.setAdapter(teachterChatAdapter);
                //注册listview的下拉刷新
                listview.setOnRefreshListenerHead(TeachterChatActivity.this);
                //listvie按下时则关闭对话框
                listview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            hideSoftInputView();
                        }
                        return false;
                    }
                });
            }
        }
        //获取聊天记录请求
        createTime = "";
        RequestBean(false);
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //让输入框获取焦点
                input.requestFocus();
            }
        }, 100);
        //获取聊天标签的状态
        getChatHead();

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
     * 聊天界面接受消息
     *
     * @param msgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEvent msgEvent) {
//        T11：老师拒绝交换电话；
//        T12：老师接受交换电话；
//        T21：老师拒绝交换微信；
//        T22：老师接受交换微信；
//        T31：老师拒绝交换协议；
//        T32：老师接受交换协议
        if (Message.Type.chat == msgEvent.getType()) {
            Logger.d("TeachterChatActivity:" + msgEvent.getMsgBody() + "  接收者TO:" + msgEvent.getTo() + "  发送者FROM:" + msgEvent.getFrom());
            if(tchId.equals(msgEvent.getTo()) && stuId.equals(msgEvent.getFrom())){
            //将xmpp协议发送消息的转换成MsgBean对象
            msgBean = gson.fromJson(msgEvent.getMsgBody(), MsgBean.class);
            type=msgBean.getType();
            //将信息赋值到本地数据库的类型
            dBmsgBean = new DBmsgBean();
            dBmsgBean.setType(msgBean.getType());
            dBmsgBean.setMsg(msgBean.getMsg());
            dBmsgBean.setBusinessId(msgBean.getBusinessId());
            ChatMsgBean.DataBean.RowsBean rowsBean = new ChatMsgBean.DataBean.RowsBean();
            rowsBean.setUserContactId(Integer.parseInt(userContactId));
            rowsBean.setSender(msgEvent.getFrom());
            rowsBean.setReceiver(msgEvent.getTo());
            rowsBean.setMsgType(msgBean.getType());
            rowsBean.setMsgName(dataBean.getRealName());
            rowsBean.setMsg(gson.toJson(dBmsgBean));
            rowsBean.setMsgCount(0);
            rowsBean.setCreateTime(new Date().getTime());
            list.add(rowsBean);
            //更改按钮的状态
            changeButtonState(type);
            Logger.d("StudentChatActivity：" + rowsBean.toString());
            listview.post(new Runnable() {
                @Override
                public void run() {
                    listview.setSelection(teachterChatAdapter.getCount() - 1);
                }
            });
            teachterChatAdapter.notifyDataSetChanged();
        }
        }
    }

    /**
     * 根据消息类型改变按钮的状态
     * @param type
     */
    private  void  changeButtonState(String type ){
        Drawable drawable;
        switch (type){
            case "S11":
                isPhone=false;
                drawable=getResources().getDrawable(R.mipmap.im_phone_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btPhone.setCompoundDrawables(drawable, null, null, null);
                btPhone.setText("换电话");
                break;
            case "S12":
                isPhone = true;
                drawable = getResources().getDrawable(R.mipmap.im_phone);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btPhone.setCompoundDrawables(drawable, null, null, null);
                btPhone.setText("已交换");
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("S10")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "S21":
                isWeixin=false;
                drawable=getResources().getDrawable(R.mipmap.im_wechat_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btWeixin.setCompoundDrawables(drawable, null, null, null);
                btWeixin.setText("换微信");
                break;
            case "S22":
                isWeixin = true;
                drawable = getResources().getDrawable(R.mipmap.im_wechat);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btWeixin.setCompoundDrawables(drawable, null, null, null);
                btWeixin.setText("已交换");
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("S20")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "S31":
                isAgree=false;
                drawable=getResources().getDrawable(R.mipmap.im_agreement_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btAgree.setCompoundDrawables(drawable, null, null, null);
                btAgree.setText("换协议");
                break;
            case "S32":
                isAgree = true;
                drawable = getResources().getDrawable(R.mipmap.im_agreement);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btAgree.setCompoundDrawables(drawable, null, null, null);
                btAgree.setText("已交换");
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("S30")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "S41":
                isInvited = true;
                drawable = getResources().getDrawable(R.mipmap.im_interview);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btInvite.setCompoundDrawables(drawable, null, null, null);
                btInvite.setText("已邀请");
                break;
            case "S42":
                //学生拒绝面试邀请
                isInvited=false;
                drawable = getResources().getDrawable(R.mipmap.im_interview_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btInvite.setCompoundDrawables(drawable, null, null, null);
                btInvite.setText("邀面试");
                break;
        }
    }
    /**
     * 獲取聊天記錄
     *
     * @param onLoadMore
     */
    private void RequestBean(final boolean onLoadMore) {
        if (TextUtils.isEmpty(userContactId)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("userContactId", userContactId);
        if (!TextUtils.isEmpty(createTime)) {
            map.put("createTimeStr", createTime);
        }
        map.put("rows", String.valueOf(row));
        HttpClient.post(this, Api.FIND_CHAT_MSG_RECORD, map, new CallBack<ChatMsgBean>() {
            @Override
            public void onSuccess(ChatMsgBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    if (!onLoadMore) {
                        total = result.getData().getTotal();
                        list.clear();
                    }
                    if (list.size() < total) {
                        list.addAll(result.getData().getRows());
                        Collections.reverse(list); // 倒序排列
                    }
                }
                teachterChatAdapter.notifyDataSetChanged();
            }


        });
    }

    /**
     * 获取聊天头信息
     */
    private void getChatHead() {
        if (!TextUtils.isEmpty(stuId) && !TextUtils.isEmpty(tchId)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("stuId", stuId.split("_")[1]);
            map.put("tchId", tchId.split("_")[1]);
            HttpClient.post(this, Api.GET_CHAT_HEAD, map, new CallBack<ChatHeadBean>() {
                @Override
                public void onSuccess(ChatHeadBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        //你设置了不合适操作或者师徒解约
                        if (result.getData().getIsNotDisturb() == 1 || result.getData().getStuIsDisabled()==1) {
                                //禁用
                                if(result.getData().getStuIsDisabled()==1){
                                    Drawable drawable;
                                    //设置手机
                                    btPhone.setEnabled(false);
                                    drawable = getResources().getDrawable(R.mipmap.im_phone);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btPhone.setCompoundDrawables(drawable, null, null, null);
                                    //设置微信
                                    btWeixin.setEnabled(false);
                                    drawable = getResources().getDrawable(R.mipmap.im_wechat);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btWeixin.setCompoundDrawables(drawable, null, null, null);
                                     //设置协议
                                    btAgree.setEnabled(false);
                                    drawable = getResources().getDrawable(R.mipmap.im_agreement);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btAgree.setCompoundDrawables(drawable, null, null, null);
                                    //设置邀面试
                                    btInvite.setEnabled(false);
                                    drawable = getResources().getDrawable(R.mipmap.im_interview);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btInvite.setCompoundDrawables(drawable, null, null, null);
                                   //设置不合适
                                    btOut.setEnabled(false);
                                    drawable = getResources().getDrawable(R.mipmap.icon_refuse_dis);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btOut.setCompoundDrawables(drawable, null, null, null);
                                }else {
                                    isPhone = true;
                                    isWeixin = true;
                                    isAgree = true;
                                    isInvited = true;
                                    llLabel.setVisibility(View.GONE);
                                    rlNoMessage.setVisibility(View.VISIBLE);
                                }

                            //设置键盘不可用
                            input.setInputType(InputType.TYPE_NULL);
                            send.setEnabled(false);
                            send.setBackgroundResource(R.drawable.btn_start_gray);
                        } else {
                            Drawable drawable;
                            if (result.getData().getStudy() != null) {
                                studyId=String.valueOf(result.getData().getStudy().getId());
                                //显示邀约状态
                                int  status =result.getData().getStudy().getCurrentStatus();
                                if (status== 100 || status==102 || status==104 || status==111 ||status==113 || status==114 || status==122 || status==123 || status==124) {
                                    isInvited=false;
                                    drawable = getResources().getDrawable(R.mipmap.im_interview_green);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btInvite.setCompoundDrawables(drawable, null, null, null);
                                    btInvite.setText("邀面试");
                                }else {
                                    isInvited = true;
                                    drawable = getResources().getDrawable(R.mipmap.im_interview);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    btInvite.setCompoundDrawables(drawable, null, null, null);
                                    btInvite.setText("已邀请");
                                }
                            }

                            if (result.getData().getExchanges() == null) {
                                return;
                            }
                            for (int i = 0; i < result.getData().getExchanges().size(); i++) {
                                ChatHeadBean.DataBean.ExchangesBean changeBean = result.getData().getExchanges().get(i);
                                if (changeBean.getStatus() == 4 || changeBean.getStatus() == 0) {
                                    switch (changeBean.getChangeType()) {
                                        //换电话的状态
                                        case 0:
                                            isPhone = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_phone);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btPhone.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==0){
                                                btPhone.setText("交换中");
                                            }else {
                                                btPhone.setText("已交换");
                                            }
                                            break;
                                            //换微信的状态
                                        case 1:
                                            isWeixin = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_wechat);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btWeixin.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==0){
                                                btWeixin.setText("交换中");
                                            }else {
                                                btWeixin.setText("已交换");
                                            }
                                            break;
                                            //换协议的状态
                                        case 2:
                                            isAgree = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_agreement);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btAgree.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==0){
                                                btAgree.setText("交换中");
                                            }else {
                                                btAgree.setText("已交换");
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    }

                }

            });
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                assert manager != null;
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 重载的方法
     *
     * @param context
     * @param dataBean
     */
    public static void StartActivity(Context context, String stuId, TeacherDetailBean.DataBean dataBean, StudentRecordBean.DataBean stuDataBean, String userContactId) {
        Intent intent = new Intent(context, TeachterChatActivity.class);
        intent.putExtra("stuId", stuId);
        intent.putExtra("dataBean", dataBean);
        intent.putExtra("stuDataBean", stuDataBean);
        intent.putExtra("userContactId", userContactId);
        context.startActivity(intent);

    }

    @OnClick({R.id.rl_back, R.id.bt_phone, R.id.bt_weixin, R.id.bt_agree, R.id.bt_invite, R.id.bt_out, R.id.send, R.id.rl_no_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ClearSessionCount();
                break;
            case R.id.bt_phone:
                if (!isPhone) {
                    //获取交换电弧详情的id
                    ApplyExchange(0);

                }
                break;
            case R.id.bt_weixin:
                if (!isWeixin) {
                    //获取交换微信详情点id
                    ApplyExchange(1);

                }
                break;
            case R.id.bt_agree:
                if (!isAgree) {
                    //获取交换协议详情点id
                    ApplyExchange(2);
                }
                break;
            case R.id.bt_invite:
                if (!isInvited) {
                    Intent intent = new Intent(TeachterChatActivity.this, TachterInterviewActivity.class);
                    intent.putExtra("dataBean", dataBean);
                    intent.putExtra("stuDataBean", stuDataBean);
                    intent.putExtra("userContactId", userContactId);
                    intent.putExtra("studyId",studyId);
                    startActivityForResult(intent, 0);
                }

                break;
            //设置不合适操作
            case R.id.bt_out:
                show();
                break;
            //取消不合适操作
            case R.id.rl_no_message:
                //发送消息
                //网络状态判断
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                    return;
                }
                sendMsgText("", "", "T04");
                updateTeachterSet(0);
                break;
            case R.id.send:
                String content = input.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToast(TeachterChatActivity.this, "请输入消息");
                    return;
                }
                if (content.length() > 200) {
                    ToastUtil.showShortToast(TeachterChatActivity.this, "消息不能超过200字");
                } else {
                    //网络状态判断
                    if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                        NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                        return;
                    }
                    //发送消息
                    sendMsgText(content, "", "T01");
                }
                break;
        }
    }

    /**
     * 展示对话框
     */
    private void show() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TeachterChatActivity.this);
        //    设置Title的图标
        //    设置Title的内容
        builder.setTitle("TA不合适");
        //    设置Content来显示一个信息
        builder.setMessage("认为该学生不合适,将不再收到TA的任何消息，你可以在聊天界面中取消该设置");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //网络状态判断
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                    return;
                }
                sendMsgText("", "", "T03");
                updateTeachterSet(1);
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    /**
     * 教师操作沟通是否合适
     */
    private void updateTeachterSet(int disturbFlag) {
        HashMap<String,String>map=new HashMap<>();
        map.put("id",userContactId);
        map.put("disturbFlag",String.valueOf(disturbFlag));
        map.put("tchId",tchId.split("_")[1]);
        HttpClient.post(this, Api.UPDATE_TEACHTER_SET, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(TeachterChatActivity.this,"设置成功");
                    //重新获取消息头的状态
                    getChatHead();
                }else {
                    ToastUtil.showShortToast(TeachterChatActivity.this,result.getMsg());
                }

            }

        });
    }


    /**
     * 获取操作标签的id
     */
    private void ApplyExchange(final int changeType) {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("stuId", stuId.split("_")[1]);
        map.put("tchId", tchId.split("_")[1]);
        map.put("changeType", String.valueOf(changeType));
        map.put("askType", "1");
        HttpClient.post(this, Api.APPLE_EXCHANGE, map, new CallBack<TableExchangeBean>() {
            @Override
            public void onSuccess(TableExchangeBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    exchangeId = result.getData().getId();
                    //交换电话
                    if (changeType == 0) {
                        //设置电话
                        Drawable phone = getResources().getDrawable(R.mipmap.im_phone);
                        phone.setBounds(0, 0, phone.getMinimumWidth(), phone.getMinimumHeight());
                        btPhone.setCompoundDrawables(phone, null, null, null);
                        if(result.getData().isIsNew()){
                            sendMsgText("", String.valueOf(exchangeId), "T10");
                            btPhone.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "T12");
                            btPhone.setText("已交换");
                            isPhone=true;
                        }
                        //交换微信
                    } else if (changeType == 1) {
                        //设置微信
                        Drawable weixin = getResources().getDrawable(R.mipmap.im_wechat);
                        weixin.setBounds(0, 0, weixin.getMinimumWidth(), weixin.getMinimumHeight());
                        btWeixin.setCompoundDrawables(weixin, null, null, null);
                        if(result.getData().isIsNew()){
                            sendMsgText("", String.valueOf(exchangeId), "T20");
                            btWeixin.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "T22");
                            btWeixin.setText("已交换");
                            isWeixin=true;
                        }

                        //交换协议
                    } else if (changeType == 2) {
                        //设置协议
                        Drawable agree = getResources().getDrawable(R.mipmap.im_agreement);
                        agree.setBounds(0, 0, agree.getMinimumWidth(), agree.getMinimumHeight());
                        btAgree.setCompoundDrawables(agree, null, null, null);
                        if(result.getData().isIsNew()){
                            sendMsgText("", String.valueOf(exchangeId), "T30");
                            btAgree.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "T32");
                            btAgree.setText("已交换");
                            isAgree=true;
                        }

                    }

                } else {
                    ToastUtil.showShortToast(TeachterChatActivity.this, result.getMsg());
                }
            }
        });
    }

    /**
     * 清除会话计数器
     */
    private void ClearSessionCount() {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            finish();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("askType", "1");
        map.put("userContactId", userContactId);
        HttpClient.post(this, Api.ClEAR_SESSION_COUNT, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    finish();
                }
            }


        });
    }

    /**
     * 适配器操作本地发送消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AdapterSendMsgEvent adapterSendMsgEvent) {
        Logger.d("本地发送消息"+adapterSendMsgEvent.getType()+adapterSendMsgEvent.getBusinessId());
        sendMsgText(adapterSendMsgEvent.getContent(), adapterSendMsgEvent.getBusinessId(), adapterSendMsgEvent.getType());
//        T11：老师拒绝交换电话；
//        T12：老师接受交换电话；
//        T21：老师拒绝交换微信；
//        T22：老师接受交换微信；
//        T31：老师拒绝交换协议；
//        T32：老师接受交换协议
        if ("T11".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("T12".equals(adapterSendMsgEvent.getType())) {
            optFlag = "1";
            isPhone = true;
            Drawable drawable = getResources().getDrawable(R.mipmap.im_phone);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btPhone.setCompoundDrawables(drawable, null, null, null);
            btPhone.setText("已交换");
        } else if ("T21".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("T22".equals(adapterSendMsgEvent.getType())) {
            optFlag = "1";
            isWeixin = true;
            Drawable  drawable = getResources().getDrawable(R.mipmap.im_wechat);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btWeixin.setCompoundDrawables(drawable, null, null, null);
            btWeixin.setText("已交换");
        } else if ("T31".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("T32".equals(adapterSendMsgEvent.getType())) {
            optFlag = "1";
            isAgree = true;
            Drawable drawable = getResources().getDrawable(R.mipmap.im_agreement);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btAgree.setCompoundDrawables(drawable, null, null, null);
            btAgree.setText("已交换");
        }
        if (!TextUtils.isEmpty(optFlag)) {
            optExchange(adapterSendMsgEvent.getBusinessId(), optFlag);
        }
    }

    /**
     * 发送消息
     *
     * @param content
     */
    private void sendMsgText(String content, String businessId, String type) {
        if (MyApplication.xmppConnection != null && MyApplication.xmppConnection.isAuthenticated()) {
            //封装xmpp发送的格式
            msgBean = new MsgBean();
            msgBean.setType(type);
            msgBean.setMsg(content);
            msgBean.setSessionId(Integer.parseInt(userContactId));
            msgBean.setBusinessId(businessId);
            //封装本地消息的保存DB的形式
            dBmsgBean = new DBmsgBean();
            dBmsgBean.setBusinessId(businessId);
            dBmsgBean.setMsg(content);
            dBmsgBean.setType(type);
            //添加到本地列表中
            ChatMsgBean.DataBean.RowsBean rowsBean = new ChatMsgBean.DataBean.RowsBean();
            rowsBean.setUserContactId(Integer.parseInt(userContactId));
            rowsBean.setSender(tchId);
            rowsBean.setReceiver(stuId);
            rowsBean.setMsgType(msgBean.getType());
            rowsBean.setMsgName(dataBean.getRealName());
            rowsBean.setMsg(gson.toJson(dBmsgBean));
            rowsBean.setMsgCount(0);
            rowsBean.setCreateTime(new Date().getTime());
            list.add(rowsBean);
            Logger.d("StudentChatActivity：新增的消息记录" + rowsBean.toString());
            listview.post(new Runnable() {
                @Override
                public void run() {
                    listview.setSelection(teachterChatAdapter.getCount() - 1);
                }
            });
            switch (type){
                case "T12":
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("S10")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
                case "T22":
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("S20")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
                case "T32":
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("S30")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
                case "T03":
                    //设置键盘不可以用
                    input.setInputType(InputType.TYPE_NULL);
                    rlNoMessage.setVisibility(View.VISIBLE);
                    llLabel.setVisibility(View.GONE);
                    send.setEnabled(false);
                    send.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T04":
                    //对方取消了对我不合适的设置
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    rlNoMessage.setVisibility(View.GONE);
                    llLabel.setVisibility(View.VISIBLE);
                    send.setEnabled(true);
                    send.setBackgroundResource(R.drawable.btn_chat_send);
                    break;
            }
            teachterChatAdapter.notifyDataSetChanged();
            input.setText("");
            //xmpp发送
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        XmppUtil.sendMessage(MyApplication.xmppConnection, gson.toJson(msgBean).replace("type", "msgType"), tchId, stuId);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        ToastUtil.showShortToast(TeachterChatActivity.this, "发送失败");
                    }
                }
            }).start();
            //将消息记录保存后台
            SaveChat(gson.toJson(dBmsgBean), tchId, stuId, msgBean.getType());
        } else {
            ToastUtil.showShortToast(TeachterChatActivity.this, "登录服务器出错，请重新登录");
        }
    }

    /*
  * 保存聊天记录
  * 提交后台服务器保存
  */
    private void SaveChat(String message, String sender, String receiver, String msgType) {
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userContactId", userContactId);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("msg", message);
        hashMap.put("msgType", msgType);//私聊

        HttpClient.post(this, Api.SAVE_CHAT_MSG, hashMap, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    Logger.d("消息保存服务器成功");
                } else {
                    ToastUtil.showShortToast(TeachterChatActivity.this, result.getMsg());
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        listview.onRefreshCompleteHeader();
        if (list.size() == 0) {
            createTime = "";
        } else {
            createTime = String.valueOf(list.get(0).getCreateTime());
        }
        if (total > list.size()) {
            RequestBean(true);
        }
    }

    /**
     * 交换操作 学生拒绝、学生接受、老师拒绝、老师接收
     *
     * @param businessId
     * @param optFlag
     */
    private void optExchange(String businessId, String optFlag) {
        HashMap<String, String> map = new HashMap<>();
        map.put("detailId", businessId);
//      处理人 0：学生；1：教师
        map.put("answerType", "1");
        map.put("optFlag", optFlag);
        map.put("stuId", stuId.split("_")[1]);
        map.put("tchId", tchId.split("_")[1]);
        HttpClient.post(this, Api.OPT_EXCHANGE, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {

                } else {
                    ToastUtil.showShortToast(TeachterChatActivity.this, result.getMsg());
                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String type = bundle.getString(Const.INVITE_TYPE);
                if ("T41".equals(type)) {
                    //获取聊天记录请求
                    createTime = "";
                    RequestBean(false);
                    Drawable drawable = getResources().getDrawable(R.mipmap.im_interview);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    isInvited=true;
                    btInvite.setCompoundDrawables(drawable, null, null, null);
                    btInvite.setText("邀请中");
                }

            }

        }
    }

    /**
     * 监听返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            hideSoftInputView();
            ClearSessionCount();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

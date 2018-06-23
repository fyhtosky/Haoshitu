package com.sj.yinjiaoyun.shituwang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.ChatRecordAdapter;
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

/**
 * 学生端的私聊界面
 */
public class StudentChatActivity extends AppCompatActivity implements DropdownListView.OnRefreshListenerHeader {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_phone)
    Button btPhone;
    @BindView(R.id.bt_weixin)
    Button btWeixin;
    @BindView(R.id.bt_agree)
    Button btAgree;
    @BindView(R.id.listview)
    DropdownListView listview;
    @BindView(R.id.input)
    MyEdittext input;
    @BindView(R.id.chat_activity)
    RelativeLayout chatActivity;
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
    //适配器
    private ChatRecordAdapter chatRecordAdapter;
    //登录的id
    private int id;
    //记录三个状态是否可点(默认可点的)
    private boolean isPhone = false;
    private boolean isWeixin = false;
    private boolean isAgree = false;
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
    //标示三个lable是否可点
    private boolean isAllClick=false;
    //消息的类型
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //过滤掉输入法中的表情
        EditViewUtil.setProhibitEmoji(input,StudentChatActivity.this);
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(StudentChatActivity.this, Const.ID);
        stuId = "5stu_" + id;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tchId = bundle.getString("tchId");
            dataBean = (TeacherDetailBean.DataBean) bundle.getSerializable("dataBean");
            stuDataBean = (StudentRecordBean.DataBean) bundle.getSerializable("stuDataBean");
            userContactId = bundle.getString("userContactId");
            Logger.d("传值操作的数据：学生自己的id:" + stuId + "  老师的id:" + tchId + "  会话的id:" + userContactId);
            //获取聊天标签的状态
            getChatHead();
            if (dataBean != null) {
                tvTitle.setText(dataBean.getRealName());
                chatRecordAdapter = new ChatRecordAdapter(StudentChatActivity.this, chatActivity, list, dataBean, stuDataBean);
                listview.setAdapter(chatRecordAdapter);
                //注册listview的下拉刷新
                listview.setOnRefreshListenerHead(StudentChatActivity.this);
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
                chatRecordAdapter.notifyDataSetChanged();
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
                        //老师设置不合适或者师徒解约
                        if (result.getData().getIsNotDisturb() == 1 || result.getData().getTchIsDisabled()==1) {
                            if(result.getData().getTchIsDisabled()==1){
                                btPhone.setEnabled(false);
                                btWeixin.setEnabled(false);
                                btAgree.setEnabled(false);
                            }else {
                                isPhone = true;
                                isWeixin = true;
                                isAgree = true;
                            }
                            //设置电话
                            Drawable phone = getResources().getDrawable(R.mipmap.im_phone);
                            phone.setBounds(0, 0, phone.getMinimumWidth(), phone.getMinimumHeight());
                            btPhone.setCompoundDrawables(phone, null, null, null);
                            //设置微信
                            Drawable weixin = getResources().getDrawable(R.mipmap.im_wechat);
                            weixin.setBounds(0, 0, weixin.getMinimumWidth(), weixin.getMinimumHeight());
                            btWeixin.setCompoundDrawables(weixin, null, null, null);
                            //设置协议
                            Drawable agree = getResources().getDrawable(R.mipmap.im_agreement);
                            agree.setBounds(0, 0, agree.getMinimumWidth(), agree.getMinimumHeight());
                            btAgree.setCompoundDrawables(agree, null, null, null);
                            //设置键盘不可以用
                            input.setInputType(InputType.TYPE_NULL);
                            send.setEnabled(false);
                            send.setBackgroundResource(R.drawable.btn_start_gray);
                        } else {
                            Drawable drawable;
                            if (result.getData().getExchanges() == null) {
                                return;
                            }
                            for (int i = 0; i < result.getData().getExchanges().size(); i++) {
                                ChatHeadBean.DataBean.ExchangesBean changeBean = result.getData().getExchanges().get(i);
                                if (changeBean.getStatus() == 4 || changeBean.getStatus() == 1) {
                                    switch (changeBean.getChangeType()) {
                                        case 0:
                                            isPhone = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_phone);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btPhone.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==1){
                                                btPhone.setText("交换中");
                                            }else {
                                                btPhone.setText("已交换");
                                            }

                                            break;
                                        case 1:
                                            isWeixin = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_wechat);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btWeixin.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==1){
                                                btWeixin.setText("交换中");
                                            }else {
                                                btWeixin.setText("已交换");
                                            }

                                            break;
                                        case 2:
                                            isAgree = true;
                                            drawable = getResources().getDrawable(R.mipmap.im_agreement);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            btAgree.setCompoundDrawables(drawable, null, null, null);
                                            if(changeBean.getStatus()==1){
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


    @OnClick({R.id.rl_back, R.id.bt_phone, R.id.bt_weixin, R.id.bt_agree, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ClearSessionCount();
                break;
            case R.id.bt_phone:
                if(!isAllClick){
                    if (!isPhone) {
                        //获取交换电弧详情的id
                        ApplyExchange(0);
                    }
                }

                break;
            case R.id.bt_weixin:
                if(!isAllClick){
                    if (!isWeixin) {
                        //获取交换微信详情点id
                        ApplyExchange(1);
                    }
                }

                break;
            case R.id.bt_agree:
                if(!isAllClick){
                    if (!isAgree) {
                        //获取交换协议详情点id
                        ApplyExchange(2);
                    }
                }

                break;
            case R.id.send:
                String content = input.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToast(StudentChatActivity.this, "请输入消息");
                    return;
                }
                if (content.length() > 200) {
                    ToastUtil.showShortToast(StudentChatActivity.this, "消息不能超过200字");
                } else {
                    //发送消息
                    //网络状态判断
                    if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                        NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                        return;
                    }
                    sendMsgText(content, "", "S01");
                }
                break;
        }
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
        map.put("askType", "0");
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
        map.put("askType", "0");
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
                            sendMsgText("", String.valueOf(exchangeId), "S10");
                            btPhone.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "S12");
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
                            sendMsgText("", String.valueOf(exchangeId), "S20");
                            btWeixin.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "S22");
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
                            sendMsgText("", String.valueOf(exchangeId), "S30");
                            btAgree.setText("交换中");
                        }else {
                            sendMsgText("", String.valueOf(exchangeId), "S32");
                            isAgree=true;
                            btAgree.setText("已交换");
                        }
                    }
                } else {
                    ToastUtil.showShortToast(StudentChatActivity.this, result.getMsg());
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
     * 重载的方法
     *
     * @param context
     * @param dataBean
     */
    public static void StartActivity(Context context, String tchId, TeacherDetailBean.DataBean dataBean, StudentRecordBean.DataBean stuDataBean, String userContactId) {
        Intent intent = new Intent(context, StudentChatActivity.class);
        intent.putExtra("tchId", tchId);
        intent.putExtra("dataBean", dataBean);
        intent.putExtra("stuDataBean", stuDataBean);
        intent.putExtra("userContactId", userContactId);
        context.startActivity(intent);
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
        if (Message.Type.chat == msgEvent.getType()) {
            Logger.d("StudentChatActivity:" + msgEvent.getMsgBody() + "  接收者TO:" + msgEvent.getTo() + "  发送者FROM:" + msgEvent.getFrom());
            if(stuId.equals(msgEvent.getTo()) && tchId.equals(msgEvent.getFrom())){
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
                    listview.setSelection(chatRecordAdapter.getCount() - 1);
                }
            });

            chatRecordAdapter.notifyDataSetChanged();
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
            case "T11":
                isPhone=false;
                drawable = getResources().getDrawable(R.mipmap.im_phone_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btPhone.setCompoundDrawables(drawable, null, null, null);
                btPhone.setText("换电话");
                break;
            case "T12":
                isPhone = true;
                drawable = getResources().getDrawable(R.mipmap.im_phone);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btPhone.setCompoundDrawables(drawable, null, null, null);
                btPhone.setText("已交换");
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("T10")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "T21":
                isWeixin=false;
                drawable = getResources().getDrawable(R.mipmap.im_wechat_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btWeixin.setCompoundDrawables(drawable, null, null, null);
                btWeixin.setText("换微信");
                break;
            case "T22":
                isWeixin = true;
                drawable = getResources().getDrawable(R.mipmap.im_wechat);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btWeixin.setCompoundDrawables(drawable, null, null, null);
                btWeixin.setText("已交换");
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("T20")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "T31":
                isAgree=false;
                drawable = getResources().getDrawable(R.mipmap.im_agreement_green);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btAgree.setCompoundDrawables(drawable, null, null, null);
                btAgree.setText("换协议");
                break;
            case "T32":
                isAgree = true;
                drawable = getResources().getDrawable(R.mipmap.im_agreement);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btAgree.setCompoundDrawables(drawable, null, null, null);
                btAgree.setText("已交换");
                //更改数据源中所有该消息类型的操作状态
                for (int i=0;i<list.size();i++){
                    if(list.get(i).getMsgType().equals("T30")){
                        list.get(i).setStatus("1");
                    }
                }
                break;
            case "T03":
                //当对方将我设置不合适时不可以发送消息则Edittext不可用
                //设置键盘不可以用
                input.setInputType(InputType.TYPE_NULL);
                send.setEnabled(false);
                send.setBackgroundResource(R.drawable.btn_start_gray);
                isAllClick=true;
                //重新查找各种Type的状态
                getChatHead();
                hideSoftInputView();
                break;
            case "T04":
                //对方取消了对我不合适的设置
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                send.setEnabled(true);
                send.setBackgroundResource(R.drawable.btn_chat_send);
                isAllClick=false;
                //重新查找各种Type的状态
                getChatHead();
                break;
        }
    }
    /**
     * 适配器操作本地发送消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AdapterSendMsgEvent adapterSendMsgEvent) {
        sendMsgText(adapterSendMsgEvent.getContent(), adapterSendMsgEvent.getBusinessId(), adapterSendMsgEvent.getType());
//        S11：学生拒绝交换电话；
//        S12：学生接受交换电话；
//        S21：学生拒绝交换微信；
//        S22：学生接受交换微信；
//        S31：学生拒绝交换协议；
//        S32：学生接受交换协议
        if ("S11".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("S12".equals(adapterSendMsgEvent.getType())) {
            optFlag = "1";
            isPhone = true;
            Drawable drawable = getResources().getDrawable(R.mipmap.im_phone);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btPhone.setCompoundDrawables(drawable, null, null, null);
            btPhone.setText("已交换");

        } else if ("S21".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("S22".equals(adapterSendMsgEvent.getType())) {
            optFlag = "1";
            isWeixin = true;
            Drawable  drawable = getResources().getDrawable(R.mipmap.im_wechat);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btWeixin.setCompoundDrawables(drawable, null, null, null);
            btWeixin.setText("已交换");

        } else if ("S31".equals(adapterSendMsgEvent.getType())) {
            optFlag = "2";
        } else if ("S32".equals(adapterSendMsgEvent.getType())) {
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
            rowsBean.setSender(stuId);
            rowsBean.setReceiver(tchId);
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
                    listview.setSelection(chatRecordAdapter.getCount() - 1);
                }
            });
            switch (type){
                case "S12":
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("T10")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
                case "S22":
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("T20")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
                case "S32":
                    //更改数据源中所有该消息类型的操作状态
                    for (int i=0;i<list.size();i++){
                        if(list.get(i).getMsgType().equals("T30")){
                            list.get(i).setStatus("1");
                        }
                    }
                    break;
            }
            chatRecordAdapter.notifyDataSetChanged();
            input.setText("");
            //xmpp发送
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        XmppUtil.sendMessage(MyApplication.xmppConnection, gson.toJson(msgBean).replace("type", "msgType"), stuId, tchId);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        ToastUtil.showShortToast(StudentChatActivity.this, "发送失败");
                    }
                }
            }).start();
            //将消息记录保存后台
            SaveChat(gson.toJson(dBmsgBean), stuId, tchId, msgBean.getType());
        } else {
            ToastUtil.showShortToast(StudentChatActivity.this, "登录服务器出错，请重新登录");
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
                    ToastUtil.showShortToast(StudentChatActivity.this, result.getMsg());
                }
            }
        });

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
        map.put("answerType", "0");
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
                    ToastUtil.showShortToast(StudentChatActivity.this, result.getMsg());
                }
            }


        });
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

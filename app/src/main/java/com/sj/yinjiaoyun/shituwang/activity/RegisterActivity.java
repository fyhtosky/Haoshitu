package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.CodeBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRegisterBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherRegisterBean;
import com.sj.yinjiaoyun.shituwang.event.SmsEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.receiver.SmsContentObserver;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeCountUtil;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnFocusChangeListener {

   private static String PHONE="phone";
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_code)
    Button btCode;
    //获取登录的身份
    // 1是学生2是老师
    private int loginId = 0;
    //标示是否勾选
    private boolean flag = true;
    //获取短信的内容
    private SmsContentObserver observer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }
    public static void openActivity(Context context, String phone){
        Intent intent=new Intent(context,RegisterActivity.class);
        intent.putExtra(PHONE,phone);
        context.startActivity(intent);
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
     * 初始化
     */
    private void init() {
        loginId = PreferencesUtils.getSharePreInt(RegisterActivity.this, Const.LOGINID);
        Logger.d("LoginActivity:默认身份：" + loginId);
        observer = new SmsContentObserver(RegisterActivity.this, new Handler());
        Uri uri = Uri.parse("content://sms");
        //第二个参数： 是否监听对应服务所有URI监听  例如sms 所有uri
//        监听ContentProvider的数据改变
        getContentResolver().registerContentObserver(uri, true, observer);
        //获取学校数据
        etAccount.setOnFocusChangeListener(this);
        etCode.setOnFocusChangeListener(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            etAccount.setText(bundle.getString(PHONE));
            etAccount.setEnabled(false);
        }
    }


    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.rl_back, R.id.rl_agreement, R.id.bt_code, R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //跳转协议
            case R.id.rl_agreement:
                startActivity(new Intent(RegisterActivity.this, AgreementActivity.class));
                break;
            //点击获取验证码
            case R.id.bt_code:
                if (checkPhone(etAccount.getText().toString().trim())) {
                    //获取验证码
                    getCode();
                }
                break;
            //激活
            case R.id.bt_login:
                if (loginId == 1) {
                    StudentRegis();
                } else if (loginId == 2) {
                    TeacherRegis();
                }
                break;
        }
    }

    /**
     * 老师注册
     */
    private void TeacherRegis() {
        if (check()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("mobile", etAccount.getText().toString().trim());
            map.put("smsCode", etCode.getText().toString().trim());
            HttpClient.post(this, Api.TEACHER_ACTIVE, map, new CallBack<TeacherRegisterBean>() {
                @Override
                public void onSuccess(TeacherRegisterBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        ToastUtil.showShortToast(RegisterActivity.this, result.getMsg());
                        PreferencesUtils.putSharePre(RegisterActivity.this,Const.LOGIN_STATUS,true);
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        finish();
                    } else {
                        ToastUtil.showShortToast(RegisterActivity.this, result.getMsg());
                    }
                }
            });
        }

    }

    /**
     * 学生注册
     */
    private void StudentRegis() {
        if (check()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("mobile", etAccount.getText().toString().trim());
            map.put("smsCode", etCode.getText().toString().trim());
            HttpClient.post(this, Api.STUDENT_ACTIVE, map, new CallBack<StudentRegisterBean>() {
                @Override
                public void onSuccess(StudentRegisterBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        ToastUtil.showShortToast(RegisterActivity.this, result.getMsg());
                        PreferencesUtils.putSharePre(RegisterActivity.this,Const.LOGIN_STATUS,true);
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        finish();
                    } else {
                        ToastUtil.showShortToast(RegisterActivity.this, result.getMsg());
                    }

                }


            });
        }
    }

    /**
     * 校验
     * @return
     */
    private boolean check() {
        if (!checkPhone(etAccount.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
            ToastUtil.showShortToast(RegisterActivity.this, "请输入短信验证码");
            return false;
        }
        if (!flag) {
            ToastUtil.showShortToast(RegisterActivity.this, "请勾选用户相关协议");
            return false;
        }
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
            return false;
        }
        return true;
    }

    /**
     * 发送请求判断手机号码的有效性及获取验证码
     */
    private void getCode() {
        String params = "?mobile=" + etAccount.getText().toString().trim() + "&flag=" + String.valueOf(loginId);
        HttpClient.get(this, Api.GET_CODE + params, new CallBack<CodeBean>() {
            @Override
            public void onSuccess(CodeBean result) {
                if (result.getStatus() == 200) {
                    //发送验证码按钮开始倒计时
                    TimeCountUtil time = new TimeCountUtil(RegisterActivity.this, 60000, 1000, btCode);
                    time.start();
                } else {
                    ToastUtil.showShortToast(RegisterActivity.this, result.getMsg());
                }
            }

        });

    }


    /**
     * 监听短信的验证码拦截
     * @param smsEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SmsEvent smsEvent) {
        if (smsEvent.getSmsContent() != null) {
            //设置Edittext获取焦点
            etCode.setFocusable(true);
            etCode.setText(smsEvent.getSmsContent());
            etCode.setSelection(smsEvent.getSmsContent().length());

        }

    }

    /**
     * 检验手机号码的有效性
     * @param phone
     * @return
     */
    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShortToast(RegisterActivity.this, "请输入手机号码");
            return false;
        }
        String regex = "^[1][3-8][0-9]{9}$";
        if (!phone.matches(regex)) {
            ToastUtil.showShortToast(RegisterActivity.this, "请输入有效的手机号码");
            return false;
        }
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
            return false;
        }
        return true;
    }



    /**
     * Edittext控件的焦点变化的监听器
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_account:
                //账号
                if (!hasFocus) {
                    checkPhone(etAccount.getText().toString().trim());
                }
                break;
            case R.id.et_code:
                if (!hasFocus) {
                    if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                        ToastUtil.showShortToast(RegisterActivity.this, "请输入短信验证码");
                    }
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }


}

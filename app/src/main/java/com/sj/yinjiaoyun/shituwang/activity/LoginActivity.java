package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.PersonalBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    public static final String PHONE="pnone";
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    //标示登录省份(1是学生2是老师)
    private int loginId=0;
    //登录的api地址
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        loginId= PreferencesUtils.getSharePreInt(MyApplication.getContext(), Const.LOGINID);
        Logger.d("LoginActivity:默认身份："+loginId);
        if(loginId==2){
            tvRank.setText("我是师父，我要找学生");
            url= Api.TEACHER_LOGIN;
        }else  if(loginId==1){
            tvRank.setText("我是学生，我要找师父");
            url= Api.STUDENT_LOGIN;
        }
        etAccount.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //网络识别
        ToastUtil.getNetworkSatte(MyApplication.getContext());
    }

    @OnClick({R.id.bt_login, R.id.tv_switch, R.id.tv_active,R.id.tv_forget_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            //登录成功则跳转主界面
            case R.id.bt_login:
                login();
                break;
            //切换身份
            case R.id.tv_switch:
                startActivity(new Intent(LoginActivity.this,SelectiveIdentityActivity.class));
                finish();
                break;
            //注册界面
            case R.id.tv_active:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            //忘记密码
            case R.id.tv_forget_pwd:
                Intent intent=new Intent(LoginActivity.this,ForgetPwdActivity.class);
                intent.putExtra(PHONE,etAccount.getText().toString().trim());
                startActivity(intent);
                break;
        }

    }

    /**
     * 登录
     */
    private void login() {
        if(!TextUtils.isEmpty(etAccount.getText().toString().trim())){
               if(!TextUtils.isEmpty(etPassword.getText().toString().trim())){
                    GetRequest();
               }else{
                   ToastUtil.showShortToast(LoginActivity.this,"请输入密码");
               }
        }else{
            ToastUtil.showShortToast(LoginActivity.this,"请输入账号");
        }
    }

    /**
     * 发送网络请求登录
     */
    private void GetRequest() {
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        map.put("mobile",etAccount.getText().toString().trim());
        map.put("password",etPassword.getText().toString().trim());
        map.put("platform ","2");
        HttpClient.post(this, url, map, new CallBack<PersonalBean>() {
            @Override
            public void onSuccess(PersonalBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200 || result.getStatus()==201){
                    //登录成功之后返回的id
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.TOKEN,result.getData().getToken());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.ID,result.getData().getId());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.PWD,result.getData().getPassword());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.PASSWORD,etPassword.getText().toString().trim());
                    //真实姓名
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.NAME,result.getData().getRealName());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.UUID,result.getData().getUuid());
                    //用户图片
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.USERiMG,result.getData().getHead());
                    //手机号
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.PHONE,result.getData().getMobile());
                    //微信
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.WEIXIN,result.getData().getWeChat());
                    //邮箱
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.EMAIL,result.getData().getEmail());
                    //个人画像的字段值存储
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.CITIZENSHIP,result.getData().getCitizenship());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.MORALTRAIT,result.getData().getMoralTrait());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.TASTE,result.getData().getTaste());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.STUDY,result.getData().getStudy());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.LOGICAL_MATHE,result.getData().getLogicalMathe());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.SELF_COGNITIVE,result.getData().getSelfCognitive());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.SUBJECT_ATTAINMENT,result.getData().getSubjectAttainment());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.INTERACTION,result.getData().getInteraction());
                    PreferencesUtils.putSharePre(LoginActivity.this,Const.SPACE,result.getData().getSpace());
                    //偏好设置存储登录状态
                    if(result.getStatus()==200){
                        PreferencesUtils.putSharePre(LoginActivity.this,Const.LOGIN_STATUS,true);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        RegisterActivity.openActivity(LoginActivity.this,etAccount.getText().toString());
                    }
                    finish();
                }else{
                    ToastUtil.showShortToast(LoginActivity.this,result.getMsg());
                }
            }


        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_account:
                if(!hasFocus){
                    if(TextUtils.isEmpty(etAccount.getText().toString().trim())){
                        ToastUtil.showShortToast(LoginActivity.this,"请输入账号");
                    }
                }
                break;
            case R.id.et_password:
                if(!hasFocus){
                    if(TextUtils.isEmpty(etPassword.getText().toString().trim())){
                        ToastUtil.showShortToast(LoginActivity.this,"请输入密码");
                    }
                }
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

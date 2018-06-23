package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PopEmail;
import com.sj.yinjiaoyun.shituwang.utils.PopPhone;
import com.sj.yinjiaoyun.shituwang.utils.PopWeixin;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账号设置
 */
public class AccountSetActivity extends AppCompatActivity {


    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rl_account_set)
    RelativeLayout rlAccountSet;
    //手机号
    private String phone;
    //微信
    private String weixin;
    //邮箱
    private String email;
    private PopPhone popPhone;
    private PopWeixin popWeixin;
    private PopEmail popEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        popPhone = new PopPhone(AccountSetActivity.this);
        popWeixin=new PopWeixin(AccountSetActivity.this);
        popEmail=new PopEmail(AccountSetActivity.this);
        phone = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.PHONE);
        weixin = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.WEIXIN);
        email = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.EMAIL);
        Logger.d("电话：" + phone + ",微信：" + weixin + ",邮箱=" + email);
        tvPhone.setText(phone);
        tvWeixin.setText(weixin);
        tvEmail.setText(email);
    }


    @OnClick({R.id.rl_back, R.id.rl_phone_set, R.id.rl_weixin_set, R.id.rl_email_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_phone_set:
                startActivity(new Intent(AccountSetActivity.this,JSChangePhoneActivity.class));
//                 phone = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.PHONE);
//                 popPhone.pop(rlAccountSet, phone, new PhoneCallBack() {
//                     @Override
//                     public void setContent(String content) {
//                         Logger.d("电话：" + phone + ",微信：" + weixin + ",邮箱=" + email);
//                         tvPhone.setText(content);
//                     }
//                 });
                break;
            case R.id.rl_weixin_set:
                weixin = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.WEIXIN);
                popWeixin.pop(rlAccountSet, weixin, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        Logger.d("电话：" + phone + ",微信：" + weixin + ",邮箱=" + email);
                        tvWeixin.setText(content);
                    }
                });
                break;
            case R.id.rl_email_set:
                email = PreferencesUtils.getSharePreStr(AccountSetActivity.this, Const.EMAIL);
                popEmail.pop(rlAccountSet, email, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        Logger.d("电话：" + phone + ",微信：" + weixin + ",邮箱=" + email);
                        tvEmail.setText(content);
                    }
                });
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

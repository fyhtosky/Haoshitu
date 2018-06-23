package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份选择界面
 */
public class SelectiveIdentityActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb_manster)
    RadioButton rbManster;
    @BindView(R.id.rb_student)
    RadioButton rbStudent;
    //获取登录的省份
    private int loginId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_identity);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        loginId=PreferencesUtils.getSharePreInt(SelectiveIdentityActivity.this,Const.LOGINID);
        Logger.d("SelectiveIdentityActivity:默认身份："+loginId);
        if(loginId==2){
            rbStudent.setChecked(true);
        }else  if(loginId==1){
            rbManster.setChecked(true);
        }else {
            rbManster.setChecked(true);
        }
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.iv_master, R.id.iv_student, R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_master:
                radioGroup.check(R.id.rb_manster);
                break;
            case R.id.iv_student:
                radioGroup.check(R.id.rb_student);
                break;
            case R.id.bt_next:
                //判断选择的是老师还是学生
               if(rbManster.isChecked()){
                   PreferencesUtils.putSharePre(SelectiveIdentityActivity.this, Const.LOGINID,2);
                }else if(rbStudent.isChecked()){
                   PreferencesUtils.putSharePre(SelectiveIdentityActivity.this, Const.LOGINID,1);
               }
                startActivity(new Intent(SelectiveIdentityActivity.this,LoginActivity.class));
                finish();
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

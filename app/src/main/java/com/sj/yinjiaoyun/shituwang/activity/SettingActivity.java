package com.sj.yinjiaoyun.shituwang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.utils.AppManager;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DateclearManager;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends AppCompatActivity {
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        loginId = PreferencesUtils.getSharePreInt(SettingActivity.this, Const.LOGINID);
    }

    @OnClick({R.id.rl_back, R.id.rl_account_set, R.id.rl_change_pwd, R.id.rl_about_my, R.id.rl_clear_cache, R.id.bt_login_off})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //账号设置
            case R.id.rl_account_set:
                startActivity(new Intent(SettingActivity.this,AccountSetActivity.class));
                break;
            //修改密码
            case R.id.rl_change_pwd:
//               startActivity(new Intent(SettingActivity.this,ChangePwdActivity.class));
                startActivity(new Intent(SettingActivity.this,JSChangePwdActivity.class));
                break;
            //关于我们
            case R.id.rl_about_my:
                startActivity(new Intent(SettingActivity.this,AboutActivity.class));
                break;
            //清除缓存
            case R.id.rl_clear_cache:
                claenCache();
                break;
            //退出登录
            case R.id.bt_login_off:
                //偏好设置存储登录状态
                PreferencesUtils.putSharePre(SettingActivity.this, Const.LOGIN_STATUS,false);
                PreferencesUtils.putSharePre(SettingActivity.this,Const.ISSHOW,false);
                //存储身份标识
                PreferencesUtils.putSharePre(SettingActivity.this, Const.LOGINID,loginId);
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
    //清除缓存
    private void claenCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清除缓存");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                DateclearManager.clearAllCache(SettingActivity.this);
                try {
                    String appSize= DateclearManager.getTotalCacheSize(SettingActivity.this);
//                    qingchu.setValuesForMark(appSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

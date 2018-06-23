package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.squareup.leakcanary.LeakCanary;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 启动页
 */
public class BootActivity extends AppCompatActivity {

    @BindView(R.id.iv_boot)
    ImageView ivBoot;
    private static Handler handler=new Handler();
    //登录状态
    private boolean loginStatus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        ButterKnife.bind(this);
        loginStatus=PreferencesUtils.getSharePreBoolean(BootActivity.this,Const.LOGIN_STATUS);
        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        ivBoot.startAnimation(animation);
        //延时启动
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //是否显示引导界面
                if(!PreferencesUtils.getSharePreBoolean(BootActivity.this, Const.ISFIRST)){
                    Intent intent=new Intent(BootActivity.this,GuideActivity.class);
                    startActivity(intent);
                    PreferencesUtils.putSharePre(BootActivity.this,Const.ISFIRST,true);
                }else{
                    if(loginStatus){//自动登录
                        startActivity(new Intent(BootActivity.this, MainActivity.class));
                    }else{
                        startActivity(new Intent(BootActivity.this,SelectiveIdentityActivity.class));
                    }

                }
                finish();
            }


        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

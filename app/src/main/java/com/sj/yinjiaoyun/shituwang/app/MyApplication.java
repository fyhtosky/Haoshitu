package com.sj.yinjiaoyun.shituwang.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.sj.yinjiaoyun.shituwang.bean.AllCodesBean;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.utils.AppManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.SophixManager;

import org.jivesoftware.smack.XMPPConnection;

import java.util.Map;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/13.
 */
public class MyApplication extends MultiDexApplication {
    private static Context context;
    private static MyApplication Instances;
    //字典表数据缓存
    public static Map<String ,Children> map;
    //字典表的解析类
    public static AllCodesBean allCodesBean;
    public static XMPPConnection xmppConnection;
    //内存泄露
    private RefWatcher refWatcher;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
        refWatcher= setupLeakCanary();
        context = getApplicationContext();
        Instances=this;
        //日志管理初始化
        try {
            Settings settings= Logger.init("iiiiii");
            settings.logLevel(LogLevel.FULL)//  显示全部日志，LogLevel.NONE不显示日志，默认是Full
                    .methodCount(5)//  方法栈打印的个数，默认是2
                    .methodOffset(0)//  设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                    .hideThreadInfo(); //  隐藏线程信息

        /**
         * 实现 ActivityLifecycleCallbacks 并注册给 Application
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //创建Activity进行压栈操作
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                 if(null==AppManager.activityStack && AppManager.activityStack.isEmpty()){
                     return;
                 }
                 if(AppManager.activityStack.contains(activity)){
                     AppManager.getAppManager().finishActivity(activity);
                 }
            }
        });
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }


    public static RefWatcher   getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static MyApplication getInstances() {
        return Instances;
    }
    public static Context getContext(){
        return context;
    }
    public static XMPPConnection getXmppConnection() {
        return xmppConnection;
    }
}

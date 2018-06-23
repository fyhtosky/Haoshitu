package com.sj.yinjiaoyun.shituwang.callback;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/13.
 * Activity的生命周期的操作的界面
 */
public interface ActivityLifecycleCallbacks {
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
    void onActivityStarted(Activity activity);
    void onActivityResumed(Activity activity);
    void onActivityPaused(Activity activity);
    void onActivityStopped(Activity activity);
    void onActivitySaveInstanceState(Activity activity, Bundle outState);
    void onActivityDestroyed(Activity activity);
}

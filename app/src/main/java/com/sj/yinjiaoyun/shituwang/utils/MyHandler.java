package com.sj.yinjiaoyun.shituwang.utils;

import android.os.Handler;
import android.os.Message;

import com.sj.yinjiaoyun.shituwang.activity.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MyHandler extends Handler {
    private WeakReference<MainActivity>mActivity;

    public MyHandler(MainActivity activity) {
        mActivity =new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity mainActivity= mActivity.get();
       switch (msg.what){
           case 0:
               if(mainActivity!=null){
                   mainActivity.toastShort();
               }
               break;
       }
       super.handleMessage(msg);
    }
}

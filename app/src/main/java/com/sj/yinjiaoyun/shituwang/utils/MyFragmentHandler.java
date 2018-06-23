package com.sj.yinjiaoyun.shituwang.utils;

import android.os.Handler;
import android.os.Message;

import com.sj.yinjiaoyun.shituwang.fragment.StudentRecommendFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/1/4.
 */

public  class MyFragmentHandler extends Handler  {
    private WeakReference<StudentRecommendFragment> mActivity;


    public MyFragmentHandler(StudentRecommendFragment target) {
        this.mActivity = new WeakReference<>(target);

    }

    @Override
    public void handleMessage(Message msg) {
        StudentRecommendFragment fragment=mActivity.get();
       switch (msg.what){
           case 1:
               if(fragment!=null){
                   fragment.getDictionary();
               }
               break;
       }
        super.handleMessage(msg);
    }


}

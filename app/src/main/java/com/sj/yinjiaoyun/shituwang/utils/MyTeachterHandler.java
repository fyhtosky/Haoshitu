package com.sj.yinjiaoyun.shituwang.utils;

import android.os.Handler;
import android.os.Message;

import com.sj.yinjiaoyun.shituwang.fragment.TeacherRecommendFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/1/4.
 */

public  class MyTeachterHandler extends Handler  {
    private WeakReference<TeacherRecommendFragment> mActivity;


    public MyTeachterHandler(TeacherRecommendFragment target) {
        this.mActivity = new WeakReference<>(target);

    }

    @Override
    public void handleMessage(Message msg) {
        TeacherRecommendFragment fragment=mActivity.get();
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

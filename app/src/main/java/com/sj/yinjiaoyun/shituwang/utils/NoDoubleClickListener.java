package com.sj.yinjiaoyun.shituwang.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 作者：Administrator on 2018/4/23 18:22
 * <p>
 * 邮箱：xjs250@163.com
 */
public  abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }
    public abstract void onNoDoubleClick(View v);
}

package com.sj.yinjiaoyun.shituwang.utils;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.callback.NetWorkCallBack;


/**
 * Created by Administrator on 2018/1/2.
 */

public class PopErrorNetWork {
    private PopupWindow popupWindow;
    private Activity activity;

    public PopErrorNetWork(Activity activity) {
        this.activity = activity;
    }
    public void pop( @NonNull final View view, @NonNull final NetWorkCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.net_work_error,null);
        Button btReload= (Button) pView.findViewById(R.id.bt_reload);
        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //断网了
                callBack.isNetInvalid(true);
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }
}

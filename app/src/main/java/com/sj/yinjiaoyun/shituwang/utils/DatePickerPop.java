package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;

import java.util.Calendar;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/18.
 */
public class DatePickerPop {
    private PopupWindow popupWindow;
    private Activity activity;
    private String text;
    private int year = 0;
    private int monthOfYear = 0;
    private int dayOfMonth = 0;
    public DatePickerPop(Activity activity) {
        this.activity = activity;

    }
    public void show(final View view,  final PhoneCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        Calendar c=Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        // 获取当前月份
        monthOfYear = c.get(Calendar.MONTH);
        // 获取当前月份的天数
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_date_picker,null);
        final TextView tvCancel= (TextView) pView.findViewById(R.id.tv_cancel);
        final TextView tvConfirm= (TextView) pView.findViewById(R.id.tv_confirm);
        DatePicker datePicker= (DatePicker) pView.findViewById(R.id.datePicker);
        text=year+"-"+ (monthOfYear+1)+"-"+dayOfMonth;
        //直接创建一个DatePickerDialog对话框实例，并显示出来
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                text=year+"-"+(monthOfYear+1)
                        +"-"+dayOfMonth;
            }
        });
        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //确认
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text!=null){
                    callBack.setContent(text);
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        closePopupWindow(0.6f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closePopupWindow(1f);
            }
        });
    }

    private void closePopupWindow(float alpaha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpaha;
        if (alpaha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }


}

package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/29.
 */
public class PopName {
    private PopupWindow popupWindow;
    private Activity activity;


    public PopName(Activity activity) {
        this.activity = activity;
    }
    public void pop(final View view, final String title, String name, final PhoneCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_name,null);
        final EditText etName= (EditText) pView.findViewById(R.id.et_name);
        final TextView tvHint= (TextView) pView.findViewById(R.id.tv_name_hint);
        TextView tvTitle= (TextView) pView.findViewById(R.id.tv_title);
        Button btCancel= (Button) pView.findViewById(R.id.bt_name_cancel);
        Button btConfirm= (Button) pView.findViewById(R.id.bt_name_comfirm);
        //显示标题
        tvTitle.setText(title);
        etName.setHint(title);
        //显示默认值
        etName.setText(name);
        etName.setSelection(etName.getText().toString().trim().length());
        //取消
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //确认
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etName.getText().toString().trim())){
                        tvHint.setVisibility(View.GONE);
                        callBack.setContent(etName.getText().toString().trim());
                        popupWindow.dismiss();
                }else{
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText(title);
                    tvHint.setTextColor(Color.parseColor("#F14347"));
                }
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        closePopupWindow(0.6f);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
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

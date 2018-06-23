package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.MyRecordActivity;
import com.sj.yinjiaoyun.shituwang.activity.TeachterRecordActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.view.CreditScoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/19.
 * 个人画像的对画框
 */
public class PopPortrait {
    private PopupWindow popupWindow;
    private Activity activity;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    private ArrayList<String>titleList=new ArrayList<>();
    private List<Double>data=new ArrayList<>();
    public PopPortrait(Activity activity) {
        this.activity = activity;
        loginId = PreferencesUtils.getSharePreInt(activity, Const.LOGINID);
        init();
    }

    private void init() {
        titleList.clear();
        titleList.add("公民素养");
        titleList.add("道德品质");
        titleList.add("审美与表现");
        titleList.add("学习与兴趣");
        titleList.add("数理智能");
        titleList.add("自我认知智能");
        titleList.add("学科素养");
        titleList.add("人际交往");
        titleList.add("空间智能");
        data.clear();
        double c=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.CITIZENSHIP);
        double m=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.MORALTRAIT);
        double t=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.TASTE);
        double s=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.STUDY);
        double l=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.LOGICAL_MATHE);
        double s_c=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.SELF_COGNITIVE);
        double s_a=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.SUBJECT_ATTAINMENT);
        double i=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.INTERACTION);
        double s_e=PreferencesUtils.getSharePreInt(MyApplication.getContext(),Const.SPACE);
        data.add(c);
        data.add(m);
        data.add(t);
        data.add(s);
        data.add(l);
        data.add(s_c);
        data.add(s_a);
        data.add(i);
        data.add(s_e);
        Logger.d(data.toString());
    }

    public void pop(final View view){
        if (popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }

        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(MyApplication.getContext())
                .inflate(R.layout.pop_protayal,null);
        CreditScoreView creditScoreView=  pView.findViewById(R.id.credit_view);
        creditScoreView.setPortrait(true);
        //设置点的数量
        creditScoreView.setCount(titleList.size());
        //设置标题的数据源
        creditScoreView.setTitles(titleList);
        //设置各维度的值
        creditScoreView.setData(data);
        TextPaint paint=new TextPaint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(22);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        //设置绘制字体颜色的画笔
        creditScoreView.setTextPaint(paint);
        //设置划线画笔的颜色
        Paint  mainPaint = new Paint();
        mainPaint.setColor(Color.WHITE);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1);
        mainPaint.setStyle(Paint.Style.STROKE);
        creditScoreView.setMainPaint(mainPaint);
        //设置绘制区域画笔的颜色
        Paint valuePaint=new Paint();
        valuePaint.setColor(Color.WHITE);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);
        creditScoreView.setValuePaint(valuePaint);
        //设置分数
        TextPaint gardePaint=new TextPaint();
        gardePaint.setColor(Color.WHITE);
        gardePaint.setAntiAlias(true);
        gardePaint.setTextSize(22);
        gardePaint.setStrokeWidth(1);
        gardePaint.setStyle(Paint.Style.STROKE);
        creditScoreView.setGardePaint(gardePaint);
        Button btInfo= pView.findViewById(R.id.bt_info);
        //点击完善信息
        btInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //默认显示主页
                if (loginId == 1) {//学生登录
                    activity.startActivity(new Intent(activity, MyRecordActivity.class));
                } else if (loginId == 2) {//老师登录
                    activity.startActivity(new Intent(activity, TeachterRecordActivity.class));
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        closePopupWindow(0.4f);
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

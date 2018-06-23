package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.callback.ChildrenCallBack;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.view.DirviderDecoration;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/28.
 */
public class PopWorkState {
    private PopupWindow popupWindow;
    private Activity activity;

    public PopWorkState(Activity activity) {
        this.activity = activity;
    }
    public void pop(final View view,List<Children>list,final ChildrenCallBack callBack){
        if (popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_work_state,null);
        RecyclerView rv = (RecyclerView) pView.findViewById(R.id.rv);
        TextView tvCancel= (TextView) pView.findViewById(R.id.tv_pop_work_state_cancel);
        rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false));
        rv.addItemDecoration(new DirviderDecoration());
        rv.setAdapter(new BaseQuickAdapter<Children>(R.layout.item_pop_work_state,list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final Children dataBean) {
                final TextView tv=baseViewHolder.getView(R.id.tv_state);
                tv.setText(dataBean.getName());

                baseViewHolder.setOnClickListener(R.id.rl_item_pop_work_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        callBack.setChildren(dataBean);
                    }
                });
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAsDropDown(view,0, DensityUtils.dp2px(context,-3));
        closePopupWindow(0.6f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closePopupWindow(1f);
            }
        });
    }
    public void popString(final View view,List<String>list,final PhoneCallBack callBack){
        if (popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_work_state,null);
        RecyclerView rv = (RecyclerView) pView.findViewById(R.id.rv);
        TextView tvCancel= (TextView) pView.findViewById(R.id.tv_pop_work_state_cancel);
        rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false));
        rv.addItemDecoration(new DirviderDecoration());
        rv.setAdapter(new BaseQuickAdapter<String>(R.layout.item_pop_work_state,list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final String dataBean) {
                final TextView tv=baseViewHolder.getView(R.id.tv_state);
                tv.setText(dataBean);

                baseViewHolder.setOnClickListener(R.id.rl_item_pop_work_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        callBack.setContent(dataBean);
                    }
                });
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAsDropDown(view,0, DensityUtils.dp2px(context,-3));
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


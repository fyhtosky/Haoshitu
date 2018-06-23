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
import com.sj.yinjiaoyun.shituwang.view.DirviderDecoration;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/1.
 * 行业的对话框
 */
public class PopIndustry {
    private PopupWindow popupWindow;
    private Activity activity;

    public PopIndustry(Activity activity) {
        this.activity = activity;
    }
    public void pop(final View view, List<Children> list, final ChildrenCallBack callBack){
        if (popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_industry,null);
        RecyclerView rv = (RecyclerView) pView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false));
        rv.addItemDecoration(new DirviderDecoration());
        rv.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item,list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final Children dataBean) {
                final TextView tv=baseViewHolder.getView(R.id.tv_industry);
                tv.setText(dataBean.getName());

                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        callBack.setChildren(dataBean);
                    }
                });
            }
        });

        popupWindow.setContentView(pView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAsDropDown(view, 0,0);
        closePopupWindow(0.6f);
        popupWindow.showAtLocation(view,Gravity.LEFT, DensityUtils.dp2px(activity,200),DensityUtils.dp2px(activity,-50));
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

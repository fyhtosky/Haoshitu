package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
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
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/18.
 */
public class PopReason {
    private PopupWindow popupWindow;
    private Activity activity;

    public PopReason(Activity activity) {
        this.activity = activity;
    }
    public void show(final View view, final List<String> list, final PhoneCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_reason,null);
        final TagFlowLayout flowLayout= (TagFlowLayout) pView.findViewById(R.id.tag_trait);
        final EditText etReason= (EditText) pView.findViewById(R.id.et_reason);
        final TextView tvHint= (TextView) pView.findViewById(R.id.tv_hint);
        Button btCancel= (Button) pView.findViewById(R.id.bt_cancel);
        Button btconfirm= (Button) pView.findViewById(R.id.bt_comfirm);
        flowLayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(activity)
                        .inflate(R.layout.item_reason, parent, false);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(s);
                return view;
            }

        });
        //给标签布局添加点击事件
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                etReason.setText(list.get(position));
                etReason.setSelection(etReason.getText().toString().trim().length());
                return false;
            }
        });
        //取消
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etReason.getText().toString().trim())){
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText("原因不能为空");
                }else{
                    tvHint.setVisibility(View.GONE);
                    callBack.setContent(etReason.getText().toString().trim());
                    popupWindow.dismiss();
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

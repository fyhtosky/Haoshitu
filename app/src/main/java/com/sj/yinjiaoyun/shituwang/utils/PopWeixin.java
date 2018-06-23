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

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;

import java.util.HashMap;

import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/26.
 */
public class PopWeixin {
    private PopupWindow popupWindow;
    private Activity activity;
    //    1是学生2是老师
    private int loginId = 0;
    //登录的id
    private int id;

    public PopWeixin(Activity activity) {
        this.activity = activity;
    }
    public void pop(final View view, String weixin, final PhoneCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_weixin,null);
        final EditText etWeixin= (EditText) pView.findViewById(R.id.et_weixin);
        final TextView tvHint= (TextView) pView.findViewById(R.id.tv_weixin_hint);
        Button btCancel= (Button) pView.findViewById(R.id.bt_weixin_cancel);
        Button btConfirm= (Button) pView.findViewById(R.id.bt_weixin_comfirm);
        etWeixin.setText(weixin);
        etWeixin.setSelection(etWeixin.getText().toString().trim().length());
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
                   if(!TextUtils.isEmpty(etWeixin.getText().toString().trim())){
                       tvHint.setVisibility(View.GONE);
                       if(etWeixin.getText().toString().trim().length()<20){
                           changeWeixin(etWeixin.getText().toString().trim(),tvHint,callBack);
                       }else {
                           tvHint.setVisibility(View.VISIBLE);
                           tvHint.setText("微信的长度为20个字符以内");
                           tvHint.setTextColor(Color.parseColor("#F14347"));
                       }

                   }else{
                       tvHint.setVisibility(View.VISIBLE);
                       tvHint.setText("输入微信号");
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
    /**
     * 发送请求修改微信
     * @param weixin
     * @param textView
     * @param callBack
     */
    private void  changeWeixin(final String weixin,final TextView textView, final PhoneCallBack callBack){
        loginId = PreferencesUtils.getSharePreInt(MyApplication.getContext(), Const.LOGINID);
        id=PreferencesUtils.getSharePreInt(MyApplication.getContext(), Const.ID);
        Logger.d("身份标示："+loginId+",标示id:"+id);
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        String url;
        HashMap<String,String> map=new HashMap<>();
        map.put("weChat",weixin);
        if(loginId==1){
            //学生
            url= Api.UPDATE_STUDENT_DETAIL;
            map.put("stuUserId",String.valueOf(id));
        }else{
            //老师
            url=Api.UPDATE_TEACHER_DETAIL;
            map.put("id",String.valueOf(id));
        }
        HttpClient.post(this, url, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    textView.setVisibility(View.GONE);
                    //更新本地
                    PreferencesUtils.putSharePre(MyApplication.getContext(),Const.WEIXIN,weixin);
                    ToastUtil.showShortToast(activity,result.getMsg());
                    popupWindow.dismiss();
                    callBack.setContent(weixin);
                }else{
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(result.getMsg());
                    textView.setTextColor(Color.parseColor("#F14347"));
                }
            }


        });
    }

}

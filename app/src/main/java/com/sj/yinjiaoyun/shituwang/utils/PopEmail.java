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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/26.
 */
public class PopEmail {
    private PopupWindow popupWindow;
    private Activity activity;
    //    1是学生2是老师
    private int loginId = 0;
    //登录的id
    private int id;

    public PopEmail(Activity activity) {
        this.activity = activity;
    }
    public void pop(final View view, final String email, final PhoneCallBack callBack) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        popupWindow = new PopupWindow(activity);
        final View pView = LayoutInflater.from(activity)
                .inflate(R.layout.pop_email,null);
        final EditText etEmail= (EditText) pView.findViewById(R.id.et_email);
        final TextView tvHint= (TextView) pView.findViewById(R.id.tv_email_hint);
        Button btCancel= (Button) pView.findViewById(R.id.bt_email_cancel);
        Button btConfirm= (Button) pView.findViewById(R.id.bt_email_comfirm);
        //显示默认值
        etEmail.setText(email);
        etEmail.setSelection(etEmail.getText().toString().trim().length());
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
                if(!TextUtils.isEmpty(etEmail.getText().toString().trim())){
                    tvHint.setVisibility(View.GONE);
                    if(isEmail(etEmail.getText().toString().trim())) {
                        changeWeixin(etEmail.getText().toString().trim(), tvHint, callBack);
                    }else {
                        tvHint.setVisibility(View.VISIBLE);
                        tvHint.setText("请输入有效的邮箱");
                        tvHint.setTextColor(Color.parseColor("#F14347"));
                    }

                }else{
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText("请输入邮箱");
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
     * @param email
     * @param textView
     * @param callBack
     */
    private void  changeWeixin(final String email,final TextView textView, final PhoneCallBack callBack){
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
        map.put("email",email);
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
                    PreferencesUtils.putSharePre(MyApplication.getContext(),Const.EMAIL,email);
                    ToastUtil.showShortToast(activity,result.getMsg());
                    popupWindow.dismiss();
                    callBack.setContent(email);
                }else{
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(result.getMsg());
                    textView.setTextColor(Color.parseColor("#F14347"));
                }
            }


        });
    }
    // 判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}

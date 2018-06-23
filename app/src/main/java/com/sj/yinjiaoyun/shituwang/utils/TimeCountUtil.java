package com.sj.yinjiaoyun.shituwang.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/15
 * 倒计时获取验证码
 */
public class TimeCountUtil extends CountDownTimer {

    private Activity mActivity;
    private Button btn;//按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个事，就把这个按钮传过来就可以了
    public TimeCountUtil(Activity mActivity, long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.btn =btn;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText(millisUntilFinished / 1000 + "秒后可重新发送");//设置倒计时时间
        btn.setPadding(5,5,5,5);//设置内间距
        btn.setTextColor(Color.WHITE);//设置字体颜色为白色

        //设置按钮为灰色，这时是不能点击的
        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        btn.setText(span);
//        btn.setText(RichTextUtil.fillColor(btn.getText().toString(),btn.getText().toString().split("秒")[0],Color.RED));

    }


    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setPadding(5,5,5,5);//设置内间距
        btn.setClickable(true);//重新获得点击
    }


}
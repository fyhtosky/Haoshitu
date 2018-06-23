package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议的界面
 */
public class AgreementActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        try {
            //设置打开的页面地址
//            webView.loadUrl("https://www.baidu.com/");
            webView.loadUrl("file:///android_asset/info.html");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
    @OnClick(R.id.agreement_back)
    public void onClick() {
        finish();
    }
}

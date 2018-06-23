package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.JsReturnBean;
import com.sj.yinjiaoyun.shituwang.jsforandroid.ForgetPwdObject;
import com.sj.yinjiaoyun.shituwang.jsforandroid.JSforAndroidListener;
import com.sj.yinjiaoyun.shituwang.utils.AppManager;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/10.
 */

public abstract class JSBaseActivity extends AppCompatActivity implements JSforAndroidListener {
    @BindView(R.id.webView)
    WebView webView;
    //标示登录省份(1是学生2是老师)
    protected int loginId = 0;
    //webView添加cookie
    protected String cookies ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jschange_phone);
        ButterKnife.bind(this);
        init();
    }


    /**
     * 加载指定url
     * @return
     */
    @NonNull
    protected abstract String getJsUrl() ;

    /**
     * 获取操作之后的处理
     * @param result
     */
    protected abstract void onResult(String result);

    /**
     * 布局初始化
     */
    protected  void init(){
        cookies=PreferencesUtils.getSharePreStr(JSBaseActivity.this,Const.TOKEN);
        loginId = PreferencesUtils.getSharePreInt(JSBaseActivity.this, Const.LOGINID);
        WebSettings webSettings = webView.getSettings();
        //是否支持js
        webSettings.setJavaScriptEnabled(true);
        //设置是否使用WebView推荐使用的窗口
        webSettings.setUseWideViewPort(true);
        //设置加载页面的模式
        webSettings.setLoadWithOverviewMode(true);
        //设置是否支持缩放
        webSettings.setSupportZoom(true);
        webView.addJavascriptInterface(new ForgetPwdObject(this), "iXueLingPaiJs");
        if(getJsUrl()!=null){
            syncCookie();
            webView.loadUrl(getJsUrl());
            webView.setWebViewClient(new WebViewClient() {
                // 在当前WebView中打开新连接，不跳转到系统浏览器
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.loadUrl(url);
                    return true;
                }

                // 重写此方法可以让webview处理https请求
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
//                super.onReceivedSslError(view, handler, error);
                }
            });
            //添加google自带的浏览器
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    Logger.d("当前网页的加载进度：" + webView.getProgress());
                    if (newProgress == 100) {
                        Logger.d("当前网页的url:" + webView.getUrl());
                        Logger.d("重定向之后的url:" + webView.getOriginalUrl());
                        Logger.d("当前网页的标题：" + webView.getTitle());
                        Logger.d("当前网页的加载进度：" + webView.getProgress());
                    }
                    super.onProgressChanged(view, newProgress);
                }

                @Override
                public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                    return super.onJsPrompt(view, url, message, defaultValue, result);
                }
            });

        }

    }

    /**
     * 设置webView cookie
     */
    protected  void syncCookie(){

    }
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        //恢复pauseTimers状态
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((RelativeLayout) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
            Logger.d("WebView销毁");
        }
            //内存泄露检测
            MyApplication.getRefWatcher(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getStatus(String status) {
       JsReturnBean jsReturnBean= new Gson().fromJson(status, JsReturnBean.class);
        Logger.d("js回调的结果："+status);
        switch (jsReturnBean.getStatus()){
            case 200:
               onResult(jsReturnBean.getData());
                break;
            case 401:
                //偏好设置存储登录状态
                PreferencesUtils.putSharePre(JSBaseActivity.this, Const.LOGIN_STATUS, false);
                PreferencesUtils.putSharePre(JSBaseActivity.this, Const.ISSHOW, false);
                //存储身份标识
                PreferencesUtils.putSharePre(JSBaseActivity.this, Const.LOGINID, loginId);
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(JSBaseActivity.this, LoginActivity.class));
                finish();
                break;
            case 500:
                ToastUtil.showShortToast(JSBaseActivity.this,jsReturnBean.getData());
                break;
        }
    }


}

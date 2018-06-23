package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

/**
 * 修改手机号
 */
public class JSChangePhoneActivity extends JSBaseActivity {

    private String BaseUrl = Api.TOKEN_CHANGE_PHONE;
//    private String url = "139.196.254.188";
    private String url = Api.TOKEN_HOST;
//    private String BaseUrl = "http://ixue.5xuexi.com/app/toUpdatePhonePage.action";
//    private String url = "ixue.5xuexi.com";


    @Override
    protected String getJsUrl() {
        return BaseUrl;
    }

    /**
     * 修改手机号成功之后存储手机号
     */
    @Override
    protected void onResult(String result) {
        //偏好设置存储登录状态
        PreferencesUtils.putSharePre(MyApplication.getContext(), Const.PHONE, result);
        finish();
    }

    @Override
    protected void syncCookie() {
        super.syncCookie();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            }
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除以前的cookie
            cookieManager.removeAllCookie();
            cookieManager.setCookie(url, "domain=" + url);
            cookieManager.setCookie(url, "path=/");
            cookieManager.setCookie(url, "ixuetoken=" + cookies);
            CookieSyncManager.getInstance().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.utils.AppManager;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

/**
 * 修改密码
 */
public class JSChangePwdActivity extends JSBaseActivity {

    private String BaseUrl = Api.TOKEN_CHANGE_PWD;
//    private String url = "139.196.254.188";
    private String url = Api.TOKEN_HOST;
//     private String BaseUrl = "http://ixue.5xuexi.com/app/toUpdatePasswordPage.action";
//    private String url = "ixue.5xuexi.com";

    @Override
    protected String getJsUrl() {
        return BaseUrl;
    }

    /**
     * 修改密码成功之后重新登录
     */
    @Override
    protected void onResult(String result) {
        //偏好设置存储登录状态
        PreferencesUtils.putSharePre(JSChangePwdActivity.this, Const.LOGIN_STATUS, false);
        PreferencesUtils.putSharePre(JSChangePwdActivity.this, Const.ISSHOW, false);
        //存储身份标识
        PreferencesUtils.putSharePre(JSChangePwdActivity.this, Const.LOGINID, loginId);
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(JSChangePwdActivity.this, LoginActivity.class));
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

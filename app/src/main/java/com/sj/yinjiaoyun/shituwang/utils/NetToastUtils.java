package com.sj.yinjiaoyun.shituwang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/2.
 * Toast提示的工具类及网络状态提示
 */

public class NetToastUtils {
    public static void showShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void getNetworkSatte(Context mContext) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        assert cm != null;
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        // 遍历每一个对象
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return;
            }
        }
        showShortToast(mContext,"网络异常，请稍后再试吧。");

    }
}

package com.sj.yinjiaoyun.shituwang.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.sj.yinjiaoyun.shituwang.activity.LoginActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.xmppmanager.CheckConnectionListener;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/24.
 */
public class DialogUtils {
    public static AlertDialog dialog;
    public static void showNormalDialog(final Context context, final CheckConnectionListener checkConnectionListener){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("你的账号已在别处登录，请确认是否是本人登录");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("重新登录",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MyApplication.xmppConnection !=null){
                            MyApplication.xmppConnection.removeConnectionListener(checkConnectionListener);
                            MyApplication.xmppConnection.disconnect();
                            MyApplication.xmppConnection=null;

                        }
                        //设置登录状态
                        PreferencesUtils.putSharePre(context, Const.LOGIN_STATUS,false);
                        context.startActivity(new Intent(context,LoginActivity.class));
                        AppManager.getAppManager().finishAllActivity();
                    }
                });
        normalDialog.setNegativeButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog=normalDialog.create();
        // 显示
        dialog.show();

    }
}

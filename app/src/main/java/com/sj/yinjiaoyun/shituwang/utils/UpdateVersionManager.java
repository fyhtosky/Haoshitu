package com.sj.yinjiaoyun.shituwang.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.VersionCheckBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/18.
 * 版本更新的管理类
 */
public class UpdateVersionManager {
    private Context mContext;
    //提示语
    private String updateMsg = "有最新的软件包哦，亲快下载吧~";
    //提示的对话框
    private Dialog noticeDialog;
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    //下载的对话框
    private Dialog downloadDialog;
    private int progress;
    private Thread downLoadThread;
    private boolean interceptFlag = false;
    //返回的安装包url
    private String apkUrl;
    /* 下载包安装路径 */
    private static final String savePath =  MyApplication.getContext().getExternalCacheDir().toString()+"/";

    private static final String saveFileName = savePath + "haoshitu.apk";
    private Handler mHandler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //下载完成时自动安装
                case 0:
                      installApk();
                    break;
                //正在下载
                case 1:
                    mProgress.setProgress(progress);
                    break;
            }
        }
    };

    public UpdateVersionManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 检查app的版本号
     */
    public  void checkHttpVersion(){
        HttpClient.get(this, Api.UPDATE_VERSION, new CallBack<VersionCheckBean>() {
            @Override
            public void onSuccess(VersionCheckBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    apkUrl=result.getMap().getUrl();
                    //版本号大于app当前的版本说明有新版本
                    int htpcode=Integer.parseInt(result.getMap().getVersion());
                    if(htpcode>getNewVersion(mContext)){
                        //显示提示版本更新的对话框
                       showNoticeDialog();
                }
                }

            }

        });
    }

    /**
     * 获取当前app的版本号
     * @param context
     * @return
     */
    public int getNewVersion(Context context){
        int versionCode=0;
        try {
            versionCode=context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 提示框  下载 或者 以后再说
     */
    private void showNoticeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 提示框（下载完成后） 版本是否更新
     */
    private void showDownloadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progress);
        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }
    /**
     * 另开线程 下载apk
     */
    private void downloadApk(){
        Logger.d("downloadApk");
        if(apkUrl!=null && !TextUtils.isEmpty(apkUrl)){
            downLoadThread = new Thread(mDownApkRunnable);
            downLoadThread.start();
        }

    }

    /**
     * 下载安装包
     */
    private Runnable mDownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                Logger.d("run: +apkUrl");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(1);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(0);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    };

    /**
     * 安装apk
     * @param
     */
    private void installApk(){
        Logger.d("安装apk");
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        //版本更新之后出现引导页
        PreferencesUtils.putSharePre(mContext, Const.ISFIRST,false);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            // Android7.0及以上版本
            Uri uri = FileProvider.getUriForFile(mContext, "com.sj.yinjiaoyun.shitu.fileProvider", apkFile);
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }else {
            // Android7.0以下版本
            intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);

    }
}

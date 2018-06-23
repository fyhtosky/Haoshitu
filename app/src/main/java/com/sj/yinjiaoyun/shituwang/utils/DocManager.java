package com.sj.yinjiaoyun.shituwang.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.AgrementBean;
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
 * Created by ${沈军 961784535@qq.com} on 2017/9/14.
 * 下载文档内调wps显示文档
 */
public class DocManager {
    private Activity context;
//    private String savePath="/sdcard/haoshitu/";
    private String  savePath= MyApplication.getContext().getExternalCacheDir().toString()+"/";
    private String saveFileName;
    private String docUrl;
    private Thread downLoadThread;
    private boolean isrun=false;
    //标示登录省份(1是学生2是老师)
    private int loginId=0;
    //文档的路径
    private  File DocFile;
    private  Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    call();
                    break;
            }
        }
    };

    public DocManager(Activity context) {
        this.context = context;

    }

    /**
     * 发送请求获取文档的url和文档的名称
     */
    public void getAgrement(int schoolId,int companyId) {
        loginId= PreferencesUtils.getSharePreInt(context, Const.LOGINID);
        String searchType="";
        if(loginId==2){
            searchType="0";
        }else  if(loginId==1){
           searchType="1";
        }
        String params="?searchType="+searchType+"&schoolId="+String.valueOf(schoolId)+"&companyId="+String.valueOf(companyId);
        HttpClient.get(this, Api.FIND_AGREEMENT + params, new CallBack<AgrementBean>() {
            @Override
            public void onSuccess(AgrementBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
//                    saveFileName = savePath + (result.getData().getAgreementName()==null ?"":result.getData().getAgreementName());
                    docUrl=result.getData().getAgreement().replace("\n","");
                    saveFileName=savePath+FileUtils.getFileName(docUrl);
                    File file=new File(saveFileName);
                    if(file.exists()){
                        call();
                    }else {
                        downloaddoc();
                    }
                }
            }


        });
    }

    private void downloaddoc() {
        if(docUrl!=null&& !TextUtils.isEmpty(docUrl)){
            downLoadThread = new Thread(mdownDocRunnable);
            downLoadThread.start();
        }

    }

    //下载安装包
    private Runnable mdownDocRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(docUrl);
                Logger.d("run: "+docUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String docFile = saveFileName;
                 DocFile = new File(docFile);
                FileOutputStream fos = new FileOutputStream(DocFile);
                byte buf[] = new byte[1024];
                do{
                    int numread = is.read(buf);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(0);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!isrun);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    };
    private void  call(){
        File savefile = new File(saveFileName);
        if (!savefile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        String fileMimeType = "application/msword";
        intent.setDataAndType(Uri.fromFile(savefile), fileMimeType);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //检测到系统尚未安装OliveOffice的apk程序
            Toast.makeText(context, "未找到软件", Toast.LENGTH_LONG).show();

        }
    }
}

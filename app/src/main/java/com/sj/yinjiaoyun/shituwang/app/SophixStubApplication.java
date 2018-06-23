package com.sj.yinjiaoyun.shituwang.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by Administrator on 2018/3/19.
 */

public class SophixStubApplication extends SophixApplication {
    public static  final String AppId="24825866";
    public static final String AppSecret="78f92bf8c713dcf63edbbd353a354e6e";
    private  static  final String RSA="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCRXSiuhOp4Q1ACo1vJci6U3AoqSRICtsVG777h3d1ZnSelTZiKvD/lCdQfkVkoJ1udmsA6TrJC7mOxfNrLVUYP5vbell+QeVzyDmY45knBns7fHn1XDn5fty5k87VBpQEohPBmVdpcGv53EYnV6H5YBs0b8gGjjxcd9RchbCe5/WlvrIt/yxKYNUmLb0sPy6qbcmYFXWm4H99sNZJ0ygtUSMJacuYTD9neNDVLCmgj05rtCWF0uhkxD7pJW++iLM8wltGzUOSBD/lbvbOM7Ic56H5x/lx9McCyg/6fI1YHEMFlmiTNZ6rYW3NTxhHeQ+A6RlCKyP7bfAvkiztzdSwRAgMBAAECggEAK+f6iLQG6ipSuWlEKRV+8mGABIOrl/xCnAmAG1LbL90jA2/rmkP//Js6YgqR5mn05ya91x68AzFkFVED+RSZo+X8PQEofkfaBhbJU3hjYMMp8wSPkUMEcr6umbluzxQaxHVa814NJXLV13sJqf2eY9IyE7XGPvPSZK8JvIOhgDCPx/1oDJEA1mwkuxP26ACFfo4PMeFYcGeI6S+4pibwL/A/wvF51cY2DbBJtT0sQ2HqtY/FRGi7YXa3E+aji1abDTdNj8hDLGfZfgOs943cL0YbWnUHqfEA5ihXy1FH7H1uGVUNn1854Z6FHDpayDgXDnmOC1eAbhCyAtssxPB10QKBgQDg0+r+7ku7CkqgNU7jyqFkVSiOiGzUDS2p6MkJn0wMTzv/Z6RX7paFSx0R3Wzxmi/exF5F8U9b/VG9l/d7bftpTaDuTrEQzAFJ15vKcZJMmrDRiW+zfBLiipyoAywSrI2bnLfUE4ZcBPpRDgJDVgH6lf8ZUmvyu0om/yO6qNXYjQKBgQClhLvRyKQkkZb5EBMpITG0TcTslPLUrazcTuWL7S1Rh/KTtA4VNPZdVMoyjW5EAMJRvltG6aYaK/fq/kRmunuS+MXsnOVq60U16b3MxMVuK10lV5C9odZ5MHY+ZBVUA39ZyiLz7fLUCHZfZSIcYPueMehckhu9q2Y7MB0A4xMqlQKBgCJyNYnzhqTUkdRz9m9OJ2YQ6hLUciAXGMz0foC76FHmfeHGxlY2ltc7R+vpyfxGTFlMKsl6jFciu7hZpTBDDIb4vtRkOBrDttrSbusYdXrJJJgl/3g3ZUlSRWg0fDcsOtIBs+SJTUeh95wdklkwv0LeYmRFzYY2Dcllw9qgbYstAoGAVt35oJj3pIWQQXixRaW5K1KhZjSp2PqpPRpfGHfKj/6UTfZyaajZH5RvZeaaFzB6y2HnD6lyVkv7GiaGCZAXaMQNRCf3s4x0T140EXK31yEFCqMRQKTeJc5MUGRAWU5YmOwgOfsItUUXsusl4tT3WUgCFgtMade5mJigsCV8mp0CgYBRu27ufKlmGuPwLxhlT9cSWw0V8rEK2ROV7pB85GhjqsmZ+Jf3w9vZQ7WLldNklN9c8UXcQE8MfCnOpbBMX4arD2ZtT5NFVW3DFbuS3NjuDGfaNzE+yZ12wFg73UsKjR8HckvDsAenCXWSJIIJSw1Vq1lPQ1XTAsITmfp7xKJ3dw==";
    private final String TAG = "SophixStubApplication";
    private Handler handler=new Handler(Looper.getMainLooper());
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //初始化热跟新
        initSophix();
    }
    /**
     * 初始化Sophix
     */
    private void initSophix() {
        String appVersion ;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion="1.0.0";
        }
        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(AppId,AppSecret,RSA)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
                                    PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    manager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, restartIntent);//200ms后重启应用
                                }
                            },5000);
                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
    }

}

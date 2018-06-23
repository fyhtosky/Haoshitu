package com.sj.yinjiaoyun.shituwang.xmppmanager;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;

import org.jivesoftware.smack.packet.Message;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/6.
 * 指的是客户端每隔一段时间发一个空消息给服务端，
 * 以防止公网将本客户端使用的端口挪给其它客户端使用；
 * 因酷似心脏一样有规律的跳动，所以称之为心跳包
 */
public class HeartbeatPackage extends Thread {
    private static HeartbeatPackage heartbeat ;
    public static boolean isRunning=true;
    //在构造方法里启动当前类工作线程

    private HeartbeatPackage(){
        this.start();
    }
    //此方法供外办调用，创建类实例
    public static synchronized HeartbeatPackage newInstens(){
        if(heartbeat==null){
            heartbeat=new HeartbeatPackage();
        }
        return heartbeat;
    }
    //重写run方法，在里面实现每隔45秒向服务端发送一次空消息
    public void run() {
              //while循环
        while (isRunning) {
             //每隔45秒循环一次
            try {
                sleep(4 * 60 * 1000 + 50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
              //发送一个时间消息，注意Message导包需是smark框架里的包
            Message message = new Message();
            message.setBody(String.valueOf(System.currentTimeMillis()));
            if(MyApplication.xmppConnection!=null && MyApplication.xmppConnection.isAuthenticated()){
                MyApplication.xmppConnection.sendPacket(message);
                Logger.d("心跳包："+String.valueOf(System.currentTimeMillis()));
            }else {
                isRunning=false;
            }

        }
    }
}

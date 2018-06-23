package com.sj.yinjiaoyun.shituwang.xmppmanager;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.event.InfoEvent;
import com.sj.yinjiaoyun.shituwang.event.MsgEvent;
import com.sj.yinjiaoyun.shituwang.event.NoticeEvent;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.json.JSONObject;

/**
 * 私聊的消息监听器
 */
public class MyChatManagerListener implements ChatManagerListener {
    @Override
    public void chatCreated(Chat chat, boolean b) {
        chat.addMessageListener(new MessageListener() {
            @Override
            public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                try {
                     Logger.xml(message.toXML());
                    if (TextUtils.isEmpty(message.getBody())) {
                        return;
                    }
                    String to = message.getTo().split("@")[0];//接收者
                    String from = message.getFrom().split("@")[0];//发送者
                    /**
                     * 此处进行消息类型的判断区分 系统通知和IM消息
                     */
                    JSONObject object=new JSONObject(message.getBody());
                    String infoType=object.getString("msgType");
                    if("C01".equals(infoType)){
                       //拦截系统的通知
                        EventBus.getDefault().post(new InfoEvent(to,from,message.getBody(),infoType,message.getType()));
                        Logger.d("系统的通知：body:" + message.getBody() + "  接收者TO:" + to + "  发送者FROM:" + from);
                    }else {
                        String msgBody = message.getBody().replace("msgType","type");
                        //发送消息通知聊天界面刷新聊天记录
                        EventBus.getDefault().post(new MsgEvent(to,from,msgBody,message.getType()));
                        //通知界面未读IM消息更新
                        EventBus.getDefault().post(new NoticeEvent(1));
                        Logger.d("IM的消息：body:" + msgBody + "  接收者TO:" + message.getTo() + "  发送者FROM:" + message.getFrom());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

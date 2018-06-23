package com.sj.yinjiaoyun.shituwang.event;

/**
 * Created by Administrator on 2017/12/15.
 */

import org.jivesoftware.smack.packet.Message;

/**
 * 通知的事件
 */
public class InfoEvent {
    private String to;
    private String from;
    private String msgBody;
    private String infoType;
    private Message.Type type;

    public InfoEvent(String to, String from, String msgBody, String infoType, Message.Type type) {
        this.to = to;
        this.from = from;
        this.msgBody = msgBody;
        this.infoType = infoType;
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public String getInfoType() {
        return infoType;
    }

    public Message.Type getType() {
        return type;
    }
}

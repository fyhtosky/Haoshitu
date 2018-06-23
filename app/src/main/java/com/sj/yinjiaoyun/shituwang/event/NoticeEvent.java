package com.sj.yinjiaoyun.shituwang.event;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/7.
 */
public class NoticeEvent {
    private int messageCount;

    public NoticeEvent(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getMessageCount() {
        return messageCount;
    }
}

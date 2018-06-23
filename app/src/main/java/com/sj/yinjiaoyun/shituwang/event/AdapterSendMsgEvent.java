package com.sj.yinjiaoyun.shituwang.event;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/1.
 * 适配器操作的状态发送xmpp请求
 */
public class AdapterSendMsgEvent {
    private String content;
    private String businessId;
    private String type;

    public AdapterSendMsgEvent(String content, String businessId, String type) {
        this.content = content;
        this.businessId = businessId;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getType() {
        return type;
    }
}

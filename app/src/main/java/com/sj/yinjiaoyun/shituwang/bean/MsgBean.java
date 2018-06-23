package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/21.
 * XMPP发送消息的msg封装的实体类
 */
public class MsgBean {

    /**
     * type : T01
     * sessionId : 542
     * msg : 按时去干活去按时打算
     * businessId :
     */

    private String type;
    private int sessionId;
    private String msg;
    private String businessId;

    public MsgBean() {
    }

    public MsgBean(String type, int sessionId, String msg, String businessId) {
        this.type = type;
        this.sessionId = sessionId;
        this.msg = msg;
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}

package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/28.
 * 数据库保存XMPP消息的封装实体类
 */
public class DBmsgBean {

    /**
     * type : T01
     * msg : 简直就是考试咔咔咔
     * businessId :
     */

    private String type;
    private String msg;
    private String businessId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

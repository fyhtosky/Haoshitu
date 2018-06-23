package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by Administrator on 2017/12/13.
 */

public class UnreadNoticeBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : 3
     * map : null
     * ok : true
     */

    private int status;
    private String msg;
    private int data;
    private Object map;
    private boolean ok;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Object getMap() {
        return map;
    }

    public void setMap(Object map) {
        this.map = map;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}

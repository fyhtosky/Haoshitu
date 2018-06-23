package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/27.
 * 字典表的封装类
 */
public class AllCodesBean {
    private int status;
    private String msg;
    private List<Children> data;
    private String map;
    private boolean ok;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<Children> data) {
        this.data = data;
    }
    public List<Children> getData() {
        return data;
    }

    public void setMap(String map) {
        this.map = map;
    }
    public String getMap() {
        return map;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    public boolean getOk() {
        return ok;
    }
}

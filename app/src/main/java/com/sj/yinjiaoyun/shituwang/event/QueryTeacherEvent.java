package com.sj.yinjiaoyun.shituwang.event;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/21.
 * 根据选择的城市和职位查询老师
 */
public class QueryTeacherEvent {
    private String addressNow;
    private String realName;

    public QueryTeacherEvent(String addressNow, String realName) {
        this.addressNow = addressNow;
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public String getAddressNow() {
        return addressNow;
    }
}

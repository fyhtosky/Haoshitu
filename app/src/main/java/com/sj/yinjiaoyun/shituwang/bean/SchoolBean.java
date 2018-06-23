package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/16.
 * 获取学校
 */
public class SchoolBean {

    /**
     * status : 200
     * msg : 获取院校信息成功!
     * data : [{"id":1,"schoolName":"重庆城市管理职业学院","createTime":1488943500000},{"id":2,"schoolName":"智隆学院","createTime":1488943500000}]
     * map : null
     * ok : true
     */

    private int status;
    private String msg;
    private Object map;
    private boolean ok;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * schoolName : 重庆城市管理职业学院
         * createTime : 1488943500000
         */

        private int id;
        private String schoolName;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}

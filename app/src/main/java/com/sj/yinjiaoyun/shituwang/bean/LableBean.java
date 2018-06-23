package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/24.
 * 标签的状态的实体类
 */
public class LableBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : [{"id":122,"stuId":507,"tchId":522,"changeType":0,"status":1,"createTime":1496741695000,"updateTime":1496741695000}]
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
         * id : 122
         * stuId : 507
         * tchId : 522
         * changeType : 0
         * status : 1
         * createTime : 1496741695000
         * updateTime : 1496741695000
         */

        private int id;
        private int stuId;
        private int tchId;
        private int changeType;
        private int status;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStuId() {
            return stuId;
        }

        public void setStuId(int stuId) {
            this.stuId = stuId;
        }

        public int getTchId() {
            return tchId;
        }

        public void setTchId(int tchId) {
            this.tchId = tchId;
        }

        public int getChangeType() {
            return changeType;
        }

        public void setChangeType(int changeType) {
            this.changeType = changeType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}

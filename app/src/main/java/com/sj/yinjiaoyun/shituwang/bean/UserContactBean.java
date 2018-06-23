package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/1.
 * 第一次发起沟通的封装实体类
 */
public class UserContactBean {

    /**
     * status : 200
     * msg : 生成记录成功！
     * data : {"id":514,"stuId":null,"tchId":null,"isNotDisturb":null,"createTime":null,"updateTime":null,"flag":1}
     * map : null
     * ok : true
     */

    private int status;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * id : 514
         * stuId : null
         * tchId : null
         * isNotDisturb : null
         * createTime : null
         * updateTime : null
         * flag : 1
         */

        private String id;
        private String stuId;
        private String tchId;
        private String isNotDisturb;
        private String createTime;
        private String updateTime;
        private int flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getTchId() {
            return tchId;
        }

        public void setTchId(String tchId) {
            this.tchId = tchId;
        }

        public String getIsNotDisturb() {
            return isNotDisturb;
        }

        public void setIsNotDisturb(String isNotDisturb) {
            this.isNotDisturb = isNotDisturb;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}

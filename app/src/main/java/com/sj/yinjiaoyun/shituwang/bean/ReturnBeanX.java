package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/7.
 */
public class ReturnBeanX {


    /**
     * status : 200
     * msg : 教师发起面试邀约成功！
     * data : 101
     * map : {"studyId":"261"}
     * ok : true
     */

    private int status;
    private String msg;
    private int data;
    private MapBean map;
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

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static class MapBean {
        /**
         * studyId : 261
         */

        private String studyId;

        public String getStudyId() {
            return studyId;
        }

        public void setStudyId(String studyId) {
            this.studyId = studyId;
        }
    }
}

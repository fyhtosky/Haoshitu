package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/14.
 */
public class AgrementBean {

    /**
     * status : 200
     * msg : 获取学生详细信息成功!
     * data : {"agreement":"http://139.196.255.175:8220//sch/1709/09af76fa-9cce-4944-b873-3ab8458c35c7_.pdf\n","agreementName":"考试.pdf"}
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
         * agreement : http://139.196.255.175:8220//sch/1709/09af76fa-9cce-4944-b873-3ab8458c35c7_.pdf

         * agreementName : 考试.pdf
         */

        private String agreement;
        private String agreementName;

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public String getAgreementName() {
            return agreementName;
        }

        public void setAgreementName(String agreementName) {
            this.agreementName = agreementName;
        }
    }
}

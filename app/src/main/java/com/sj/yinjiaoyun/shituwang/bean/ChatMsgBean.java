package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/24.
 * 聊天記錄的封裝對象
 */
public class ChatMsgBean {


    /**
     * status : 200
     * msg : 操作成功
     * data : {"total":284,"rows":[{"id":2016,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"哦敏敏\",\"type\":\"S01\"}","createTime":1501582049000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2015,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"你是我唯一用蘑菇\",\"type\":\"S01\"}","createTime":1501582034000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2014,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"通行证无\",\"type\":\"S01\"}","createTime":1501582026000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2013,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"type\":\"S01\",\"msg\":\"发发生了空间\",\"businessId\":\"\"}","createTime":1501580945000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1997,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"捶你的\\n\",\"businessId\":\"\"}","createTime":1501554469000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1996,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发顺丰啦；看\",\"businessId\":\"\"}","createTime":1501554462000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1995,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"罚款啦放假撒\",\"businessId\":\"\"}","createTime":1501554407000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1994,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"asfasfa\",\"businessId\":\"\"}","createTime":1501554396000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1993,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fjlksaljk \",\"businessId\":\"\"}","createTime":1501554369000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1991,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"放假萨克交流方式\",\"businessId\":\"\"}","createTime":1501552927000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1973,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"你可以问问\",\"type\":\"S01\"}","createTime":1501482041000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1972,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"雕塑断丝度阿斯顿 \",\"businessId\":\"\"}","createTime":1501481260000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1971,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"搞事情你怕不怕\",\"businessId\":\"\"}","createTime":1501481254000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1970,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发神经六块腹肌索拉卡\",\"businessId\":\"\"}","createTime":1501481241000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1969,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发就看看就\",\"businessId\":\"\"}","createTime":1501481237000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1968,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"带你飞一飞咯\",\"businessId\":\"\"}","createTime":1501481209000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1967,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fjalsfslkja\",\"businessId\":\"\"}","createTime":1501481188000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1966,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fasfskla\",\"businessId\":\"\"}","createTime":1501481181000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1938,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"请问撒旦阿斯顿按时的\",\"businessId\":\"\"}","createTime":1501222212000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1937,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"按时打算打算的\",\"businessId\":\"\"}","createTime":1501222209000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null}],"pageSize":10,"pageNo":1}
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
         * total : 284
         * rows : [{"id":2016,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"哦敏敏\",\"type\":\"S01\"}","createTime":1501582049000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2015,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"你是我唯一用蘑菇\",\"type\":\"S01\"}","createTime":1501582034000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2014,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"通行证无\",\"type\":\"S01\"}","createTime":1501582026000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":2013,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"type\":\"S01\",\"msg\":\"发发生了空间\",\"businessId\":\"\"}","createTime":1501580945000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1997,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"捶你的\\n\",\"businessId\":\"\"}","createTime":1501554469000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1996,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发顺丰啦；看\",\"businessId\":\"\"}","createTime":1501554462000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1995,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"罚款啦放假撒\",\"businessId\":\"\"}","createTime":1501554407000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1994,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"asfasfa\",\"businessId\":\"\"}","createTime":1501554396000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1993,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fjlksaljk \",\"businessId\":\"\"}","createTime":1501554369000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1991,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"放假萨克交流方式\",\"businessId\":\"\"}","createTime":1501552927000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1973,"userContactId":438,"sender":"5stu_507","receiver":"5tch_520","msgType":"S01","msg":"{\"businessId\":\"\",\"msg\":\"你可以问问\",\"type\":\"S01\"}","createTime":1501482041000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1972,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"雕塑断丝度阿斯顿 \",\"businessId\":\"\"}","createTime":1501481260000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1971,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"搞事情你怕不怕\",\"businessId\":\"\"}","createTime":1501481254000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1970,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发神经六块腹肌索拉卡\",\"businessId\":\"\"}","createTime":1501481241000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1969,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"发就看看就\",\"businessId\":\"\"}","createTime":1501481237000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1968,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"带你飞一飞咯\",\"businessId\":\"\"}","createTime":1501481209000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1967,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fjalsfslkja\",\"businessId\":\"\"}","createTime":1501481188000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1966,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"fasfskla\",\"businessId\":\"\"}","createTime":1501481181000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1938,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"请问撒旦阿斯顿按时的\",\"businessId\":\"\"}","createTime":1501222212000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null},{"id":1937,"userContactId":438,"sender":"5tch_520","receiver":"5stu_507","msgType":"T01","msg":"{\"type\":\"T01\",\"msg\":\"按时打算打算的\",\"businessId\":\"\"}","createTime":1501222209000,"msgCount":0,"msgName":null,"msgLogo":null,"isNotDisturb":null,"sex":null,"companyName":null,"positionName":null,"status":null,"businessType":null,"businessId":null}]
         * pageSize : 10
         * pageNo : 1
         */

        private int total;
        private int pageSize;
        private int pageNo;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 2016
             * userContactId : 438
             * sender : 5stu_507
             * receiver : 5tch_520
             * msgType : S01
             * msg : {"businessId":"","msg":"哦敏敏","type":"S01"}
             * createTime : 1501582049000
             * msgCount : 0
             * msgName : null
             * msgLogo : null
             * isNotDisturb : null
             * sex : null
             * companyName : null
             * positionName : null
             * status : null
             * businessType : null
             * businessId : null
             */

            private int id;
            private int userContactId;
            private String sender;
            private String receiver;
            private String msgType;
            private String msg;
            private long createTime;
            private int msgCount;
            private String msgName;
            private String msgLogo;
            private String isNotDisturb;
            private String sex;
            private String companyName;
            private String positionName;
            private String status;
            private String businessType;
            private String businessId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserContactId() {
                return userContactId;
            }

            public void setUserContactId(int userContactId) {
                this.userContactId = userContactId;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getMsgType() {
                return msgType;
            }

            public void setMsgType(String msgType) {
                this.msgType = msgType;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getMsgCount() {
                return msgCount;
            }

            public void setMsgCount(int msgCount) {
                this.msgCount = msgCount;
            }

            public String getMsgName() {
                return msgName;
            }

            public void setMsgName(String msgName) {
                this.msgName = msgName;
            }

            public String getMsgLogo() {
                return msgLogo;
            }

            public void setMsgLogo(String msgLogo) {
                this.msgLogo = msgLogo;
            }

            public String getIsNotDisturb() {
                return isNotDisturb;
            }

            public void setIsNotDisturb(String isNotDisturb) {
                this.isNotDisturb = isNotDisturb;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            @Override
            public String toString() {
                return "RowsBean{" +
                        "id=" + id +
                        ", userContactId=" + userContactId +
                        ", sender='" + sender + '\'' +
                        ", receiver='" + receiver + '\'' +
                        ", msgType='" + msgType + '\'' +
                        ", msg='" + msg + '\'' +
                        ", createTime=" + createTime +
                        ", msgCount=" + msgCount +
                        ", msgName='" + msgName + '\'' +
                        ", msgLogo='" + msgLogo + '\'' +
                        ", isNotDisturb='" + isNotDisturb + '\'' +
                        ", sex='" + sex + '\'' +
                        ", companyName='" + companyName + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", status='" + status + '\'' +
                        ", businessType='" + businessType + '\'' +
                        ", businessId='" + businessId + '\'' +
                        '}';
            }
        }
    }
}



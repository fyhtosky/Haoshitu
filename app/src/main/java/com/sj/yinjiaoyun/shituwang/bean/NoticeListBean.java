package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class NoticeListBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : {"total":3,"rows":[{"id":3,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":654,"msg":"您收到【大大888】导师的面试邀请，请及时处理哦~","hasRead":0,"createTime":1513063293000,"msgType":null},{"id":2,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":653,"msg":"您于【2017-12-11 19:00】的面试已被取消，请查看~","hasRead":0,"createTime":1512988012000,"msgType":null},{"id":1,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":653,"msg":"您收到【大大888】导师的面试邀请，请及时处理哦~","hasRead":0,"createTime":1512987741000,"msgType":null}],"pageSize":10,"pageNo":1}
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
         * total : 3
         * rows : [{"id":3,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":654,"msg":"您收到【大大888】导师的面试邀请，请及时处理哦~","hasRead":0,"createTime":1513063293000,"msgType":null},{"id":2,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":653,"msg":"您于【2017-12-11 19:00】的面试已被取消，请查看~","hasRead":0,"createTime":1512988012000,"msgType":null},{"id":1,"senderId":641,"receiverId":642,"receiverType":0,"type":0,"businessId":653,"msg":"您收到【大大888】导师的面试邀请，请及时处理哦~","hasRead":0,"createTime":1512987741000,"msgType":null}]
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
             * id : 3
             * senderId : 641
             * receiverId : 642
             * receiverType : 0
             * type : 0
             * businessId : 654
             * msg : 您收到【大大888】导师的面试邀请，请及时处理哦~
             * hasRead : 0
             * createTime : 1513063293000
             * msgType : null
             */

            private int id;
            private int senderId;
            private int receiverId;
            private int receiverType;
            private int type;
            private int businessId;
            private String msg;
            private int hasRead;
            private long createTime;
            private String msgType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSenderId() {
                return senderId;
            }

            public void setSenderId(int senderId) {
                this.senderId = senderId;
            }

            public int getReceiverId() {
                return receiverId;
            }

            public void setReceiverId(int receiverId) {
                this.receiverId = receiverId;
            }

            public int getReceiverType() {
                return receiverType;
            }

            public void setReceiverType(int receiverType) {
                this.receiverType = receiverType;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getBusinessId() {
                return businessId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getHasRead() {
                return hasRead;
            }

            public void setHasRead(int hasRead) {
                this.hasRead = hasRead;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getMsgType() {
                return msgType;
            }

            public void setMsgType(String msgType) {
                this.msgType = msgType;
            }
        }
    }
}

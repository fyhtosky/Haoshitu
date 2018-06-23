package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/14.
 * 评价列表的实体类
 */
public class LogBean {


    /**
     * status : 200
     * msg : 加载评价列表信息成功!
     * data : {"total":3,"rows":[{"id":64,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":5,"parameter2":5,"parameter3":5,"labels":"[\"R0010,P0055\",\"R0010,P0056\",\"R0010,P0057\",\"R0010,P0060\",\"R0010,P0059\",\"R0010,P0058\"]","notes":"按时打算达的阿斯顿撒旦撒","isTch":1,"createTime":1502370801000,"name":"刘子橙","teacher":null,"student":null},{"id":63,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":3,"parameter2":3,"parameter3":3,"labels":"[\"R0010,P0056\",\"R0010,P0061\",\"R0010,P0062\"]","notes":"大声的撒","isTch":1,"createTime":1501434132000,"name":"刘子橙","teacher":null,"student":null},{"id":61,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":4,"parameter2":3,"parameter3":3,"labels":"[\"R0010,P0057\",\"R0010,P0056\",\"R0010,P0055\",\"R0010,P0060\",\"R0010,P0061\",\"R0010,P0062\",\"R0010,P0058\",\"R0010,P0059\"]","notes":"鬼搞黄金价格","isTch":1,"createTime":1501233290000,"name":"刘子橙","teacher":null,"student":null}],"pageSize":10,"pageNo":1}
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
         * rows : [{"id":64,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":5,"parameter2":5,"parameter3":5,"labels":"[\"R0010,P0055\",\"R0010,P0056\",\"R0010,P0057\",\"R0010,P0060\",\"R0010,P0059\",\"R0010,P0058\"]","notes":"按时打算达的阿斯顿撒旦撒","isTch":1,"createTime":1502370801000,"name":"刘子橙","teacher":null,"student":null},{"id":63,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":3,"parameter2":3,"parameter3":3,"labels":"[\"R0010,P0056\",\"R0010,P0061\",\"R0010,P0062\"]","notes":"大声的撒","isTch":1,"createTime":1501434132000,"name":"刘子橙","teacher":null,"student":null},{"id":61,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":4,"parameter2":3,"parameter3":3,"labels":"[\"R0010,P0057\",\"R0010,P0056\",\"R0010,P0055\",\"R0010,P0060\",\"R0010,P0061\",\"R0010,P0062\",\"R0010,P0058\",\"R0010,P0059\"]","notes":"鬼搞黄金价格","isTch":1,"createTime":1501233290000,"name":"刘子橙","teacher":null,"student":null}]
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
             * id : 64
             * evaluateId : 25
             * stuId : 507
             * tchId : 530
             * parameter1 : 5
             * parameter2 : 5
             * parameter3 : 5
             * labels : ["R0010,P0055","R0010,P0056","R0010,P0057","R0010,P0060","R0010,P0059","R0010,P0058"]
             * notes : 按时打算达的阿斯顿撒旦撒
             * isTch : 1
             * createTime : 1502370801000
             * name : 刘子橙
             * teacher : null
             * student : null
             */

            private int id;
            private int evaluateId;
            private int stuId;
            private int tchId;
            private int parameter1;
            private int parameter2;
            private int parameter3;
            private String labels;
            private String notes;
            private int isTch;
            private long createTime;
            private String name;
            private Object teacher;
            private Object student;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getEvaluateId() {
                return evaluateId;
            }

            public void setEvaluateId(int evaluateId) {
                this.evaluateId = evaluateId;
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

            public int getParameter1() {
                return parameter1;
            }

            public void setParameter1(int parameter1) {
                this.parameter1 = parameter1;
            }

            public int getParameter2() {
                return parameter2;
            }

            public void setParameter2(int parameter2) {
                this.parameter2 = parameter2;
            }

            public int getParameter3() {
                return parameter3;
            }

            public void setParameter3(int parameter3) {
                this.parameter3 = parameter3;
            }

            public String getLabels() {
                return labels;
            }

            public void setLabels(String labels) {
                this.labels = labels;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public int getIsTch() {
                return isTch;
            }

            public void setIsTch(int isTch) {
                this.isTch = isTch;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getTeacher() {
                return teacher;
            }

            public void setTeacher(Object teacher) {
                this.teacher = teacher;
            }

            public Object getStudent() {
                return student;
            }

            public void setStudent(Object student) {
                this.student = student;
            }
        }
    }
}

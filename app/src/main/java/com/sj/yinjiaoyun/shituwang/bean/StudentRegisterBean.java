package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/16.
 */
public class StudentRegisterBean {

    /**
     * status : 200
     * msg : 账户激活成功!
     * data : {"id":599,"uuid":"041bce95f6fd4c9b8bab9174c20d5804","username":null,"password":"9dea58781660c7daae763b9cd9e33148","realName":"范学生","sex":1,"mobile":"18672593926","status":1,"schoolId":1,"createTime":1497257485000,"email":"","weChat":null,"updateTime":1497577198000,"birthday":null,"addressNow":"","department":"","stuNo":"","citizenship":68,"moralTrait":85,"taste":89,"study":75,"logicalMathe":95,"selfCognitive":99,"subjectAttainment":89,"interaction":65,"space":65,"workStatus":0,"head":"","lastLogin":1497577198000,"isShowRadar":1,"description":null,"evaluationLabels":"","schoolName":null,"resumes":null,"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}
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
         * id : 599
         * uuid : 041bce95f6fd4c9b8bab9174c20d5804
         * username : null
         * password : 9dea58781660c7daae763b9cd9e33148
         * realName : 范学生
         * sex : 1
         * mobile : 18672593926
         * status : 1
         * schoolId : 1
         * createTime : 1497257485000
         * email :
         * weChat : null
         * updateTime : 1497577198000
         * birthday : null
         * addressNow :
         * department :
         * stuNo :
         * citizenship : 68
         * moralTrait : 85
         * taste : 89
         * study : 75
         * logicalMathe : 95
         * selfCognitive : 99
         * subjectAttainment : 89
         * interaction : 65
         * space : 65
         * workStatus : 0
         * head :
         * lastLogin : 1497577198000
         * isShowRadar : 1
         * description : null
         * evaluationLabels :
         * schoolName : null
         * resumes : null
         * isStudy : null
         * studyId : null
         * contactId : null
         * resumePerfectDegree : 0.0
         */

        private int id;
        private String uuid;
        private String username;
        private String password;
        private String realName;
        private int sex;
        private String mobile;
        private int status;
        private int schoolId;
        private long createTime;
        private String email;
        private String weChat;
        private long updateTime;
        private String birthday;
        private String addressNow;
        private String department;
        private String stuNo;
        private int citizenship;
        private int moralTrait;
        private int taste;
        private int study;
        private int logicalMathe;
        private int selfCognitive;
        private int subjectAttainment;
        private int interaction;
        private int space;
        private int workStatus;
        private String head;
        private long lastLogin;
        private int isShowRadar;
        private String description;
        private String evaluationLabels;
        private String schoolName;
        private Object resumes;
        private int isStudy;
        private int studyId;
        private long contactId;
        private double resumePerfectDegree;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWeChat() {
            return weChat;
        }

        public void setWeChat(String weChat) {
            this.weChat = weChat;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddressNow() {
            return addressNow;
        }

        public void setAddressNow(String addressNow) {
            this.addressNow = addressNow;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getStuNo() {
            return stuNo;
        }

        public void setStuNo(String stuNo) {
            this.stuNo = stuNo;
        }

        public int getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(int citizenship) {
            this.citizenship = citizenship;
        }

        public int getMoralTrait() {
            return moralTrait;
        }

        public void setMoralTrait(int moralTrait) {
            this.moralTrait = moralTrait;
        }

        public int getTaste() {
            return taste;
        }

        public void setTaste(int taste) {
            this.taste = taste;
        }

        public int getStudy() {
            return study;
        }

        public void setStudy(int study) {
            this.study = study;
        }

        public int getLogicalMathe() {
            return logicalMathe;
        }

        public void setLogicalMathe(int logicalMathe) {
            this.logicalMathe = logicalMathe;
        }

        public int getSelfCognitive() {
            return selfCognitive;
        }

        public void setSelfCognitive(int selfCognitive) {
            this.selfCognitive = selfCognitive;
        }

        public int getSubjectAttainment() {
            return subjectAttainment;
        }

        public void setSubjectAttainment(int subjectAttainment) {
            this.subjectAttainment = subjectAttainment;
        }

        public int getInteraction() {
            return interaction;
        }

        public void setInteraction(int interaction) {
            this.interaction = interaction;
        }

        public int getSpace() {
            return space;
        }

        public void setSpace(int space) {
            this.space = space;
        }

        public int getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(int workStatus) {
            this.workStatus = workStatus;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public long getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(long lastLogin) {
            this.lastLogin = lastLogin;
        }

        public int getIsShowRadar() {
            return isShowRadar;
        }

        public void setIsShowRadar(int isShowRadar) {
            this.isShowRadar = isShowRadar;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEvaluationLabels() {
            return evaluationLabels;
        }

        public void setEvaluationLabels(String evaluationLabels) {
            this.evaluationLabels = evaluationLabels;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public Object getResumes() {
            return resumes;
        }

        public void setResumes(Object resumes) {
            this.resumes = resumes;
        }

        public int getIsStudy() {
            return isStudy;
        }

        public void setIsStudy(int isStudy) {
            this.isStudy = isStudy;
        }

        public int getStudyId() {
            return studyId;
        }

        public void setStudyId(int studyId) {
            this.studyId = studyId;
        }

        public long getContactId() {
            return contactId;
        }

        public void setContactId(long contactId) {
            this.contactId = contactId;
        }

        public double getResumePerfectDegree() {
            return resumePerfectDegree;
        }

        public void setResumePerfectDegree(double resumePerfectDegree) {
            this.resumePerfectDegree = resumePerfectDegree;
        }
    }
}

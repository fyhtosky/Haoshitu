package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/14.
 *学生登录成功返回的实体类
 */
public class PersonalBean {


    /**
     * status : 200
     * msg : 登录成功!
     * data : {"id":264,"uuid":"e4c635aebbe64c7daa192f4c8e4764d8","username":null,"realName":"李善平","sex":0,"mobile":"18111111111","password":"1e11dc20b0646c8054fdeabe7705b629","status":1,"schoolId":2,"createTime":null,"email":"33@168.com","weChat":"sswdfe","updateTime":1495016355000,"birthday":546192000,"addressNow":"内蒙古自治区,巴彦淖尔市,临河区","department":"2","stuNo":"096","citizenship":68,"moralTrait":71,"taste":73,"study":86,"logicalMathe":65,"selfCognitive":81,"subjectAttainment":72,"interaction":62,"space":65,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/33/i8T_r1ka0u2AEkgyAAAN6_-h7JE180.jpg","schoolName":null,"description":"呵呵呵呵呵呵呵","isStudy":null,"studyId":null,"evaluationLabels":"[\"能力出众\",\"与人为善\",\"勤勤恳恳\"]","lastLogin":1497325918000,"resumes":null,"resumePerfectDegree":0}
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
         * id : 264
         * uuid : e4c635aebbe64c7daa192f4c8e4764d8
         * username : null
         * realName : 李善平
         * sex : 0
         * mobile : 18111111111
         * password : 1e11dc20b0646c8054fdeabe7705b629
         * status : 1
         * schoolId : 2
         * createTime : null
         * email : 33@168.com
         * weChat : sswdfe
         * updateTime : 1495016355000
         * birthday : 546192000
         * addressNow : 内蒙古自治区,巴彦淖尔市,临河区
         * department : 2
         * stuNo : 096
         * citizenship : 68
         * moralTrait : 71
         * taste : 73
         * study : 86
         * logicalMathe : 65
         * selfCognitive : 81
         * subjectAttainment : 72
         * interaction : 62
         * space : 65
         * workStatus : 1
         * head : http://139.196.255.175/group1/M00/00/33/i8T_r1ka0u2AEkgyAAAN6_-h7JE180.jpg
         * schoolName : null
         * description : 呵呵呵呵呵呵呵
         * isStudy : null
         * studyId : null
         * evaluationLabels : ["能力出众","与人为善","勤勤恳恳"]
         * lastLogin : 1497325918000
         * resumes : null
         * resumePerfectDegree : 0.0
         */

        private int id;
        private String uuid;
        private String username;
        private String realName;
        private int sex;
        private String mobile;
        private String password;
        private int status;
        private int schoolId;
        private Long createTime;
        private String email;
        private String weChat;
        private long updateTime;
        private int birthday;
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
        private String schoolName;
        private String description;
        private int isStudy;
        private int studyId;
        private String evaluationLabels;
        private long lastLogin;
        private String resumes;
        private double resumePerfectDegree;
        private String token;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
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

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
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

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getEvaluationLabels() {
            return evaluationLabels;
        }

        public void setEvaluationLabels(String evaluationLabels) {
            this.evaluationLabels = evaluationLabels;
        }

        public long getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(long lastLogin) {
            this.lastLogin = lastLogin;
        }

        public String getResumes() {
            return resumes;
        }

        public void setResumes(String resumes) {
            this.resumes = resumes;
        }

        public double getResumePerfectDegree() {
            return resumePerfectDegree;
        }

        public void setResumePerfectDegree(double resumePerfectDegree) {
            this.resumePerfectDegree = resumePerfectDegree;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/23.
 * 个人中心中获取学生的详细信息
 */
public class StudentPersonalCenterDetailBean {


    /**
     * status : 200
     * msg : 获取学生详细信息成功!
     * data : {"id":599,"uuid":"ec778466036d4313811f7d552d1f7977","username":null,"realName":"范学生","sex":1,"mobile":"18672593926","password":"9dea58781660c7daae763b9cd9e33148","status":1,"schoolId":1,"createTime":1497257485000,"email":"","weChat":null,"updateTime":1497602533000,"birthday":null,"addressNow":"","department":"","stuNo":"","citizenship":94,"moralTrait":76,"taste":77,"study":63,"logicalMathe":90,"selfCognitive":63,"subjectAttainment":66,"interaction":88,"space":74,"workStatus":0,"head":"","schoolName":"重庆城市管理职业学院","description":null,"isStudy":null,"studyId":null,"evaluationLabels":"","lastLogin":1498200598000,"resumes":null,"resumePerfectDegree":0}
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
         * uuid : ec778466036d4313811f7d552d1f7977
         * username : null
         * realName : 范学生
         * sex : 1
         * mobile : 18672593926
         * password : 9dea58781660c7daae763b9cd9e33148
         * status : 1
         * schoolId : 1
         * createTime : 1497257485000
         * email :
         * weChat : null
         * updateTime : 1497602533000
         * birthday : null
         * addressNow :
         * department :
         * stuNo :
         * citizenship : 94
         * moralTrait : 76
         * taste : 77
         * study : 63
         * logicalMathe : 90
         * selfCognitive : 63
         * subjectAttainment : 66
         * interaction : 88
         * space : 74
         * workStatus : 0
         * head :
         * schoolName : 重庆城市管理职业学院
         * description : null
         * isStudy : null
         * studyId : null
         * evaluationLabels :
         * lastLogin : 1498200598000
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
        private long createTime;
        private String email;
        private String weChat;
        private long updateTime;
        private Long birthday;
        private String addressNow;
        private String department;
        private String stuNo;
        private double citizenship;
        private double moralTrait;
        private double taste;
        private double study;
        private double logicalMathe;
        private double selfCognitive;
        private double subjectAttainment;
        private double interaction;
        private double space;
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

        public Long getBirthday() {
            return birthday;
        }

        public void setBirthday(Long birthday) {
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

        public double getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(double citizenship) {
            this.citizenship = citizenship;
        }

        public double getMoralTrait() {
            return moralTrait;
        }

        public void setMoralTrait(double moralTrait) {
            this.moralTrait = moralTrait;
        }

        public double getTaste() {
            return taste;
        }

        public void setTaste(double taste) {
            this.taste = taste;
        }

        public double getStudy() {
            return study;
        }

        public void setStudy(double study) {
            this.study = study;
        }

        public double getLogicalMathe() {
            return logicalMathe;
        }

        public void setLogicalMathe(double logicalMathe) {
            this.logicalMathe = logicalMathe;
        }

        public double getSelfCognitive() {
            return selfCognitive;
        }

        public void setSelfCognitive(double selfCognitive) {
            this.selfCognitive = selfCognitive;
        }

        public double getSubjectAttainment() {
            return subjectAttainment;
        }

        public void setSubjectAttainment(double subjectAttainment) {
            this.subjectAttainment = subjectAttainment;
        }

        public double getInteraction() {
            return interaction;
        }

        public void setInteraction(double interaction) {
            this.interaction = interaction;
        }

        public double getSpace() {
            return space;
        }

        public void setSpace(double space) {
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
    }
}

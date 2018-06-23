package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/27.
 * 学生档案的实体类
 */
public class StudentRecordBean  implements Serializable {

    /**
     * status : 200
     * msg : 获取学生详细信息成功!
     * data : {"id":264,"uuid":"e4c635aebbe64c7daa192f4c8e4764d8","username":null,"realName":"李善平","sex":0,"mobile":"18111111111","password":"1e11dc20b0646c8054fdeabe7705b629","status":1,"schoolId":2,"createTime":null,"email":"33@168.com","weChat":"sswdfe","updateTime":1495016355000,"birthday":546192000,"addressNow":"内蒙古自治区,巴彦淖尔市,临河区","department":"2","stuNo":"096","citizenship":68,"moralTrait":71,"taste":73,"study":86,"logicalMathe":65,"selfCognitive":81,"subjectAttainment":72,"interaction":62,"space":65,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/33/i8T_r1ka0u2AEkgyAAAN6_-h7JE180.jpg","schoolName":"智隆学院","description":"呵呵呵呵呵呵呵","isStudy":null,"studyId":null,"evaluationLabels":"[\"能力出众\",\"与人为善\",\"勤勤恳恳\"]","lastLogin":1497434177000,"resumes":[{"id":202,"stuUserId":264,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0014,S0083\",\"expectPosition\":\"R0001,P0002,S0010,T0142\",\"expectSalary\":\"R0005,P0030\"}","updateTime":1496198383000},{"id":279,"stuUserId":264,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"发是\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"发发\"}","updateTime":1497326406000},{"id":208,"stuUserId":264,"resumeType":2,"resume":"{\"beginTime\":1451606400,\"collegeName\":\"武汉大学\",\"diplomas\":\"R0006,P0034\",\"majorName\":\"化学专业\"}","updateTime":1496198196000},{"id":5,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1483228800,\"trainingCertificateName\":\"高级\",\"trainingCourseName\":\"C++开发\",\"trainingOrgName\":\"北大青鸟\"}","updateTime":1494577957000},{"id":6,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1388534400,\"endTime\":1483228800,\"trainingCourseName\":\"PHP开发\",\"trainingOrgName\":\"达内\"}","updateTime":1493358686000},{"id":32,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1420070400,\"endTime\":1483228800,\"trainingCertificateName\":\"\",\"trainingCourseName\":\"PHP\",\"trainingOrgName\":\"北大青鸟\"}","updateTime":1492759887000},{"id":3,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1464105600,\"certificateName\":\"开发工程师\",\"certificateOrgName\":\"党国\"}","updateTime":1492582810000},{"id":4,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1458230400,\"certificateName\":\"测试工程师\",\"certificateOrgName\":\"劳动局\"}","updateTime":1492582958000},{"id":31,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1483228800,\"certificateName\":\"会计证\",\"certificateOrgName\":\"北大青鸟\"}","updateTime":1492850586000},{"id":209,"stuUserId":264,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"顶顶顶顶\"}","updateTime":1496198206000},{"id":210,"stuUserId":264,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"啊啊啊啊\"}","updateTime":1496198229000}],"resumePerfectDegree":75}
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

    public static class DataBean  implements Serializable  {
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
         * schoolName : 智隆学院
         * description : 呵呵呵呵呵呵呵
         * isStudy : null
         * studyId : null
         * evaluationLabels : ["能力出众","与人为善","勤勤恳恳"]
         * lastLogin : 1497434177000
         * resumes : [{"id":202,"stuUserId":264,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0014,S0083\",\"expectPosition\":\"R0001,P0002,S0010,T0142\",\"expectSalary\":\"R0005,P0030\"}","updateTime":1496198383000},{"id":279,"stuUserId":264,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"发是\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"发发\"}","updateTime":1497326406000},{"id":208,"stuUserId":264,"resumeType":2,"resume":"{\"beginTime\":1451606400,\"collegeName\":\"武汉大学\",\"diplomas\":\"R0006,P0034\",\"majorName\":\"化学专业\"}","updateTime":1496198196000},{"id":5,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1483228800,\"trainingCertificateName\":\"高级\",\"trainingCourseName\":\"C++开发\",\"trainingOrgName\":\"北大青鸟\"}","updateTime":1494577957000},{"id":6,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1388534400,\"endTime\":1483228800,\"trainingCourseName\":\"PHP开发\",\"trainingOrgName\":\"达内\"}","updateTime":1493358686000},{"id":32,"stuUserId":264,"resumeType":5,"resume":"{\"beginTime\":1420070400,\"endTime\":1483228800,\"trainingCertificateName\":\"\",\"trainingCourseName\":\"PHP\",\"trainingOrgName\":\"北大青鸟\"}","updateTime":1492759887000},{"id":3,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1464105600,\"certificateName\":\"开发工程师\",\"certificateOrgName\":\"党国\"}","updateTime":1492582810000},{"id":4,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1458230400,\"certificateName\":\"测试工程师\",\"certificateOrgName\":\"劳动局\"}","updateTime":1492582958000},{"id":31,"stuUserId":264,"resumeType":6,"resume":"{\"awardTime\":1483228800,\"certificateName\":\"会计证\",\"certificateOrgName\":\"北大青鸟\"}","updateTime":1492850586000},{"id":209,"stuUserId":264,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"顶顶顶顶\"}","updateTime":1496198206000},{"id":210,"stuUserId":264,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"啊啊啊啊\"}","updateTime":1496198229000}]
         * resumePerfectDegree : 75.0
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
        private String contactId;
        private long lastLogin;
        private double resumePerfectDegree;
        private List<ResumesBean> resumes;
        private int isDisabled;

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

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

        public double getResumePerfectDegree() {
            return resumePerfectDegree;
        }

        public void setResumePerfectDegree(double resumePerfectDegree) {
            this.resumePerfectDegree = resumePerfectDegree;
        }

        public List<ResumesBean> getResumes() {
            return resumes;
        }

        public void setResumes(List<ResumesBean> resumes) {
            this.resumes = resumes;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public static class ResumesBean implements Serializable{
            /**
             * id : 202
             * stuUserId : 264
             * resumeType : 1
             * resume : {"address":"天津市,河东区","expectBusiness":"R0002,P0014,S0083","expectPosition":"R0001,P0002,S0010,T0142","expectSalary":"R0005,P0030"}
             * updateTime : 1496198383000
             */

            private int id;
            private int stuUserId;
            private int resumeType;
            private String resume;
            private long updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStuUserId() {
                return stuUserId;
            }

            public void setStuUserId(int stuUserId) {
                this.stuUserId = stuUserId;
            }

            public int getResumeType() {
                return resumeType;
            }

            public void setResumeType(int resumeType) {
                this.resumeType = resumeType;
            }

            public String getResume() {
                return resume;
            }

            public void setResume(String resume) {
                this.resume = resume;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            @Override
            public String toString() {
                return "ResumesBean{" +
                        "id=" + id +
                        ", stuUserId=" + stuUserId +
                        ", resumeType=" + resumeType +
                        ", resume='" + resume + '\'' +
                        ", updateTime=" + updateTime +
                        '}';
            }
        }
    }
}

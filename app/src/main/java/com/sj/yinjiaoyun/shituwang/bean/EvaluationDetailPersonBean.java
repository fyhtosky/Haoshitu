package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 * 评价人的基本信息
 */

public class EvaluationDetailPersonBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : {"id":39,"studyId":695,"stuId":694,"tchId":522,"status":0,"isTchEva":1,"isStuEva":1,"createTime":1513584973000,"updateTime":1513838811000,"teacher":null,"student":{"id":694,"uuid":"b9230fc320114488a37f97b60777e0d8","username":null,"realName":"张昭文","sex":1,"mobile":"15652576834","password":"3d231a24ba28c8770be888df3eb4e413","status":1,"schoolId":16,"createTime":1513233302000,"email":"ycs@163.com","weChat":null,"updateTime":1513652035000,"birthday":1502035200,"addressNow":"上海市,上海市,黄浦区","department":"","stuNo":"120111104","citizenship":94,"moralTrait":71,"taste":91,"study":66,"logicalMathe":82,"selfCognitive":94,"subjectAttainment":80,"interaction":65,"space":65,"workStatus":1,"head":"http://d1.5xuexi.com/group1/M00/00/30/i-AgeFo3NHiAYZxOAAFTIVR-lO4144.png","schoolName":"四川大学","description":"英语课课练","isStudy":null,"studyId":null,"evaluationLabels":"","contactId":null,"lastLogin":1513826797000,"resumes":[{"id":647,"stuUserId":694,"resumeType":1,"resume":"{\"address\":\"北京市,北京市,东城区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0003\",\"expectSalary\":\"P0032\"}","updateTime":1513567062000},{"id":648,"stuUserId":694,"resumeType":2,"resume":"{\"beginTime\":1346428800,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"endTime\":1464710400,\"majorName\":\"计算机技术\"}","updateTime":1513567121000},{"id":652,"stuUserId":694,"resumeType":3,"resume":"{\"beginTime\":1446307200,\"companyBusiness\":\"R0002,P0016,S0095\",\"companyName\":\"太阳红文化科技公司\",\"endTime\":1480521600,\"positionName\":\"编导助理\",\"positionType\":\"R0001,P0006,S0035,T0588\",\"workDesc\":\"咿呀咿呀哟\",\"workType\":\"R0007,P0037\"}","updateTime":1513567316000},{"id":649,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"梧桐泉寺\"}","updateTime":1513567146000},{"id":650,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"大学英语六级\"}","updateTime":1513567146000},{"id":651,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"法语\"}","updateTime":1513567160000}],"resumePerfectDegree":62.5}}
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
         * id : 39
         * studyId : 695
         * stuId : 694
         * tchId : 522
         * status : 0
         * isTchEva : 1
         * isStuEva : 1
         * createTime : 1513584973000
         * updateTime : 1513838811000
         * teacher : null
         * student : {"id":694,"uuid":"b9230fc320114488a37f97b60777e0d8","username":null,"realName":"张昭文","sex":1,"mobile":"15652576834","password":"3d231a24ba28c8770be888df3eb4e413","status":1,"schoolId":16,"createTime":1513233302000,"email":"ycs@163.com","weChat":null,"updateTime":1513652035000,"birthday":1502035200,"addressNow":"上海市,上海市,黄浦区","department":"","stuNo":"120111104","citizenship":94,"moralTrait":71,"taste":91,"study":66,"logicalMathe":82,"selfCognitive":94,"subjectAttainment":80,"interaction":65,"space":65,"workStatus":1,"head":"http://d1.5xuexi.com/group1/M00/00/30/i-AgeFo3NHiAYZxOAAFTIVR-lO4144.png","schoolName":"四川大学","description":"英语课课练","isStudy":null,"studyId":null,"evaluationLabels":"","contactId":null,"lastLogin":1513826797000,"resumes":[{"id":647,"stuUserId":694,"resumeType":1,"resume":"{\"address\":\"北京市,北京市,东城区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0003\",\"expectSalary\":\"P0032\"}","updateTime":1513567062000},{"id":648,"stuUserId":694,"resumeType":2,"resume":"{\"beginTime\":1346428800,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"endTime\":1464710400,\"majorName\":\"计算机技术\"}","updateTime":1513567121000},{"id":652,"stuUserId":694,"resumeType":3,"resume":"{\"beginTime\":1446307200,\"companyBusiness\":\"R0002,P0016,S0095\",\"companyName\":\"太阳红文化科技公司\",\"endTime\":1480521600,\"positionName\":\"编导助理\",\"positionType\":\"R0001,P0006,S0035,T0588\",\"workDesc\":\"咿呀咿呀哟\",\"workType\":\"R0007,P0037\"}","updateTime":1513567316000},{"id":649,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"梧桐泉寺\"}","updateTime":1513567146000},{"id":650,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"大学英语六级\"}","updateTime":1513567146000},{"id":651,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"法语\"}","updateTime":1513567160000}],"resumePerfectDegree":62.5}
         */

        private int id;
        private int studyId;
        private int stuId;
        private int tchId;
        private int status;
        private int isTchEva;
        private int isStuEva;
        private long createTime;
        private long updateTime;
        private TeacherBean teacher;
        private StudentBean student;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStudyId() {
            return studyId;
        }

        public void setStudyId(int studyId) {
            this.studyId = studyId;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsTchEva() {
            return isTchEva;
        }

        public void setIsTchEva(int isTchEva) {
            this.isTchEva = isTchEva;
        }

        public int getIsStuEva() {
            return isStuEva;
        }

        public void setIsStuEva(int isStuEva) {
            this.isStuEva = isStuEva;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public TeacherBean getTeacher() {
            return teacher;
        }

        public void setTeacher(TeacherBean teacher) {
            this.teacher = teacher;
        }

        public StudentBean getStudent() {
            return student;
        }

        public void setStudent(StudentBean student) {
            this.student = student;
        }
        public static class TeacherBean {
            /**
             * id : 522
             * realName : 李小依
             * companyImg : http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg
             * companyName : aa上海银教云数据系统有限公司武汉分公司
             * positionId : 美容导师
             * skills : null
             * evaluationLabels :
             * sex : 0
             * imgUrl : http://139.196.255.175/group1/M00/00/52/i8T_r1nAxbqAb7jgAAGQqQQdWjk608.png
             * citizenship : 88
             * moralTrait : 94
             * taste : 82
             * study : 85
             * logicalMathe : 80
             * selfCognitive : 85
             * subjectAttainment : 86
             * interaction : 87
             * space : 88
             * password : 1e11dc20b0646c8054fdeabe7705b629
             * mobile : 13387622553
             * email : 110@qq.com
             * weChat : null
             */

            private int id;
            private String realName;
            private String companyImg;
            private String companyName;
            private String positionId;
            private List<SkillsBean> skills;
            private String evaluationLabels;
            private String sex;
            private String imgUrl;
            private int citizenship;
            private int moralTrait;
            private int taste;
            private int study;
            private int logicalMathe;
            private int selfCognitive;
            private int subjectAttainment;
            private int interaction;
            private int space;
            private String password;
            private String mobile;
            private String email;
            private Object weChat;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getCompanyImg() {
                return companyImg;
            }

            public void setCompanyImg(String companyImg) {
                this.companyImg = companyImg;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getPositionId() {
                return positionId;
            }

            public void setPositionId(String positionId) {
                this.positionId = positionId;
            }

            public List<SkillsBean> getSkills() {
                return skills;
            }

            public void setSkills(List<SkillsBean> skills) {
                this.skills = skills;
            }

            public String getEvaluationLabels() {
                return evaluationLabels;
            }

            public void setEvaluationLabels(String evaluationLabels) {
                this.evaluationLabels = evaluationLabels;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
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

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getWeChat() {
                return weChat;
            }

            public void setWeChat(Object weChat) {
                this.weChat = weChat;
            }
            public static class SkillsBean {
                /**
                 * skillName : gggggggh
                 * skillLevel : R0008,P0040
                 */

                private String skillName;
                private String skillLevel;

                public String getSkillName() {
                    return skillName;
                }

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public String getSkillLevel() {
                    return skillLevel;
                }

                public void setSkillLevel(String skillLevel) {
                    this.skillLevel = skillLevel;
                }
            }
        }
        public static class StudentBean {
            /**
             * id : 694
             * uuid : b9230fc320114488a37f97b60777e0d8
             * username : null
             * realName : 张昭文
             * sex : 1
             * mobile : 15652576834
             * password : 3d231a24ba28c8770be888df3eb4e413
             * status : 1
             * schoolId : 16
             * createTime : 1513233302000
             * email : ycs@163.com
             * weChat : null
             * updateTime : 1513652035000
             * birthday : 1502035200
             * addressNow : 上海市,上海市,黄浦区
             * department :
             * stuNo : 120111104
             * citizenship : 94
             * moralTrait : 71
             * taste : 91
             * study : 66
             * logicalMathe : 82
             * selfCognitive : 94
             * subjectAttainment : 80
             * interaction : 65
             * space : 65
             * workStatus : 1
             * head : http://d1.5xuexi.com/group1/M00/00/30/i-AgeFo3NHiAYZxOAAFTIVR-lO4144.png
             * schoolName : 四川大学
             * description : 英语课课练
             * isStudy : null
             * studyId : null
             * evaluationLabels :
             * contactId : null
             * lastLogin : 1513826797000
             * resumes : [{"id":647,"stuUserId":694,"resumeType":1,"resume":"{\"address\":\"北京市,北京市,东城区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0003\",\"expectSalary\":\"P0032\"}","updateTime":1513567062000},{"id":648,"stuUserId":694,"resumeType":2,"resume":"{\"beginTime\":1346428800,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"endTime\":1464710400,\"majorName\":\"计算机技术\"}","updateTime":1513567121000},{"id":652,"stuUserId":694,"resumeType":3,"resume":"{\"beginTime\":1446307200,\"companyBusiness\":\"R0002,P0016,S0095\",\"companyName\":\"太阳红文化科技公司\",\"endTime\":1480521600,\"positionName\":\"编导助理\",\"positionType\":\"R0001,P0006,S0035,T0588\",\"workDesc\":\"咿呀咿呀哟\",\"workType\":\"R0007,P0037\"}","updateTime":1513567316000},{"id":649,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"梧桐泉寺\"}","updateTime":1513567146000},{"id":650,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"大学英语六级\"}","updateTime":1513567146000},{"id":651,"stuUserId":694,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"法语\"}","updateTime":1513567160000}]
             * resumePerfectDegree : 62.5
             */

            private int id;
            private String uuid;
            private Object username;
            private String realName;
            private int sex;
            private String mobile;
            private String password;
            private int status;
            private int schoolId;
            private long createTime;
            private String email;
            private Object weChat;
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
            private Object isStudy;
            private Object studyId;
            private String evaluationLabels;
            private Object contactId;
            private long lastLogin;
            private double resumePerfectDegree;
            private List<ResumesBean> resumes;

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

            public Object getUsername() {
                return username;
            }

            public void setUsername(Object username) {
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

            public Object getWeChat() {
                return weChat;
            }

            public void setWeChat(Object weChat) {
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

            public Object getIsStudy() {
                return isStudy;
            }

            public void setIsStudy(Object isStudy) {
                this.isStudy = isStudy;
            }

            public Object getStudyId() {
                return studyId;
            }

            public void setStudyId(Object studyId) {
                this.studyId = studyId;
            }

            public String getEvaluationLabels() {
                return evaluationLabels;
            }

            public void setEvaluationLabels(String evaluationLabels) {
                this.evaluationLabels = evaluationLabels;
            }

            public Object getContactId() {
                return contactId;
            }

            public void setContactId(Object contactId) {
                this.contactId = contactId;
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

            public static class ResumesBean {
                /**
                 * id : 647
                 * stuUserId : 694
                 * resumeType : 1
                 * resume : {"address":"北京市,北京市,东城区","expectBusiness":"R0002,P0012,S0066","expectPosition":"R0001,P0001,S0001,T0003","expectSalary":"P0032"}
                 * updateTime : 1513567062000
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
            }
        }
    }
}

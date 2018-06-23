package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/7.
 */
public class StudyDetailBean {

    /**
     * status : 200
     * msg : 获取面试详情信息成功！
     * data : {"id":86,"stuId":264,"tchId":527,"currentStatus":110,"interviewGoal":1,"interviewTime":1497078960000,"interviewPhone":"13400000001","interviewAddr":"ggggggggg","interviewDesc":"yy677y","interviewResultNote":null,"interviewRefuseNote":null,"interviewCancelNote":null,"internStartTime":null,"internEndTime":null,"internRefuseNote":null,"internCancelNote":null,"createTime":1493881355000,"updateTime":1497078961000,"teacher":{"id":527,"realName":"杨一一","companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海智隆信息技术股份有限公司","positionId":"UI设计师","skills":[{"skillName":"gggggggh","skillLevel":"R0008,P0040"}],"evaluationLabels":"[\"教授态度好\",\"思维严密\",\"尽职尽责\"]","sex":"1","imgUrl":"http://139.196.255.175/group1/M00/00/3B/i8T_r1kebBmAB_-TAAZWkHYx_X4.pictur"},"student":null,"studentDeatil":null}
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
         * id : 86
         * stuId : 264
         * tchId : 527
         * currentStatus : 110
         * interviewGoal : 1
         * interviewTime : 1497078960000
         * interviewPhone : 13400000001
         * interviewAddr : ggggggggg
         * interviewDesc : yy677y
         * interviewResultNote : null
         * interviewRefuseNote : null
         * interviewCancelNote : null
         * internStartTime : null
         * internEndTime : null
         * internRefuseNote : null
         * internCancelNote : null
         * createTime : 1493881355000
         * updateTime : 1497078961000
         * teacher : {"id":527,"realName":"杨一一","companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海智隆信息技术股份有限公司","positionId":"UI设计师","skills":[{"skillName":"gggggggh","skillLevel":"R0008,P0040"}],"evaluationLabels":"[\"教授态度好\",\"思维严密\",\"尽职尽责\"]","sex":"1","imgUrl":"http://139.196.255.175/group1/M00/00/3B/i8T_r1kebBmAB_-TAAZWkHYx_X4.pictur"}
         * student : null
         * studentDeatil : null
         */

        private int id;
        private int stuId;
        private int tchId;
        private int currentStatus;
        private int interviewGoal;
        private String interviewTime;
        private String interviewPhone;
        private String interviewAddr;
        private String interviewDesc;
        private String interviewResultNote;
        private String interviewRefuseNote;
        private String interviewCancelNote;
        private String internStartTime;
        private String internEndTime;
        private String internRefuseNote;
        private String internCancelNote;
        private long createTime;
        private long updateTime;
        private TeacherBean teacher;
        private StudentBean student;
        private StudentDeatilBean studentDeatil;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(int currentStatus) {
            this.currentStatus = currentStatus;
        }

        public int getInterviewGoal() {
            return interviewGoal;
        }

        public void setInterviewGoal(int interviewGoal) {
            this.interviewGoal = interviewGoal;
        }

        public String getInterviewTime() {
            return interviewTime;
        }

        public void setInterviewTime(String interviewTime) {
            this.interviewTime = interviewTime;
        }

        public String getInterviewPhone() {
            return interviewPhone;
        }

        public void setInterviewPhone(String interviewPhone) {
            this.interviewPhone = interviewPhone;
        }

        public String getInterviewAddr() {
            return interviewAddr;
        }

        public void setInterviewAddr(String interviewAddr) {
            this.interviewAddr = interviewAddr;
        }

        public String getInterviewDesc() {
            return interviewDesc;
        }

        public void setInterviewDesc(String interviewDesc) {
            this.interviewDesc = interviewDesc;
        }

        public String getInterviewResultNote() {
            return interviewResultNote;
        }

        public void setInterviewResultNote(String interviewResultNote) {
            this.interviewResultNote = interviewResultNote;
        }

        public String getInterviewRefuseNote() {
            return interviewRefuseNote;
        }

        public void setInterviewRefuseNote(String interviewRefuseNote) {
            this.interviewRefuseNote = interviewRefuseNote;
        }

        public String getInterviewCancelNote() {
            return interviewCancelNote;
        }

        public void setInterviewCancelNote(String interviewCancelNote) {
            this.interviewCancelNote = interviewCancelNote;
        }

        public String getInternStartTime() {
            return internStartTime;
        }

        public void setInternStartTime(String internStartTime) {
            this.internStartTime = internStartTime;
        }

        public String getInternEndTime() {
            return internEndTime;
        }

        public void setInternEndTime(String internEndTime) {
            this.internEndTime = internEndTime;
        }

        public String getInternRefuseNote() {
            return internRefuseNote;
        }

        public void setInternRefuseNote(String internRefuseNote) {
            this.internRefuseNote = internRefuseNote;
        }

        public String getInternCancelNote() {
            return internCancelNote;
        }

        public void setInternCancelNote(String internCancelNote) {
            this.internCancelNote = internCancelNote;
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

        public StudentDeatilBean getStudentDeatil() {
            return studentDeatil;
        }

        public void setStudentDeatil(StudentDeatilBean studentDeatil) {
            this.studentDeatil = studentDeatil;
        }

        public static class TeacherBean {
            /**
             * id : 527
             * realName : 杨一一
             * companyImg : http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg
             * companyName : 上海智隆信息技术股份有限公司
             * positionId : UI设计师
             * skills : [{"skillName":"gggggggh","skillLevel":"R0008,P0040"}]
             * evaluationLabels : ["教授态度好","思维严密","尽职尽责"]
             * sex : 1
             * imgUrl : http://139.196.255.175/group1/M00/00/3B/i8T_r1kebBmAB_-TAAZWkHYx_X4.pictur
             */

            private int id;
            private String realName;
            private String companyImg;
            private String companyName;
            private String positionId;
            private String evaluationLabels;
            private String sex;
            private String imgUrl;
            private List<SkillsBean> skills;

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

            public List<SkillsBean> getSkills() {
                return skills;
            }

            public void setSkills(List<SkillsBean> skills) {
                this.skills = skills;
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
        public static class StudentBean{

            private  String realName;
            private String  head;

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }
        public static class StudentDeatilBean{


            /**
             * id : 568
             * uuid : 88dd7cb5744a4e8b99f2b122d600bc75
             * username :
             * realName : 李翠花2
             * sex : 1
             * mobile : 15800000002
             * password : b6450492e82dde0afd127285ee5a65c8
             * status : 1
             * schoolId : 1
             * createTime : 1495072769000
             * email :
             * weChat :
             * updateTime : 1495073136000
             * birthday : null
             * addressNow :
             * department :
             * stuNo :
             * citizenship : 98
             * moralTrait : 79
             * taste : 99
             * study : 71
             * logicalMathe : 88
             * selfCognitive : 96
             * subjectAttainment : 75
             * interaction : 100
             * space : 91
             * workStatus : 0
             * head :
             * schoolName : null
             * description :
             * isStudy : null
             * studyId : null
             * evaluationLabels :
             * lastLogin : 1499939249000
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
            private String createTime;
            private String email;
            private String weChat;
            private String updateTime;
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
            private String schoolName;
            private String description;
            private int isStudy;
            private int studyId;
            private String evaluationLabels;
            private long lastLogin;
            private List<ResumesBean> resumes;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
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

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
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

            public List<ResumesBean> getResumes() {
                return resumes;
            }

            public void setResumes(List<ResumesBean> resumes) {
                this.resumes = resumes;
            }

            public double getResumePerfectDegree() {
                return resumePerfectDegree;
            }

            public void setResumePerfectDegree(double resumePerfectDegree) {
                this.resumePerfectDegree = resumePerfectDegree;
            }

            public static class ResumesBean {
                /**
                 * id : 86
                 * stuUserId : 505
                 * resumeType : 5
                 * resume : {"beginTime":1325376000,"endTime":1341100800,"trainingCertificateName":"我学习","trainingCourseName":"开发","trainingOrgName":"武汉人人科技"}
                 * updateTime : 1493343033000
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

package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/9.
 * 学生查询评价的列表封装的实体类
 */
public class StuEvaBean implements Serializable {


    /**
     * status : 200
     * msg : 加载评价列表信息成功!
     * data : {"total":3,"rows":[{"id":25,"studyId":232,"stuId":507,"tchId":530,"status":0,"isTchEva":0,"isStuEva":1,"createTime":1499942939000,"updateTime":1501437677000,"teacher":{"id":530,"realName":"橙子师傅7","companyImg":null,"companyName":"上海智隆信息技术股份有限公司","positionId":"小说家","skills":null,"evaluationLabels":"[\"尽职尽责\",\"教授态度好\",\"资深大牛\"]","sex":"1","imgUrl":""},"student":null},{"id":26,"studyId":234,"stuId":507,"tchId":533,"status":1,"isTchEva":0,"isStuEva":1,"createTime":1499943409000,"updateTime":1501233319000,"teacher":{"id":533,"realName":"橙子师傅4","companyImg":null,"companyName":"上海智隆信息技术股份有限公司","positionId":"万达影院","skills":null,"evaluationLabels":"[\"资深大牛\",\"和蔼可亲\"]","sex":"1","imgUrl":""},"student":null}],"pageSize":10,"pageNo":1}
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

    public static class DataBean implements Serializable{
        /**
         * total : 3
         * rows : [{"id":25,"studyId":232,"stuId":507,"tchId":530,"status":0,"isTchEva":0,"isStuEva":1,"createTime":1499942939000,"updateTime":1501437677000,"teacher":{"id":530,"realName":"橙子师傅7","companyImg":null,"companyName":"上海智隆信息技术股份有限公司","positionId":"小说家","skills":null,"evaluationLabels":"[\"尽职尽责\",\"教授态度好\",\"资深大牛\"]","sex":"1","imgUrl":""},"student":null},{"id":26,"studyId":234,"stuId":507,"tchId":533,"status":1,"isTchEva":0,"isStuEva":1,"createTime":1499943409000,"updateTime":1501233319000,"teacher":{"id":533,"realName":"橙子师傅4","companyImg":null,"companyName":"上海智隆信息技术股份有限公司","positionId":"万达影院","skills":null,"evaluationLabels":"[\"资深大牛\",\"和蔼可亲\"]","sex":"1","imgUrl":""},"student":null}]
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

        public static class RowsBean implements Serializable {
            /**
             * id : 25
             * studyId : 232
             * stuId : 507
             * tchId : 530
             * status : 0
             * isTchEva : 0
             * isStuEva : 1
             * createTime : 1499942939000
             * updateTime : 1501437677000
             * teacher : {"id":530,"realName":"橙子师傅7","companyImg":null,"companyName":"上海智隆信息技术股份有限公司","positionId":"小说家","skills":null,"evaluationLabels":"[\"尽职尽责\",\"教授态度好\",\"资深大牛\"]","sex":"1","imgUrl":""}
             * student : null
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

            public static class TeacherBean implements Serializable{
                /**
                 * id : 530
                 * realName : 橙子师傅7
                 * companyImg : null
                 * companyName : 上海智隆信息技术股份有限公司
                 * positionId : 小说家
                 * skills : null
                 * evaluationLabels : ["尽职尽责","教授态度好","资深大牛"]
                 * sex : 1
                 * imgUrl :
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

                public static class SkillsBean implements Serializable {
                    /**
                     * skillName : 陌陌墨迹hulk
                     * skillLevel : R0008,P0039
                     * resumeId : 629
                     */

                    private String skillName;
                    private String skillLevel;
                    private int resumeId;

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

                    public int getResumeId() {
                        return resumeId;
                    }

                    public void setResumeId(int resumeId) {
                        this.resumeId = resumeId;
                    }
                }
            }
            public static class StudentBean implements Serializable{
                /**
                 * id : 511
                 * uuid : 9528543125184e1ca83c8bc782121c2f
                 * username : null
                 * realName : 张六
                 * sex : 1
                 * mobile : 18900000005
                 * password : 1e11dc20b0646c8054fdeabe7705b629
                 * status : 1
                 * schoolId : 1
                 * createTime : 1493279486000
                 * email :
                 * weChat : null
                 * updateTime : 1493281764000
                 * birthday : 662659200
                 * addressNow : 天津市,河东区
                 * department :
                 * stuNo :
                 * citizenship : 74
                 * moralTrait : 100
                 * taste : 67
                 * study : 92
                 * logicalMathe : 69
                 * selfCognitive : 70
                 * subjectAttainment : 71
                 * interaction : 85
                 * space : 90
                 * workStatus : 1
                 * head :
                 * schoolName : null
                 * description :
                 * isStudy : null
                 * studyId : null
                 * evaluationLabels : ["能力出众"]
                 * contactId : null
                 * lastLogin : 1499940513000
                 * resumes : null
                 * resumePerfectDegree : 0
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
                private String isStudy;
                private String studyId;
                private String evaluationLabels;
                private String contactId;
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

                public String getIsStudy() {
                    return isStudy;
                }

                public void setIsStudy(String isStudy) {
                    this.isStudy = isStudy;
                }

                public String getStudyId() {
                    return studyId;
                }

                public void setStudyId(String studyId) {
                    this.studyId = studyId;
                }

                public String getEvaluationLabels() {
                    return evaluationLabels;
                }

                public void setEvaluationLabels(String evaluationLabels) {
                    this.evaluationLabels = evaluationLabels;
                }

                public String getContactId() {
                    return contactId;
                }

                public void setContactId(String contactId) {
                    this.contactId = contactId;
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
                public static class ResumesBean implements Serializable {
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
}

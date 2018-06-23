package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/15.
 */
public class EvaBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : {"id":64,"evaluateId":25,"stuId":507,"tchId":530,"parameter1":5,"parameter2":5,"parameter3":5,"labels":"[\"R0010,P0055\",\"R0010,P0056\",\"R0010,P0057\",\"R0010,P0060\",\"R0010,P0059\",\"R0010,P0058\"]","notes":"按时打算达的阿斯顿撒旦撒","isTch":1,"createTime":1502370801000,"name":null,"teacher":{"id":530,"uuid":"1657dd59ce22434994ac8bca253010ff","realName":"橙子师傅7","sex":"1","password":"1e11dc20b0646c8054fdeabe7705b629","addressNow":"北京市,朝阳区","positionId":"小说家","mobile":"13437177700","email":null,"weChat":null,"companyId":1,"imgUrl":"","citizenship":93,"moralTrait":88,"taste":80,"study":84,"logicalMathe":96,"selfCognitive":85,"subjectAttainment":86,"interaction":87,"space":88,"isShowRadar":1,"status":1,"updateTime":1493282313000,"createTime":1493282019000,"lastLogin":1499942887000,"isDeleted":0,"isRecommended":1,"evaluationLabels":"[\"尽职尽责\",\"教授态度好\",\"资深大牛\"]","startWorkTime":null,"workList":null,"isStudy":null,"isEnshrine":null,"companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海智隆信息技术股份有限公司","type":"教育行业","scale":"100-500人","tchResumeVO":{"id":null,"tchId":null,"resumeType":null,"resume":null,"updateTime":null,"tchAbilities":[{"abilityName":"会讲笑话","abilityValue":"90","resumeId":131},{"abilityName":"会java","abilityValue":"75","resumeId":132},{"abilityName":"交际能力强","abilityValue":"60","resumeId":133},{"abilityName":"回打豆豆","abilityValue":"75","resumeId":134}],"tchSkills":null,"tchWorkExps":null,"tchRequireDesc":{"requireDesc":"记得记得今生今世计算机技术监督局大家放放风","resumeId":130}},"workingLife":null,"skills":null,"contactId":null},"student":null}
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
         * name : null
         * teacher : {"id":530,"uuid":"1657dd59ce22434994ac8bca253010ff","realName":"橙子师傅7","sex":"1","password":"1e11dc20b0646c8054fdeabe7705b629","addressNow":"北京市,朝阳区","positionId":"小说家","mobile":"13437177700","email":null,"weChat":null,"companyId":1,"imgUrl":"","citizenship":93,"moralTrait":88,"taste":80,"study":84,"logicalMathe":96,"selfCognitive":85,"subjectAttainment":86,"interaction":87,"space":88,"isShowRadar":1,"status":1,"updateTime":1493282313000,"createTime":1493282019000,"lastLogin":1499942887000,"isDeleted":0,"isRecommended":1,"evaluationLabels":"[\"尽职尽责\",\"教授态度好\",\"资深大牛\"]","startWorkTime":null,"workList":null,"isStudy":null,"isEnshrine":null,"companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海智隆信息技术股份有限公司","type":"教育行业","scale":"100-500人","tchResumeVO":{"id":null,"tchId":null,"resumeType":null,"resume":null,"updateTime":null,"tchAbilities":[{"abilityName":"会讲笑话","abilityValue":"90","resumeId":131},{"abilityName":"会java","abilityValue":"75","resumeId":132},{"abilityName":"交际能力强","abilityValue":"60","resumeId":133},{"abilityName":"回打豆豆","abilityValue":"75","resumeId":134}],"tchSkills":null,"tchWorkExps":null,"tchRequireDesc":{"requireDesc":"记得记得今生今世计算机技术监督局大家放放风","resumeId":130}},"workingLife":null,"skills":null,"contactId":null}
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
        private TeacherBean teacher;
        private StudentBean student;

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
             * id : 530
             * uuid : 1657dd59ce22434994ac8bca253010ff
             * realName : 橙子师傅7
             * sex : 1
             * password : 1e11dc20b0646c8054fdeabe7705b629
             * addressNow : 北京市,朝阳区
             * positionId : 小说家
             * mobile : 13437177700
             * email : null
             * weChat : null
             * companyId : 1
             * imgUrl :
             * citizenship : 93
             * moralTrait : 88
             * taste : 80
             * study : 84
             * logicalMathe : 96
             * selfCognitive : 85
             * subjectAttainment : 86
             * interaction : 87
             * space : 88
             * isShowRadar : 1
             * status : 1
             * updateTime : 1493282313000
             * createTime : 1493282019000
             * lastLogin : 1499942887000
             * isDeleted : 0
             * isRecommended : 1
             * evaluationLabels : ["尽职尽责","教授态度好","资深大牛"]
             * startWorkTime : null
             * workList : null
             * isStudy : null
             * isEnshrine : null
             * companyImg : http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg
             * companyName : 上海智隆信息技术股份有限公司
             * type : 教育行业
             * scale : 100-500人
             * tchResumeVO : {"id":null,"tchId":null,"resumeType":null,"resume":null,"updateTime":null,"tchAbilities":[{"abilityName":"会讲笑话","abilityValue":"90","resumeId":131},{"abilityName":"会java","abilityValue":"75","resumeId":132},{"abilityName":"交际能力强","abilityValue":"60","resumeId":133},{"abilityName":"回打豆豆","abilityValue":"75","resumeId":134}],"tchSkills":null,"tchWorkExps":null,"tchRequireDesc":{"requireDesc":"记得记得今生今世计算机技术监督局大家放放风","resumeId":130}}
             * workingLife : null
             * skills : null
             * contactId : null
             */

            private int id;
            private String uuid;
            private String realName;
            private String sex;
            private String password;
            private String addressNow;
            private String positionId;
            private String mobile;
            private String email;
            private String weChat;
            private int companyId;
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
            private int isShowRadar;
            private int status;
            private long updateTime;
            private long createTime;
            private long lastLogin;
            private int isDeleted;
            private int isRecommended;
            private String evaluationLabels;
            private long startWorkTime;
            private Object workList;
            private String isStudy;
            private String isEnshrine;
            private String companyImg;
            private String companyName;
            private String type;
            private String scale;
            private TchResumeVOBean tchResumeVO;
            private String workingLife;
            private String skills;
            private String contactId;

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

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getAddressNow() {
                return addressNow;
            }

            public void setAddressNow(String addressNow) {
                this.addressNow = addressNow;
            }

            public String getPositionId() {
                return positionId;
            }

            public void setPositionId(String positionId) {
                this.positionId = positionId;
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

            public String getWeChat() {
                return weChat;
            }

            public void setWeChat(String weChat) {
                this.weChat = weChat;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
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

            public int getSpace() {
                return space;
            }

            public void setSpace(int space) {
                this.space = space;
            }

            public int getInteraction() {
                return interaction;
            }

            public void setInteraction(int interaction) {
                this.interaction = interaction;
            }

            public int getIsShowRadar() {
                return isShowRadar;
            }

            public void setIsShowRadar(int isShowRadar) {
                this.isShowRadar = isShowRadar;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getLastLogin() {
                return lastLogin;
            }

            public void setLastLogin(long lastLogin) {
                this.lastLogin = lastLogin;
            }

            public int getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
            }

            public int getIsRecommended() {
                return isRecommended;
            }

            public void setIsRecommended(int isRecommended) {
                this.isRecommended = isRecommended;
            }

            public String getEvaluationLabels() {
                return evaluationLabels;
            }

            public void setEvaluationLabels(String evaluationLabels) {
                this.evaluationLabels = evaluationLabels;
            }

            public long getStartWorkTime() {
                return startWorkTime;
            }

            public void setStartWorkTime(long startWorkTime) {
                this.startWorkTime = startWorkTime;
            }

            public Object getWorkList() {
                return workList;
            }

            public void setWorkList(Object workList) {
                this.workList = workList;
            }

            public String getIsStudy() {
                return isStudy;
            }

            public void setIsStudy(String isStudy) {
                this.isStudy = isStudy;
            }

            public String getIsEnshrine() {
                return isEnshrine;
            }

            public void setIsEnshrine(String isEnshrine) {
                this.isEnshrine = isEnshrine;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
                this.scale = scale;
            }

            public TchResumeVOBean getTchResumeVO() {
                return tchResumeVO;
            }

            public void setTchResumeVO(TchResumeVOBean tchResumeVO) {
                this.tchResumeVO = tchResumeVO;
            }

            public String getWorkingLife() {
                return workingLife;
            }

            public void setWorkingLife(String workingLife) {
                this.workingLife = workingLife;
            }

            public String getSkills() {
                return skills;
            }

            public void setSkills(String skills) {
                this.skills = skills;
            }

            public String getContactId() {
                return contactId;
            }

            public void setContactId(String contactId) {
                this.contactId = contactId;
            }

            public static class TchResumeVOBean {
                /**
                 * id : null
                 * tchId : null
                 * resumeType : null
                 * resume : null
                 * updateTime : null
                 * tchAbilities : [{"abilityName":"会讲笑话","abilityValue":"90","resumeId":131},{"abilityName":"会java","abilityValue":"75","resumeId":132},{"abilityName":"交际能力强","abilityValue":"60","resumeId":133},{"abilityName":"回打豆豆","abilityValue":"75","resumeId":134}]
                 * tchSkills : null
                 * tchWorkExps : null
                 * tchRequireDesc : {"requireDesc":"记得记得今生今世计算机技术监督局大家放放风","resumeId":130}
                 */

                private int id;
                private String tchId;
                private String resumeType;
                private String resume;
                private String updateTime;
                private List<TckSkillsBean> tchSkills;
                private List<TchWorkExpsBean> tchWorkExps;
                private TchRequireDescBean tchRequireDesc;
                private List<TchAbilitiesBean> tchAbilities;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTchId() {
                    return tchId;
                }

                public void setTchId(String tchId) {
                    this.tchId = tchId;
                }

                public String getResumeType() {
                    return resumeType;
                }

                public void setResumeType(String resumeType) {
                    this.resumeType = resumeType;
                }

                public String getResume() {
                    return resume;
                }

                public void setResume(String resume) {
                    this.resume = resume;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public List<TckSkillsBean> getTchSkills() {
                    return tchSkills;
                }

                public void setTchSkills(List<TckSkillsBean> tchSkills) {
                    this.tchSkills = tchSkills;
                }

                public List<TchWorkExpsBean> getTchWorkExps() {
                    return tchWorkExps;
                }

                public void setTchWorkExps(List<TchWorkExpsBean> tchWorkExps) {
                    this.tchWorkExps = tchWorkExps;
                }

                public TchRequireDescBean getTchRequireDesc() {
                    return tchRequireDesc;
                }

                public void setTchRequireDesc(TchRequireDescBean tchRequireDesc) {
                    this.tchRequireDesc = tchRequireDesc;
                }

                public List<TchAbilitiesBean> getTchAbilities() {
                    return tchAbilities;
                }

                public void setTchAbilities(List<TchAbilitiesBean> tchAbilities) {
                    this.tchAbilities = tchAbilities;
                }

                public static class TchRequireDescBean {
                    /**
                     * requireDesc : 记得记得今生今世计算机技术监督局大家放放风
                     * resumeId : 130
                     */

                    private String requireDesc;
                    private int resumeId;

                    public String getRequireDesc() {
                        return requireDesc;
                    }

                    public void setRequireDesc(String requireDesc) {
                        this.requireDesc = requireDesc;
                    }

                    public int getResumeId() {
                        return resumeId;
                    }

                    public void setResumeId(int resumeId) {
                        this.resumeId = resumeId;
                    }
                }

                public static class TchAbilitiesBean {
                    /**
                     * abilityName : 会讲笑话
                     * abilityValue : 90
                     * resumeId : 131
                     */

                    private String abilityName;
                    private String abilityValue;
                    private int resumeId;

                    public String getAbilityName() {
                        return abilityName;
                    }

                    public void setAbilityName(String abilityName) {
                        this.abilityName = abilityName;
                    }

                    public String getAbilityValue() {
                        return abilityValue;
                    }

                    public void setAbilityValue(String abilityValue) {
                        this.abilityValue = abilityValue;
                    }

                    public int getResumeId() {
                        return resumeId;
                    }

                    public void setResumeId(int resumeId) {
                        this.resumeId = resumeId;
                    }
                }
                public static class  TckSkillsBean{
                    private String skillName;
                    private String skillLevel;
                    private String resumeId;

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

                    public String getResumeId() {
                        return resumeId;
                    }

                    public void setResumeId(String resumeId) {
                        this.resumeId = resumeId;
                    }
                }
                public static class TchWorkExpsBean{

                    /**
                     * position : 五台山是GG
                     * companyName : 转于僮仆亲如图
                     * beginDate : 1177948800
                     * endDate : 1246377600
                     * positionDesc :
                     * resumeId : 690
                     */

                    private String position;
                    private String companyName;
                    private String beginDate;
                    private String endDate;
                    private String positionDesc;
                    private String resumeId;

                    public String getPosition() {
                        return position;
                    }

                    public void setPosition(String position) {
                        this.position = position;
                    }

                    public String getCompanyName() {
                        return companyName;
                    }

                    public void setCompanyName(String companyName) {
                        this.companyName = companyName;
                    }

                    public String getBeginDate() {
                        return beginDate;
                    }

                    public void setBeginDate(String beginDate) {
                        this.beginDate = beginDate;
                    }

                    public String getEndDate() {
                        return endDate;
                    }

                    public void setEndDate(String endDate) {
                        this.endDate = endDate;
                    }

                    public String getPositionDesc() {
                        return positionDesc;
                    }

                    public void setPositionDesc(String positionDesc) {
                        this.positionDesc = positionDesc;
                    }

                    public String getResumeId() {
                        return resumeId;
                    }

                    public void setResumeId(String resumeId) {
                        this.resumeId = resumeId;
                    }
                }
            }
        }
        public static class StudentBean implements Serializable {
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

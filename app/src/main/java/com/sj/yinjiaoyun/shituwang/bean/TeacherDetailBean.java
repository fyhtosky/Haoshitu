package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/21.
 * 老师的详细信息的封装类
 */
public class TeacherDetailBean  implements Serializable {

    /**
     * status : 200
     * msg : 加载师父信息成功!
     * data : {"id":568,"uuid":"82b9306200054639a2eea7b1fcc0a0fc","realName":"范远华","sex":"1","password":"9dea58781660c7daae763b9cd9e33148","addressNow":"湖北省,武汉市,江岸区","positionId":"美容导师","mobile":"18672593926","email":null,"weChat":null,"companyId":2,"imgUrl":"","citizenship":80,"moralTrait":97,"taste":79,"study":75,"logicalMathe":98,"selfCognitive":64,"subjectAttainment":76,"interaction":99,"space":84,"isShowRadar":1,"status":1,"updateTime":1497602788000,"createTime":1497257435000,"lastLogin":1497868408000,"isDeleted":0,"isRecommended":1,"evaluationLabels":"","workList":null,"isStudy":1,"isEnshrine":0,"companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海银教云数据系统有限公司武汉分公司","type":"软件开发","scale":"100-500人","tchResumeVO":{"id":null,"tchId":null,"resumeType":null,"resume":null,"updateTime":null,"tchAbilities":[{"abilityName":"默默无闻","abilityValue":"60","resumeId":630},{"abilityName":"嗯中央音乐学院","abilityValue":"60","resumeId":631},{"abilityName":"铜模学习","abilityValue":"60","resumeId":632},{"abilityName":"魔装学院","abilityValue":"100","resumeId":633}],"tchSkills":[{"skillName":"陌陌墨迹hulk","skillLevel":"R0008,P0039","resumeId":629}],"tchWorkExps":[{"position":"你问问你妈","companyName":"陌陌找我","beginDate":1483228800,"endDate":null,"positionDesc":"","resumeId":634}],"tchRequireDesc":{"requireDesc":"哦哦松松的抹额计算机专业默认哦哦你我考虑一下哦你王者荣耀寻龙诀今晚有空下雨","resumeId":635}},"skills":null,"contactId":509}
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

    public static class DataBean  implements Serializable {
        /**
         * id : 568
         * uuid : 82b9306200054639a2eea7b1fcc0a0fc
         * realName : 范远华
         * sex : 1
         * password : 9dea58781660c7daae763b9cd9e33148
         * addressNow : 湖北省,武汉市,江岸区
         * positionId : 美容导师
         * mobile : 18672593926
         * email : null
         * weChat : null
         * companyId : 2
         * imgUrl :
         * citizenship : 80
         * moralTrait : 97
         * taste : 79
         * study : 75
         * logicalMathe : 98
         * selfCognitive : 64
         * subjectAttainment : 76
         * interaction : 99
         * space : 84
         * isShowRadar : 1
         * status : 1
         * updateTime : 1497602788000
         * createTime : 1497257435000
         * lastLogin : 1497868408000
         * isDeleted : 0
         * isRecommended : 1
         * evaluationLabels :
         * workList : null
         * isStudy : 1
         * isEnshrine : 0
         * companyImg : http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg
         * companyName : 上海银教云数据系统有限公司武汉分公司
         * type : 软件开发
         * scale : 100-500人
         * tchResumeVO : {"id":null,"tchId":null,"resumeType":null,"resume":null,"updateTime":null,"tchAbilities":[{"abilityName":"默默无闻","abilityValue":"60","resumeId":630},{"abilityName":"嗯中央音乐学院","abilityValue":"60","resumeId":631},{"abilityName":"铜模学习","abilityValue":"60","resumeId":632},{"abilityName":"魔装学院","abilityValue":"100","resumeId":633}],"tchSkills":[{"skillName":"陌陌墨迹hulk","skillLevel":"R0008,P0039","resumeId":629}],"tchWorkExps":[{"position":"你问问你妈","companyName":"陌陌找我","beginDate":1483228800,"endDate":null,"positionDesc":"","resumeId":634}],"tchRequireDesc":{"requireDesc":"哦哦松松的抹额计算机专业默认哦哦你我考虑一下哦你王者荣耀寻龙诀今晚有空下雨","resumeId":635}}
         * skills : null
         * contactId : 509
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
        private Object workList;
        private int isStudy;
        private int isEnshrine;
        private String companyImg;
        private String companyName;
        private String type;
        private String scale;
        private TchResumeVOBean tchResumeVO;
        private Object skills;
        private String contactId;
        private int isDisabled;

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
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

        public Object getWorkList() {
            return workList;
        }

        public void setWorkList(Object workList) {
            this.workList = workList;
        }

        public int getIsStudy() {
            return isStudy;
        }

        public void setIsStudy(int isStudy) {
            this.isStudy = isStudy;
        }

        public int getIsEnshrine() {
            return isEnshrine;
        }

        public void setIsEnshrine(int isEnshrine) {
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

        public Object getSkills() {
            return skills;
        }

        public void setSkills(Object skills) {
            this.skills = skills;
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public static class TchResumeVOBean  implements Serializable{
            /**
             * id : null
             * tchId : null
             * resumeType : null
             * resume : null
             * updateTime : null
             * tchAbilities : [{"abilityName":"默默无闻","abilityValue":"60","resumeId":630},{"abilityName":"嗯中央音乐学院","abilityValue":"60","resumeId":631},{"abilityName":"铜模学习","abilityValue":"60","resumeId":632},{"abilityName":"魔装学院","abilityValue":"100","resumeId":633}]
             * tchSkills : [{"skillName":"陌陌墨迹hulk","skillLevel":"R0008,P0039","resumeId":629}]
             * tchWorkExps : [{"position":"你问问你妈","companyName":"陌陌找我","beginDate":1483228800,"endDate":null,"positionDesc":"","resumeId":634}]
             * tchRequireDesc : {"requireDesc":"哦哦松松的抹额计算机专业默认哦哦你我考虑一下哦你王者荣耀寻龙诀今晚有空下雨","resumeId":635}
             */

            private Long id;
            private Long tchId;
            private Long resumeType;
            private Long resume;
            private Long updateTime;
            private TchRequireDescBean tchRequireDesc;
            private List<TchAbilitiesBean> tchAbilities;
            private List<TchSkillsBean> tchSkills;
            private List<TchWorkExpsBean> tchWorkExps;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getTchId() {
                return tchId;
            }

            public void setTchId(Long tchId) {
                this.tchId = tchId;
            }

            public Long getResumeType() {
                return resumeType;
            }

            public void setResumeType(Long resumeType) {
                this.resumeType = resumeType;
            }

            public Long getResume() {
                return resume;
            }

            public void setResume(Long resume) {
                this.resume = resume;
            }

            public Long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Long updateTime) {
                this.updateTime = updateTime;
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

            public List<TchSkillsBean> getTchSkills() {
                return tchSkills;
            }

            public void setTchSkills(List<TchSkillsBean> tchSkills) {
                this.tchSkills = tchSkills;
            }

            public List<TchWorkExpsBean> getTchWorkExps() {
                return tchWorkExps;
            }

            public void setTchWorkExps(List<TchWorkExpsBean> tchWorkExps) {
                this.tchWorkExps = tchWorkExps;
            }

            public static class TchRequireDescBean  implements Serializable {
                /**
                 * requireDesc : 哦哦松松的抹额计算机专业默认哦哦你我考虑一下哦你王者荣耀寻龙诀今晚有空下雨
                 * resumeId : 635
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

            public static class TchAbilitiesBean  implements Serializable{
                /**
                 * abilityName : 默默无闻
                 * abilityValue : 60
                 * resumeId : 630
                 */

                private String abilityName;
                private String abilityValue;
                private String resumeId;

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

                public String getResumeId() {
                    return resumeId;
                }

                public void setResumeId(String resumeId) {
                    this.resumeId = resumeId;
                }


            }

            public static class TchSkillsBean  implements Serializable{
                /**
                 * skillName : 陌陌墨迹hulk
                 * skillLevel : R0008,P0039
                 * resumeId : 629
                 */

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

            public static class TchWorkExpsBean  implements Serializable{
                /**
                 * position : 你问问你妈
                 * companyName : 陌陌找我
                 * beginDate : 1483228800
                 * endDate : null
                 * positionDesc :
                 * resumeId : 634
                 */

                private String position;
                private String companyName;
                private String beginDate;
                private String endDate;
                private String positionDesc;
                private int resumeId;

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

                public int getResumeId() {
                    return resumeId;
                }

                public void setResumeId(int resumeId) {
                    this.resumeId = resumeId;
                }
            }
        }
    }
}

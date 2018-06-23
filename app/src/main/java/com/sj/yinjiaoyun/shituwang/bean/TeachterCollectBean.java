package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/16.
 * 老师收藏列表的数据封装类
 */
public class TeachterCollectBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : {"total":2,"rows":[{"id":186,"stuUserId":600,"tchTeacherId":520,"createTime":1502864087000,"tchTeacherVO":null,"stuUserVO":{"id":600,"uuid":"43779f071e0f45e6a7c66774c12847a7","username":null,"password":"b4872ba166d6c60fcb6216e9e7a4add3","realName":"曾的","sex":1,"mobile":"15172427599","status":1,"schoolId":1,"createTime":1497257545000,"email":"","weChat":null,"updateTime":1497840831000,"birthday":1277801257,"addressNow":"湖南省,湘潭市,雨湖区","department":"","stuNo":"","citizenship":65,"moralTrait":91,"taste":78,"study":60,"logicalMathe":96,"selfCognitive":88,"subjectAttainment":61,"interaction":87,"space":70,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/44/i8T_r1lUvpSAauh0AAIOWPCJ4DE437.png","lastLogin":1502864098000,"isShowRadar":1,"description":"帅uuu你OK哦咯","evaluationLabels":"","schoolName":null,"resumes":[{"id":271,"stuUserId":600,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0001\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1497258598000},{"id":272,"stuUserId":600,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"汉口\",\"diplomas\":\"R0006,P0035\",\"endTime\":1483228800,\"majorName\":\"计算机\"}","updateTime":1497258633000},{"id":273,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"计算机\"}","updateTime":1497258641000},{"id":278,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"帅气\"}","updateTime":1497320659000},{"id":330,"stuUserId":600,"resumeType":6,"resume":"{\"awardTime\":1499844134,\"certificateName\":\"1616\",\"certificateOrgName\":\"你够\"}","updateTime":1499844138000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}},{"id":179,"stuUserId":536,"tchTeacherId":520,"createTime":1502174898000,"tchTeacherVO":null,"stuUserVO":{"id":536,"uuid":"64c417332e7b42d988b750d2363319db","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子007","sex":1,"mobile":"13437199907","status":1,"schoolId":1,"createTime":1495015098000,"email":"","weChat":null,"updateTime":1495015234000,"birthday":null,"addressNow":"","department":"","stuNo":"","citizenship":93,"moralTrait":66,"taste":62,"study":66,"logicalMathe":66,"selfCognitive":87,"subjectAttainment":94,"interaction":90,"space":64,"workStatus":0,"head":"","lastLogin":1502430168000,"isShowRadar":1,"description":null,"evaluationLabels":"","schoolName":null,"resumes":null,"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}}],"pageSize":10,"pageNo":1}
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
         * total : 2
         * rows : [{"id":186,"stuUserId":600,"tchTeacherId":520,"createTime":1502864087000,"tchTeacherVO":null,"stuUserVO":{"id":600,"uuid":"43779f071e0f45e6a7c66774c12847a7","username":null,"password":"b4872ba166d6c60fcb6216e9e7a4add3","realName":"曾的","sex":1,"mobile":"15172427599","status":1,"schoolId":1,"createTime":1497257545000,"email":"","weChat":null,"updateTime":1497840831000,"birthday":1277801257,"addressNow":"湖南省,湘潭市,雨湖区","department":"","stuNo":"","citizenship":65,"moralTrait":91,"taste":78,"study":60,"logicalMathe":96,"selfCognitive":88,"subjectAttainment":61,"interaction":87,"space":70,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/44/i8T_r1lUvpSAauh0AAIOWPCJ4DE437.png","lastLogin":1502864098000,"isShowRadar":1,"description":"帅uuu你OK哦咯","evaluationLabels":"","schoolName":null,"resumes":[{"id":271,"stuUserId":600,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0001\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1497258598000},{"id":272,"stuUserId":600,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"汉口\",\"diplomas\":\"R0006,P0035\",\"endTime\":1483228800,\"majorName\":\"计算机\"}","updateTime":1497258633000},{"id":273,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"计算机\"}","updateTime":1497258641000},{"id":278,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"帅气\"}","updateTime":1497320659000},{"id":330,"stuUserId":600,"resumeType":6,"resume":"{\"awardTime\":1499844134,\"certificateName\":\"1616\",\"certificateOrgName\":\"你够\"}","updateTime":1499844138000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}},{"id":179,"stuUserId":536,"tchTeacherId":520,"createTime":1502174898000,"tchTeacherVO":null,"stuUserVO":{"id":536,"uuid":"64c417332e7b42d988b750d2363319db","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子007","sex":1,"mobile":"13437199907","status":1,"schoolId":1,"createTime":1495015098000,"email":"","weChat":null,"updateTime":1495015234000,"birthday":null,"addressNow":"","department":"","stuNo":"","citizenship":93,"moralTrait":66,"taste":62,"study":66,"logicalMathe":66,"selfCognitive":87,"subjectAttainment":94,"interaction":90,"space":64,"workStatus":0,"head":"","lastLogin":1502430168000,"isShowRadar":1,"description":null,"evaluationLabels":"","schoolName":null,"resumes":null,"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}}]
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
             * id : 186
             * stuUserId : 600
             * tchTeacherId : 520
             * createTime : 1502864087000
             * tchTeacherVO : null
             * stuUserVO : {"id":600,"uuid":"43779f071e0f45e6a7c66774c12847a7","username":null,"password":"b4872ba166d6c60fcb6216e9e7a4add3","realName":"曾的","sex":1,"mobile":"15172427599","status":1,"schoolId":1,"createTime":1497257545000,"email":"","weChat":null,"updateTime":1497840831000,"birthday":1277801257,"addressNow":"湖南省,湘潭市,雨湖区","department":"","stuNo":"","citizenship":65,"moralTrait":91,"taste":78,"study":60,"logicalMathe":96,"selfCognitive":88,"subjectAttainment":61,"interaction":87,"space":70,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/44/i8T_r1lUvpSAauh0AAIOWPCJ4DE437.png","lastLogin":1502864098000,"isShowRadar":1,"description":"帅uuu你OK哦咯","evaluationLabels":"","schoolName":null,"resumes":[{"id":271,"stuUserId":600,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0001\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1497258598000},{"id":272,"stuUserId":600,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"汉口\",\"diplomas\":\"R0006,P0035\",\"endTime\":1483228800,\"majorName\":\"计算机\"}","updateTime":1497258633000},{"id":273,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"计算机\"}","updateTime":1497258641000},{"id":278,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"帅气\"}","updateTime":1497320659000},{"id":330,"stuUserId":600,"resumeType":6,"resume":"{\"awardTime\":1499844134,\"certificateName\":\"1616\",\"certificateOrgName\":\"你够\"}","updateTime":1499844138000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}
             */

            private int id;
            private int stuUserId;
            private int tchTeacherId;
            private long createTime;
            private Object tchTeacherVO;
            private StuUserVOBean stuUserVO;

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

            public int getTchTeacherId() {
                return tchTeacherId;
            }

            public void setTchTeacherId(int tchTeacherId) {
                this.tchTeacherId = tchTeacherId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public Object getTchTeacherVO() {
                return tchTeacherVO;
            }

            public void setTchTeacherVO(Object tchTeacherVO) {
                this.tchTeacherVO = tchTeacherVO;
            }

            public StuUserVOBean getStuUserVO() {
                return stuUserVO;
            }

            public void setStuUserVO(StuUserVOBean stuUserVO) {
                this.stuUserVO = stuUserVO;
            }

            public static class StuUserVOBean {
                /**
                 * id : 600
                 * uuid : 43779f071e0f45e6a7c66774c12847a7
                 * username : null
                 * password : b4872ba166d6c60fcb6216e9e7a4add3
                 * realName : 曾的
                 * sex : 1
                 * mobile : 15172427599
                 * status : 1
                 * schoolId : 1
                 * createTime : 1497257545000
                 * email :
                 * weChat : null
                 * updateTime : 1497840831000
                 * birthday : 1277801257
                 * addressNow : 湖南省,湘潭市,雨湖区
                 * department :
                 * stuNo :
                 * citizenship : 65
                 * moralTrait : 91
                 * taste : 78
                 * study : 60
                 * logicalMathe : 96
                 * selfCognitive : 88
                 * subjectAttainment : 61
                 * interaction : 87
                 * space : 70
                 * workStatus : 1
                 * head : http://139.196.255.175/group1/M00/00/44/i8T_r1lUvpSAauh0AAIOWPCJ4DE437.png
                 * lastLogin : 1502864098000
                 * isShowRadar : 1
                 * description : 帅uuu你OK哦咯
                 * evaluationLabels :
                 * schoolName : null
                 * resumes : [{"id":271,"stuUserId":600,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0001\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1497258598000},{"id":272,"stuUserId":600,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"汉口\",\"diplomas\":\"R0006,P0035\",\"endTime\":1483228800,\"majorName\":\"计算机\"}","updateTime":1497258633000},{"id":273,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"计算机\"}","updateTime":1497258641000},{"id":278,"stuUserId":600,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"帅气\"}","updateTime":1497320659000},{"id":330,"stuUserId":600,"resumeType":6,"resume":"{\"awardTime\":1499844134,\"certificateName\":\"1616\",\"certificateOrgName\":\"你够\"}","updateTime":1499844138000}]
                 * isStudy : null
                 * studyId : null
                 * contactId : null
                 * resumePerfectDegree : 0
                 */

                private int id;
                private String uuid;
                private Object username;
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
                private long lastLogin;
                private int isShowRadar;
                private String description;
                private String evaluationLabels;
                private String schoolName;
                private String isStudy;
                private String studyId;
                private String contactId;
                private int resumePerfectDegree;
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

                public String getContactId() {
                    return contactId;
                }

                public void setContactId(String contactId) {
                    this.contactId = contactId;
                }

                public int getResumePerfectDegree() {
                    return resumePerfectDegree;
                }

                public void setResumePerfectDegree(int resumePerfectDegree) {
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
                     * id : 271
                     * stuUserId : 600
                     * resumeType : 1
                     * resume : {"address":"湖北省,武汉市,江岸区","expectBusiness":"R0002,P0012,S0066","expectPosition":"R0001,P0001,S0001,T0001","expectSalary":"R0005,P0031"}
                     * updateTime : 1497258598000
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

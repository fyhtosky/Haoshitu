package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/16.
 */
public class TeacherRegisterBean {

    /**
     * status : 200
     * msg : 账户激活成功!
     * data : {"id":568,"uuid":"b4581839f0e14c2681737ecaef0c5cb9","realName":"范远华","sex":"1","password":"9dea58781660c7daae763b9cd9e33148","addressNow":"湖北省,武汉市,江岸区","positionId":"美容导师","mobile":"18672593926","email":null,"weChat":null,"companyId":2,"imgUrl":"/static/img/recommend/master.png","citizenship":65,"moralTrait":74,"taste":98,"study":89,"logicalMathe":69,"selfCognitive":69,"subjectAttainment":87,"interaction":74,"space":86,"isShowRadar":1,"status":1,"updateTime":1497577412000,"createTime":1497257435000,"lastLogin":1497577412000,"isDeleted":1,"isRecommended":null,"evaluationLabels":"","workList":null,"isStudy":null,"isEnshrine":null,"companyImg":null,"companyName":null,"type":null,"scale":null,"tchResumeVO":null,"skills":null,"contactId":null}
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
         * id : 568
         * uuid : b4581839f0e14c2681737ecaef0c5cb9
         * realName : 范远华
         * sex : 1
         * password : 9dea58781660c7daae763b9cd9e33148
         * addressNow : 湖北省,武汉市,江岸区
         * positionId : 美容导师
         * mobile : 18672593926
         * email : null
         * weChat : null
         * companyId : 2
         * imgUrl : /static/img/recommend/master.png
         * citizenship : 65
         * moralTrait : 74
         * taste : 98
         * study : 89
         * logicalMathe : 69
         * selfCognitive : 69
         * subjectAttainment : 87
         * interaction : 74
         * space : 86
         * isShowRadar : 1
         * status : 1
         * updateTime : 1497577412000
         * createTime : 1497257435000
         * lastLogin : 1497577412000
         * isDeleted : 1
         * isRecommended : null
         * evaluationLabels :
         * workList : null
         * isStudy : null
         * isEnshrine : null
         * companyImg : null
         * companyName : null
         * type : null
         * scale : null
         * tchResumeVO : null
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
        private long companyId;
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
        private String isRecommended;
        private String evaluationLabels;
        private Object workList;
        private int isStudy;
        private int isEnshrine;
        private String companyImg;
        private String companyName;
        private String type;
        private String scale;
        private Object tchResumeVO;
        private String skills;
        private long contactId;

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

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
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

        public String getIsRecommended() {
            return isRecommended;
        }

        public void setIsRecommended(String isRecommended) {
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

        public Object getTchResumeVO() {
            return tchResumeVO;
        }

        public void setTchResumeVO(Object tchResumeVO) {
            this.tchResumeVO = tchResumeVO;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        public long getContactId() {
            return contactId;
        }

        public void setContactId(long contactId) {
            this.contactId = contactId;
        }
    }
}

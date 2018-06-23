package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/11.
 * 老师端的个人中心老师基本信息的封装类
 */
public class TeacherPersonalCenterDetailBean {

    /**
     * status : 200
     * msg : 操作成功
     * data : {"id":568,"uuid":"82b9306200054639a2eea7b1fcc0a0fc","realName":"范远华","sex":"1","password":"9dea58781660c7daae763b9cd9e33148","addressNow":"湖北省,武汉市,江岸区","positionId":"美容导师","mobile":"18672593926","email":null,"weChat":null,"companyId":2,"imgUrl":"","citizenship":80,"moralTrait":97,"taste":79,"study":75,"logicalMathe":98,"selfCognitive":64,"subjectAttainment":76,"interaction":99,"space":84,"isShowRadar":1,"status":1,"updateTime":1497602788000,"createTime":1497257435000,"lastLogin":1499756080000,"isDeleted":0,"isRecommended":1,"evaluationLabels":"","workList":null,"isStudy":null,"isEnshrine":null,"companyImg":"http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg","companyName":"上海银教云数据系统有限公司武汉分公司","type":"软件开发","scale":"100-500人","tchResumeVO":null,"skills":null,"contactId":null}
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
         * lastLogin : 1499756080000
         * isDeleted : 0
         * isRecommended : 1
         * evaluationLabels :
         * workList : null
         * isStudy : null
         * isEnshrine : null
         * companyImg : http://d1.5xuexi.com/group1/M00/00/1C/i-AgdVjt8u6AY1KnAABSxooRXMg875.jpg
         * companyName : 上海银教云数据系统有限公司武汉分公司
         * type : 软件开发
         * scale : 100-500人
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
        private int companyId;
        private String imgUrl;
        private double citizenship;
        private double moralTrait;
        private double taste;
        private double study;
        private double logicalMathe;
        private double selfCognitive;
        private double subjectAttainment;
        private double interaction;
        private double space;
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
        private Object tchResumeVO;
        private Object skills;
        private Object contactId;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
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

        public Object getTchResumeVO() {
            return tchResumeVO;
        }

        public void setTchResumeVO(Object tchResumeVO) {
            this.tchResumeVO = tchResumeVO;
        }

        public Object getSkills() {
            return skills;
        }

        public void setSkills(Object skills) {
            this.skills = skills;
        }

        public Object getContactId() {
            return contactId;
        }

        public void setContactId(Object contactId) {
            this.contactId = contactId;
        }
    }
}

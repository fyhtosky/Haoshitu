package com.sj.yinjiaoyun.shituwang.bean;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/10.
 * 老师端学生列表的数据封装类
 */
public class StudentListBean {


    /**
     * status : 200
     * msg : 加载学徒列表信息成功!
     * data : {"total":12,"rows":[{"id":505,"uuid":"a08b6be554004b3a9fc01fadd2067e5f","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"李雷","sex":1,"mobile":"18900000001","status":1,"schoolId":1,"createTime":1493194884000,"email":"","weChat":"33322255","updateTime":1493280504000,"birthday":631123200,"addressNow":"湖北省,武汉市,江汉区","department":"","stuNo":"","citizenship":83,"moralTrait":75,"taste":97,"study":90,"logicalMathe":67,"selfCognitive":70,"subjectAttainment":71,"interaction":85,"space":90,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/34/i8T_r1kcGbGAGXBUAAANsgIFN-Y042.jpg","lastLogin":1499394387000,"isShowRadar":1,"description":"mmmmmmmmmmmmmmm","evaluationLabels":"","schoolName":null,"resumes":[{"id":86,"stuUserId":505,"resumeType":5,"resume":"{\"beginTime\":1325376000,\"endTime\":1341100800,\"trainingCertificateName\":\"我学习\",\"trainingCourseName\":\"开发\",\"trainingOrgName\":\"武汉人人科技\"}","updateTime":1493343033000},{"id":87,"stuUserId":505,"resumeType":6,"resume":"{\"awardTime\":1325376000,\"certificateName\":\"cet\",\"certificateOrgName\":\"333333\"}","updateTime":1493343078000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":592,"uuid":"5a85d5e3a48840608df09a9336449447","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"许小花","sex":1,"mobile":"18700000007","status":1,"schoolId":1,"createTime":1496886534000,"email":"","weChat":null,"updateTime":1496886600000,"birthday":680972400,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":60,"moralTrait":66,"taste":72,"study":77,"logicalMathe":79,"selfCognitive":100,"subjectAttainment":90,"interaction":67,"space":69,"workStatus":1,"head":"","lastLogin":1499393939000,"isShowRadar":1,"description":"梧桐树","evaluationLabels":"","schoolName":null,"resumes":[{"id":274,"stuUserId":592,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0015,S0090\",\"expectPosition\":\"R0001,P0006,S0035,T0588\",\"expectSalary\":\"R0005,P0032\"}","updateTime":1497261134000},{"id":275,"stuUserId":592,"resumeType":2,"resume":"{\"beginTime\":1356998400,\"collegeName\":\"YY图\",\"diplomas\":\"R0006,P0034\",\"endTime\":1420070400,\"majorName\":\"武器图片\"}","updateTime":1497261177000},{"id":276,"stuUserId":592,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"语气\"}","updateTime":1497261202000},{"id":277,"stuUserId":592,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"舞曲\"}","updateTime":1497261202000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":533,"uuid":"2321df2d405c43b7b574ee4ea1cf172c","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子005","sex":0,"mobile":"13437122222","status":1,"schoolId":1,"createTime":1495014260000,"email":"","weChat":null,"updateTime":1496824088000,"birthday":1262275200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":90,"moralTrait":72,"taste":75,"study":66,"logicalMathe":95,"selfCognitive":93,"subjectAttainment":93,"interaction":97,"space":73,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/45/i8T_r1leAkyAPSyDAACmRFhLMyE972.png","lastLogin":1499306942000,"isShowRadar":1,"description":"小激动减肥ifi次费劲的居然看","evaluationLabels":"","schoolName":null,"resumes":[{"id":247,"stuUserId":533,"resumeType":1,"resume":"{\"address\":\"北京市,东城区\",\"expectBusiness\":\"R0002,P0016,S0095\",\"expectPosition\":\"R0001,P0005,S0031,T0519\",\"expectSalary\":\"R0005,P0029\"}","updateTime":1496821320000},{"id":249,"stuUserId":533,"resumeType":2,"resume":"{\"beginTime\":1388534400,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"计算机\"}","updateTime":1496821405000},{"id":250,"stuUserId":533,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"吃饭\"}","updateTime":1496821415000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":502,"uuid":"e8352a386b5e4179af07869d6b989012","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小同","sex":1,"mobile":"18991332111","status":1,"schoolId":1,"createTime":1493174842000,"email":"111111@qq.com","weChat":"212121321313213","updateTime":1497322200000,"birthday":704995200,"addressNow":"湖北省,武汉市,洪山区","department":"","stuNo":"","citizenship":88,"moralTrait":90,"taste":75,"study":96,"logicalMathe":83,"selfCognitive":76,"subjectAttainment":94,"interaction":90,"space":64,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/0C/i8T_r1ihILCAFiRcAAAasQ1Nuq4397.jpg","lastLogin":1497322461000,"isShowRadar":1,"description":"1、本人性格开朗、为人诚恳、乐观向上、兴趣广泛、拥有较强的组织能力和适应能力、并具有较强的管理策划与组织管理协调能力。\n\n2、忠实诚信,讲原则，说到做到，决不推卸责任;有自制力，做事情始终坚持有始有终，从不半途而废;肯学习,有问题不逃避,愿意虚心向他人学习;自信但不自负,不以自我为中心;愿意以谦虚态度赞扬接纳优越者,权威者;会用100%的热情和精力投入到工作中;平易近人。为人诚恳,性格开朗,积极进取,适应力强、勤奋好学、脚踏实地，有较强的团队精神,工作积极进取,态度认真。\n\n3、活泼开朗、乐观向上、兴趣广泛、适应力强、上手快、勤奋好学、脚踏实地、认真负责、坚毅不拔、吃苦耐劳、勇于迎接新挑战。","evaluationLabels":"[\"勤勤恳恳\",\"乐观活泼\"]","schoolName":null,"resumes":[{"id":67,"stuUserId":502,"resumeType":6,"resume":"{\"awardTime\":1396310400,\"certificateName\":\"cet 4\",\"certificateOrgName\":\"xxxxxxx\"}","updateTime":1493192675000},{"id":68,"stuUserId":502,"resumeType":5,"resume":"{\"beginTime\":1396310400,\"endTime\":1409529600,\"trainingCertificateName\":\"zzz\",\"trainingCourseName\":\"aaaaaaa\",\"trainingOrgName\":\"qqqqq\"}","updateTime":1493192758000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":535,"uuid":"90de4f88917a4efdbe7069ee64ab2103","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子006","sex":1,"mobile":"13437199906","status":1,"schoolId":2,"createTime":1495014733000,"email":"","weChat":null,"updateTime":1495014781000,"birthday":1167580800,"addressNow":"湖北省,武汉市,新洲区","department":"","stuNo":"","citizenship":94,"moralTrait":61,"taste":66,"study":99,"logicalMathe":73,"selfCognitive":95,"subjectAttainment":65,"interaction":87,"space":61,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/35/i8T_r1kcH7KAD8i9AAASqo_0EdQ557.jpg","lastLogin":1496974954000,"isShowRadar":1,"description":"啦啦啊啦啊啦放假记得记得叫解放军队记得记得计算机中心主任韦恩我们是非对方也许就是一辈子只有","evaluationLabels":"","schoolName":null,"resumes":[{"id":257,"stuUserId":535,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"会计\"}","updateTime":1496825504000},{"id":258,"stuUserId":535,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"snooker\"}","updateTime":1496825516000},{"id":259,"stuUserId":535,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0002\",\"expectSalary\":\"R0005,P0028\"}","updateTime":1496825538000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":590,"uuid":"e131a01637c54a639aa94de03731fc1f","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗05","sex":1,"mobile":"18700000005","status":1,"schoolId":1,"createTime":1496823584000,"email":"","weChat":"54332223","updateTime":1496824289000,"birthday":788889600,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":88,"moralTrait":83,"taste":97,"study":96,"logicalMathe":90,"selfCognitive":90,"subjectAttainment":71,"interaction":99,"space":75,"workStatus":1,"head":"","lastLogin":1496913757000,"isShowRadar":1,"description":"TT热热","evaluationLabels":"","schoolName":null,"resumes":[{"id":254,"stuUserId":590,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0014,S0082\",\"expectPosition\":\"R0001,P0003,S0015,T0219\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496824217000},{"id":255,"stuUserId":590,"resumeType":2,"resume":"{\"beginTime\":1199145600,\"collegeName\":\"5556666\",\"diplomas\":\"R0006,P0035\",\"endTime\":1325376000,\"majorName\":\"呃呃呃呃呃呃呃\"}","updateTime":1496824242000},{"id":256,"stuUserId":590,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"刚刚好皇冠夫妇\"}","updateTime":1496824254000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":589,"uuid":"ed4cdcef0c264bf79d2c33320bea2463","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗04","sex":0,"mobile":"18700000004","status":1,"schoolId":1,"createTime":1496821505000,"email":"","weChat":null,"updateTime":1496821731000,"birthday":631123200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":73,"moralTrait":68,"taste":66,"study":91,"logicalMathe":82,"selfCognitive":76,"subjectAttainment":92,"interaction":88,"space":95,"workStatus":1,"head":"","lastLogin":1496908731000,"isShowRadar":1,"description":"6666677767777656","evaluationLabels":"","schoolName":null,"resumes":[{"id":251,"stuUserId":589,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0014,S0084\",\"expectPosition\":\"R0001,P0004,S0020,T0328\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496821765000},{"id":252,"stuUserId":589,"resumeType":2,"resume":"{\"beginTime\":1283299200,\"collegeName\":\"大学\",\"diplomas\":\"R0006,P0035\",\"endTime\":1388534400,\"majorName\":\"管理\"}","updateTime":1496821789000},{"id":253,"stuUserId":589,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"呃呃呃呃呃呃\"}","updateTime":1496821799000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":576,"uuid":"a405b3da76364685bb3d11363b9e4097","username":null,"password":"b6450492e82dde0afd127285ee5a65c8","realName":"熊大","sex":1,"mobile":"15900000000","status":1,"schoolId":1,"createTime":1495079558000,"email":"","weChat":null,"updateTime":1495079611000,"birthday":567964800,"addressNow":"湖北省,武汉市,江汉区","department":"","stuNo":"","citizenship":87,"moralTrait":91,"taste":96,"study":100,"logicalMathe":81,"selfCognitive":72,"subjectAttainment":83,"interaction":87,"space":84,"workStatus":2,"head":"http://139.196.255.175/group1/M00/00/39/i8T_r1kdV92AMBWeAAVr3tiEx90.pictur","lastLogin":1496888405000,"isShowRadar":1,"description":"YY知我者谓我心忧呀知我者谓我心忧呀YY知我者谓我心忧知我者谓我心忧YY知我者谓我心忧知我者谓我心忧","evaluationLabels":"","schoolName":null,"resumes":null,"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":587,"uuid":"7121a379446241e68acb8bafe8b91055","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗","sex":1,"mobile":"18700000002","status":1,"schoolId":1,"createTime":1496816592000,"email":"","weChat":null,"updateTime":1496816843000,"birthday":631123200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":77,"moralTrait":94,"taste":64,"study":75,"logicalMathe":71,"selfCognitive":95,"subjectAttainment":89,"interaction":63,"space":87,"workStatus":1,"head":"","lastLogin":1496826077000,"isShowRadar":1,"description":"哈哈哈哈哈哈哈","evaluationLabels":"","schoolName":null,"resumes":[{"id":226,"stuUserId":587,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0002\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496819551000},{"id":227,"stuUserId":587,"resumeType":2,"resume":"{\"beginTime\":1451606400,\"collegeName\":\"武汉大学\",\"diplomas\":\"R0006,P0034\",\"majorName\":\"计算机\"}","updateTime":1496816927000},{"id":228,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"哈哈哈\"}","updateTime":1496816946000},{"id":229,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"呵呵呵\"}","updateTime":1496816946000},{"id":230,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"啊啊啊\"}","updateTime":1496816946000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":588,"uuid":"91990e5a1dff43b0823ae96d51e697a4","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗03","sex":1,"mobile":"18700000003","status":1,"schoolId":1,"createTime":1496819775000,"email":"","weChat":null,"updateTime":1496819793000,"birthday":694195200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":83,"moralTrait":67,"taste":69,"study":60,"logicalMathe":62,"selfCognitive":62,"subjectAttainment":71,"interaction":95,"space":86,"workStatus":1,"head":"","lastLogin":1496820286000,"isShowRadar":1,"description":"呃呃呃呃","evaluationLabels":"","schoolName":null,"resumes":[{"id":235,"stuUserId":588,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0016,S0094\",\"expectPosition\":\"R0001,P0006,S0035,T0589\",\"expectSalary\":\"R0005,P0032\"}","updateTime":1496819818000},{"id":236,"stuUserId":588,"resumeType":2,"resume":"{\"beginTime\":1293840000,\"collegeName\":\"古古怪怪\",\"diplomas\":\"R0006,P0036\",\"endTime\":1388534400,\"majorName\":\"新闻学\"}","updateTime":1496819848000},{"id":237,"stuUserId":588,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"哇哇哇\"}","updateTime":1496819861000},{"id":238,"stuUserId":588,"resumeType":3,"resume":"{\"beginTime\":1356998400,\"companyBusiness\":\"R0002,P0016,S0095\",\"companyName\":\"好好好\",\"endTime\":1420070400,\"positionName\":\"豫剧\",\"positionType\":\"R0001,P0006,S0035,T0589\",\"workDesc\":\"YY呀\",\"workType\":\"R0007,P0037\"}","updateTime":1496819905000},{"id":240,"stuUserId":588,"resumeType":4,"resume":"{\"beginTime\":1356998400,\"endTime\":1388534400,\"position\":\"R0001,P0006,S0036,T0603\",\"projectDesc\":\"发广告\",\"projectName\":\"去去去\"}","updateTime":1496819951000},{"id":242,"stuUserId":588,"resumeType":5,"resume":"{\"beginTime\":1425168000,\"trainingCertificateName\":\"55\",\"trainingCourseName\":\"333333\",\"trainingOrgName\":\"33333\"}","updateTime":1496819980000},{"id":244,"stuUserId":588,"resumeType":6,"resume":"{\"awardTime\":1388534400,\"certificateName\":\"44\",\"certificateOrgName\":\"21122\"}","updateTime":1496820000000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}],"pageSize":10,"pageNo":1}
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
         * total : 12
         * rows : [{"id":505,"uuid":"a08b6be554004b3a9fc01fadd2067e5f","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"李雷","sex":1,"mobile":"18900000001","status":1,"schoolId":1,"createTime":1493194884000,"email":"","weChat":"33322255","updateTime":1493280504000,"birthday":631123200,"addressNow":"湖北省,武汉市,江汉区","department":"","stuNo":"","citizenship":83,"moralTrait":75,"taste":97,"study":90,"logicalMathe":67,"selfCognitive":70,"subjectAttainment":71,"interaction":85,"space":90,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/34/i8T_r1kcGbGAGXBUAAANsgIFN-Y042.jpg","lastLogin":1499394387000,"isShowRadar":1,"description":"mmmmmmmmmmmmmmm","evaluationLabels":"","schoolName":null,"resumes":[{"id":86,"stuUserId":505,"resumeType":5,"resume":"{\"beginTime\":1325376000,\"endTime\":1341100800,\"trainingCertificateName\":\"我学习\",\"trainingCourseName\":\"开发\",\"trainingOrgName\":\"武汉人人科技\"}","updateTime":1493343033000},{"id":87,"stuUserId":505,"resumeType":6,"resume":"{\"awardTime\":1325376000,\"certificateName\":\"cet\",\"certificateOrgName\":\"333333\"}","updateTime":1493343078000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":592,"uuid":"5a85d5e3a48840608df09a9336449447","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"许小花","sex":1,"mobile":"18700000007","status":1,"schoolId":1,"createTime":1496886534000,"email":"","weChat":null,"updateTime":1496886600000,"birthday":680972400,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":60,"moralTrait":66,"taste":72,"study":77,"logicalMathe":79,"selfCognitive":100,"subjectAttainment":90,"interaction":67,"space":69,"workStatus":1,"head":"","lastLogin":1499393939000,"isShowRadar":1,"description":"梧桐树","evaluationLabels":"","schoolName":null,"resumes":[{"id":274,"stuUserId":592,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0015,S0090\",\"expectPosition\":\"R0001,P0006,S0035,T0588\",\"expectSalary\":\"R0005,P0032\"}","updateTime":1497261134000},{"id":275,"stuUserId":592,"resumeType":2,"resume":"{\"beginTime\":1356998400,\"collegeName\":\"YY图\",\"diplomas\":\"R0006,P0034\",\"endTime\":1420070400,\"majorName\":\"武器图片\"}","updateTime":1497261177000},{"id":276,"stuUserId":592,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"语气\"}","updateTime":1497261202000},{"id":277,"stuUserId":592,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"舞曲\"}","updateTime":1497261202000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":533,"uuid":"2321df2d405c43b7b574ee4ea1cf172c","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子005","sex":0,"mobile":"13437122222","status":1,"schoolId":1,"createTime":1495014260000,"email":"","weChat":null,"updateTime":1496824088000,"birthday":1262275200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":90,"moralTrait":72,"taste":75,"study":66,"logicalMathe":95,"selfCognitive":93,"subjectAttainment":93,"interaction":97,"space":73,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/45/i8T_r1leAkyAPSyDAACmRFhLMyE972.png","lastLogin":1499306942000,"isShowRadar":1,"description":"小激动减肥ifi次费劲的居然看","evaluationLabels":"","schoolName":null,"resumes":[{"id":247,"stuUserId":533,"resumeType":1,"resume":"{\"address\":\"北京市,东城区\",\"expectBusiness\":\"R0002,P0016,S0095\",\"expectPosition\":\"R0001,P0005,S0031,T0519\",\"expectSalary\":\"R0005,P0029\"}","updateTime":1496821320000},{"id":249,"stuUserId":533,"resumeType":2,"resume":"{\"beginTime\":1388534400,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"计算机\"}","updateTime":1496821405000},{"id":250,"stuUserId":533,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0040\",\"skillName\":\"吃饭\"}","updateTime":1496821415000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":502,"uuid":"e8352a386b5e4179af07869d6b989012","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小同","sex":1,"mobile":"18991332111","status":1,"schoolId":1,"createTime":1493174842000,"email":"111111@qq.com","weChat":"212121321313213","updateTime":1497322200000,"birthday":704995200,"addressNow":"湖北省,武汉市,洪山区","department":"","stuNo":"","citizenship":88,"moralTrait":90,"taste":75,"study":96,"logicalMathe":83,"selfCognitive":76,"subjectAttainment":94,"interaction":90,"space":64,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/0C/i8T_r1ihILCAFiRcAAAasQ1Nuq4397.jpg","lastLogin":1497322461000,"isShowRadar":1,"description":"1、本人性格开朗、为人诚恳、乐观向上、兴趣广泛、拥有较强的组织能力和适应能力、并具有较强的管理策划与组织管理协调能力。\n\n2、忠实诚信,讲原则，说到做到，决不推卸责任;有自制力，做事情始终坚持有始有终，从不半途而废;肯学习,有问题不逃避,愿意虚心向他人学习;自信但不自负,不以自我为中心;愿意以谦虚态度赞扬接纳优越者,权威者;会用100%的热情和精力投入到工作中;平易近人。为人诚恳,性格开朗,积极进取,适应力强、勤奋好学、脚踏实地，有较强的团队精神,工作积极进取,态度认真。\n\n3、活泼开朗、乐观向上、兴趣广泛、适应力强、上手快、勤奋好学、脚踏实地、认真负责、坚毅不拔、吃苦耐劳、勇于迎接新挑战。","evaluationLabels":"[\"勤勤恳恳\",\"乐观活泼\"]","schoolName":null,"resumes":[{"id":67,"stuUserId":502,"resumeType":6,"resume":"{\"awardTime\":1396310400,\"certificateName\":\"cet 4\",\"certificateOrgName\":\"xxxxxxx\"}","updateTime":1493192675000},{"id":68,"stuUserId":502,"resumeType":5,"resume":"{\"beginTime\":1396310400,\"endTime\":1409529600,\"trainingCertificateName\":\"zzz\",\"trainingCourseName\":\"aaaaaaa\",\"trainingOrgName\":\"qqqqq\"}","updateTime":1493192758000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":535,"uuid":"90de4f88917a4efdbe7069ee64ab2103","username":null,"password":"1e11dc20b0646c8054fdeabe7705b629","realName":"大胖橙子006","sex":1,"mobile":"13437199906","status":1,"schoolId":2,"createTime":1495014733000,"email":"","weChat":null,"updateTime":1495014781000,"birthday":1167580800,"addressNow":"湖北省,武汉市,新洲区","department":"","stuNo":"","citizenship":94,"moralTrait":61,"taste":66,"study":99,"logicalMathe":73,"selfCognitive":95,"subjectAttainment":65,"interaction":87,"space":61,"workStatus":1,"head":"http://139.196.255.175/group1/M00/00/35/i8T_r1kcH7KAD8i9AAASqo_0EdQ557.jpg","lastLogin":1496974954000,"isShowRadar":1,"description":"啦啦啊啦啊啦放假记得记得叫解放军队记得记得计算机中心主任韦恩我们是非对方也许就是一辈子只有","evaluationLabels":"","schoolName":null,"resumes":[{"id":257,"stuUserId":535,"resumeType":2,"resume":"{\"beginTime\":1483228800,\"collegeName\":\"湖北大学\",\"diplomas\":\"R0006,P0035\",\"majorName\":\"会计\"}","updateTime":1496825504000},{"id":258,"stuUserId":535,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"snooker\"}","updateTime":1496825516000},{"id":259,"stuUserId":535,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0002\",\"expectSalary\":\"R0005,P0028\"}","updateTime":1496825538000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":590,"uuid":"e131a01637c54a639aa94de03731fc1f","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗05","sex":1,"mobile":"18700000005","status":1,"schoolId":1,"createTime":1496823584000,"email":"","weChat":"54332223","updateTime":1496824289000,"birthday":788889600,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":88,"moralTrait":83,"taste":97,"study":96,"logicalMathe":90,"selfCognitive":90,"subjectAttainment":71,"interaction":99,"space":75,"workStatus":1,"head":"","lastLogin":1496913757000,"isShowRadar":1,"description":"TT热热","evaluationLabels":"","schoolName":null,"resumes":[{"id":254,"stuUserId":590,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0014,S0082\",\"expectPosition\":\"R0001,P0003,S0015,T0219\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496824217000},{"id":255,"stuUserId":590,"resumeType":2,"resume":"{\"beginTime\":1199145600,\"collegeName\":\"5556666\",\"diplomas\":\"R0006,P0035\",\"endTime\":1325376000,\"majorName\":\"呃呃呃呃呃呃呃\"}","updateTime":1496824242000},{"id":256,"stuUserId":590,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"刚刚好皇冠夫妇\"}","updateTime":1496824254000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":589,"uuid":"ed4cdcef0c264bf79d2c33320bea2463","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗04","sex":0,"mobile":"18700000004","status":1,"schoolId":1,"createTime":1496821505000,"email":"","weChat":null,"updateTime":1496821731000,"birthday":631123200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":73,"moralTrait":68,"taste":66,"study":91,"logicalMathe":82,"selfCognitive":76,"subjectAttainment":92,"interaction":88,"space":95,"workStatus":1,"head":"","lastLogin":1496908731000,"isShowRadar":1,"description":"6666677767777656","evaluationLabels":"","schoolName":null,"resumes":[{"id":251,"stuUserId":589,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0014,S0084\",\"expectPosition\":\"R0001,P0004,S0020,T0328\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496821765000},{"id":252,"stuUserId":589,"resumeType":2,"resume":"{\"beginTime\":1283299200,\"collegeName\":\"大学\",\"diplomas\":\"R0006,P0035\",\"endTime\":1388534400,\"majorName\":\"管理\"}","updateTime":1496821789000},{"id":253,"stuUserId":589,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"呃呃呃呃呃呃\"}","updateTime":1496821799000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":576,"uuid":"a405b3da76364685bb3d11363b9e4097","username":null,"password":"b6450492e82dde0afd127285ee5a65c8","realName":"熊大","sex":1,"mobile":"15900000000","status":1,"schoolId":1,"createTime":1495079558000,"email":"","weChat":null,"updateTime":1495079611000,"birthday":567964800,"addressNow":"湖北省,武汉市,江汉区","department":"","stuNo":"","citizenship":87,"moralTrait":91,"taste":96,"study":100,"logicalMathe":81,"selfCognitive":72,"subjectAttainment":83,"interaction":87,"space":84,"workStatus":2,"head":"http://139.196.255.175/group1/M00/00/39/i8T_r1kdV92AMBWeAAVr3tiEx90.pictur","lastLogin":1496888405000,"isShowRadar":1,"description":"YY知我者谓我心忧呀知我者谓我心忧呀YY知我者谓我心忧知我者谓我心忧YY知我者谓我心忧知我者谓我心忧","evaluationLabels":"","schoolName":null,"resumes":null,"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":587,"uuid":"7121a379446241e68acb8bafe8b91055","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗","sex":1,"mobile":"18700000002","status":1,"schoolId":1,"createTime":1496816592000,"email":"","weChat":null,"updateTime":1496816843000,"birthday":631123200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":77,"moralTrait":94,"taste":64,"study":75,"logicalMathe":71,"selfCognitive":95,"subjectAttainment":89,"interaction":63,"space":87,"workStatus":1,"head":"","lastLogin":1496826077000,"isShowRadar":1,"description":"哈哈哈哈哈哈哈","evaluationLabels":"","schoolName":null,"resumes":[{"id":226,"stuUserId":587,"resumeType":1,"resume":"{\"address\":\"天津市,河东区\",\"expectBusiness\":\"R0002,P0012,S0066\",\"expectPosition\":\"R0001,P0001,S0001,T0002\",\"expectSalary\":\"R0005,P0031\"}","updateTime":1496819551000},{"id":227,"stuUserId":587,"resumeType":2,"resume":"{\"beginTime\":1451606400,\"collegeName\":\"武汉大学\",\"diplomas\":\"R0006,P0034\",\"majorName\":\"计算机\"}","updateTime":1496816927000},{"id":228,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"哈哈哈\"}","updateTime":1496816946000},{"id":229,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"呵呵呵\"}","updateTime":1496816946000},{"id":230,"stuUserId":587,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0039\",\"skillName\":\"啊啊啊\"}","updateTime":1496816946000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0},{"id":588,"uuid":"91990e5a1dff43b0823ae96d51e697a4","username":null,"password":"3d231a24ba28c8770be888df3eb4e413","realName":"张小茗03","sex":1,"mobile":"18700000003","status":1,"schoolId":1,"createTime":1496819775000,"email":"","weChat":null,"updateTime":1496819793000,"birthday":694195200,"addressNow":"湖北省,武汉市,江岸区","department":"","stuNo":"","citizenship":83,"moralTrait":67,"taste":69,"study":60,"logicalMathe":62,"selfCognitive":62,"subjectAttainment":71,"interaction":95,"space":86,"workStatus":1,"head":"","lastLogin":1496820286000,"isShowRadar":1,"description":"呃呃呃呃","evaluationLabels":"","schoolName":null,"resumes":[{"id":235,"stuUserId":588,"resumeType":1,"resume":"{\"address\":\"湖北省,武汉市,江岸区\",\"expectBusiness\":\"R0002,P0016,S0094\",\"expectPosition\":\"R0001,P0006,S0035,T0589\",\"expectSalary\":\"R0005,P0032\"}","updateTime":1496819818000},{"id":236,"stuUserId":588,"resumeType":2,"resume":"{\"beginTime\":1293840000,\"collegeName\":\"古古怪怪\",\"diplomas\":\"R0006,P0036\",\"endTime\":1388534400,\"majorName\":\"新闻学\"}","updateTime":1496819848000},{"id":237,"stuUserId":588,"resumeType":7,"resume":"{\"skillLevel\":\"R0008,P0041\",\"skillName\":\"哇哇哇\"}","updateTime":1496819861000},{"id":238,"stuUserId":588,"resumeType":3,"resume":"{\"beginTime\":1356998400,\"companyBusiness\":\"R0002,P0016,S0095\",\"companyName\":\"好好好\",\"endTime\":1420070400,\"positionName\":\"豫剧\",\"positionType\":\"R0001,P0006,S0035,T0589\",\"workDesc\":\"YY呀\",\"workType\":\"R0007,P0037\"}","updateTime":1496819905000},{"id":240,"stuUserId":588,"resumeType":4,"resume":"{\"beginTime\":1356998400,\"endTime\":1388534400,\"position\":\"R0001,P0006,S0036,T0603\",\"projectDesc\":\"发广告\",\"projectName\":\"去去去\"}","updateTime":1496819951000},{"id":242,"stuUserId":588,"resumeType":5,"resume":"{\"beginTime\":1425168000,\"trainingCertificateName\":\"55\",\"trainingCourseName\":\"333333\",\"trainingOrgName\":\"33333\"}","updateTime":1496819980000},{"id":244,"stuUserId":588,"resumeType":6,"resume":"{\"awardTime\":1388534400,\"certificateName\":\"44\",\"certificateOrgName\":\"21122\"}","updateTime":1496820000000}],"isStudy":null,"studyId":null,"contactId":null,"resumePerfectDegree":0}]
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
             * id : 505
             * uuid : a08b6be554004b3a9fc01fadd2067e5f
             * username : null
             * password : 1e11dc20b0646c8054fdeabe7705b629
             * realName : 李雷
             * sex : 1
             * mobile : 18900000001
             * status : 1
             * schoolId : 1
             * createTime : 1493194884000
             * email :
             * weChat : 33322255
             * updateTime : 1493280504000
             * birthday : 631123200
             * addressNow : 湖北省,武汉市,江汉区
             * department :
             * stuNo :
             * citizenship : 83
             * moralTrait : 75
             * taste : 97
             * study : 90
             * logicalMathe : 67
             * selfCognitive : 70
             * subjectAttainment : 71
             * interaction : 85
             * space : 90
             * workStatus : 1
             * head : http://139.196.255.175/group1/M00/00/34/i8T_r1kcGbGAGXBUAAANsgIFN-Y042.jpg
             * lastLogin : 1499394387000
             * isShowRadar : 1
             * description : mmmmmmmmmmmmmmm
             * evaluationLabels :
             * schoolName : null
             * resumes : [{"id":86,"stuUserId":505,"resumeType":5,"resume":"{\"beginTime\":1325376000,\"endTime\":1341100800,\"trainingCertificateName\":\"我学习\",\"trainingCourseName\":\"开发\",\"trainingOrgName\":\"武汉人人科技\"}","updateTime":1493343033000},{"id":87,"stuUserId":505,"resumeType":6,"resume":"{\"awardTime\":1325376000,\"certificateName\":\"cet\",\"certificateOrgName\":\"333333\"}","updateTime":1493343078000}]
             * isStudy : null
             * studyId : null
             * contactId : null
             * resumePerfectDegree : 0.0
             */

            private int id;
            private String uuid;
            private String username;
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
            private int isStudy;
            private int studyId;
            private Long contactId;
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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
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

            public int getIsStudy() {
                return isStudy;
            }

            public void setIsStudy(int isStudy) {
                this.isStudy = isStudy;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public int getStudyId() {
                return studyId;
            }

            public void setStudyId(int studyId) {
                this.studyId = studyId;
            }

            public Long getContactId() {
                return contactId;
            }

            public void setContactId(Long contactId) {
                this.contactId = contactId;
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

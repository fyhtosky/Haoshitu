package com.sj.yinjiaoyun.shituwang.http;

/**
 * Created by wanzhiying on 2017/3/6.
 */
public class Api {
    /***
     * IM服务器
     */
    //线上服务器
    public static final String XMPP_HOST = "139.224.32.120";
    //测试的服务器
//    public static final String XMPP_HOST = "139.196.155.123";
    public static final int XMPP_PORT = 5280;
    /**
     * I学令牌的服务器
     */
    //线上
    public static final String TOKEN_HOST="http://ixue.5xuexi.com";
    //测试
//    public static final String TOKEN_HOST="http://139.196.254.188:8080";


    /**
     * 接口的服务系
     */
    //测试环境
//    public static final String Host = "http://139.196.255.175:8088";
    //正式环境
    public static final String Host = "http://139.196.255.175:8087";


    /**
     * 公共上传图片
     */
    //测试环境图片库
//    public static final String COMMON_UPLOAD_PHOTO = "http://139.196.255.175:8086/picroom/commPicWithBase.action";
    //线上环境图片库
    public static final String COMMON_UPLOAD_PHOTO = "http://picroom.5xuexi.com/picroom/commPicWithBase.action";

    /**
     * 版本更新
     */
    public static final String UPDATE_VERSION = Host + "/api/appVersion.action?phoneType=android";
    /**
     * 学生登录
     */
    public static final String STUDENT_LOGIN = Host + "/api/stu/login.action";
    /**
     * 师傅登录
     */
    public static final String TEACHER_LOGIN = Host + "/api/tch/login.action";
    /**
     * 学生激活
     */
    public static final String STUDENT_ACTIVE = Host + "/api/stu/register.action";
    /**
     * 师父激活
     */
    public static final String TEACHER_ACTIVE = Host + "/api/tch/register.action";
    /**
     * 获取短信验证码的
     */
    public static final String GET_CODE = Host + "/api/sms/sendMsg.action";
    /**
     * 获取学校的接口
     */
    public static final String GET_SCHOOL_LIST = Host + "/api/sch/findAllSchool.action";
    /**
     * 发现老师界面获取老师的列表
     */
    public static final String GTE_MASTER_LIST = Host + "/api/tch/queryTeacherPg.action";
    /**
     * 根据ip查询地区
     */
    public static final String GET_DICTIONARY = "http://ip.taobao.com/service/getIpInfo2.php";
    public static final String GET_IP = "http://freeapi.ipip.net/";
    /**
     * 根据主键 id（+学生ID）查询师傅详情
     */
    public static final String GET_TEACHER_DEATIL = Host + "/api/tch/getTeacherDetails.action";
    /**
     * 新增或删除收藏
     */
    public static final String SET_ENSHRINE = Host + "/api/enshrine/addOrRemoveEnshrine.action";
    /**
     * 获取个人中心中获取学生详细信息的
     */
    public static final String GET_STUDENT_CENTER = Host + "/api/stu/getDetails.action";
    /***
     * 修改密码 老师身份
     */
    public static final String UPDATE_TEACHER_PWD = Host + "/api/tch/ updatePwd.action";

    /**
     * 修改密码 学生身份
     */
    public static final String UPDATE_STUDENT_PWD = Host + "/api/stu/updatePwd.action";
    /**
     * 修改手机号、微信、邮箱、老师身份
     */
    public static final String UPDATE_TEACHER_DETAIL = Host + "/api/tch/updateTeacherDetail.action";
    /**
     * 修改手机号、微信、邮箱、学生身份
     */
    public static final String UPDATE_STUDENT_DETAIL = Host + "/api/stu/updateUserInfo.action";
    /**
     * 获取学生档案的详情
     */
    public static final String GTE_STUDENT_RECORD = Host + "/api/stu/getResumeInfo.action";
    /**
     * 根据学生id 查询学生信息and 档案信息（是否与老师沟通过）
     */
    public static final String GET_STUDENT_DETAIL = Host + "/api/stu/getStuDetails.action";
    /**
     * 获取所有字典表的信息
     */
    public static final String GET_ALL_CODES = Host + "/api/code/queryAllCodes.action";


    /**
     * 更新学生的基本信息的接口
     */
    public static final String UPDATE_STUDENT_BASE_INFO = Host + "/api/stu/updateResumeInfo.action";

    /**
     * 删除学生档案
     */
    public static final String DELETE_RESUME_BY_ID = Host + "/api/stu/deleteResumeById.action";

    /**
     * 添加或者更新    学生期望工作（1）
     */
    public static final String SAVE_EXPECT_WORK = Host + "/api/stu/addOrUpdateExpectWork.action";
    /**
     * 添加或者更新    学生教育经历（2）
     */
    public static final String ADD_UODATE_EDUCATION = Host + "/api/stu/addOrUpdateEducational.action";
    /***
     * 添加或者更新    学生工作经验（3）
     */
    public static final String ADD_UPDATE_WORK = Host + "/api/stu/addOrUpdateWorkExp.action";
    /**
     * 添加或者更新   学生项目经验（4）
     */
    public static final String ADD_UPDATE_PROJECT = Host + "/api/stu/addOrUpdateProjectExp.action";
    /**
     * 添加或者更新   学生培训经历（5）
     */
    public static final String ADD_UPDATE_TRAIN = Host + "/api/stu/addOrUpdateTraining.action";
    /**
     * 添加或者更新   学生我的证书（6）
     */
    public static final String ADD_UPDATE_CERTINFICATE = Host + "/api/stu/addOrUpdateCertificate.action";
    /**
     * 修改学生工作状态接口
     */
    public static final String UPDATE_WORK_STATUS = Host + "/api/stu/updateWorkStatus.action";
    /**
     * 获取求职管理对应状态的接口
     */
    public static final String GET_STUDY = Host + "/api/study/getSudies.action";
    /**
     * 批量 添加和删除 学生专业技能
     */
    public static final String ADD_OR_REMOVE_SKILL = Host + "/api/stu/addAndRemoveSkills.action";
    /**
     * 6.4 根据学习面试id 查询 面试详情信息
     */
    public static final String GET_STUDY_DEATIL = Host + "/api/study/getStudyDetail.action";
    /**
     * 根据登录时间倒序、姓名、地点 分页查询学徒
     */
    public static final String GET_STUDENT_LIST = Host + "/api/stu/queryStuPg.action";
    /**
     * 根据主键id 查询师傅  基本信息
     * 老师端个人中心获取老师的基本信息
     */
    public static final String GET_TEACHER_CENTER = Host + "/api/tch/findTeacherDetail.action";
    /**
     * 修改师傅基本 信息
     */
    public static final String UPDATE_TEACHTER_BASE_INFO = Host + "/api/tch/updateTchInfo.action";
    /**
     * 添加或者删除老师综合能力
     */
    public static final String ADD_ABILITY = Host + "/api/tch/addOrRemoveAbility.action";
    /**
     * 批量添加和删除老师专业技能
     */
    public static final String ADD_TEACHTER_SKILL = Host + "/api/tch/addAndRemoveSkills.action";
    /**
     * 添加或者更新老师项目经验
     */
    public static final String UODATE_TEACHTER_WORK = Host + "/api/tch/addOrUpdateTchWorkExp.action";
    /**
     * 删除老师的项目经验
     */
    public static final String DELETE_TEACHTER_WORK = Host + "/api/tch/deleteResumeById.action";
    /**
     * 添加或者更新老师诉求描述
     */
    public static final String ADD_REQUIRE = Host + "/api/tch/addOrUpdateRequireDesc.action";
    /**
     * 更新师傅收徒(推荐)和不收徒（不推荐）
     */
    public static final String UODATE_TEACHTER_RECOMMEND = Host + "/api/tch/updateTeacherRecommended.action";
    /**
     * 6.5 更新面试学习状态
     */
    public static final String UPDATE_STUDY = Host + "/api/study/updateStudy.action";
    /**
     * 6.2 学生发起跟师学习
     */
    public static final String STUDY_BY_STU = Host + "/api/study/studyByStu.action";
    /**
     * 6.3 学生和老师 id获取跟师学习
     */
    public static final String GET_STUDY_STATE=Host+"/api/study/getStudy.action";
    /**
     * 查询会话列表
     */
    public static final String FIND_RECENTLY_MSG = Host + "/api/shitu_im/findRecentlyMsg.action";
    /**
     * 第一次发起沟通
     */
    public static final String START_USER_CONTACT = Host + "/api/shitu_im/startUserContact.action";
    /**
     * 查看标签的状态
     */
    public static final String FIND_LABLE_STATE = Host + "/api/shitu_im/findExchangeStatus.action";

    /**
     * 获取聊天头信息
     */
    public static final String GET_CHAT_HEAD = Host + "/api/shitu_im/getChatHeadInfo.action";
    /**
     * 查询未读消息总数
     */
    public static final String FIND_MSG_COUNT = Host + "/api/shitu_im/findUnreadMsgCount.action";
    /**
     * 查詢聊天記錄
     */
//   public static final String FIND_CHAT_MSG_RECORD=Host+"/api/shitu_im/findChatJidMsg.action";
    public static final String FIND_CHAT_MSG_RECORD = Host + "/api/shitu_im/findChatJidMsgAndStatus.action";
    /**
     * 保存聊天记录
     */
    public static final String SAVE_CHAT_MSG = Host + "/api/shitu_im/saveMsg.action";
    /**
     * 交换申请
     */
    public static final String APPLE_EXCHANGE = Host + "/api/shitu_im/applyExchange.action";
    /**
     * 清除会话计数API
     */
    public static final String ClEAR_SESSION_COUNT = Host + "/api/shitu_im/clearSessionCount.action";
    /**
     * 根据交换详情的id查询聊天详情界面的卡片的操作状态
     */
    public static final String GET_EXCHANGE_DEATIL = Host + "/api/shitu_im/getExchangeDetails.action";
    /**
     * 3.7: 交换操作 学生拒绝、学生接受、老师拒绝、老师接收
     */
    public static final String OPT_EXCHANGE = Host + "/api/shitu_im/optExchange.action";
    /**
     * 3.8: 教师操作沟通是否合适
     */
    public static final String UPDATE_TEACHTER_SET = Host + "/api/shitu_im/updateDisturbByTeacher.action";
    /**
     * 分页查询 学生收藏
     */
    public static final String STUDENT_COLLECT = Host + "/api/enshrine/findEnshrineByStudent.action";
    /**
     * 5.3分页查询 老师收藏
     */
    public static final String TEACHTER_COLLECT = Host + "/api/enshrine/findEnshrineByTeacher.action";
    /**
     * 学生查询评价
     */
    public static final String QUEERY_STU_EVAPG = Host + "/api/eva/queryStuEvaPg.action";
    /**
     * 7.2 :老师查询评价
     */
    public static final String QUERY_TCH_EVAPG = Host + "/api/eva/queryTchEvaPg.action";
    /**
     * 7.3 : 查询评价Log列表
     */
    public static final String QUERY_EVA_LOG_LIST = Host + "/api/eva/queryEvaLogListPg.action";
    /**
     * 新增评价
     */
    public static final String ADD_EVA = Host + "/api/eva/addEva.action";
    /**
     * 7.5 : 主键ID 查询评价
     */
    public static final String FIND_EVA=Host+"/api/eva/findEva.action";
    /**
     * 7.6 : 主键ID 查询评价列表详情
     */
    public static final String FIND_EVA_BYE_ID = Host + "/api/eva/findEvaLogById.action";
    /**
     * 获取协议地址API
     */
    public static final String FIND_AGREEMENT = Host + "/api/schcop/findAgreement.action";
    /**
     * 获取未读消息数
     */
    public static final String FIND_UNREAD_NOTICE = Host + "/api/notice/findUnreadNoticeMsg.action";
    /**
     * 分页查询通知列表
     */
    public static final String FIND_NOTICE_LIST = Host + "/api/notice/findNoticeMsgByPg.action";
    /**
     * 表示未读通知已读
     */
    public static final String UPDATE_NOTICE_MSG = Host + "/api/notice/updateNoticeMsgByDTO.action";

    /**
     * I学令牌
     * 忘记密码界面所需要的url
     */
    public static final String TOKEN_FORGET_PWD=TOKEN_HOST+"/app/toForgetPasswordPage.action";

    /**
     * I学令牌
     * 修改密码所需要的url
     */
    public static final String TOKEN_CHANGE_PWD=TOKEN_HOST+"/app/toUpdatePasswordPage.action";

    /**
     * I学令牌
     * 修改手机号所需要的url
     */
    public static final String TOKEN_CHANGE_PHONE=TOKEN_HOST+"/app/toUpdatePhonePage.action";
}
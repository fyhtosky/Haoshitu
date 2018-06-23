package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.DBmsgBean;
import com.sj.yinjiaoyun.shituwang.bean.MsgBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBeanX;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.bean.StudyDetailBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.bean.UserContactBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.CustomDatePicker;
import com.sj.yinjiaoyun.shituwang.xmppmanager.XmppUtil;

import org.jivesoftware.smack.XMPPException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师端的面试邀请
 */
public class TachterInterviewActivity extends AppCompatActivity implements TextWatcher{

    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.rl_iv)
    RelativeLayout rlIv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.tv_interview_time)
    TextView tvInterviewTime;
    @BindView(R.id.tv_interview_target)
    TextView tvInterviewTarget;
    @BindView(R.id.tv_interview_phone)
    TextView tvInterviewPhone;
    @BindView(R.id.tv_interview_address)
    EditText tvInterviewAddress;
    @BindView(R.id.rl_address)
    LinearLayout rlAddress;
    @BindView(R.id.tv_explain)
    EditText tvExplain;
    @BindView(R.id.rl_explain)
    LinearLayout rlExplain;
    @BindView(R.id.bt_interview)
    Button btInterview;
    @BindView(R.id.tv_text_size)
    TextView tvTextSize;
    @BindView(R.id.interview)
    RelativeLayout interview;
    //界面传值的id
    private int id;
    //标示是否可选时间
    private boolean isClick = false;
    //电话号码
    private String phone = "";
    private CustomDatePicker customDatePicker;
    private  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    //数据交互的 对象
    private StudyDetailBean detailBean;
    //标示操作的类型
    private int optFla=7;
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //学生档案的信息
    private StudentRecordBean.DataBean stuDataBean;
    //xmpp发送消息封装的实体类
    private MsgBean msgBean;
    //DB保存消息的实体类
    private DBmsgBean dBmsgBean;
    //对象转换成json
    private Gson gson = new Gson();
    private String userContactId = "";
    private String stuId = "";
    private String tchId = "";
    //跟师学的id
    private String studyId="";
    //登录的id
    private int loginId;
    //对发起邀请的做点击次数判断
    private boolean iskey=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tachter_interview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvExplain.addTextChangedListener(this);
        String text="0/100字";
        tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
        //获取登录的id
        loginId = PreferencesUtils.getSharePreInt(TachterInterviewActivity.this, Const.ID);
        tchId = "5tch_" + loginId;
        initDatePicker();
        phone = PreferencesUtils.getSharePreStr(TachterInterviewActivity.this, Const.PHONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            dataBean = (TeacherDetailBean.DataBean) bundle.getSerializable("dataBean");
            stuDataBean= (StudentRecordBean.DataBean) bundle.getSerializable("stuDataBean");
            userContactId = bundle.getString("userContactId");
            studyId=bundle.getString("studyId");
            Logger.d("传值的id:" + id);
             if(stuDataBean!=null){
                 stuId="5stu_"+stuDataBean.getId();
                 showStudentDetail(stuDataBean);
             }else {
                 getStudyDetail();
             }

        }
    }

    private void showStudentDetail(final StudentRecordBean.DataBean stuDataBean) {
        //展示用户名
        tvName.setText(stuDataBean.getRealName());
        //展示性别
        if (stuDataBean.getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //展示学历
        String education = "";
        //展示地址
        String address = "";
        //地址赋值
        if (stuDataBean.getAddressNow() != null && !TextUtils.isEmpty(stuDataBean.getAddressNow())) {
            address = stuDataBean.getAddressNow().split(",")[1];
        }
        //展示薪资
        String salary = "";
        //展示职位
        String post = "";
//                档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if (stuDataBean.getResumes() != null && stuDataBean.getResumes().size() > 0) {
            for (StudentRecordBean.DataBean.ResumesBean resumesBean : stuDataBean.getResumes()) {
                //展示薪资及职位
                if (resumesBean.getResumeType() == 1) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取职位
                    String expectPosition = mapstr.get("expectPosition");
                    //薪资
                    String expectSalary = mapstr.get("expectSalary");
                    if (MyApplication.map != null) {
                        if (expectPosition != null && !TextUtils.isEmpty(expectPosition)) {
                            String[] poss = expectPosition.split(",");
                            //期望职位赋值
                            post = MyApplication.map.get(poss[poss.length - 1]).getName();
                        }
                        if (expectSalary != null && !TextUtils.isEmpty(expectSalary)) {
                            String[] salarys = expectSalary.split(",");
                            //薪资赋值
                            salary = MyApplication.map.get(salarys[salarys.length - 1]).getName();
                        }
                    }
                } else if (resumesBean.getResumeType() == 2) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String diplomas = mapstr.get("diplomas");
                    if (MyApplication.map != null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            education = MyApplication.map.get(dips[dips.length - 1]).getName();
                        }
                    }
                }
            }
        }
        //控件显示学历和地址
        if (!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)) {
            tvAddress.setText(education + "|" + address);
        } else if (TextUtils.isEmpty(education)) {
            tvAddress.setText(address);
        } else if (TextUtils.isEmpty(address)) {
            tvAddress.setText(education);
        } else {
            tvAddress.setText("");
        }
        //展示薪资
        if (!TextUtils.isEmpty(salary)) {
            tvTime.setText(salary);
            tvTime.setTextColor(Color.RED);
        }
        //展示职位
        if (!TextUtils.isEmpty(post)) {
            tvPost.setText("期望:" + post);
        }
        //显示头像
        if (stuDataBean.getHead() != null && !TextUtils.isEmpty(stuDataBean.getHead())) {
            PicassoUtils.LoadPathCorners(TachterInterviewActivity.this, stuDataBean.getHead(), circleView);
        } else {
            PicassoUtils.LoadCorners(TachterInterviewActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //评价
        if (stuDataBean.getEvaluationLabels() != null && !TextUtils.isEmpty(stuDataBean.getEvaluationLabels())) {
            tagTrait.setVisibility(View.VISIBLE);
            String labels = stuDataBean.getEvaluationLabels().substring(1, stuDataBean.getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(TachterInterviewActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagTrait.setVisibility(View.GONE);
        }
        //头像点击事件
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转界面
                StudentResumeActivity.StartActivity(TachterInterviewActivity.this, stuDataBean.getId());
            }
        });
        //展示面试时间
        isClick = true;
        tvInterviewTime.setText("请选择");
        //联系电话
        if (!TextUtils.isEmpty(phone)) {
            tvInterviewPhone.setText(phone);
        }

    }


    private void initDatePicker() {
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                try {
                    if(sdf.parse(time).getTime()<=new Date().getTime()){
                        ToastUtil.showShortToast(TachterInterviewActivity.this,"面试时间只能在当前时间以后");
                        return;
                    }
                    tvInterviewTime.setText(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, "1970-01-01 00:00", "2030-12-30 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }
    /**
     * 获取面试详情信息
     */
    private void getStudyDetail() {
        String params = "?id=" + String.valueOf(id) + "&searchBy=0";
        HttpClient.get(this, Api.GET_STUDY_DEATIL + params, new CallBack<StudyDetailBean>() {
            @Override
            public void onSuccess(StudyDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    detailBean=result;
                    stuId="5stu_"+result.getData().getStuId();
                    startUserContact(result.getData().getStuId(),id);
                    showDeatil(result);

                } else {
                    ToastUtil.showShortToast(TachterInterviewActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 展示数据
     *
     * @param result
     */
    private void showDeatil(final StudyDetailBean result) {
        //展示用户名
        tvName.setText(result.getData().getStudentDeatil().getRealName());
        //展示性别
        if (result.getData().getStudentDeatil().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //展示学历
        String education = "";
        //展示地址
        String address = "";
        //地址赋值
        if (result.getData().getStudentDeatil().getAddressNow() != null && !TextUtils.isEmpty(result.getData().getStudentDeatil().getAddressNow())) {
            address = result.getData().getStudentDeatil().getAddressNow().split(",")[1];
        }
        //展示薪资
        String salary = "";
        //展示职位
        String post = "";
//                档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if (result.getData().getStudentDeatil().getResumes() != null && result.getData().getStudentDeatil().getResumes().size() > 0) {
            for (StudyDetailBean.DataBean.StudentDeatilBean.ResumesBean resumesBean : result.getData().getStudentDeatil().getResumes()) {
                //展示薪资及职位
                if (resumesBean.getResumeType() == 1) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取职位
                    String expectPosition = mapstr.get("expectPosition");
                    //薪资
                    String expectSalary = mapstr.get("expectSalary");
                    if (MyApplication.map != null) {
                        if (expectPosition != null && !TextUtils.isEmpty(expectPosition)) {
                            String[] poss = expectPosition.split(",");
                            //期望职位赋值
                            post = MyApplication.map.get(poss[poss.length - 1]).getName();
                        }
                        if (expectSalary != null && !TextUtils.isEmpty(expectSalary)) {
                            String[] salarys = expectSalary.split(",");
                            //薪资赋值
                            salary = MyApplication.map.get(salarys[salarys.length - 1]).getName();
                        }
                    }
                } else if (resumesBean.getResumeType() == 2) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String diplomas = mapstr.get("diplomas");
                    if (MyApplication.map != null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            education = MyApplication.map.get(dips[dips.length - 1]).getName();
                        }
                    }
                }
            }
        }
        //控件显示学历和地址
        if (!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)) {
            tvAddress.setText(education + "|" + address);
        } else if (TextUtils.isEmpty(education)) {
            tvAddress.setText(address);
        } else if (TextUtils.isEmpty(address)) {
            tvAddress.setText(education);
        } else {
            tvAddress.setText("");
        }
        //展示薪资
        if (!TextUtils.isEmpty(salary)) {
            tvTime.setText(salary);
            tvTime.setTextColor(Color.RED);
        }
        //展示职位
        if (!TextUtils.isEmpty(post)) {
            tvPost.setText("期望:" + post);
        }
        //显示头像
        if (result.getData().getStudentDeatil().getHead() != null && !TextUtils.isEmpty(result.getData().getStudentDeatil().getHead())) {
            PicassoUtils.LoadPathCorners(TachterInterviewActivity.this, result.getData().getStudentDeatil().getHead(), circleView);
        } else {
            PicassoUtils.LoadCorners(TachterInterviewActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //评价
        if (result.getData().getStudentDeatil().getEvaluationLabels() != null && !TextUtils.isEmpty(result.getData().getStudentDeatil().getEvaluationLabels())) {
            tagTrait.setVisibility(View.VISIBLE);
            String labels = result.getData().getStudentDeatil().getEvaluationLabels().substring(1, result.getData().getStudentDeatil().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(TachterInterviewActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagTrait.setVisibility(View.GONE);
        }
        //头像点击事件
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转界面
                StudentResumeActivity.StartActivity(TachterInterviewActivity.this, result.getData().getStuId());
            }
        });
        //展示面试时间
        if (result.getData().getInterviewTime() != null && !TextUtils.isEmpty(result.getData().getInterviewTime())) {
            isClick = false;
            tvInterviewTime.setText(TimeRender.getBirthday2(Long.parseLong(result.getData().getInterviewTime())));
        } else {
            isClick = true;
            tvInterviewTime.setText("请选择");
        }
        //联系电话
        if (!TextUtils.isEmpty(phone)) {
            tvInterviewPhone.setText(phone);
        }
        //面试地点
        if (result.getData().getInterviewAddr() != null && !TextUtils.isEmpty(result.getData().getInterviewAddr())) {
            tvInterviewAddress.setText(result.getData().getInterviewAddr());
        }
        //补充说明
        if (result.getData().getInterviewDesc() != null && !TextUtils.isEmpty(result.getData().getInterviewDesc())) {
            tvExplain.setText(result.getData().getInterviewDesc());
        }

    }

    @OnClick({R.id.rl_back, R.id.ll_time, R.id.bt_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_time:
                if (isClick) {
                    // 日期格式为yyyy-MM-dd HH:mm
                    customDatePicker.show(sdf.format(new Date()));
                }
                break;
            case R.id.bt_interview:
                if(!iskey){
                    iskey=true;
                    if (MyApplication.xmppConnection != null && MyApplication.xmppConnection.isAuthenticated()) {
                        if(stuDataBean!=null){
                            updateStudyX();
                        }else {
                            updateStudy();
                        }
                    }else {
                        ToastUtil.showShortToast(TachterInterviewActivity.this, "登录服务器出错，请重新登录");
                    }

                }


                break;
        }
    }

    /**
     * 更新面试学习状态
     */
    private void updateStudy() {
        try {
        if("请选择".equals(tvInterviewTime.getText().toString().trim())){
            ToastUtil.showShortToast(TachterInterviewActivity.this,"请选择面试时间");
            return;
        }
        if(TextUtils.isEmpty(tvInterviewAddress.getText().toString().trim())){
            ToastUtil.showShortToast(TachterInterviewActivity.this,"请填写面试地点");
            return;
        }
        if(!TextUtils.isEmpty(tvExplain.getText().toString().trim())){
            if(tvExplain.getText().toString().trim().length()>100){
                ToastUtil.showShortToast(TachterInterviewActivity.this,"补充说明要求100字符以内");
                return;
            }
        }else{
            ToastUtil.showShortToast(TachterInterviewActivity.this,"请填写补充说明");
            return;

        }
            //网络状态判断
            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                return;
            }
        HashMap<String,String>map=new HashMap<>();
        map.put("id",String.valueOf(detailBean.getData().getId()));
        map.put("stuId",String.valueOf(detailBean.getData().getStuId()));
        map.put("tchId",String.valueOf(detailBean.getData().getTchId()));
        map.put("optFlag",String.valueOf(optFla));
        map.put("interviewGoal","1");
        if(optFla==7){
            map.put("interviewTime",String.valueOf(sdf.parse(tvInterviewTime.getText().toString().trim()).getTime()));
            map.put("interviewPhone",tvInterviewPhone.getText().toString().trim());
            map.put("interviewAddr",tvInterviewAddress.getText().toString().trim());
            map.put("interviewDesc",tvExplain.getText().toString().trim());
        }
        HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBeanX>() {
            @Override
            public void onSuccess(ReturnBeanX result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    if(result.getMap()!=null){
                        if(!TextUtils.isEmpty(userContactId)){
                            sendMsgText("",result.getMap().getStudyId(),"T41");
                        }
                    }
                }else {
                    ToastUtil.showShortToast(TachterInterviewActivity.this,result.getMsg());
                }
            }

        });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * 更新面试学习状态
     */
    private void updateStudyX() {
        try {
            if("请选择".equals(tvInterviewTime.getText().toString().trim())){
                ToastUtil.showShortToast(TachterInterviewActivity.this,"请选择面试时间");
                return;
            }
            if(TextUtils.isEmpty(tvInterviewAddress.getText().toString().trim())){
                ToastUtil.showShortToast(TachterInterviewActivity.this,"请填写面试地点");
                return;
            }
            if(!TextUtils.isEmpty(tvExplain.getText().toString().trim())){
                if(tvExplain.getText().toString().trim().length()>100){
                    ToastUtil.showShortToast(TachterInterviewActivity.this,"补充说明要求100字符以内");
                    return;
                }
            }else{
                ToastUtil.showShortToast(TachterInterviewActivity.this,"请填写补充说明");
                return;
            }
            //网络状态判断
            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                return;
            }
            HashMap<String,String>map=new HashMap<>();
            if(studyId!=null && !TextUtils.isEmpty(studyId)){
                map.put("id",studyId);
            }
            map.put("stuId", stuId.split("_")[1]);
            map.put("tchId", tchId.split("_")[1]);
            map.put("optFlag",String.valueOf(optFla));
            map.put("interviewGoal","1");
            if(optFla==7){
                map.put("interviewTime",String.valueOf(sdf.parse(tvInterviewTime.getText().toString().trim()).getTime()));
                map.put("interviewPhone",tvInterviewPhone.getText().toString().trim());
                map.put("interviewAddr",tvInterviewAddress.getText().toString().trim());
                map.put("interviewDesc",tvExplain.getText().toString().trim());
            }
            HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBeanX>() {
                @Override
                public void onSuccess(ReturnBeanX result) {
                    if(result==null){
                        return;
                    }
                    if(result.getStatus()==200){
                        if(!TextUtils.isEmpty(userContactId)){
                            sendMsgText("",result.getMap().getStudyId(),"T41");
                        }
                    }else {
                        ToastUtil.showShortToast(TachterInterviewActivity.this,result.getMsg());
                    }
                }

            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param id
     */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, TachterInterviewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
    public static void StartActivity(Context context, TeacherDetailBean.DataBean dataBean,StudentRecordBean.DataBean stuDataBean,String userContactId) {
        Intent intent = new Intent(context, TachterInterviewActivity.class);
        intent.putExtra("dataBean", dataBean);
        intent.putExtra("stuDataBean", stuDataBean);
        intent.putExtra("userContactId", userContactId);
        context.startActivity(intent);
    }

    /**
     * 发送消息
     * @param content
     * @param businessId
     * @param type
     */
    private void sendMsgText(String content, String businessId, String type) {
        if (MyApplication.xmppConnection != null && MyApplication.xmppConnection.isAuthenticated()) {
            //封装xmpp发送的格式
            msgBean = new MsgBean();
            msgBean.setType(type);
            msgBean.setMsg(content);
            msgBean.setSessionId(Integer.parseInt(userContactId));
            msgBean.setBusinessId(businessId);
            //封装本地消息的保存DB的形式
            dBmsgBean = new DBmsgBean();
            dBmsgBean.setBusinessId(businessId);
            dBmsgBean.setMsg(content);
            dBmsgBean.setType(type);
            //xmpp发送
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        XmppUtil.sendMessage(MyApplication.xmppConnection, gson.toJson(msgBean).replace("type", "msgType"), tchId, stuId);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        ToastUtil.showShortToast(TachterInterviewActivity.this, "发送失败");
                    }
                }
            }).start();
            //将消息记录保存后台
            SaveChat(gson.toJson(dBmsgBean), tchId, stuId, msgBean.getType());
        } else {
            ToastUtil.showShortToast(TachterInterviewActivity.this, "登录服务器出错，请重新登录");
        }

    }
    /*
  * 保存聊天记录
  * 提交后台服务器保存
  */
    private void SaveChat(String message, String sender, String receiver, String msgType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userContactId", userContactId);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("msg", message);
        hashMap.put("msgType", msgType);//私聊

        HttpClient.post(this, Api.SAVE_CHAT_MSG, hashMap, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    Logger.d("消息保存服务器成功");
                    Intent intent=new Intent();
                    intent.putExtra(Const.INVITE_TYPE,"T41");
                    TachterInterviewActivity.this.setResult(EditPersonInfoActivity.requestCode,intent);
                    finish();
                } else {
                    ToastUtil.showShortToast(TachterInterviewActivity.this, result.getMsg());
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text=tvExplain.getText().toString().trim().length() + "/100字";
        tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
    }
    /**
     * 获取回话id
     */
    private void startUserContact(int stuId,int tchId) {
        HashMap<String,String>map=new HashMap<>();
        map.put("stuId",String.valueOf(stuId));
        map.put("tchId",String.valueOf(tchId));
        HttpClient.post(this, Api.START_USER_CONTACT, map, new CallBack<UserContactBean>() {
            @Override
            public void onSuccess(UserContactBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    userContactId=String.valueOf(result.getData().getId());

                }else {
                    ToastUtil.showShortToast(TachterInterviewActivity.this,result.getMsg());
                }
            }


        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

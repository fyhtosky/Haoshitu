package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudyDetailBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DatePickerPop;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.CustomDatePicker;

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

/**
 * 面试详情
 */
public class InterviewDeatilActivity extends AppCompatActivity {

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
    TextView tvInterviewAddress;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.rl_explain)
    LinearLayout rlExplain;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.iv_yes)
    ImageView ivYes;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_refused_person)
    TextView tvRefusedPerson;
    @BindView(R.id.tv_refused_cause)
    TextView tvRefusedCause;
    @BindView(R.id.rl_refused)
    LinearLayout rlRefused;
    @BindView(R.id.bt_interview)
    Button btInterview;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.detail)
    RelativeLayout detail;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    //界面传值的id
    private int id;
    //电话号码
    private String phone = "";
    private boolean isClick = false;
    //时间选择器
    private DatePickerPop pickerPop;
    private StudyDetailBean detailBean;
    private CustomDatePicker startPicker, endPicker;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private int optFlag;
    //是否选择面试结果
    private boolean isSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_deatil);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initDatePicker();
        pickerPop = new DatePickerPop(InterviewDeatilActivity.this);
        phone = PreferencesUtils.getSharePreStr(InterviewDeatilActivity.this, Const.PHONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            Logger.d("传值的id:" + id);
            getStudyDetail();
        }
    }

    private void initDatePicker() {
        //选择开始时间
        startPicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvStart.setText(time.split(" ")[0]);
            }
        }, "1970-01-01 00:00", "2030-12-30 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startPicker.showSpecificTime(false); // 不显示时和分
        startPicker.setIsLoop(false); // 不允许循环滚动
        //选择结束时间
        endPicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvEnd.setText(time.split(" ")[0]);
            }
        }, "1970-01-01 00:00", "2030-12-30 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        endPicker.showSpecificTime(false); // 不显示时和分
        endPicker.setIsLoop(false); // 不允许循环滚动
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
                    detailBean = result;
                    showDeatil(result);
                } else {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, result.getMsg());
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
        //显示Button的状态
        switch (result.getData().getCurrentStatus()) {
            case 101:
                btInterview.setText("取消面试");
                btInterview.setTextColor(Color.parseColor("#24C789"));
                btInterview.setBackgroundResource(R.drawable.btn_start_white);
                break;
            case 102:
                rlRefused.setVisibility(View.VISIBLE);
                tvRefusedPerson.setText(PreferencesUtils.getSharePreStr(InterviewDeatilActivity.this, Const.NAME));
                if (result.getData().getInterviewRefuseNote() != null && !TextUtils.isEmpty(result.getData().getInterviewRefuseNote())) {
                    tvRefusedCause.setText(result.getData().getInterviewRefuseNote());
                }
                btInterview.setText("已拒绝");
                rlRefused.setVisibility(View.VISIBLE);
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 103:
                btInterview.setText("取消面试");
                btInterview.setTextColor(Color.parseColor("#24C789"));
                btInterview.setBackgroundResource(R.drawable.btn_start_white);
                break;
            case 104:
                btInterview.setText("已取消");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 110:
                optFlag = 3;
                btInterview.setText("确认");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_1);
                break;
            case 111:
                btInterview.setText("面试失败");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 112:
                btInterview.setText("面试通过");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 113:
                rlRefused.setVisibility(View.VISIBLE);
                tvRefusedPerson.setText(result.getData().getStudentDeatil().getRealName());
                if (result.getData().getInternRefuseNote() != null && !TextUtils.isEmpty(result.getData().getInternRefuseNote())) {
                    tvRefusedCause.setText(result.getData().getInternRefuseNote());
                }
                btInterview.setText("实习拒绝");
                rlRefused.setVisibility(View.VISIBLE);
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 114:
                btInterview.setText("已取消");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 120:
                btInterview.setText("待实习");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 121:
                btInterview.setText("实习中");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 122:
                btInterview.setText("已实习");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            //123（面试终止） 124 实习终止
            case 123:
                btInterview.setText("已终止");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                llResult.setVisibility(View.GONE);
                break;
            case 124:
                btInterview.setText("已终止");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;


            default:
                break;
        }
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
            PicassoUtils.LoadPathCorners(InterviewDeatilActivity.this, result.getData().getStudentDeatil().getHead(), circleView);
        } else {
            PicassoUtils.LoadCorners(InterviewDeatilActivity.this, R.drawable.wukong, 60, 60, circleView);
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
                    View view = LayoutInflater.from(InterviewDeatilActivity.this)
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
                StudentResumeActivity.StartActivity(InterviewDeatilActivity.this, result.getData().getStuId());
            }
        });
        //展示面试时间
        if (result.getData().getInterviewTime() != null && !TextUtils.isEmpty(result.getData().getInterviewTime())) {

            tvInterviewTime.setText(TimeRender.getBirthday2(Long.parseLong(result.getData().getInterviewTime())));
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

        //显示面试结果的状态
        switch (result.getData().getCurrentStatus()) {
            //面试失败的
            case 111:
                isClick = false;
                ivNo.setImageResource(R.mipmap.fail_select);
                tvNo.setTextColor(Color.parseColor("#24C789"));
                ivYes.setImageResource(R.mipmap.pass_normal);
                tvYes.setTextColor(Color.GRAY);
                llTime.setVisibility(View.GONE);
                break;
            case 103:
            case 112:
            case 113:
            case 114:
            case 115:
            case 120:
            case 121:
            case 122:
                isClick = false;
                ivYes.setImageResource(R.mipmap.pass_select);
                tvYes.setTextColor(Color.parseColor("#24C789"));
                ivNo.setImageResource(R.mipmap.fail_normal);
                tvNo.setTextColor(Color.GRAY);
                llTime.setVisibility(View.VISIBLE);
                break;


            default:
                isClick = true;
                ivNo.setImageResource(R.mipmap.fail_normal);
                tvNo.setTextColor(Color.GRAY);
                ivYes.setImageResource(R.mipmap.pass_normal);
                tvYes.setTextColor(Color.GRAY);
                llTime.setVisibility(View.GONE);
                break;
        }
        //展示实习的时间
        if (result.getData().getInternStartTime() != null && !TextUtils.isEmpty(result.getData().getInternStartTime())) {
            tvStart.setText(TimeRender.getTime(Long.parseLong(result.getData().getInternStartTime())));
        } else {
            tvStart.setText("请选择开始日期");
        }
        if (result.getData().getInternEndTime() != null && !TextUtils.isEmpty(result.getData().getInternEndTime())) {
            tvEnd.setText(TimeRender.getTime(Long.parseLong(result.getData().getInternEndTime())));
        } else {
            tvEnd.setText("请选择结束日期");
        }

    }

    /**
     * 启动Activity
     *
     * @param context
     * @param id
     */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, InterviewDeatilActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_no, R.id.rl_yes, R.id.tv_start, R.id.tv_end, R.id.bt_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_no:
                if (isClick) {
                    ivNo.setImageResource(R.mipmap.fail_select);
                    tvNo.setTextColor(Color.parseColor("#24C789"));
                    ivYes.setImageResource(R.mipmap.pass_normal);
                    tvYes.setTextColor(Color.GRAY);
                    llTime.setVisibility(View.GONE);
                    isSelect = true;
                    //面试不通过的标示
                    optFlag = 4;
                }
                break;
            case R.id.rl_yes:
                if (isClick) {
                    ivYes.setImageResource(R.mipmap.pass_select);
                    tvYes.setTextColor(Color.parseColor("#24C789"));
                    ivNo.setImageResource(R.mipmap.fail_normal);
                    tvNo.setTextColor(Color.GRAY);
                    llTime.setVisibility(View.VISIBLE);
                    isSelect = true;
                    //面试通过标示
                    optFlag = 3;
                }

                break;
            case R.id.tv_start:
                if (detailBean.getData().getCurrentStatus() == 110) {
//                    pickerPop.show(detail, new PhoneCallBack() {
//                        @Override
//                        public void setContent(String content) {
//                            tvStart.setText(content);
//                        }
//                    });
                    startPicker.show(sdf.format(new Date()));
                }
                break;
            case R.id.tv_end:
                if (detailBean.getData().getCurrentStatus() == 110) {
//                    pickerPop.show(detail, new PhoneCallBack() {
//                        @Override
//                        public void setContent(String content) {
//                            tvEnd.setText(content);
//                        }
//                    });
                    endPicker.show(sdf.format(new Date()));
                }
                break;
            case R.id.bt_interview:
                if (detailBean.getData().getCurrentStatus() == 110) {
                    updateStudy();
                }
                break;
        }
    }

    /**
     * 更新面试学习状态
     */
    private void updateStudy() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(detailBean.getData().getId()));
            map.put("stuId", String.valueOf(detailBean.getData().getStuId()));
            map.put("tchId", String.valueOf(detailBean.getData().getTchId()));
            map.put("optFlag", String.valueOf(optFlag));
            map.put("interviewGoal", "1");
            if (optFlag == 3) {
                if (!isSelect) {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, "请选择面试结果-图片中通过或不通过");
                    return;
                }
                if ("请选择开始日期".equals(tvStart.getText().toString().trim())) {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, "请选择实习开始时间");
                    return;
                }
                if ("请选择结束日期".equals(tvEnd.getText().toString().trim())) {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, "请选择实习结束时间");
                    return;
                }
                if (sdf.parse(tvStart.getText().toString().trim()).getTime() <= new Date().getTime()) {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, "实习开始时间应该在当前时间以后");
                    return;
                }
                if (sdf.parse(tvStart.getText().toString().trim()).getTime() >= sdf.parse(tvEnd.getText().toString().trim()).getTime()) {
                    ToastUtil.showShortToast(InterviewDeatilActivity.this, "选择实习时间不合理,请重新选择实习时间");
                    return;
                }
                map.put("internStartTime", String.valueOf(sdf.parse(tvStart.getText().toString().trim()).getTime()));
                map.put("internEndTime", String.valueOf(sdf.parse(tvEnd.getText().toString().trim()).getTime()));
            } else if (optFlag == 4) {
                map.put("interviewResultNote", "面试不通过");
            }
            HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        if (optFlag == 3) {
                            btInterview.setText("面试通过");
                            btInterview.setTextColor(Color.WHITE);
                            btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                        } else if (optFlag == 4) {
                            btInterview.setText("面试不通过");
                            btInterview.setTextColor(Color.WHITE);
                            btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                        }

                    } else {
                        ToastUtil.showShortToast(InterviewDeatilActivity.this, result.getMsg());
                    }
                }

            });

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}


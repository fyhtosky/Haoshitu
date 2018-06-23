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
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.event.AdapterSendMsgEvent;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopReason;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 面试邀请的界面
 */
public class IterviewActivity extends AppCompatActivity {

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
    @BindView(R.id.tv_interview_target)
    TextView tvInterviewTarget;
    @BindView(R.id.tv_interview_time)
    TextView tvInterviewTime;
    @BindView(R.id.tv_interview_phone)
    TextView tvInterviewPhone;
    @BindView(R.id.tv_interview_address)
    TextView tvInterviewAddress;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.rl_explain)
    LinearLayout rlExplain;
    @BindView(R.id.tv_refused_person)
    TextView tvRefusedPerson;
    @BindView(R.id.tv_refused_cause)
    TextView tvRefusedCause;
    @BindView(R.id.rl_refused)
    LinearLayout rlRefused;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.rl_remark)
    LinearLayout rlRemark;
    @BindView(R.id.bt_interview)
    Button btInterview;
    @BindView(R.id.iterview)
    RelativeLayout iterview;
    //界面传值的id
    private int id;
    //电话号码
    private String phone = "";
    //数据交互的 对象
    private StudyDetailBean detailBean;
    //标示操作的类型
    private int optFlag;
    //原因的选择器
    private PopReason popReason;
    private List<String>list=Arrays.asList("学徒主动联系","天气等不可控制原因");
    //标示是否可点
    private boolean isShow=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iterview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        popReason=new PopReason(IterviewActivity.this);
        phone = PreferencesUtils.getSharePreStr(IterviewActivity.this, Const.PHONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            Logger.d("传值的id:" + id);
            getStudyDetail();
        }
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
                    ToastUtil.showShortToast(IterviewActivity.this, result.getMsg());
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
                isShow=true;
                optFlag = 2;
                btInterview.setText("取消面试");
                btInterview.setTextColor(Color.parseColor("#24C789"));
                btInterview.setBackgroundResource(R.drawable.btn_start_white);
                break;
            case 102:
                isShow=false;
                rlRefused.setVisibility(View.VISIBLE);
                btInterview.setText("已拒绝");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 103:
                isShow=false;
                btInterview.setText("已接受");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 104:
                isShow=false;
                rlRemark.setVisibility(View.VISIBLE);
                btInterview.setText("已取消");
                btInterview.setTextColor(Color.WHITE);
                btInterview.setBackgroundResource(R.drawable.btn_start_gray);
                break;
            case 110:
                optFlag=3;
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
                btInterview.setText("实习拒绝");
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
            default:
                break;
        }
        btInterview.setVisibility(View.VISIBLE);
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
            PicassoUtils.LoadPathCorners(IterviewActivity.this, result.getData().getStudentDeatil().getHead(), circleView);
        } else {
            PicassoUtils.LoadCorners(IterviewActivity.this, R.drawable.wukong, 60, 60, circleView);
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
                    View view = LayoutInflater.from(IterviewActivity.this)
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
                StudentResumeActivity.StartActivity(IterviewActivity.this, result.getData().getStuId());
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
        //显示拒绝的人
            tvRefusedPerson.setText(PreferencesUtils.getSharePreStr(IterviewActivity.this, Const.NAME));
            if (result.getData().getInterviewRefuseNote() != null && !TextUtils.isEmpty(result.getData().getInterviewRefuseNote())) {
                tvRefusedCause.setText(result.getData().getInterviewRefuseNote());
            }

        //显示备注
            if (result.getData().getInterviewCancelNote() != null && !TextUtils.isEmpty(result.getData().getInterviewCancelNote())) {
                tvRemark.setText(result.getData().getInterviewCancelNote());
            }

    }

    @OnClick({R.id.rl_back, R.id.bt_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_interview:
                if(isShow){
                    popReason.show(iterview, list, new PhoneCallBack() {
                        @Override
                        public void setContent(String content) {
                            updateStudy(content);
                        }
                    });
                }


                break;
        }
    }

    /**
     * 更新面试学习状态
     */
    private void updateStudy(String content) {
        if (optFlag == 2) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(detailBean.getData().getId()));
            map.put("stuId", String.valueOf(detailBean.getData().getStuId()));
            map.put("tchId", String.valueOf(detailBean.getData().getTchId()));
            map.put("optFlag", String.valueOf(optFlag));
            map.put("interviewGoal", "1");
            map.put("interviewCancelNote", content);
            HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        IterviewActivity.this.finish();
                    } else {
                        ToastUtil.showShortToast(IterviewActivity.this, result.getMsg());
                    }
                }

            });
        }

    }

    /**
     * 启动Activity
     *
     * @param context
     * @param id
     */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, IterviewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

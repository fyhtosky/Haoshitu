package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudyDetailBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 学生端的求职管理中的待处理点击的面试邀请
 */
public class StudentInterviewActivity extends AppCompatActivity {
    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.tv_interview_target)
    TextView tvInterviewTarget;
    @BindView(R.id.tv_interview_time)
    TextView tvInterviewTime;
    @BindView(R.id.tv_interview_phone)
    TextView tvInterviewPhone;
    @BindView(R.id.tv_interview_address)
    TextView tvInterviewAddress;
    @BindView(R.id.rl_address)
    LinearLayout rlAddress;
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
    @BindView(R.id.ll_button)
    LinearLayout llButton;
    @BindView(R.id.rl_student_interview)
    RelativeLayout rlStudentInterview;
    //界面传值的id
    private int id;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    private int searchBy = 0;
    //拒绝面试的选择器
    private PopReason popReason;
    private StudyDetailBean detailBean;
    //拒绝的原因的数据源
    private List<String>list=Arrays.asList("实习过远","方向不对口","薪资过低","已有实习师父");
    private int optFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_interview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        popReason=new PopReason(StudentInterviewActivity.this);
        loginId = PreferencesUtils.getSharePreInt(StudentInterviewActivity.this, Const.LOGINID);
        if (loginId == 1) {//学生登录
            searchBy = 1;
        } else if (loginId == 2) {//老师登录
            searchBy = 0;
        }
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
        String params = "?id=" + String.valueOf(id) + "&searchBy=" + String.valueOf(searchBy);
        HttpClient.get(this, Api.GET_STUDY_DEATIL + params, new CallBack<StudyDetailBean>() {
            @Override
            public void onSuccess(StudyDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    detailBean=result;
                    showDeatil(result);
                } else {
                    ToastUtil.showShortToast(StudentInterviewActivity.this, result.getMsg());
                }
            }


        });
    }

    private void showDeatil(final StudyDetailBean result) {
        //显示Button的状态
//        switch (result.getData().getCurrentStatus()) {
//            case 101:
//                btInterview.setText("待处理");
//                break;
//            case 102:
//                btInterview.setText("已拒绝");
//                break;
//            case 103:
//                btInterview.setText("已接受");
//                break;
//            case 104:
//                btInterview.setText("已取消");
//                break;
//            case 110:
//                btInterview.setText("待面试");
//                break;
//            case 111:
//                btInterview.setText("面试失败");
//                break;
//            case 112:
//                btInterview.setText("面试成功");
//                break;
//            case 113:
//                btInterview.setText("已拒绝");
//                break;
//            case 114:
//                btInterview.setText("已取消");
//                break;
//            case 115:
//                btInterview.setText("已接受");
//                break;
//            case 120:
//                btInterview.setText("待实习");
//                break;
//            case 121:
//                btInterview.setText("实习中");
//                break;
//            case 122:
//                btInterview.setText("已实习");
//                break;
//            default:
//                break;
//        }

        //展示用户名
        tvName.setText(result.getData().getTeacher().getRealName());
        //职位和公司
        tvPost.setText(result.getData().getTeacher().getPositionId() + " | " + result.getData().getTeacher().getCompanyName());
        //显示头像
        if (result.getData().getTeacher().getImgUrl() != null && !TextUtils.isEmpty(result.getData().getTeacher().getImgUrl())) {
            PicassoUtils.LoadPathCorners(StudentInterviewActivity.this, result.getData().getTeacher().getImgUrl(), circleView);
        } else {
            PicassoUtils.LoadCorners(StudentInterviewActivity.this, R.drawable.master, 60, 60, circleView);
        }
        //头像点击事件
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转界面
                Intent intent = new Intent(StudentInterviewActivity.this, TeacherDetailActivity.class);
                intent.putExtra("id", result.getData().getTchId());
                startActivity(intent);
            }
        });
        //技能标签
        if (result.getData().getTeacher().getSkills() != null) {
            tagTrait.setVisibility(View.VISIBLE);
            tagTrait.setAdapter(new TagAdapter<StudyDetailBean.DataBean.TeacherBean.SkillsBean>(result.getData().getTeacher().getSkills()) {
                @Override
                public View getView(FlowLayout parent, int position, StudyDetailBean.DataBean.TeacherBean.SkillsBean skillsBean) {
                    View view = LayoutInflater.from(StudentInterviewActivity.this)
                            .inflate(R.layout.item_textview, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(skillsBean.getSkillName());
                    return view;
                }
            });
        } else {
            tagTrait.setVisibility(View.GONE);
        }
        //评价
        if (result.getData().getTeacher().getEvaluationLabels() != null && !TextUtils.isEmpty(result.getData().getTeacher().getEvaluationLabels())) {
            tagSkill.setVisibility(View.VISIBLE);
            String labels = result.getData().getTeacher().getEvaluationLabels().substring(1, result.getData().getTeacher().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagSkill.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StudentInterviewActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagSkill.setVisibility(View.GONE);
        }
        //面试目标
        tvInterviewTarget.setText("跟师学习");
        //面试时间
        if (result.getData().getInterviewTime() != null && !TextUtils.isEmpty(result.getData().getInterviewTime())) {
            tvInterviewTime.setText(TimeRender.getBirthday2(Long.parseLong(result.getData().getInterviewTime())));
        }
        //联系电话
        if (result.getData().getInterviewPhone() != null && !TextUtils.isEmpty(result.getData().getInterviewTime())) {
            tvInterviewPhone.setText(result.getData().getInterviewPhone());
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
        if (result.getData().getCurrentStatus() == 102) {
            rlRefused.setVisibility(View.VISIBLE);
            tvRefusedPerson.setText(PreferencesUtils.getSharePreStr(StudentInterviewActivity.this, Const.NAME));
            if (result.getData().getInterviewRefuseNote() != null && !TextUtils.isEmpty(result.getData().getInterviewRefuseNote())) {
                tvRefusedCause.setText(result.getData().getInterviewRefuseNote());
            }
        } else {
            rlRefused.setVisibility(View.GONE);
        }
        //显示备注
        if (result.getData().getCurrentStatus() == 104) {
            rlRemark.setVisibility(View.VISIBLE);
            if (result.getData().getInterviewCancelNote() != null && !TextUtils.isEmpty(result.getData().getInterviewCancelNote())) {
                tvRemark.setText(result.getData().getInterviewCancelNote());
            }
        } else {
            rlRemark.setVisibility(View.GONE);
        }
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param id
     */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, StudentInterviewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.bt_interview,R.id.bt_refused, R.id.bt_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_refused:
                //学生拒绝面试
                optFlag=0;
                popReason.show(rlStudentInterview, list, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        updateStudy(content);
                    }
                });
                break;
            case R.id.bt_accept:
                //学生接受面试
               optFlag=1;
                updateStudy("");
                break;
            case R.id.bt_interview:
                break;
        }
    }

    /**
     * 更新面试学习状态
     */
    private void updateStudy(final String content) {
            HashMap<String,String> map=new HashMap<>();
            map.put("id",String.valueOf(detailBean.getData().getId()));
            map.put("stuId",String.valueOf(detailBean.getData().getStuId()));
            map.put("tchId",String.valueOf(detailBean.getData().getTchId()));
            map.put("optFlag",String.valueOf(optFlag));
            map.put("interviewGoal","1");
            if(optFlag==0){
                map.put("interviewRefuseNote",content);
            }
            HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if(result==null){
                        return;
                    }
                    if(result.getStatus()==200){
                        llButton.setVisibility(View.GONE);
                        btInterview.setVisibility(View.VISIBLE);
                         if(optFlag==0){
                             rlRefused.setVisibility(View.VISIBLE);
                             tvRefusedPerson.setText(PreferencesUtils.getSharePreStr(StudentInterviewActivity.this, Const.NAME));
                             tvRefusedCause.setText(content);
                             btInterview.setText("已拒绝");
                         }else{
                             btInterview.setText("已接受");
                         }
                    }else {
                        ToastUtil.showShortToast(StudentInterviewActivity.this,result.getMsg());
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

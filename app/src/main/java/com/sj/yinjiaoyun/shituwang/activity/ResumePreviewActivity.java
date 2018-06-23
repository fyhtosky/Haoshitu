package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.DirviderDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 简历预览界面
 */
public class ResumePreviewActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.rv_education)
    RecyclerView rvEducation;
    @BindView(R.id.rv_work)
    RecyclerView rvWork;
    @BindView(R.id.rv_project)
    RecyclerView rvProject;
    @BindView(R.id.rv_train)
    RecyclerView rvTrain;
    @BindView(R.id.rv_certificate)
    RecyclerView rvCertificate;
    @BindView(R.id.rv_skill)
    RecyclerView rvSkill;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_work_state)
    TextView tvWorkState;
    //登录的id
    private int id;
    //期望工作的集合
    private List<StudentRecordBean.DataBean.ResumesBean> expectList = new ArrayList<>();
    //教育经历的集合
    private List<StudentRecordBean.DataBean.ResumesBean> educationList = new ArrayList<>();
    //专业技能的集合
    private List<StudentRecordBean.DataBean.ResumesBean> skillList = new ArrayList<>();
    //工作经历的集合
    private List<StudentRecordBean.DataBean.ResumesBean> workList = new ArrayList<>();
    //项目经验的集合
    private List<StudentRecordBean.DataBean.ResumesBean> projectList = new ArrayList<>();
    //培训经历的集合
    private List<StudentRecordBean.DataBean.ResumesBean> trainList = new ArrayList<>();
    //证书的集合
    private List<StudentRecordBean.DataBean.ResumesBean> lcsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_preview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(ResumePreviewActivity.this, Const.ID);
        //获取学生档案的信息
        getStudentRecord();
        /**
         * 教育经历的适配器
         */
        rvEducation.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
//        rvEducation.addItemDecoration(new DirviderDecoration());
        rvEducation.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.education_item,educationList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvEducationTime = baseViewHolder.getView(R.id.tv_education_time);
                TextView tvEducationSchool = baseViewHolder.getView(R.id.tv_education_school);
                TextView tvEducationMarjor = baseViewHolder.getView(R.id.tv_education_marjor);
                if (resumesBean.getResume() != null && !TextUtils.isEmpty(resumesBean.getResume())) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //学校
                    tvEducationSchool.setText(mapstr.get("collegeName"));
                    //就读时间
                    String beginTime = mapstr.get("beginTime");
                    String endTime = mapstr.get("endTime");
                    if (endTime != null && !TextUtils.isEmpty(endTime)) {
                        Logger.d("beginTime=" + beginTime + ",endTime=" + endTime);
                        tvEducationTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "-" + TimeRender.getForm(Long.parseLong(endTime)));
                    } else {
                        Logger.d("beginTime=" + beginTime);
                        tvEducationTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "-至今");
                    }
                    //展示学历
                    String education = mapstr.get("diplomas");
                    String record="";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(education)) {
                            String[] educations = education.split(",");
                            record=MyApplication.map.get(educations[educations.length - 1]).getName();
                        }
                    }
                    //专业
                    String  majorName = mapstr.get("majorName");
                    if(!TextUtils.isEmpty(record)){
                        tvEducationMarjor.setText(majorName+"|"+record);
                    }else{
                        tvEducationMarjor.setText(majorName);
                    }

                }
            }
        });
        /**
         * 工作经验的适配器
         */
        rvWork.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
        rvWork.addItemDecoration(new DirviderDecoration());
        rvWork.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.work_item,workList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                  TextView tvWorkTime=baseViewHolder.getView(R.id.tv_work_time);
                  TextView tvWorkCompany=baseViewHolder.getView(R.id.tv_work_company);
                  TextView tvWorkPost=baseViewHolder.getView(R.id.tv_work_post);
                  TextView tvWorkDescribe=baseViewHolder.getView(R.id.tv_work_describe);
                  String resume = resumesBean.getResume();
                if (resume != null && !TextUtils.isEmpty(resume)) {
                    Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //公司行业
                    String  companyBusiness = mapstr.get("companyBusiness");
                    String business="";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(companyBusiness)) {
                            String[] Business = companyBusiness.split(",");
                            business=MyApplication.map.get(Business[Business.length - 1]).getName();
                        }
                    }
                    //公司的名字
                    String   companyName = mapstr.get("companyName");
                    tvWorkCompany.setText(companyName+"|"+business);
                    //工作类型
                   String workType = mapstr.get("workType");
                    String type="";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(workType)) {
                            String[] types = workType.split(",");
                            //展示工作类型
                            type=MyApplication.map.get(types[types.length - 1]).getName();
                        }
                    }

                    //职位名称
                    String postName = mapstr.get("positionName");
                    //职位类型
                   String positionType = mapstr.get("positionType");
                    String position="";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(positionType)) {
                            String[] positionTypes = positionType.split(",");
                            //展示工作类型
                            position=MyApplication.map.get(positionTypes[positionTypes.length - 1]).getName();
                        }
                    }
                   tvWorkPost.setText(postName+"|"+position+"|"+type);
                    // 任职时间
                   String beginTime = mapstr.get("beginTime");
                  String  endTime = mapstr.get("endTime");
                    if (endTime != null&&!TextUtils.isEmpty(endTime)) {
                        Logger.d("beginTime=" + beginTime + ",endTime=" + endTime);
                        tvWorkTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "至" + TimeRender.getForm(Long.parseLong(endTime)));
                    } else {
                        Logger.d("beginTime=" + beginTime);
                        tvWorkTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "至 至今");
                    }
                    //工作描述
                    String description = mapstr.get("workDesc");
                   tvWorkDescribe.setText(description);
                }
            }
        });
        /**
         *
         * 项目经验的适配器
         */
        rvProject.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
//        rvProject.addItemDecoration(new DirviderDecoration());
        rvProject.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.work_item,projectList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvWorkTime=baseViewHolder.getView(R.id.tv_work_time);
                TextView tvWorkCompany=baseViewHolder.getView(R.id.tv_work_company);
                TextView tvWorkPost=baseViewHolder.getView(R.id.tv_work_post);
                TextView tvWorkDescribe=baseViewHolder.getView(R.id.tv_work_describe);
                String resume = resumesBean.getResume();
                {
                    Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //项目名称
                   String projectName = mapstr.get("projectName");
                    tvWorkCompany.setText(projectName);
                    //项目时间
                    String beginTime = mapstr.get("beginTime");
                    String endTime = mapstr.get("endTime");
                    if (endTime != null && !TextUtils.isEmpty(endTime)) {
                        tvWorkTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "至 " + TimeRender.getForm(Long.parseLong(endTime)));
                    } else {
                        tvWorkTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + "至 至今");
                    }
                    //项目类别
                   String position = mapstr.get("position");
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(position)) {
                            String[] positions = position.split(",");
                            tvWorkPost.setText(MyApplication.map.get(positions[positions.length - 1]).getName());
                        }
                    }
                    //项目描述
                  String  description = mapstr.get("projectDesc");
                   tvWorkDescribe.setText(description);
                }
            }
        });
        /**
         * 培训经历的适配器
         */
        rvTrain.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
        rvTrain.addItemDecoration(new DirviderDecoration());
        rvTrain.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.train_item,trainList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvTrainTime=baseViewHolder.getView(R.id.tv_train_time);
                TextView tvTrainOrgan=baseViewHolder.getView(R.id.tv_train_organ);
                TextView tvTrainCourse=baseViewHolder.getView(R.id.tv_train_course);
                TextView tvTrainLcs=baseViewHolder.getView(R.id.tv_train_lcs);
                String resume = resumesBean.getResume();
                if (resume != null && !TextUtils.isEmpty(resume)) {
                    Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //培训机构
                   String trainingOrgName = mapstr.get("trainingOrgName");
                    tvTrainOrgan.setText(trainingOrgName);
                    //培训时间
                    String beginTime = mapstr.get("beginTime");
                    String endTime = mapstr.get("endTime");
                    if (endTime != null&& !TextUtils.isEmpty(endTime)) {
                        tvTrainTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + " 至 " + TimeRender.getForm(Long.parseLong(endTime)));
                    } else {
                        tvTrainTime.setText(TimeRender.getForm(Long.parseLong(beginTime)) + " 至 至今");
                    }
                    //培训课程
                   String trainingCourseName = mapstr.get("trainingCourseName");
                    tvTrainCourse.setText(trainingCourseName);
                    //证书名称
                   String trainingCertificateName = mapstr.get("trainingCertificateName");
                    tvTrainLcs.setText(trainingCertificateName);
                }
            }
        });
        /**
         * 我的证书
         */
        rvCertificate.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
        rvCertificate.addItemDecoration(new DirviderDecoration());
        rvCertificate.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.certificate_item,lcsList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvCertificateTime=baseViewHolder.getView(R.id.tv_certificate_time);
                TextView tvCertificateName=baseViewHolder.getView(R.id.tv_certificate_name);
                TextView tvCertificateUnit=baseViewHolder.getView(R.id.tv_certificate_unit);

                String resume = resumesBean.getResume();
                if (resume != null && !TextUtils.isEmpty(resume)) {
                    Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //证书名称
                   String lcsName = mapstr.get("certificateName");
                    tvCertificateName.setText(lcsName);
                    //发证时间
                    String awardTime = mapstr.get("awardTime");
                    Logger.d("awardTime=" + awardTime);
                    tvCertificateTime.setText(TimeRender.getFormat(Long.parseLong(awardTime)));
                    //发证单位
                   String unit = mapstr.get("certificateOrgName");
                    tvCertificateUnit.setText(unit);


                }
            }
        });
        /**
         * 专业技能适配器
         */
        rvSkill.setLayoutManager(new LinearLayoutManager(ResumePreviewActivity.this, LinearLayoutManager.VERTICAL, false));
        rvSkill.addItemDecoration(new DirviderDecoration());
        rvSkill.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.skill_item,skillList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvSkillName=baseViewHolder.getView(R.id.tv_skill_name);
                TextView tvSkillExp=baseViewHolder.getView(R.id.tv_skill_exp);
                String resume = resumesBean.getResume();
                if(resume!=null && !TextUtils.isEmpty(resume)){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    tvSkillName.setText(mapstr.get("skillName"));
                    String skillLevel = mapstr.get("skillLevel").split(",")[1];
                    Logger.d("SkillAdapter：skillLevel=" + skillLevel);
                    if(MyApplication.map!=null){
                        if(MyApplication.map.get(skillLevel)!=null){
                            Logger.d("SkillAdapter："+MyApplication.map.get(skillLevel).toString());
                            String skill = MyApplication.map.get(skillLevel).getName();
                            tvSkillExp.setText(skill);
                        }
                    }
                }
            }
        });

        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
      }

    private void getStudentRecord() {
        String params = "?studentId=" + String.valueOf(id);
        HttpClient.get(this, Api.GTE_STUDENT_RECORD + params, new CallBack<StudentRecordBean>() {
            @Override
            public void onSuccess(StudentRecordBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    expectList.clear();
                    educationList.clear();
                    skillList.clear();
                    workList.clear();
                    projectList.clear();
                    trainList.clear();
                    lcsList.clear();
//                    档案类型    1:期望工作 2：教育经历 3：工作经历 4：项目经验 5：培训经历；6：我的证书；7：职业技能

                    if (result.getData().getResumes() != null && result.getData().getResumes().size() > 0) {
                        for (int i = 0; i < result.getData().getResumes().size(); i++) {
                            //教育经历
                            if (result.getData().getResumes().get(i).getResumeType() == 1) {
                                expectList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 2) {
                                educationList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 7) {
                                skillList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 3) {
                                workList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 4) {
                                projectList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 5) {
                                trainList.add(result.getData().getResumes().get(i));
                            } else if (result.getData().getResumes().get(i).getResumeType() == 6) {
                                lcsList.add(result.getData().getResumes().get(i));
                            }
                        }
                        rvEducation.getAdapter().notifyDataSetChanged();
                        rvWork.getAdapter().notifyDataSetChanged();
                        rvProject.getAdapter().notifyDataSetChanged();
                        rvTrain.getAdapter().notifyDataSetChanged();
                        rvCertificate.getAdapter().notifyDataSetChanged();
                        rvSkill.getAdapter().notifyDataSetChanged();
                    }
                    showRecord(result);
                } else {
                    ToastUtil.showShortToast(ResumePreviewActivity.this, result.getMsg());
                }

            }

        });
    }


    /**
     * 展示数据
     */
    private void showRecord(StudentRecordBean result) {
        if (result.getData() == null) {
            return;
        }
        /**
         * 基本信息
         */
        //展示简历完成度
        tvProgress.setText("简历完成度" + String.valueOf(result.getData().getResumePerfectDegree()).
                substring(0, String.valueOf(result.getData().getResumePerfectDegree()).indexOf(".")) + "%");
        //展示学生的头像
        if (result.getData().getHead() != null && !"".equals(result.getData().getHead())) {
            PicassoUtils.LoadPathCorners(ResumePreviewActivity.this, result.getData().getHead(), 70, 70, circlePicture);
        } else {
            PicassoUtils.LoadCorners(ResumePreviewActivity.this, R.drawable.wukong, 70, 70, circlePicture);
        }
        //展示姓名
        if (result.getData().getRealName() != null) {
            tvName.setText(result.getData().getRealName());
        }
        //显示性别
        if (result.getData().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //展示出生年月
        tvBirthday.setText(TimeRender.getBirthday(result.getData().getBirthday()));
        //显示地址
        tvAddress.setText(result.getData().getAddressNow());
        //显示工作状态
        if (result.getData().getWorkStatus() == 0) {
            tvWorkState.setText("暂不考虑");
        } else if (result.getData().getWorkStatus() == 1) {
            tvWorkState.setText("寻找师傅");
        } else if (result.getData().getWorkStatus() == 2) {
            tvWorkState.setText("寻找实习");
        }
        /**
         * 期望工作
         */
        if(expectList.size()>0){
            String resume=expectList.get(expectList.size()-1).getResume();
            if(resume!=null && !TextUtils.isEmpty(resume)){
                Map<String,String> mapstr=new Gson().fromJson(resume, new TypeToken<HashMap<String,String>>(){}.getType());
                if(MyApplication.map!=null){
                   String address=mapstr.get("address");
//                   String expectBusiness=mapstr.get("expectBusiness");
                   String  expectPosition=mapstr.get("expectPosition");
                   String expectSalary=mapstr.get("expectSalary");
                    //工作城市
                    tvCity.setText(address);
//                    if(!TextUtils.isEmpty(expectBusiness)){
//                        //期望行业
//                        String [] business=expectBusiness.split(",");
//                        tvWork.setText(MyApplication.map.get(business[business.length-1]).getName());
//                    }
                    if(!TextUtils.isEmpty(expectPosition)){
                        //期望职业
                        String [] position=expectPosition.split(",");
                        tvWork.setText(MyApplication.map.get(position[position.length-1]).getName());
                    }
                    if(!TextUtils.isEmpty(expectSalary)){
                        //期望薪资
                        String []salary=expectSalary.split(",");
                        tvSalary.setText(MyApplication.map.get(salary[salary.length-1]).getName()+"/月");
                    }
                }
            }
        }
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

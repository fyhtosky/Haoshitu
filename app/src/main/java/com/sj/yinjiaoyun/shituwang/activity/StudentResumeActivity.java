package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.DBmsgBean;
import com.sj.yinjiaoyun.shituwang.bean.MsgBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
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
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.DirviderDecoration;
import com.sj.yinjiaoyun.shituwang.xmppmanager.XmppUtil;

import org.jivesoftware.smack.XMPPException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 学生简历的界面
 */
public class StudentResumeActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_work_state)
    TextView tvWorkState;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.tv_description)
    TextView tvDescription;
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
    @BindView(R.id.bt_interested)
    Button btInterSted;
    @BindView(R.id.rl_expect_work)
    LinearLayout rlExpectWork;
    @BindView(R.id.ll_education)
    LinearLayout llEducation;
    @BindView(R.id.ll_work)
    LinearLayout llWork;
    @BindView(R.id.ll_project)
    LinearLayout llProject;
    @BindView(R.id.ll_train)
    LinearLayout llTrain;
    @BindView(R.id.ll_certificate)
    LinearLayout llCertificate;
    @BindView(R.id.ll_skill)
    LinearLayout llSkill;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    @BindView(R.id.rl_net)
    RelativeLayout rlNet;
    @BindView(R.id.rl_button)
    RelativeLayout rlButton;

    //学生的id
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
    //登录的id
    private int teacherId;
    //会话沟通的id
    private String contactId;
    //xmpp发送消息封装的实体类
    private MsgBean msgBean;
    //DB保存消息的实体类
    private DBmsgBean dBmsgBean;
    //对象转换成json
    private Gson gson = new Gson();
    private String stuId = "";
    private String tchId = "";
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //学生档案的信息
    private StudentRecordBean.DataBean stuDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_resume);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        teacherId = PreferencesUtils.getSharePreInt(StudentResumeActivity.this, Const.ID);
        tchId = "5tch_" + teacherId;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            stuId = "5stu_" + id;
        }
        //获取学生档案的信息
        getStudentRecord();
        /**
         * 教育经历的适配器
         */
        rvEducation.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
//        rvEducation.addItemDecoration(new DirviderDecoration());
        rvEducation.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.education_item, educationList) {
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
                    String record = "";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(education)) {
                            String[] educations = education.split(",");
                            record = MyApplication.map.get(educations[educations.length - 1]).getName();
                        }
                    }
                    //专业
                    String majorName = mapstr.get("majorName");
                    if (!TextUtils.isEmpty(record)) {
                        tvEducationMarjor.setText(majorName + "|" + record);
                    } else {
                        tvEducationMarjor.setText(majorName);
                    }

                }
            }
        });
        /**
         * 工作经验的适配器
         */
        rvWork.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
//        rvWork.addItemDecoration(new DirviderDecoration());
        rvWork.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.work_item, workList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvWorkTime = baseViewHolder.getView(R.id.tv_work_time);
                TextView tvWorkCompany = baseViewHolder.getView(R.id.tv_work_company);
                TextView tvWorkPost = baseViewHolder.getView(R.id.tv_work_post);
                TextView tvWorkDescribe = baseViewHolder.getView(R.id.tv_work_describe);
                String resume = resumesBean.getResume();
                if (resume != null && !TextUtils.isEmpty(resume)) {
                    Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //公司行业
                    String companyBusiness = mapstr.get("companyBusiness");
                    String business = "";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(companyBusiness)) {
                            String[] Business = companyBusiness.split(",");
                            business = MyApplication.map.get(Business[Business.length - 1]).getName();
                        }
                    }
                    //公司的名字
                    String companyName = mapstr.get("companyName");
                    tvWorkCompany.setText(companyName + "|" + business);
                    //工作类型
                    String workType = mapstr.get("workType");
                    String type = "";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(workType)) {
                            String[] types = workType.split(",");
                            //展示工作类型
                            type = MyApplication.map.get(types[types.length - 1]).getName();
                        }
                    }

                    //职位名称
                    String postName = mapstr.get("positionName");
                    //职位类型
                    String positionType = mapstr.get("positionType");
                    String position = "";
                    if (MyApplication.map != null) {
                        if (!TextUtils.isEmpty(positionType)) {
                            String[] positionTypes = positionType.split(",");
                            //展示工作类型
                            position = MyApplication.map.get(positionTypes[positionTypes.length - 1]).getName();
                        }
                    }
                    tvWorkPost.setText(postName + "|" + position + "|" + type);
                    // 任职时间
                    String beginTime = mapstr.get("beginTime");
                    String endTime = mapstr.get("endTime");
                    if (endTime != null && !TextUtils.isEmpty(endTime)) {
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
        rvProject.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
        rvProject.addItemDecoration(new DirviderDecoration());
        rvProject.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.project_item, projectList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvWorkTime = baseViewHolder.getView(R.id.tv_work_time);
                TextView tvWorkCompany = baseViewHolder.getView(R.id.tv_work_company);
                TextView tvWorkPost = baseViewHolder.getView(R.id.tv_work_post);
                TextView tvWorkDescribe = baseViewHolder.getView(R.id.tv_work_describe);
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
                    String description = mapstr.get("projectDesc");
                    tvWorkDescribe.setText(description);
                }
            }
        });
        /**
         * 培训经历的适配器
         */
        rvTrain.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
        rvTrain.addItemDecoration(new DirviderDecoration());
        rvTrain.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.train_item, trainList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvTrainTime = baseViewHolder.getView(R.id.tv_train_time);
                TextView tvTrainOrgan = baseViewHolder.getView(R.id.tv_train_organ);
                TextView tvTrainCourse = baseViewHolder.getView(R.id.tv_train_course);
                TextView tvTrainLcs = baseViewHolder.getView(R.id.tv_train_lcs);
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
                    if (endTime != null && !TextUtils.isEmpty(endTime)) {
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
        rvCertificate.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
//        rvCertificate.addItemDecoration(new DirviderDecoration());
        rvCertificate.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.certificate_item, lcsList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvCertificateTime = baseViewHolder.getView(R.id.tv_certificate_time);
                TextView tvCertificateName = baseViewHolder.getView(R.id.tv_certificate_name);
                TextView tvCertificateUnit = baseViewHolder.getView(R.id.tv_certificate_unit);

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
        rvSkill.setLayoutManager(new LinearLayoutManager(StudentResumeActivity.this, LinearLayoutManager.VERTICAL, false));
        rvSkill.addItemDecoration(new DirviderDecoration());
        rvSkill.setAdapter(new BaseQuickAdapter<StudentRecordBean.DataBean.ResumesBean>(R.layout.skill_item, skillList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, StudentRecordBean.DataBean.ResumesBean resumesBean) {
                TextView tvSkillName = baseViewHolder.getView(R.id.tv_skill_name);
                TextView tvSkillExp = baseViewHolder.getView(R.id.tv_skill_exp);
                String resume = resumesBean.getResume();
                if (resume != null && !TextUtils.isEmpty(resume)) {
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    tvSkillName.setText(mapstr.get("skillName"));
                    String skillLevel = mapstr.get("skillLevel").split(",")[1];
                    Logger.d("SkillAdapter：skillLevel=" + skillLevel);
                    if (MyApplication.map != null) {
                        if (MyApplication.map.get(skillLevel) != null) {
                            Logger.d("SkillAdapter：" + MyApplication.map.get(skillLevel).toString());
                            String skill = MyApplication.map.get(skillLevel).getName();
                            tvSkillExp.setText(skill);
                        }
                    }
                }
            }
        });
    }

    /*
  * 启动Activity
  * @param context
  */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, StudentResumeActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private void getStudentRecord() {
        String params = "?id=" + String.valueOf(id) + "&tchId=" + String.valueOf(teacherId);
        HttpClient.get(this, Api.GET_STUDENT_DETAIL + params, new CallBack<StudentRecordBean>() {
            @Override
            public void onSuccess(StudentRecordBean result) {
                if (result == null) {
                    return;
                }
                rlNet.setVisibility(View.GONE);
                rlLayout.setVisibility(View.VISIBLE);
                if (result.getStatus() == 200) {
                    stuDataBean = result.getData();
                    contactId = result.getData().getContactId();
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
                        //工作经验
                        if (workList.size() == 0) {
                            llWork.setVisibility(View.GONE);
                        }
                        //项目经验
                        if (projectList.size() == 0) {
                            llProject.setVisibility(View.GONE);
                        }
                        //培训经历
                        if (trainList.size() == 0) {
                            llTrain.setVisibility(View.GONE);
                        }
                        //我的证书
                        if (lcsList.size() == 0) {
                            llCertificate.setVisibility(View.GONE);
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
                    ToastUtil.showShortToast(StudentResumeActivity.this, result.getMsg());
                }

            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    rlNet.setVisibility(View.VISIBLE);
                    rlLayout.setVisibility(View.GONE);
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

        //展示学生的头像
        if (result.getData().getHead() != null && !"".equals(result.getData().getHead())) {
            PicassoUtils.LoadPathCorners(StudentResumeActivity.this, result.getData().getHead(), 70, 70, circlePicture);
        } else {
            PicassoUtils.LoadCorners(StudentResumeActivity.this, R.drawable.wukong, 70, 70, circlePicture);
        }
        //展示姓名
        if (result.getData().getRealName() != null) {
            tvTitle.setText(result.getData().getRealName());
            tvName.setText(result.getData().getRealName());
        }
        //显示性别
        if (result.getData().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }


        //展示年龄 地址 学历
        String education = "";
        //地址赋值
        String addres = "";
        //年龄
        String age = "";
        sort();
        if (educationList.size() > 0) {
            Map<String, String> map = new Gson().fromJson(educationList.get(educationList.size() - 1).getResume(), new TypeToken<HashMap<String, String>>() {
            }.getType());
            String code = map.get("diplomas").split(",")[1];
            Logger.d("获取学历的code:" + code);
            if (MyApplication.map != null) {
                education = MyApplication.map.get(code).getName();
            }

        }

        if (result.getData().getAddressNow() != null && !TextUtils.isEmpty(result.getData().getAddressNow())) {
            String[] add = result.getData().getAddressNow().split(",");
            if (add.length < 3) {
                addres = add[add.length - 1];
            } else {
                addres = result.getData().getAddressNow().split(",")[1];
            }
        }
        try {
            age = TimeRender.getAge(result.getData().getBirthday()) + "岁";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(addres) && !TextUtils.isEmpty(education)) {
            tvAddress.setText(age + "|" + education + "|" + addres);
        } else if (TextUtils.isEmpty(education)) {
            tvAddress.setText(age + "|" + addres);
        } else if (TextUtils.isEmpty(addres)) {
            tvAddress.setText(age + "|" + education);
        } else if (TextUtils.isEmpty(age)) {
            tvAddress.setText(education + "|" + addres);
        }
        //显示工作状态
        if (result.getData().getWorkStatus() == 0) {
            tvWorkState.setText("暂不考虑");
        } else if (result.getData().getWorkStatus() == 1) {
            tvWorkState.setText("寻找师父");
        } else if (result.getData().getWorkStatus() == 2) {
            tvWorkState.setText("寻找实习");
        }
        /**
         * 期望工作
         */
        if (expectList.size() > 0) {
            String resume = expectList.get(expectList.size() - 1).getResume();
            if (resume != null && !TextUtils.isEmpty(resume)) {
                Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                }.getType());
                if (MyApplication.map != null) {
                    String address = mapstr.get("address");
//                   String expectBusiness=mapstr.get("expectBusiness");
                    String expectPosition = mapstr.get("expectPosition");
                    String expectSalary = mapstr.get("expectSalary");
                    //工作城市
                    tvCity.setText(address);
//                    if(!TextUtils.isEmpty(expectBusiness)){
//                        //期望行业
//                        String [] business=expectBusiness.split(",");
//                        tvWork.setText(MyApplication.map.get(business[business.length-1]).getName());
//                    }
                    if (!TextUtils.isEmpty(expectPosition)) {
                        //期望职业
                        String[] position = expectPosition.split(",");
                        tvWork.setText(MyApplication.map.get(position[position.length - 1]).getName());
                    }
                    if (!TextUtils.isEmpty(expectSalary)) {
                        //期望薪资
                        String[] salary = expectSalary.split(",");
                        tvSalary.setText(MyApplication.map.get(salary[salary.length - 1]).getName() + "/月");
                    }
                }
            }
        }
        //
        //评价
        if (result.getData().getEvaluationLabels() != null && !TextUtils.isEmpty(result.getData().getEvaluationLabels())) {
            tagTrait.setVisibility(View.VISIBLE);
            String labels = result.getData().getEvaluationLabels().substring(1, result.getData().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StudentResumeActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagTrait.setVisibility(View.GONE);
        }
        //展示描述
        if (result.getData().getDescription() != null && !TextUtils.isEmpty(result.getData().getDescription())) {
            tvDescription.setText(result.getData().getDescription());
        } else {
            tvDescription.setText("");
        }
        //是否可以感兴趣
        /**
         * 是否禁用 0：未禁用 1：已禁用
         */
        if (result.getData().getIsDisabled() == 1) {
            btInterSted.setEnabled(false);
            btInterSted.setText("该学徒已离校，请重新选择学徒");
//            btInterSted.setTextColor(Color.BLACK);
            btInterSted.setBackgroundResource(R.drawable.btn_chat_gray);
        }
        rlButton.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.rl_back, R.id.bt_interested, R.id.bt_reload, R.id.rl_net_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //老师对学生发起感兴趣
            case R.id.bt_interested:
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
                    return;
                }
                if (contactId != null && TextUtils.isEmpty(contactId)) {
                    //直接跳转聊天详情界面
                    getTeacherDetail();
                } else {
                    //第一次发起沟通
                    startUserContact();
                }
                break;
            case R.id.bt_reload:
                getStudentRecord();
                break;
            case R.id.rl_net_back:
                finish();
                break;
        }
    }

    /**
     * 获取聊天对象的详细信息
     */
    private void getTeacherDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(teacherId));
        HttpClient.post(this, Api.GET_TEACHER_DEATIL, map, new CallBack<TeacherDetailBean>() {
            @Override
            public void onSuccess(TeacherDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200 && result.getData() != null) {
                    dataBean = result.getData();
                    TeachterChatActivity.StartActivity(StudentResumeActivity.this, stuId, dataBean, stuDataBean, String.valueOf(contactId));
                } else {
                    ToastUtil.showShortToast(StudentResumeActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 发起第一沟通
     */
    private void startUserContact() {
        HashMap<String, String> map = new HashMap<>();
        map.put("stuId", String.valueOf(id));
        map.put("tchId", String.valueOf(teacherId));
        HttpClient.post(this, Api.START_USER_CONTACT, map, new CallBack<UserContactBean>() {
            @Override
            public void onSuccess(UserContactBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    contactId = result.getData().getId();
                    if (result.getData().getFlag() == 0) {
                        //直接跳转聊天详情界面
                        getTeacherDetail();
                    } else {
                        sendMsgText("", "", "T02");
                        getTeacherDetail();
                    }
                } else {
                    ToastUtil.showShortToast(StudentResumeActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 发送消息
     *
     * @param content
     */
    private void sendMsgText(String content, String businessId, String type) {
        if (MyApplication.xmppConnection != null && MyApplication.xmppConnection.isAuthenticated()) {
            //封装xmpp发送的格式
            msgBean = new MsgBean();
            msgBean.setType(type);
            msgBean.setMsg(content);
            msgBean.setSessionId(Integer.parseInt(contactId));
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
                        ToastUtil.showShortToast(StudentResumeActivity.this, "发送失败");
                    }
                }
            }).start();
            //将消息记录保存后台
            SaveChat(gson.toJson(dBmsgBean), tchId, stuId, msgBean.getType());
        } else {
            ToastUtil.showShortToast(StudentResumeActivity.this, "登录服务器出错，请重新登录");
        }

    }

    /*
       * 保存聊天记录
       * 提交后台服务器保存
       */
    private void SaveChat(String message, String sender, String receiver, String msgType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userContactId", contactId);
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
                } else {
                    ToastUtil.showShortToast(StudentResumeActivity.this, result.getMsg());
                }
            }
        });

    }

    /**
     * 教育经历按照时间排序
     */
    private void sort() {
        //对集合经行排序
        Collections.sort(educationList, new Comparator<StudentRecordBean.DataBean.ResumesBean>() {
            @Override
            public int compare(StudentRecordBean.DataBean.ResumesBean bean, StudentRecordBean.DataBean.ResumesBean bean1) {
                long time = bean.getUpdateTime();
                long time1 = bean1.getUpdateTime();
                if (time > time1) {
                    return 1;
                } else if (time < time1) {
                    return -1;
                } else {
                    return 0;
                }

            }
        });
        Logger.d("数据排序完成:" + educationList.toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

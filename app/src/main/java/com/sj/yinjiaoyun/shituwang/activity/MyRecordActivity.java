package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.EducationAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.LcsAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.ProjectAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.SkillAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.TrainAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.WorkAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.callback.ChildrenCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopWorkState;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.NoScrollListView;

import java.util.ArrayList;
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
 * 我的档案
 */
public class MyRecordActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.lv_education)
    NoScrollListView lvEducation;
    @BindView(R.id.lv_skill)
    NoScrollListView lvSkill;
    @BindView(R.id.lv_work)
    NoScrollListView lvWork;
    @BindView(R.id.lv_project)
    NoScrollListView lvProject;
    @BindView(R.id.lv_train)
    NoScrollListView lvTrain;
    @BindView(R.id.lv_lce)
    NoScrollListView lvLce;
    @BindView(R.id.tv_ablity)
    TextView tvAblity;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.rl_my_record)
    RelativeLayout rlMyRecord;
    @BindView(R.id.tv_job_state)
    TextView tvJobState;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_expect)
    TextView tvExpect;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_skill)
    TextView tvSkill;
    @BindView(R.id.tv_job)
    TextView tvJob;
    //数据源的对象
    private StudentRecordBean studentRecordBean;
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
    private EducationAdapter educationAdapter;
    private SkillAdapter skillAdapter;
    private WorkAdapter workAdapter;
    private ProjectAdapter projectAdapter;
    private TrainAdapter trainAdapter;
    private LcsAdapter lcsAdapter;
    //工作状态的选择
    private PopWorkState popWorkState;
    //工作状态的数据源
    private List<Children> list = new ArrayList<>();
    private StudentRecordBean.DataBean.ResumesBean resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        //显示字体的
        tvInfo.setText(RichTextUtil.fillColor("编辑个人信息 *","*", Color.parseColor("#F14347")));
        tvExpect.setText(RichTextUtil.fillColor("期望工作 *","*",Color.parseColor("#F14347")));
        tvEducation.setText(RichTextUtil.fillColor("教育经历 *","*",Color.parseColor("#F14347")));
        tvSkill.setText(RichTextUtil.fillColor("专业技能 *","*",Color.parseColor("#F14347")));
//        tvJob.setText(RichTextUtil.fillColor("工作状态 *","*",Color.parseColor("#F14347")));
        list.clear();
        //工作状态数据源
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0004".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }

        }
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(MyRecordActivity.this, Const.ID);
        popWorkState = new PopWorkState(MyRecordActivity.this);

        //教育经历的集合装载
        educationAdapter = new EducationAdapter(MyRecordActivity.this, educationList);
        lvEducation.setAdapter(educationAdapter);
        //专业技能的装载
        skillAdapter = new SkillAdapter(MyRecordActivity.this, skillList);
        lvSkill.setAdapter(skillAdapter);
        //工作经历
        workAdapter = new WorkAdapter(MyRecordActivity.this, workList);
        lvWork.setAdapter(workAdapter);
        //项目经验
        projectAdapter = new ProjectAdapter(MyRecordActivity.this, projectList);
        lvProject.setAdapter(projectAdapter);
        //培训经历
        trainAdapter = new TrainAdapter(MyRecordActivity.this, trainList);
        lvTrain.setAdapter(trainAdapter);
        //我的证书
        lcsAdapter = new LcsAdapter(MyRecordActivity.this, lcsList);
        lvLce.setAdapter(lcsAdapter);
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getStudentRecord();
    }

    /**
     * 获取学生的档案信息
     */
    private void getStudentRecord() {
        String params = "?studentId=" + String.valueOf(id);
        HttpClient.get(this, Api.GTE_STUDENT_RECORD + params, new CallBack<StudentRecordBean>() {
            @Override
            public void onSuccess(StudentRecordBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    studentRecordBean = result;
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
                        educationAdapter.notifyDataSetChanged();
                        skillAdapter.notifyDataSetChanged();
                        workAdapter.notifyDataSetChanged();
                        projectAdapter.notifyDataSetChanged();
                        trainAdapter.notifyDataSetChanged();
                        lcsAdapter.notifyDataSetChanged();
                    }
                    showRecord(result);
                } else {
                    ToastUtil.showShortToast(MyRecordActivity.this, result.getMsg());
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
        //展示简历完成度
        tvProgress.setText("简历完成度" + String.valueOf(result.getData().getResumePerfectDegree()).
                substring(0, String.valueOf(result.getData().getResumePerfectDegree()).indexOf(".")) + "%");
        //展示学生的头像
        if (result.getData().getHead() != null && !"".equals(result.getData().getHead())) {
            PicassoUtils.LoadPathCorners(MyRecordActivity.this, result.getData().getHead(), 70, 70, circlePicture);
        } else {
            PicassoUtils.LoadCorners(MyRecordActivity.this, R.drawable.wukong, 70, 70, circlePicture);
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
        //显示学历和地址
        sort();
        Map<String, String> mapstr = new Gson().fromJson(educationList.get(educationList.size() - 1).getResume(), new TypeToken<HashMap<String, String>>() {
        }.getType());
        String code = mapstr.get("diplomas").split(",")[1];
        Logger.d("获取学历的code:" + code);
        String education = MyApplication.map.get(code).getName();
        Logger.d("获取学历的education:" + education);
        String rgs = ",";
        String ss = "|";
        tvAblity.setText(education + "|" + result.getData().getAddressNow().replace(rgs, ss));
        //显示工作状态
        if (result.getData().getWorkStatus() == 0) {
            tvJobState.setText("暂不考虑");
        } else if (result.getData().getWorkStatus() == 1) {
            tvJobState.setText("寻找师傅");
        } else if (result.getData().getWorkStatus() == 2) {
            tvJobState.setText("寻找实习");
        }

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

    @OnClick({R.id.rl_back, R.id.rl_reft, R.id.circle_picture, R.id.rl_expect_work, R.id.tv_add_education, R.id.tv_add_skill, R.id.tv_add_work, R.id.tv_add_project, R.id.tv_add_train, R.id.tv_add_lce, R.id.rl_job_state, R.id.bt_preview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_reft:
                if (studentRecordBean != null) {
                    EditPersonInfoActivity.StartActivity(MyRecordActivity.this,
                            studentRecordBean.getData().getRealName(), studentRecordBean.getData().getBirthday(),
                            studentRecordBean.getData().getHead(), studentRecordBean.getData().getAddressNow(),
                            studentRecordBean.getData().getSex(), studentRecordBean.getData().getDescription());
                }

                break;
            case R.id.circle_picture:
                if (studentRecordBean != null) {
                    EditPersonInfoActivity.StartActivity(MyRecordActivity.this,
                            studentRecordBean.getData().getRealName(), studentRecordBean.getData().getBirthday(),
                            studentRecordBean.getData().getHead(), studentRecordBean.getData().getAddressNow(),
                            studentRecordBean.getData().getSex(), studentRecordBean.getData().getDescription());
                }

                break;
            //期望工作
            case R.id.rl_expect_work:
                if (expectList.size() > 0) {
                    resume = expectList.get(expectList.size() - 1);
                }
                ExpectWorkActivity.StartActivity(MyRecordActivity.this, resume);
                break;
            //添加教育经历
            case R.id.tv_add_education:
                EducationActivity.StartActivity(MyRecordActivity.this, null);
                break;
            //编辑专业技能
            case R.id.tv_add_skill:
                ProfessionalSkillActivity.StartActivity(MyRecordActivity.this, studentRecordBean);
                break;
            //添加工作经历
            case R.id.tv_add_work:
                WorkExperienceActivity.StartActivity(MyRecordActivity.this, null);
                break;
            //添加项目经验
            case R.id.tv_add_project:
                ProjectExperienceActivity.StartActivity(MyRecordActivity.this, null);
                break;
            //添加培训经历
            case R.id.tv_add_train:
                TrainExperienceActivity.StartActivity(MyRecordActivity.this, null);
                break;
            //添加我的证书
            case R.id.tv_add_lce:
                startActivity(new Intent(MyRecordActivity.this, MyIcsActivity.class));
                break;
            //工作状态
            case R.id.rl_job_state:
                if (expectList.size() > 0) {
                    if (educationList.size() > 0) {
                        if (skillList.size() > 0) {
                            popWorkState.pop(rlMyRecord, list, new ChildrenCallBack() {
                                @Override
                                public void setChildren(Children content) {
                                    tvJobState.setText(content.getName());
                                    updateWorkStatus(content);
                                }

                            });
                        } else {
                            ToastUtil.showShortToast(MyRecordActivity.this, "请编辑专业技能");
                        }
                    } else {
                        ToastUtil.showShortToast(MyRecordActivity.this, "请添加教育经历");
                    }
                } else {
                    ToastUtil.showShortToast(MyRecordActivity.this, "请编辑期望工作");
                }

                break;
            //预览简历
            case R.id.bt_preview:
                startActivity(new Intent(MyRecordActivity.this, ResumePreviewActivity.class));
                break;
        }
    }

    /**
     * 修改工作状态
     *
     * @param content
     */
    private void updateWorkStatus(Children content) {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        int workStatus = 0;
        if (content.getName().equals("暂不考虑")) {
            workStatus = 0;
        } else if (content.getName().equals("寻找师父中")) {
            workStatus = 1;
        } else if (content.getName().equals("寻找实习中")) {
            workStatus = 2;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("workStatus", String.valueOf(workStatus));
        HttpClient.post(this, Api.UPDATE_WORK_STATUS, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    ToastUtil.showShortToast(MyRecordActivity.this, "切换状态成功");
                } else {
                    ToastUtil.showShortToast(MyRecordActivity.this, result.getMsg());
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

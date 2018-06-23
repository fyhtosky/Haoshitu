package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.TeacherAbilityAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.TeachterEditInfoActivity;
import com.sj.yinjiaoyun.shituwang.adapter.TeachterSkillAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.TeachterWorkAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师端的档案
 */
public class TeachterRecordActivity extends AppCompatActivity {

    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.lv_ability)
    NoScrollListView lvAbility;
    @BindView(R.id.lv_skill)
    NoScrollListView lvSkill;
    @BindView(R.id.lv_work)
    NoScrollListView lvWork;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_recruit)
    TextView tvRecruit;
    @BindView(R.id.cb_recruit)
    CheckBox cbRecruit;
    @BindView(R.id.rl_button)
    RelativeLayout rlButton;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.tv_skill)
    TextView tvSkill;
    @BindView(R.id.tv_exp)
    TextView tvExp;
    @BindView(R.id.tv_appeal)
    TextView tvAppeal;
    //登录的id
    private int id;
    //综合能力的数据源
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean> Abilitylist = new ArrayList<>();
    //综核能力的适配器
    private TeacherAbilityAdapter abilityAdapter;
    //专业技能的数据源
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean> skillList = new ArrayList<>();
    private TeachterSkillAdapter skillAdapter;
    //工作经历
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean> workList = new ArrayList<>();
    private TeachterWorkAdapter workAdapter;
    //数据传值操作赋值全局
    private TeacherDetailBean teacherDetailBean;
    //收徒的状态
    private int isCheck;
    //诉求描述
    private String requireDesc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachter_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //显示字体的
        tvAbility.setText(RichTextUtil.fillColor("综合能力 *","*", Color.parseColor("#F14347")));
        tvSkill.setText(RichTextUtil.fillColor("专业技能 *","*",Color.parseColor("#F14347")));
        tvExp.setText(RichTextUtil.fillColor("工作/实习经历 *","*",Color.parseColor("#F14347")));
        tvAppeal.setText(RichTextUtil.fillColor("诉求描述 *","*",Color.parseColor("#F14347")));
        id = PreferencesUtils.getSharePreInt(TeachterRecordActivity.this, Const.ID);
        abilityAdapter = new TeacherAbilityAdapter(TeachterRecordActivity.this, Abilitylist);
        lvAbility.setAdapter(abilityAdapter);
        skillAdapter = new TeachterSkillAdapter(TeachterRecordActivity.this, skillList);
        lvSkill.setAdapter(skillAdapter);
        workAdapter = new TeachterWorkAdapter(TeachterRecordActivity.this, workList);
        lvWork.setAdapter(workAdapter);
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTeacherDetail();
    }

    /**
     * 发送请求获取老师的详情
     */
    private void getTeacherDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        HttpClient.post(this, Api.GET_TEACHER_DEATIL, map, new CallBack<TeacherDetailBean>() {
            @Override
            public void onSuccess(TeacherDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200 && result.getData() != null) {
                    teacherDetailBean = result;
                    displayData(result);
                    Abilitylist.clear();
                    skillList.clear();
                    workList.clear();
                    if (result.getData().getTchResumeVO().getTchAbilities() != null) {
                        //综合能力
                        Logger.d("综合能力:" + result.getData().getTchResumeVO().getTchAbilities().size());
                        Abilitylist.addAll(result.getData().getTchResumeVO().getTchAbilities());
                        abilityAdapter.notifyDataSetChanged();
                    }
                    if (result.getData().getTchResumeVO().getTchSkills() != null) {
                        Logger.d("专业技能:" + result.getData().getTchResumeVO().getTchSkills().size());
                        skillList.addAll(result.getData().getTchResumeVO().getTchSkills());
                        skillAdapter.notifyDataSetChanged();
                    }
                    if (result.getData().getTchResumeVO().getTchWorkExps() != null) {
                        Logger.d("工作经历:" + result.getData().getTchResumeVO().getTchWorkExps().size());
                        workList.addAll(result.getData().getTchResumeVO().getTchWorkExps());
                        workAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtil.showShortToast(TeachterRecordActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 界面展示数据
     */
    private void displayData(TeacherDetailBean result) {
        if (result.getData() == null) {
            return;
        }

        //展示老师的头像
        if (result.getData().getImgUrl() != null && !"".equals(result.getData().getImgUrl())) {
            PicassoUtils.LoadPathCorners(TeachterRecordActivity.this, result.getData().getImgUrl(), 70, 70, circlePicture);
        } else {
            PicassoUtils.LoadCorners(TeachterRecordActivity.this, R.drawable.master, 70, 70, circlePicture);
        }
        //老师的姓名
        tvName.setText(result.getData().getRealName());
        String oldchar = ",";
        String newchar = "|";
        //显示性别
        if ("1".equals(result.getData().getSex())) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //展示地址及职位
        if (result.getData().getAddressNow() != null && !TextUtils.isEmpty(result.getData().getAddressNow())) {
            tvPost.setText(result.getData().getAddressNow().replace(oldchar, newchar) + "|" + result.getData().getPositionId());
        } else {
            tvPost.setText(result.getData().getPositionId());
        }

        //诉求描述
        if (result.getData().getTchResumeVO().getTchRequireDesc() != null && !TextUtils.isEmpty(result.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc())) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText("       " + result.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc());
            requireDesc = result.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc();
        } else {
            tvDescription.setVisibility(View.GONE);
            requireDesc = "";
        }

        //显示收徒状态
        if (result.getData().getIsRecommended() == 1) {
            isCheck = 1;
            //收徒
            tvRecruit.setText("我要收徒");
            cbRecruit.setChecked(true);
        } else {
            //不收徒
            isCheck = 0;
            tvRecruit.setText("暂不收徒");
            cbRecruit.setChecked(false);
        }
        cbRecruit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (check()) {
                        isCheck = 1;
                        tvRecruit.setText("我要收徒");
                        updateRecommend();
                    } else {
                        cbRecruit.setChecked(false);
                    }

                } else {
                    if (check()) {
                        isCheck = 0;
                        tvRecruit.setText("暂不收徒");
                        updateRecommend();
                    } else {
                        cbRecruit.setChecked(false);
                    }

                }


            }
        });
    }

    private boolean check() {
        if (Abilitylist.size() > 0) {
            if (skillList.size() > 0) {
                if (workList.size() > 0) {
                    if (requireDesc != null && !TextUtils.isEmpty(requireDesc)) {
                        return true;
                    } else {
                        ToastUtil.showShortToast(TeachterRecordActivity.this, "请编辑诉求描述");
                        return false;
                    }
                } else {
                    ToastUtil.showShortToast(TeachterRecordActivity.this, "请编辑工作/实习经历");
                    return false;
                }
            } else {
                ToastUtil.showShortToast(TeachterRecordActivity.this, "请编辑专业技能");
                return false;
            }

        } else {
            ToastUtil.showShortToast(TeachterRecordActivity.this, "请编辑综合能力");
            return false;
        }


    }

    /**
     * 更新老师是否收徒
     */
    private void updateRecommend() {
        if (Abilitylist.size() == 0) {
            ToastUtil.showShortToast(TeachterRecordActivity.this, "综合能力为必选项");
            return;
        }
        if (skillList.size() == 0) {
            ToastUtil.showShortToast(TeachterRecordActivity.this, "专业技能为必选项");
            return;
        }
        if (workList.size() == 0) {
            ToastUtil.showShortToast(TeachterRecordActivity.this, "工作/实习经历为必选项");
            return;
        }
        if (TextUtils.isEmpty(tvDescription.getText().toString().trim())) {
            ToastUtil.showShortToast(TeachterRecordActivity.this, "诉求描述为必选项");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("IsRecommended", String.valueOf(isCheck));
        map.put("id", String.valueOf(id));
        HttpClient.post(this, Api.UODATE_TEACHTER_RECOMMEND, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    ToastUtil.showShortToast(TeachterRecordActivity.this, "切换状态成功");
                } else {
                    ToastUtil.showShortToast(TeachterRecordActivity.this, result.getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(TeachterRecordActivity.this, message);
            }

        });
    }

    @OnClick({R.id.rl_back, R.id.rl_reft, R.id.circle_picture, R.id.tv_add_ability, R.id.tv_add_skill, R.id.tv_add_work, R.id.tv_add_describe, R.id.bt_preview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //编辑个人资料
            case R.id.rl_reft:
                if (teacherDetailBean != null) {
                    TeachterEditInfoActivity.StartActivity(TeachterRecordActivity.this, teacherDetailBean.getData().getRealName(), teacherDetailBean.getData().getImgUrl()
                            , teacherDetailBean.getData().getSex(), teacherDetailBean.getData().getAddressNow(), teacherDetailBean.getData().getPositionId(), teacherDetailBean.getData().getCompanyName());
                }

                break;
            //编辑个人资料
            case R.id.circle_picture:
                if (teacherDetailBean != null) {
                    TeachterEditInfoActivity.StartActivity(TeachterRecordActivity.this, teacherDetailBean.getData().getRealName(), teacherDetailBean.getData().getImgUrl()
                            , teacherDetailBean.getData().getSex(), teacherDetailBean.getData().getAddressNow(), teacherDetailBean.getData().getPositionId(), teacherDetailBean.getData().getCompanyName());
                }

                break;
            //编辑综合能力
            case R.id.tv_add_ability:
                AbilityActivity.StartActivity(TeachterRecordActivity.this, teacherDetailBean);
                break;
            //专业技能
            case R.id.tv_add_skill:
                TeachterSkillActivity.StartActivity(TeachterRecordActivity.this, teacherDetailBean);
                break;
            //工作经历
            case R.id.tv_add_work:
                TeachterWorkActivity.StartActivity(TeachterRecordActivity.this, null);
                break;
            //诉求描述
            case R.id.tv_add_describe:
                AppealDescribeActivity.StartActivity(TeachterRecordActivity.this, teacherDetailBean);
                break;
            //预览简历
            case R.id.bt_preview:
                startActivity(new Intent(TeachterRecordActivity.this, TeacherDetailActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

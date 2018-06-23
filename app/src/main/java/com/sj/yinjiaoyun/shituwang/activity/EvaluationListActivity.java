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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.EvaluationDetailPersonBean;
import com.sj.yinjiaoyun.shituwang.bean.LogBean;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 学生端评价列表的界面
 */
public class EvaluationListActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.rl_button)
    RelativeLayout rlButton;
    private StuEvaBean.DataBean.RowsBean rowsBean;
    //展示列表的数据源
    private List<LogBean.DataBean.RowsBean> list = new ArrayList<>();
    //评价的主键id
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_list);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id=bundle.getInt("id");
            rowsBean = (StuEvaBean.DataBean.RowsBean) bundle.getSerializable("rowsBean");
            if (rowsBean != null) {
                //展示数据
                showDetail(rowsBean);
                //设置布局管理器
                recyclerView.setLayoutManager(new LinearLayoutManager(EvaluationListActivity.this, LinearLayoutManager.VERTICAL, false));
                //画分割线
//                recyclerView.addItemDecoration(new GrayDecoration());
                recyclerView.setAdapter(new BaseQuickAdapter<LogBean.DataBean.RowsBean>(R.layout.eva_log_list_item, list) {
                    @Override
                    protected void convert(BaseViewHolder baseViewHolder, final LogBean.DataBean.RowsBean dataBean) {
                        //显示创建时间
                        baseViewHolder.setText(R.id.tv_time, TimeRender.getBirthday2(dataBean.getCreateTime()));
                        //显示评价人
                        baseViewHolder.setText(R.id.tv_name, "| 评价人:" + dataBean.getName());
                        //查看评价
                        baseViewHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                  LookEvaluationActivity.StartActivity(EvaluationListActivity.this,dataBean.getId());
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 获取被评价人的信息
     */
    private void findEva() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
//        0:老师评价 ；1：学生评价
        map.put("isTch", "0");
        HttpClient.post(this, Api.FIND_EVA, map, new CallBack<EvaluationDetailPersonBean>() {
            @Override
            public void onSuccess(EvaluationDetailPersonBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    showInfo(result.getData());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLog();
        if(id>0){
            findEva();
        }
    }

    /**
     * 展示评价列表
     */
    private void showLog() {
        HashMap<String, String> map = new HashMap<>();
        if(rowsBean==null){
            map.put("evaluateId", String.valueOf(id));
        }else {
            map.put("evaluateId", String.valueOf(rowsBean.getId()));
            map.put("tchId",String.valueOf(rowsBean.getTchId()));
        }
//        0:老师评价 ；1：学生评价
        map.put("isTch", "1");
        HttpClient.post(this, Api.QUERY_EVA_LOG_LIST, map, new CallBack<LogBean>() {
            @Override
            public void onSuccess(LogBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    list.clear();
                    list.addAll(result.getData().getRows());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }


        });

    }

    /**
     * 展示个人信息
     *
     * @param rowsBean
     */
    private void showInfo(EvaluationDetailPersonBean.DataBean rowsBean) {
        //是否显示新增按钮
        if(rowsBean.getStatus()==0){
           rlButton.setVisibility(View.VISIBLE);
        }else if(rowsBean.getStatus()==1){
            if(rowsBean.getIsStuEva()==0){
                rlButton.setVisibility(View.VISIBLE);
            }else {
                rlButton.setVisibility(View.GONE);
            }
        }else {
            rlButton.setVisibility(View.GONE);
        }
        //名字
        tvName.setText(rowsBean.getTeacher().getRealName());
        //职位和公司
        tvPost.setText(rowsBean.getTeacher().getPositionId() + " | " + rowsBean.getTeacher().getCompanyName());
        //显示头像
        if (rowsBean.getTeacher().getImgUrl() != null && !TextUtils.isEmpty(rowsBean.getTeacher().getImgUrl())) {
            PicassoUtils.LoadPathCorners(EvaluationListActivity.this, rowsBean.getTeacher().getImgUrl(), circleView);
        } else {
            PicassoUtils.LoadCorners(EvaluationListActivity.this, R.drawable.master, 60, 60, circleView);
        }

        //设置默认状态的复用
        tagTrait.setVisibility(View.VISIBLE);
        tagSkill.setVisibility(View.VISIBLE);
        //技能标签
        if (rowsBean.getTeacher().getSkills() != null && rowsBean.getTeacher().getSkills() != null) {
            tagTrait.setVisibility(View.VISIBLE);
             tagTrait.setmSingleLine(true);
            tagTrait.setAdapter(new TagAdapter<EvaluationDetailPersonBean.DataBean.TeacherBean.SkillsBean>(rowsBean.getTeacher().getSkills()) {
                @Override
                public View getView(FlowLayout parent, int position, EvaluationDetailPersonBean.DataBean.TeacherBean.SkillsBean tchSkillsBean) {
                    View view = LayoutInflater.from(EvaluationListActivity.this)
                            .inflate(R.layout.item_textview, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(tchSkillsBean.getSkillName());
                    return view;
                }
            });
        } else {
            tagTrait.setVisibility(View.GONE);

        }
        //评价
        if (rowsBean.getTeacher().getEvaluationLabels() != null && !TextUtils.isEmpty(rowsBean.getTeacher().getEvaluationLabels())) {
            tagSkill.setVisibility(View.VISIBLE);
            tagSkill.setmSingleLine(true);
            String labels = rowsBean.getTeacher().getEvaluationLabels().substring(1, rowsBean.getTeacher().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagSkill.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(EvaluationListActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagSkill.setVisibility(View.GONE);
        }

    }
    /**
     * 展示个人信息
     *
     * @param rowsBean
     */
    private void showDetail(StuEvaBean.DataBean.RowsBean rowsBean) {
        //是否显示新增按钮
        if(rowsBean.getStatus()==0){
            rlButton.setVisibility(View.VISIBLE);
        }else if(rowsBean.getStatus()==1){
            if(rowsBean.getIsStuEva()==0){
                rlButton.setVisibility(View.VISIBLE);
            }else {
                rlButton.setVisibility(View.GONE);
            }
        }else {
            rlButton.setVisibility(View.GONE);
        }
        //名字
        tvName.setText(rowsBean.getTeacher().getRealName());
        //职位和公司
        tvPost.setText(rowsBean.getTeacher().getPositionId() + " | " + rowsBean.getTeacher().getCompanyName());
        //显示头像
        if (rowsBean.getTeacher().getImgUrl() != null && !TextUtils.isEmpty(rowsBean.getTeacher().getImgUrl())) {
            PicassoUtils.LoadPathCorners(EvaluationListActivity.this, rowsBean.getTeacher().getImgUrl(), circleView);
        } else {
            PicassoUtils.LoadCorners(EvaluationListActivity.this, R.drawable.master, 60, 60, circleView);
        }

        //设置默认状态的复用
        tagTrait.setVisibility(View.VISIBLE);
        tagSkill.setVisibility(View.VISIBLE);
        //技能标签
        if (rowsBean.getTeacher().getSkills() != null && rowsBean.getTeacher().getSkills() != null) {
            tagTrait.setVisibility(View.VISIBLE);
            tagTrait.setmSingleLine(true);
            List<StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean>skillsBeanList;
            if(rowsBean.getTeacher().getSkills().size()>4){
                skillsBeanList=rowsBean.getTeacher().getSkills().subList(0,3);
            }else {
                skillsBeanList=rowsBean.getTeacher().getSkills();
            }
            tagTrait.setAdapter(new TagAdapter<StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean>(skillsBeanList) {
                @Override
                public View getView(FlowLayout parent, int position, StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean tchSkillsBean) {
                    View view = LayoutInflater.from(EvaluationListActivity.this)
                            .inflate(R.layout.item_textview, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(tchSkillsBean.getSkillName());
                    return view;
                }
            });
        } else {
            tagTrait.setVisibility(View.GONE);

        }
        //评价
        if (rowsBean.getTeacher().getEvaluationLabels() != null && !TextUtils.isEmpty(rowsBean.getTeacher().getEvaluationLabels())) {
            tagSkill.setVisibility(View.VISIBLE);
            tagSkill.setmSingleLine(true);
            String labels = rowsBean.getTeacher().getEvaluationLabels().substring(1, rowsBean.getTeacher().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagSkill.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(EvaluationListActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }

            });
        } else {
            tagSkill.setVisibility(View.GONE);
        }

    }

    /**
     * 重载的方法
     *
     * @param context
     * @param rowsBean
     */
    public static void StartActivity(Context context, StuEvaBean.DataBean.RowsBean rowsBean) {
        Intent intent = new Intent(context, EvaluationListActivity.class);
        intent.putExtra("rowsBean", rowsBean);
        context.startActivity(intent);
    }
    /**
     * 重载的方法
     *
     * @param context
     * @param id
     */
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, EvaluationListActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @OnClick({R.id.rl_back, R.id.bt_preview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_preview:
                if(rowsBean!=null){
                    EvaluationActivity.StartActivity(EvaluationListActivity.this,rowsBean);
                }
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

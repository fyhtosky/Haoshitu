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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 老师端的评价列表
 */
public class StuEvaluationListActivity extends AppCompatActivity {

    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
        setContentView(R.layout.activity_stu_evaluation_list);
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
                recyclerView.setLayoutManager(new LinearLayoutManager(StuEvaluationListActivity.this, LinearLayoutManager.VERTICAL, false));
                //画分割线
                recyclerView.addItemDecoration(new GrayDecoration());
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
                                StuLookEvaluationActivity.StartActivity(StuEvaluationListActivity.this,dataBean.getId());
                            }
                        });
                    }
                });
            }
        }
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
        }
//        0:老师评价 ；1：学生评价
        map.put("isTch", "0");
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
     * 获取被评价人的信息
     */
    private void findEva() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
//        0:老师评价 ；1：学生评价
        map.put("isTch", "1");
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

    /**
     * 展示老师的信息
     * @param rowsBean
     */
    private void showDetail(StuEvaBean.DataBean.RowsBean rowsBean) {
        //不显示新增的按钮
        if(rowsBean.getStatus()==1){
            if(rowsBean.getIsTchEva()==0){
                rlButton.setVisibility(View.VISIBLE);
            }else {
                rlButton.setVisibility(View.GONE);
            }
        }else if(rowsBean.getStatus()==0){
            rlButton.setVisibility(View.VISIBLE);
        }else {
            rlButton.setVisibility(View.GONE);
        }
        //显示头像
        if(rowsBean.getStudent().getHead()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getHead())){
            PicassoUtils.LoadPathCorners(StuEvaluationListActivity.this,rowsBean.getStudent().getHead(),circleView);
        }else{
            PicassoUtils.LoadCorners(StuEvaluationListActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //名字
        tvName.setText(rowsBean.getStudent().getRealName());
        //展示性别
        if (rowsBean.getStudent().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        String education="";
        //展示地址
        String address="";
        //地址赋值
        if(rowsBean.getStudent().getAddressNow()!=null && !TextUtils.isEmpty(rowsBean.getStudent().getAddressNow())){
            address=rowsBean.getStudent().getAddressNow().split(",")[0];
        }
        //展示薪资
        String salary="";
        //展示职位
        String post="";
//        档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if(rowsBean.getStudent().getResumes()!=null && rowsBean.getStudent().getResumes().size()>0){
            for ( StuEvaBean.DataBean.RowsBean.StudentBean.ResumesBean resumesBean:rowsBean.getStudent().getResumes()){
                //展示薪资及职位
                if(resumesBean.getResumeType()==1){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取职位
                    String expectPosition=mapstr.get("expectPosition");
                    //薪资
                    String expectSalary=mapstr.get("expectSalary");
                    if(MyApplication.map!=null){
                        if(expectPosition!=null && !TextUtils.isEmpty(expectPosition)){
                            String []poss=expectPosition.split(",");
                            //期望职位赋值
                            post=MyApplication.map.get(poss[poss.length-1]).getName();
                        }
                        if(expectSalary!=null && !TextUtils.isEmpty(expectSalary)){
                            String []salarys=expectSalary.split(",");
                            //薪资赋值
                            salary=MyApplication.map.get(salarys[salarys.length-1]).getName();
                        }
                    }


                }else if(resumesBean.getResumeType()==2){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String  diplomas = mapstr.get("diplomas");
                    if(MyApplication.map!=null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            education = MyApplication.map.get(dips[dips.length - 1]).getName();
                        }
                    }
                }
            }
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(salary)){
            tvSalary.setText(salary);
        }else {
            tvSalary.setText("");
        }

        //控件显示学历和地址
        if(!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)){
            tvAddress.setText(education+"|"+address);
        } else  if(TextUtils.isEmpty(education)){
            tvAddress.setText(address);
        }else if(TextUtils.isEmpty(address)){
            tvAddress.setText(education);
        }else {
            tvAddress.setText("");
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(post)){
            tvPost.setText("期望:"+post);
        }else{
            tvPost.setText("");
        }

        //技能标签
        if(rowsBean.getStudent().getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getEvaluationLabels())){
            tagTrait.setVisibility(View.VISIBLE);
            String labels=rowsBean.getStudent().getEvaluationLabels().substring(1,rowsBean.getStudent().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StuEvaluationListActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            tagTrait.setVisibility(View.GONE);
        }

    }


    /**
     * 展示老师的信息
     * @param rowsBean
     */
    private void showInfo(EvaluationDetailPersonBean.DataBean rowsBean) {
        //不显示新增的按钮
        if(rowsBean.getStatus()==1){
            if(rowsBean.getIsTchEva()==0){
                rlButton.setVisibility(View.VISIBLE);
            }else {
                rlButton.setVisibility(View.GONE);
            }
        }else if(rowsBean.getStatus()==0){
            rlButton.setVisibility(View.VISIBLE);
        }else {
            rlButton.setVisibility(View.GONE);
        }
        //显示头像
        if(rowsBean.getStudent().getHead()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getHead())){
            PicassoUtils.LoadPathCorners(StuEvaluationListActivity.this,rowsBean.getStudent().getHead(),circleView);
        }else{
            PicassoUtils.LoadCorners(StuEvaluationListActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //名字
        tvName.setText(rowsBean.getStudent().getRealName());
        //展示性别
        if (rowsBean.getStudent().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        String education="";
        //展示地址
        String address="";
        //地址赋值
        if(rowsBean.getStudent().getAddressNow()!=null && !TextUtils.isEmpty(rowsBean.getStudent().getAddressNow())){
            address=rowsBean.getStudent().getAddressNow().split(",")[0];
        }
        //展示薪资
        String salary="";
        //展示职位
        String post="";
//        档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if(rowsBean.getStudent().getResumes()!=null && rowsBean.getStudent().getResumes().size()>0){
            for ( EvaluationDetailPersonBean.DataBean.StudentBean.ResumesBean resumesBean:rowsBean.getStudent().getResumes()){
                //展示薪资及职位
                if(resumesBean.getResumeType()==1){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取职位
                    String expectPosition=mapstr.get("expectPosition");
                    //薪资
                    String expectSalary=mapstr.get("expectSalary");
                    if(MyApplication.map!=null){
                        if(expectPosition!=null && !TextUtils.isEmpty(expectPosition)){
                            String []poss=expectPosition.split(",");
                            //期望职位赋值
                            post=MyApplication.map.get(poss[poss.length-1]).getName();
                        }
                        if(expectSalary!=null && !TextUtils.isEmpty(expectSalary)){
                            String []salarys=expectSalary.split(",");
                            //薪资赋值
                            salary=MyApplication.map.get(salarys[salarys.length-1]).getName();
                        }
                    }


                }else if(resumesBean.getResumeType()==2){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String  diplomas = mapstr.get("diplomas");
                    if(MyApplication.map!=null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            education = MyApplication.map.get(dips[dips.length - 1]).getName();
                        }
                    }
                }
            }
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(salary)){
            tvSalary.setText(salary);
        }else {
            tvSalary.setText("");
        }

        //控件显示学历和地址
        if(!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)){
            tvAddress.setText(education+"|"+address);
        } else  if(TextUtils.isEmpty(education)){
            tvAddress.setText(address);
        }else if(TextUtils.isEmpty(address)){
            tvAddress.setText(education);
        }else {
            tvAddress.setText("");
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(post)){
            tvPost.setText("期望:"+post);
        }else{
            tvPost.setText("");
        }

        //技能标签
        if(rowsBean.getStudent().getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getEvaluationLabels())){
            tagTrait.setVisibility(View.VISIBLE);
            String labels=rowsBean.getStudent().getEvaluationLabels().substring(1,rowsBean.getStudent().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StuEvaluationListActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            tagTrait.setVisibility(View.GONE);
        }

    }
    /**
     * 重载的方法
     *
     * @param context
     * @param rowsBean
     */
    public static void StartActivity(Context context, StuEvaBean.DataBean.RowsBean rowsBean) {
        Intent intent = new Intent(context, StuEvaluationListActivity.class);
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
        Intent intent = new Intent(context, StuEvaluationListActivity.class);
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
                    StuEvaluationActivity.StartActivity(StuEvaluationListActivity.this,rowsBean);
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

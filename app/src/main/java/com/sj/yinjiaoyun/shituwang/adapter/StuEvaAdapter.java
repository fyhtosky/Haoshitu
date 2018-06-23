package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.EvaluationActivity;
import com.sj.yinjiaoyun.shituwang.activity.EvaluationListActivity;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/9.
 * 学生端评价的列表的适配器
 */
public class StuEvaAdapter extends RecyclerView.Adapter {
    private List<StuEvaBean.DataBean.RowsBean> list;
    private Context context;

    public StuEvaAdapter(List<StuEvaBean.DataBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stu_eva_list_item, parent, false);
        return new EvaHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EvaHolder viewHodler = (EvaHolder) holder;
        final StuEvaBean.DataBean.RowsBean rowsBean=list.get(position);
        //名字
        viewHodler.tvName.setText(rowsBean.getTeacher().getRealName());
        //职位和公司
        viewHodler.tvPost.setText(rowsBean.getTeacher().getPositionId()+" | "+rowsBean.getTeacher().getCompanyName());
        //显示头像
        if(rowsBean.getTeacher().getImgUrl()!=null&& !TextUtils.isEmpty(rowsBean.getTeacher().getImgUrl())){
            PicassoUtils.LoadPathCorners(context,rowsBean.getTeacher().getImgUrl(),viewHodler.circleView);
        }else{
            PicassoUtils.LoadCorners(context, R.drawable.master, 60, 60, viewHodler.circleView);
        }
        //如果在实习中则可以评价否则不可以评价
        if(rowsBean.getStatus()==0){
             viewHodler.llEva.setVisibility(View.VISIBLE);
             viewHodler.llEva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去评价
                    EvaluationActivity.StartActivity(context,rowsBean);


                }
            });
        }else if(rowsBean.getStatus()==1){
                if(rowsBean.getIsStuEva()==0){
                    viewHodler.llEva.setVisibility(View.VISIBLE);
                    viewHodler.llEva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //去评价
                            EvaluationActivity.StartActivity(context,rowsBean);
                        }
                    });
                }else {
                    viewHodler.llEva.setVisibility(View.GONE);
                }
        }else {
            //已终止
            viewHodler.llEva.setVisibility(View.GONE);
        }
        //设置默认状态的复用
        viewHodler.tagTrait.setVisibility(View.VISIBLE);
        viewHodler.tagSkill.setVisibility(View.VISIBLE);
        //技能标签
        if(rowsBean.getTeacher().getSkills()!=null&& rowsBean.getTeacher().getSkills()!=null){
            viewHodler.tagTrait.setVisibility(View.VISIBLE);
            viewHodler.tagTrait.setmSingleLine(true);
            List<StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean>skillsBeanList;
            if(rowsBean.getTeacher().getSkills().size()>4){
                skillsBeanList=rowsBean.getTeacher().getSkills().subList(0,3);
            }else {
                skillsBeanList=rowsBean.getTeacher().getSkills();
            }
            viewHodler.tagTrait.setAdapter(new TagAdapter<StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean>(skillsBeanList) {
                @Override
                public View getView(FlowLayout parent, int position, StuEvaBean.DataBean.RowsBean.TeacherBean.SkillsBean tchSkillsBean) {
                    View view = LayoutInflater.from(context)
                            .inflate(R.layout.item_textview, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(tchSkillsBean.getSkillName());
                    return view;
                }
            });
        }else{
            viewHodler.tagTrait.setVisibility(View.GONE);
        }
        //评价
        if(rowsBean.getTeacher().getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getTeacher().getEvaluationLabels())){
            viewHodler.tagSkill.setVisibility(View.VISIBLE);
            viewHodler.tagSkill.setmSingleLine(true);
            String labels=rowsBean.getTeacher().getEvaluationLabels().substring(1,rowsBean.getTeacher().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            viewHodler.tagSkill.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(context)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            viewHodler.tagSkill.setVisibility(View.GONE);
        }
        //添加点击事件
        viewHodler.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转界面
                EvaluationListActivity.StartActivity(context,rowsBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

     class EvaHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_eva)
        LinearLayout llEva;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tag_trait)
        TagFlowLayout tagTrait;
        @BindView(R.id.tag_skill)
        TagFlowLayout tagSkill;
        @BindView(R.id.item)
        RelativeLayout item;

         EvaHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

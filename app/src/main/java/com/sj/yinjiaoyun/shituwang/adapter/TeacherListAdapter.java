package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.TeacherDetailActivity;
import com.sj.yinjiaoyun.shituwang.bean.TeacherListBean;
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
 * Created by ${沈军 961784535@qq.com} on 2017/6/22.
 * 找师傅中的老师的列表
 */
public class TeacherListAdapter extends RecyclerView.Adapter {
    private List<TeacherListBean.DataBean.RowsBean> list;
    private Context context;

    public TeacherListAdapter(List<TeacherListBean.DataBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_teacher_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHodler = (ViewHolder) holder;
        final TeacherListBean.DataBean.RowsBean rowsBean=list.get(position);
        //名字
        viewHodler.tvName.setText(rowsBean.getRealName()==null ? "":rowsBean.getRealName());
        //职位和公司
        viewHodler.tvPost.setText((rowsBean.getPositionId()==null ? "":rowsBean.getPositionId())+" | "+(rowsBean.getCompanyName()==null ? "":rowsBean.getCompanyName()));
        //显示头像
        if(rowsBean.getImgUrl()!=null&& !TextUtils.isEmpty(rowsBean.getImgUrl())){
            PicassoUtils.LoadPathCorners(context,rowsBean.getImgUrl(),viewHodler.circleView);
        }else{
            PicassoUtils.LoadCorners(context, R.drawable.master, 60, 60, viewHodler.circleView);
        }
        //设置默认状态的复用
        viewHodler.tagTrait.setVisibility(View.VISIBLE);
        viewHodler.tagSkill.setVisibility(View.VISIBLE);
        //技能标签
        if(rowsBean.getTchResumeVO()!=null&& rowsBean.getTchResumeVO().getTchSkills()!=null){
            viewHodler.tagTrait.setVisibility(View.VISIBLE);
            viewHodler.tagTrait.setmSingleLine(true);
            List<TeacherListBean.DataBean.RowsBean.TchResumeVOBean.TchSkillsBean> tchSkillsBeanList;
            if(rowsBean.getTchResumeVO().getTchSkills().size()>4){
                tchSkillsBeanList=rowsBean.getTchResumeVO().getTchSkills().subList(0,3);
            }else {
                tchSkillsBeanList=rowsBean.getTchResumeVO().getTchSkills();
            }
            viewHodler.tagTrait.setAdapter(new TagAdapter<TeacherListBean.DataBean.RowsBean.TchResumeVOBean.TchSkillsBean>(tchSkillsBeanList) {
                @Override
                public View getView(FlowLayout parent, int position, TeacherListBean.DataBean.RowsBean.TchResumeVOBean.TchSkillsBean tchSkillsBean) {
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
        if(rowsBean.getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getEvaluationLabels())){
            viewHodler.tagSkill.setVisibility(View.VISIBLE);
            viewHodler.tagSkill.setmSingleLine(true);
            String labels=rowsBean.getEvaluationLabels().substring(1,rowsBean.getEvaluationLabels().length()-1);
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
                Intent intent=new Intent(context, TeacherDetailActivity.class);
                intent.putExtra("id",rowsBean.getId());
                context.startActivity(intent);
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

     class ViewHolder extends RecyclerView.ViewHolder{
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
        @BindView(R.id.item)
        RelativeLayout item;

      public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

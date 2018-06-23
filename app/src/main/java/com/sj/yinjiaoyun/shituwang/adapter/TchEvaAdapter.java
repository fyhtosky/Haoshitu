package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.StuEvaluationActivity;
import com.sj.yinjiaoyun.shituwang.activity.StuEvaluationListActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/9.
 * 学生端评价的列表的适配器
 */
public class TchEvaAdapter extends RecyclerView.Adapter {
    private List<StuEvaBean.DataBean.RowsBean> list;
    private Context context;

    public TchEvaAdapter(List<StuEvaBean.DataBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tch_eva_list_item, parent, false);
        return new TchHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TchHolder viewHodler = (TchHolder) holder;
        final StuEvaBean.DataBean.RowsBean rowsBean = list.get(position);
        //显示头像
        if(rowsBean.getStudent().getHead()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getHead())){
            PicassoUtils.LoadPathCorners(context,rowsBean.getStudent().getHead(),viewHodler.circleView);
        }else{
            PicassoUtils.LoadCorners(context, R.drawable.wukong, 60, 60, viewHodler.circleView);
        }
        //名字
        viewHodler.tvName.setText(rowsBean.getStudent().getRealName());
        //展示性别
        if (rowsBean.getStudent().getSex() == 1) {
            viewHodler.ivSex.setImageResource(R.mipmap.male);
        } else {
            viewHodler.ivSex.setImageResource(R.mipmap.woman);
        }
        //如果在实习中则可以评价否则不可以评价
        if (rowsBean.getStatus() == 0) {
            viewHodler.llEva.setVisibility(View.VISIBLE);
            viewHodler.llEva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去评价
                    StuEvaluationActivity.StartActivity(context, rowsBean);

                }
            });
        } else if (rowsBean.getStatus()==1){
            if(rowsBean.getIsTchEva()==0){
                viewHodler.llEva.setVisibility(View.VISIBLE);
                viewHodler.llEva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去评价
                        StuEvaluationActivity.StartActivity(context,rowsBean);
                    }
                });
            }else {
                viewHodler.llEva.setVisibility(View.GONE);
            }
        }else {
            viewHodler.llEva.setVisibility(View.GONE);
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
            viewHodler.tvSalary.setText(salary);
        }else {
            viewHodler.tvSalary.setText("");
        }

        //控件显示学历和地址
        if(!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)){
            viewHodler.tvAddress.setText(education+"|"+address);
        } else  if(TextUtils.isEmpty(education)){
            viewHodler.tvAddress.setText(address);
        }else if(TextUtils.isEmpty(address)){
            viewHodler.tvAddress.setText(education);
        }else {
            viewHodler.tvAddress.setText("");
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(post)){
            viewHodler.tvPost.setText("期望:"+post);
        }else{
            viewHodler.tvPost.setText("");
        }



        //设置默认状态的复用
        viewHodler.tagTrait.setVisibility(View.VISIBLE);
        //技能标签
        if(rowsBean.getStudent().getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getEvaluationLabels())){
            viewHodler.tagTrait.setVisibility(View.VISIBLE);
            String labels=rowsBean.getStudent().getEvaluationLabels().substring(1,rowsBean.getStudent().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            viewHodler.tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
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
            viewHodler.tagTrait.setVisibility(View.GONE);
        }
        //添加点击事件
        viewHodler.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转界面
                StuEvaluationListActivity.StartActivity(context, rowsBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    class TchHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.tv_salary)
        TextView tvSalary;
        @BindView(R.id.ll_eva)
        LinearLayout llEva;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tag_trait)
        TagFlowLayout tagTrait;
        @BindView(R.id.item)
        RelativeLayout item;

        TchHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

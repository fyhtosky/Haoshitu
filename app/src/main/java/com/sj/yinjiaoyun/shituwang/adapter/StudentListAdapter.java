package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.StudentResumeActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StudentListBean;
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
 * Created by ${沈军 961784535@qq.com} on 2017/7/10.
 * 老师端推荐界面学生的列表适配器
 */
public class StudentListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<StudentListBean.DataBean.RowsBean> list;

    public StudentListAdapter(Context context, List<StudentListBean.DataBean.RowsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
              MyHolder myHolder= (MyHolder) holder;
             final StudentListBean.DataBean.RowsBean rowsBean=list.get(position);
        //名字
        myHolder.tvName.setText(rowsBean.getRealName());
        //展示性别
        if (rowsBean.getSex() == 1) {
            myHolder.ivSex.setImageResource(R.mipmap.male);
        } else {
            myHolder.ivSex.setImageResource(R.mipmap.woman);
        }
         //展示学历
        String education="";
         //展示地址
        String address="";
        //地址赋值
        if(rowsBean.getAddressNow()!=null && !TextUtils.isEmpty(rowsBean.getAddressNow())){
            String [] add=rowsBean.getAddressNow().split(",");
            if(add.length<3){
                address=add[add.length-1];
             }else {
                address=rowsBean.getAddressNow().split(",")[1];
            }

        }
        //展示薪资
        String salary="";
        //展示职位
        String post="";
//        档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if(rowsBean.getResumes()!=null && rowsBean.getResumes().size()>0){
            for ( StudentListBean.DataBean.RowsBean.ResumesBean resumesBean:rowsBean.getResumes()){
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
                            if(MyApplication.map.get(salarys[salarys.length-1])!=null&&MyApplication.map.get(salarys[salarys.length-1]).getName()!=null){
                                salary=MyApplication.map.get(salarys[salarys.length-1]).getName();
                            }else {
                                salary="";
                            }

                        }
                    }


                }else if(resumesBean.getResumeType()==2){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String  diplomas = mapstr.get("diplomas");
                    if(MyApplication.map!=null && diplomas!=null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            if(MyApplication.map.get(dips[dips.length - 1]).getName()!=null){
                                education = MyApplication.map.get(dips[dips.length - 1]).getName();
                            }
                        }
                    }
                }
            }
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(salary)){
            myHolder.tvSalary.setText(salary);
        }else {
            myHolder.tvSalary.setText("");
        }

        //控件显示学历和地址
        if(!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)){
            myHolder.tvAddress.setText(education+"|"+address);
        } else  if(TextUtils.isEmpty(education)){
            myHolder.tvAddress.setText(address);
        }else if(TextUtils.isEmpty(address)){
            myHolder.tvAddress.setText(education);
        }else {
            myHolder.tvAddress.setText("");
        }
       //控件展示薪资
        if(!TextUtils.isEmpty(post)){
            myHolder.tvPost.setText("期望:"+post);
        }else{
            myHolder.tvPost.setText("");
        }
        //显示头像
        if(rowsBean.getHead()!=null&& !TextUtils.isEmpty(rowsBean.getHead())){
            PicassoUtils.LoadPathCorners(context,rowsBean.getHead(),myHolder.circleView);
        }else{
            PicassoUtils.LoadCorners(context, R.drawable.wukong, 60, 60, myHolder.circleView);
        }
        //评价
        if(rowsBean.getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getEvaluationLabels())){
            myHolder.tagTrait.setVisibility(View.VISIBLE);
            myHolder.tagTrait.setmSingleLine(true);
            String labels=rowsBean.getEvaluationLabels().substring(1,rowsBean.getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            myHolder.tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
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
            myHolder.tagTrait.setVisibility(View.GONE);
        }
        //展示描述
        myHolder.tvDescription.setText(rowsBean.getDescription());
        //添加点击事件
        myHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentResumeActivity.StartActivity(context,rowsBean.getId());
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

     class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.rl_title)
        RelativeLayout rlTitle;
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
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.item)
        RelativeLayout item;

         MyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

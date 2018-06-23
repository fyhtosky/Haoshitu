package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.BusinessHistoryActivity;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/22.
 */
public class WorkListAdapter extends BaseAdapter {
    private Context context;
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean> list;

    public WorkListAdapter(Context context, List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final MyHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.work_list_item, null);
            holder=new MyHolder(convertView);
            convertView.setTag(holder);
        } else {
           holder= (MyHolder) convertView.getTag();
        }
        final TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean tchWorkExpsBean=list.get(position);
        //显示头像
        PicassoUtils.LoadCorners(context,R.mipmap.cpmpany,holder.circleView);
        //显示职位
        holder.tvPost.setText(tchWorkExpsBean.getPosition());
        //显示工作公司
        holder.tvCompanyName.setText(tchWorkExpsBean.getCompanyName());
        //显示工作的时间
        if(tchWorkExpsBean.getBeginDate()!=null&&tchWorkExpsBean.getEndDate()!=null){
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())&& !TextUtils.isEmpty(tchWorkExpsBean.getEndDate())){
                holder.tvDate.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-"+TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getEndDate())));
            }
        }else{
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())){
                holder.tvDate.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-至今");
            }

        }
        //点击事件
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessHistoryActivity.openActivity(context,BusinessHistoryActivity.class,tchWorkExpsBean);
            }
        });
        return convertView;
    }

     class MyHolder {
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
         MyHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

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
import com.sj.yinjiaoyun.shituwang.activity.TeachterWorkActivity;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/12.
 */
public class TeachterWorkAdapter extends BaseAdapter {
    private Context context;
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean>list;

    public TeachterWorkAdapter(Context context, List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list==null){
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
        final TWorkHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.record_item2, null);
            holder = new TWorkHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TWorkHolder) convertView.getTag();
        }
        final TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean tchWorkExpsBean=list.get(position);
        holder.tvRecordItemName.setText(tchWorkExpsBean.getCompanyName());
        //显示工作的时间
        if(tchWorkExpsBean.getBeginDate()!=null&&tchWorkExpsBean.getEndDate()!=null){
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())&& !TextUtils.isEmpty(tchWorkExpsBean.getEndDate())){
                holder.tvRecordItemTime.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-"+TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getEndDate())));
            }
        }else{
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())){
                holder.tvRecordItemTime.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-至今");
            }

        }
        //添加点击事件
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeachterWorkActivity.StartActivity(context,tchWorkExpsBean);
            }
        });
        return convertView;
    }
    class TWorkHolder {
        @BindView(R.id.tv_record_item_name)
        TextView tvRecordItemName;
        @BindView(R.id.tv_record_item_time)
        TextView tvRecordItemTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        TWorkHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

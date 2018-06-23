package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/12.
 * 老师档案中的综合能力的是适配器
 */
public class TeacherAbilityAdapter extends BaseAdapter {
    private Context context;
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean>list;

    public TeacherAbilityAdapter(Context context, List<TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean> list) {
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
    public TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AbilityHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.record_item, null);
            holder = new AbilityHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AbilityHolder) convertView.getTag();
        }
        TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean tchAbilitiesBean=list.get(position);
        holder.tvRecordItemName.setText(tchAbilitiesBean.getAbilityName());
        holder.tvRecordItemTime.setText(tchAbilitiesBean.getAbilityValue());
        return convertView;
    }
    class AbilityHolder {
        @BindView(R.id.tv_record_item_name)
        TextView tvRecordItemName;
        @BindView(R.id.tv_record_item_time)
        TextView tvRecordItemTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        AbilityHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

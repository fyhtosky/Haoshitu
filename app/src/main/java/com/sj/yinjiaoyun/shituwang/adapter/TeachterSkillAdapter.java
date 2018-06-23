package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/12.
 * 老师档案中的专业技能的适配器
 */
public class TeachterSkillAdapter extends BaseAdapter {
    private Context context;
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean> list;

    public TeachterSkillAdapter(Context context, List<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean> list) {
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
    public TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TSkillHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.record_item, null);
            holder = new TSkillHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TSkillHolder) convertView.getTag();
        }
       TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean tchSkillsBean=list.get(position);
        holder.tvRecordItemName.setText(tchSkillsBean.getSkillName());
        holder.tvRecordItemTime.setText("");
        String skillLevel = tchSkillsBean.getSkillLevel().split(",")[1];
        Logger.d("skillLevel=" + skillLevel);
        if(MyApplication.map!=null){
            if(MyApplication.map.get(skillLevel)!=null){
                Logger.d("标签："+MyApplication.map.get(skillLevel).toString());
                String skill = MyApplication.map.get(skillLevel).getName();
                holder.tvRecordItemTime.setText(skill);
            }
        }

        return convertView;

    }
    class TSkillHolder {
        @BindView(R.id.tv_record_item_name)
        TextView tvRecordItemName;
        @BindView(R.id.tv_record_item_time)
        TextView tvRecordItemTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        TSkillHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

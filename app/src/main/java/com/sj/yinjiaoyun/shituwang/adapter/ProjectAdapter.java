package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.ProjectExperienceActivity;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/28.
 * 项目经验的适配器
 */
public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private List<StudentRecordBean.DataBean.ResumesBean> list;


    public ProjectAdapter(Context context, List<StudentRecordBean.DataBean.ResumesBean> list) {
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
    public StudentRecordBean.DataBean.ResumesBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProjectHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.record_item2, null);
            holder = new ProjectHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ProjectHolder) convertView.getTag();
        }
        final StudentRecordBean.DataBean.ResumesBean resumesBean=list.get(position);
        if(resumesBean.getResume()!=null && !TextUtils.isEmpty(resumesBean.getResume())){
            Map<String,String> mapstr=new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String,String>>(){}.getType());
            holder.tvRecordItemName.setText(mapstr.get("projectName"));
            String beginTime=mapstr.get("beginTime");
            String endTime=mapstr.get("endTime");
            if(endTime!=null){
                Logger.d("EducationAdapter：beginTime="+beginTime+",endTime="+endTime);
                holder.tvRecordItemTime.setText(TimeRender.getForm(Long.parseLong(beginTime))+"-"+TimeRender.getForm(Long.parseLong(endTime)));
            }else{
                Logger.d("EducationAdapter：beginTime="+beginTime);
                holder.tvRecordItemTime.setText(TimeRender.getForm(Long.parseLong(beginTime))+"-至今");
            }
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProjectExperienceActivity.StartActivity(context,resumesBean);
                }
            });
        }

        return convertView;
    }


    class ProjectHolder {
        @BindView(R.id.tv_record_item_name)
        TextView tvRecordItemName;
        @BindView(R.id.tv_record_item_time)
        TextView tvRecordItemTime;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        ProjectHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

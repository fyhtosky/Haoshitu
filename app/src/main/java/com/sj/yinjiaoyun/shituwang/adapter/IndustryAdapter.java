package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.bean.Children;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/3.
 * 选择行业的适配器
 */
public class IndustryAdapter extends BaseAdapter {
    private List<Children> list;
    private Context context;

    public IndustryAdapter(List<Children> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Children getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final IndustryHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rv_industry_item, null);
            holder = new IndustryHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (IndustryHolder) convertView.getTag();
        }
        final Children children=list.get(position);
        holder.tvIndustry.setText(children.getName());
        holder.rlIndustryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

   class IndustryHolder {
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.rl_industry_item)
        RelativeLayout rlIndustryItem;

       IndustryHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

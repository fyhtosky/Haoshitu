package com.sj.yinjiaoyun.shituwang.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/10.
 */
public class MyLayoutManager extends LinearLayoutManager {
    public MyLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}

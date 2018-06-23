package com.sj.yinjiaoyun.shituwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.TchEvaAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/4.
 * 评价学徒的已实习的Fragment
 */
public class WithStudentHavePractiveFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //登录的id
    private int id;
    private List<StuEvaBean.DataBean.RowsBean> list=new ArrayList<>();
    //适配器
    private TchEvaAdapter tchEvaAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.with_teachter_page_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        rv.addItemDecoration(new GrayDecoration());
        tchEvaAdapter = new TchEvaAdapter(list, getContext());
        //添加适配器
        rv.setAdapter(tchEvaAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        queryStuEva();
    }

    /**
     * 获取学生评价
     */
    private void queryStuEva() {
        HashMap<String,String> map=new HashMap<>();
        map.put("tchId",String.valueOf(id));
        HttpClient.post(this, Api.QUERY_TCH_EVAPG, map, new CallBack<StuEvaBean>() {
            @Override
            public void onSuccess(StuEvaBean result) {
                if(result==null){
                    return;
                }
                llNet.setVisibility(View.GONE);
                if (result.getData() != null && result.getData().getRows() != null) {
                    list.clear();
                    for(int i=0;i<result.getData().getRows().size();i++){
                        if(result.getData().getRows().get(i).getStatus()==1){
                            list.add(result.getData().getRows().get(i));
                        }
                    }
                    if(list.size()>0){
                        rlHint.setVisibility(View.GONE);
                    }else{
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    tchEvaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    if(list.size()==0){
                        llNet.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    @OnClick(R.id.bt_reload)
    public void onViewClicked() {
        queryStuEva();
    }
}

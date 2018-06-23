package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.StudentCollectListAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StudentCollectBean;
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
 * 学生端的
 * 我的收藏的界面
 */
public class MyCollectActivity extends AppCompatActivity {


    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //登录的id
    private int id;
    //数据源
    private List<StudentCollectBean.DataBean.RowsBean> list = new ArrayList<>();
    //适配器
    private StudentCollectListAdapter studentCollectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(MyCollectActivity.this, Const.ID);
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(MyCollectActivity.this, LinearLayoutManager.VERTICAL, false));
        //画分割线
        rv.addItemDecoration(new GrayDecoration());
        studentCollectListAdapter = new StudentCollectListAdapter(list, MyCollectActivity.this);
        //添加适配器
        rv.setAdapter(studentCollectListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        findStudentCollect(false);
    }

    /**
     * 获取学生收藏
     */
    private void findStudentCollect(final boolean isLoadMore) {
        HashMap<String, String> map = new HashMap<>();
        map.put("stuUserId", String.valueOf(id));
        HttpClient.post(this, Api.STUDENT_COLLECT, map, new CallBack<StudentCollectBean>() {
            @Override
            public void onSuccess(StudentCollectBean result) {
                if (result == null) {
                    return;
                }
                llNet.setVisibility(View.GONE);
                if (!isLoadMore) {
                    list.clear();
                }
                if (result.getData() != null && result.getData().getRows() != null) {
                    list.addAll(result.getData().getRows());
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    studentCollectListAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.rl_back,R.id.bt_reload})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_reload:
                findStudentCollect(false);
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

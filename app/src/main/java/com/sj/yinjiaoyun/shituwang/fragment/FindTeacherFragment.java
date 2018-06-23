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

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.TeacherListAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.TeacherListBean;
import com.sj.yinjiaoyun.shituwang.event.QueryTeacherEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/19.
 * 找师傅的Fragment
 */
public class FindTeacherFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    private TeacherListAdapter teacherListAdapter;
    //页数
    private int page = 1;
    //条数
    private int row = 10;
    //地址
    private String address = "";
    private String realName = "";
    //数据源
    private List<TeacherListBean.DataBean.RowsBean> list = new ArrayList<>();

    public static FindTeacherFragment newInstance() {
        Bundle args = new Bundle();
        FindTeacherFragment fragment = new FindTeacherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_teacher_fragment, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        teacherListAdapter = new TeacherListAdapter(list, getContext());
        //设置布局管理器
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        swipeTarget.addItemDecoration(new GrayDecoration());
        //添加适配器
        swipeTarget.setAdapter(teacherListAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        page++;
        getMasterList(true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getMasterList(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 地址改变或者搜索职位
     * @param queryTeacherEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(QueryTeacherEvent queryTeacherEvent) {
        if(queryTeacherEvent.getAddressNow()==null){
            return;
        }
        if ("全国".equals(queryTeacherEvent.getAddressNow())) {
            address = "";
        } else if (queryTeacherEvent.getAddressNow().contains("全省/市")) {
            address = queryTeacherEvent.getAddressNow().split(" ")[0];
        } else if (queryTeacherEvent.getAddressNow().contains("全市/区")) {
            address = queryTeacherEvent.getAddressNow().split(" ")[1];
        } else if("".equals(queryTeacherEvent.getAddressNow())) {
            address = "";
        }else {
            address = queryTeacherEvent.getAddressNow();
        }
        realName = queryTeacherEvent.getRealName();
        onRefresh();

    }

    /**
     * 获取老师的列表请求
     * @param isLoadMore
     */
    private void getMasterList(final boolean isLoadMore) {
        Logger.d("getMasterList:address：" + address);
        HashMap<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(row));
        map.put("addressNow", address);
        map.put("searchName", realName);
        HttpClient.post(this, Api.GTE_MASTER_LIST, map, new CallBack<TeacherListBean>() {
            @Override
            public void onSuccess(TeacherListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                swipeToLoadLayout.setRefreshing(false);
                if (result == null) {
                    return;
                }
                //影藏网络无效的界面
                llNet.setVisibility(View.GONE);
                if (!isLoadMore) {
                    list.clear();
                }
                if (list.size() == result.getData().getTotal()) {
                    ToastUtil.showShortToast(getContext(), "全部加载完成");
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    teacherListAdapter.notifyDataSetChanged();
                    return;
                }
                if (result.getData() != null && result.getData().getRows() != null) {
                    list.addAll(result.getData().getRows());
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    teacherListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                swipeToLoadLayout.setLoadingMore(false);
                swipeToLoadLayout.setRefreshing(false);
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
        onRefresh();
    }
}

package com.sj.yinjiaoyun.shituwang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.StudentListAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.IPBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentListBean;
import com.sj.yinjiaoyun.shituwang.cityPickter.CityPicker;
import com.sj.yinjiaoyun.shituwang.cityPickter.CityPickerListener;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.MyIpUtil;
import com.sj.yinjiaoyun.shituwang.utils.MyTeachterHandler;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/16.
 */
public class TeacherRecommendFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, CityPickerListener {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //页数
    private int page = 1;
    //条数
    private int row = 10;
    //城市
    private String areaName = "";
    //数据源
    private List<StudentListBean.DataBean.RowsBean> list = new ArrayList<>();
    private StudentListAdapter studentListAdapter;
    private InputMethodManager imm;
    private CityPicker cityPicker;
    private String ip = "";
    private Handler handler = new MyTeachterHandler(TeacherRecommendFragment.this) ;

    public static TeacherRecommendFragment newInstance() {
        Bundle args = new Bundle();
        TeacherRecommendFragment fragment = new TeacherRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_recommend_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        cityPicker = new CityPicker(getActivity(), this);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //设置布局管理器
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        swipeTarget.addItemDecoration(new GrayDecoration());
        studentListAdapter = new StudentListAdapter(getContext(), list);
        //添加适配器
        swipeTarget.setAdapter(studentListAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        new Thread() {
            @Override
            public void run() {
                ip = MyIpUtil.getNetIp();
                handler.sendEmptyMessage(1);
            }
        }.start();
        onRefresh();
        //点击软键盘上的回车键才会触发
        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //发送消息通知发现老师的界面筛选
                    onRefresh();
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        getStudentList(true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getStudentList(false);
    }

    /**
     * 获取学生的列表
     * @param isLoadMore
     */
    private void getStudentList(final boolean isLoadMore) {
        if ("全国".equals(tvCity.getText().toString().trim())) {
            areaName = "";
        } else {
            areaName = tvCity.getText().toString().trim();
        }
        Logger.d("areaName：" + areaName);
        String params = "?page=" + String.valueOf(page) + "&rows=" + String.valueOf(row) +
                "&areaName=" + areaName + "&searchName=" + etText.getText().toString().trim();
        HttpClient.get(this, Api.GET_STUDENT_LIST + params, new CallBack<StudentListBean>() {
            @Override
            public void onSuccess(StudentListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                swipeToLoadLayout.setRefreshing(false);
                if (result == null) {
                    return;
                }

                if (!isLoadMore) {
                    list.clear();
                }
                //影藏网络无效的界面
                llNet.setVisibility(View.GONE);
                if (list.size() == result.getData().getTotal()) {
                    ToastUtil.showShortToast(getContext(), "全部加载完成");
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    studentListAdapter.notifyDataSetChanged();
                    return;
                }
                if (result.getData() != null && result.getData().getRows() != null) {
                    etText.setText("");
                    list.addAll(result.getData().getRows());
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    studentListAdapter.notifyDataSetChanged();
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

    /**
     * 根据网络连接的ip获取当前的位置
     */
    public void getDictionary() {
        String params = "?ip=" + ip;
        HttpClient.get(this, Api.GET_DICTIONARY + params, new CallBack<IPBean>() {
            @Override
            public void onSuccess(IPBean result) {
                if (result == null) {
                    return;
                }
                if (result.getCode() != 1) {
                    tvCity.setText(result.getData().getCity());
                    //刷新数据
                    onRefresh();
                }

            }


        });
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.relativeLayout, R.id.bt_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout:
                cityPicker.show();
                break;
            case R.id.bt_reload:
                onRefresh();
                break;
        }

    }

    /**
     * 选择城市的回调
     * @param name
     */
    @Override
    public void getCity(String name) {
        if ("全国".equals(name)) {
            areaName = "";
        } else {
            areaName = name;
        }
        String[] provinces = name.split(" ");
        if (name.contains("全省/市")) {
            tvCity.setText(name.split(" ")[0]);
        } else if (name.contains("全市/区")) {
            tvCity.setText(name.split(" ")[1]);
        } else {
            tvCity.setText(provinces[provinces.length - 1]);
        }

        onRefresh();
    }


}

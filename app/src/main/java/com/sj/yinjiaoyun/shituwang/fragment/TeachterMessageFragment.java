package com.sj.yinjiaoyun.shituwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/3.
 * 老师端的消息列表的分类
 */
public class TeachterMessageFragment extends Fragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> list = Arrays.asList("全部", "沟通过","不合适");
    private List<Fragment> fragmentList = new ArrayList<>();
    private AllMessageListFragment allMessageListFragment;
    private ConnectMessageListFragment connectMessageListFragment;
    private OutMessageListFragment outMessageListFragment;
    public static TeachterMessageFragment newInstance() {
        Bundle args = new Bundle();
        TeachterMessageFragment fragment = new TeachterMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.teachter_message_fragment, container, false);
        ButterKnife.bind(this, viewRoot);
        init();
        return viewRoot;
    }

    /**
     * 初始化
     */
    private void init() {
        allMessageListFragment=new AllMessageListFragment();
        connectMessageListFragment=new ConnectMessageListFragment();
        outMessageListFragment=new OutMessageListFragment();
        fragmentList.clear();
        fragmentList.add(allMessageListFragment);
        fragmentList.add(connectMessageListFragment);
        fragmentList.add(outMessageListFragment);
        vp.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragmentList,list));
        vp.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(vp);
    }
}

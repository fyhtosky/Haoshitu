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
 * Created by Administrator on 2018/1/18.
 * 待处理的Fragment
 */

public class PendingFragment extends Fragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> list = Arrays.asList("新面试", "待面试","待确认","待实习");
    private List<Fragment> fragmentList = new ArrayList<>();
    //新邀请的fragment
    private NewInviteFragment newInviteFragment;
    //待面试
    private ForInterviewFragment forInterviewFragment;
    //待确认
    private ToConfirmedFragment toConfirmedFragment;
    //待实习的
    private ToInternshipFragment toInternshipFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
        newInviteFragment=new NewInviteFragment();
       forInterviewFragment=new ForInterviewFragment();
       toConfirmedFragment=new ToConfirmedFragment();
       toInternshipFragment=new ToInternshipFragment();
       fragmentList.clear();
       fragmentList.add(newInviteFragment);
       fragmentList.add(forInterviewFragment);
        fragmentList.add(toConfirmedFragment);
       fragmentList.add(toInternshipFragment);
        vp.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragmentList,list));
        vp.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(vp);
    }


}

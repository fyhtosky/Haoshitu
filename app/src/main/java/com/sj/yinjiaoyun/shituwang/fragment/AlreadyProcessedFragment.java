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
 * Created by Administrator on 2018/1/19.
 * 已处理的Fragment
 */

public class AlreadyProcessedFragment extends Fragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> list = Arrays.asList("已邀请","已接受", "实习中","已实习");
    private List<Fragment> fragmentList = new ArrayList<>();
    //已邀请的Fragment
    private SToAcceptFragment sToAcceptFragment;
    //已接受的Fragment
    private TAcceptedFragment tAcceptedFragment;
    //实习中的Fragment
    private InPracticeFragment inPracticeFragment;
    //已实习的Fragment
    private HavePractiveFragment havePractiveFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
//        1:待接受：applyPending
//        2已接受:interviewAccept
//        3已拒绝 :interviewReject
//        4已取消:interviewCancel
//        apply: 100,//申请（学生）
//                applyPending: 101,//邀约待处理（老师）;
//                interviewReject: 102, //邀约拒绝（学生）;
//                interviewAccept: 103,//邀约接受（学生）;
//                interviewCancel: 104, //邀约取消（定时器/老师）;
//                interviewWait: 110, //待面试（定时器）;
//                interviewFail: 111, //面试失败（老师）;
//                interviewSuccess: 112, //面试成功（实习邀约）（老师）;
//                internReject: 113, //实习拒绝（学生）;
//
//                internCancel: 114, //实习取消（定时器
//                //internAccept:115, //实习接受（学生）;
//                internWait: 120,//待实习（定时器）
//                interning: 121,// 实习中（定时器）
//                interned: 122// 已实习（定时器）
        sToAcceptFragment=new SToAcceptFragment();
        tAcceptedFragment=new TAcceptedFragment();
        inPracticeFragment =new InPracticeFragment();
        havePractiveFragment=new HavePractiveFragment();
        fragmentList.clear();
        fragmentList.add(sToAcceptFragment);
        fragmentList.add(tAcceptedFragment);
        fragmentList.add(inPracticeFragment);
        fragmentList.add(havePractiveFragment);
        vp.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragmentList,list));
        vp.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(vp);
    }


}

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
 * Created by ${沈军 961784535@qq.com} on 2017/7/6.
 * 求职管理中已面试的界面
 */
public class HaveInterviewFragment extends Fragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> list = Arrays.asList("已拒绝", "已取消","不合适","已终止");
    private List<Fragment> fragmentList = new ArrayList<>();
     //已拒绝
    private RefusedFragment refuseFragment;
    //已取消
    private CanceledFragment cancelFragment;
    //不合适的
    private InproperFragment inproperFragment;
    //已终止
    private TerminatedFragment terminatedFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
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
//
//        1:未处理；
//        studyStatus.applyPending
//        2已处理 ；
//        1：已接受   studyStatus.interviewAccept
//        2：待面试；  studyStatus.interviewWait
//        3 已拒绝     studyStatus.interviewReject
//        4：已取消   studyStatus.interviewCancel
//
//        3已面试 :
//        1：待实习  studyStatus.interviewSuccess;studyStatus.internWait
//        2：实习中；studyStatus.interning
//        3 已实习  studyStatus.interned
//        4：不合适 studyStatus.interviewFail,studyStatus.internReject,studyStatus.internCancel
//        toInternshipFragment=new ToInternshipFragment();
//        inInternshipFragment=new InInternshipFragment();
//        haveInternshipFragment=new HaveInternshipFragment();
        refuseFragment=new RefusedFragment();
        cancelFragment=new CanceledFragment();
        inproperFragment=new InproperFragment();
        terminatedFragment=new TerminatedFragment();
        fragmentList.clear();
        fragmentList.add(refuseFragment);
        fragmentList.add(cancelFragment);
        fragmentList.add(inproperFragment);
        fragmentList.add(terminatedFragment);
        vp.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragmentList,list));
        vp.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(vp);
    }
}

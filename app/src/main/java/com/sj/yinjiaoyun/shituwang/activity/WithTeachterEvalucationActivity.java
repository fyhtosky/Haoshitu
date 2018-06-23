package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.MyViewPagerAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.fragment.WithTeachterHavePractiveFragment;
import com.sj.yinjiaoyun.shituwang.fragment.WithTeachterInPractiveFragment;
import com.sj.yinjiaoyun.shituwang.fragment.WithTeachterTerminatedFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学生端的跟师评价
 */
public class WithTeachterEvalucationActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<String> list = Arrays.asList("实习中", "已实习", "已终止");
    private List<Fragment> fragmentList = new ArrayList<>();
    private WithTeachterInPractiveFragment withTeachterInPractiveFragment;
    private WithTeachterHavePractiveFragment withTeachterHavePractiveFragment;
    //已终止的Fragment
    private WithTeachterTerminatedFragment withTeachterTerminatedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_teachter_evalucation);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        withTeachterInPractiveFragment=new WithTeachterInPractiveFragment();
        withTeachterHavePractiveFragment=new WithTeachterHavePractiveFragment();
        withTeachterTerminatedFragment=new WithTeachterTerminatedFragment();
        fragmentList.clear();
        fragmentList.add(withTeachterInPractiveFragment);
        fragmentList.add(withTeachterHavePractiveFragment);
        fragmentList.add(withTeachterTerminatedFragment);
        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList,list));
        vp.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(vp);
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

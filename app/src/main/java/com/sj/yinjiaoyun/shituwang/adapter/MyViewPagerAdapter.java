package com.sj.yinjiaoyun.shituwang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/19.
 * tablayou+ViewPager的适配器
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    //fragment列表
    private List<Fragment> list_fragment;
    //tab名的列表
    private List<String> list_Title;
    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }


    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position % list_Title.size());
    }
}

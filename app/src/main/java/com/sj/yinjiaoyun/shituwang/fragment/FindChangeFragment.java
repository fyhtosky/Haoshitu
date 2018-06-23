package com.sj.yinjiaoyun.shituwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sj.yinjiaoyun.shituwang.R;

import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/19.
 * 找机会的Fragment
 */
public class FindChangeFragment extends Fragment  {
    public static FindChangeFragment newInstance() {
        Bundle args = new Bundle();
        FindChangeFragment fragment = new FindChangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_teacher_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

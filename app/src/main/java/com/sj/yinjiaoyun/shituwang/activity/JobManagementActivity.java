package com.sj.yinjiaoyun.shituwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.fragment.HaveInterviewFragment;
import com.sj.yinjiaoyun.shituwang.fragment.PendingFragment;
import com.sj.yinjiaoyun.shituwang.fragment.ProcessedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobManagementActivity extends AppCompatActivity {

    @BindView(R.id.bt_pending)
    Button btPending;
    @BindView(R.id.bt_processed)
    Button btProcessed;
    @BindView(R.id.bt_have_interviews)
    Button btHaveInterviews;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private FragmentManager manager;
    //新邀请的fragment
    private PendingFragment pendFragment;
    //已处理的Fragment
    private ProcessedFragment processFragmengt;
    //已取消的Fragment
    private HaveInterviewFragment haveInterviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_management);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        manager = getSupportFragmentManager();
        pendFragment=new PendingFragment();
        processFragmengt=new ProcessedFragment();
        haveInterviewFragment=new HaveInterviewFragment();
        showFragment(pendFragment);
    }

    @OnClick({R.id.rl_back, R.id.bt_pending, R.id.bt_processed, R.id.bt_have_interviews})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_pending:
                btPending.setBackgroundResource(R.drawable.ll_bac1);
                btPending.setTextColor(Color.parseColor("#24C789"));
                btProcessed.setBackgroundResource(R.drawable.ll_bac);
                btProcessed.setTextColor(Color.WHITE);
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac);
                btHaveInterviews.setTextColor(Color.WHITE);
                showFragment(pendFragment);

                break;
            case R.id.bt_processed:
                btProcessed.setBackgroundResource(R.drawable.ll_bac1);
                btProcessed.setTextColor(Color.parseColor("#24C789"));
                btPending.setBackgroundResource(R.drawable.ll_bac);
                btPending.setTextColor(Color.WHITE);
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac);
                btHaveInterviews.setTextColor(Color.WHITE);
                showFragment(processFragmengt);
                break;
            case R.id.bt_have_interviews:
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac1);
                btHaveInterviews.setTextColor(Color.parseColor("#24C789"));
                btPending.setBackgroundResource(R.drawable.ll_bac);
                btPending.setTextColor(Color.WHITE);
                btProcessed.setBackgroundResource(R.drawable.ll_bac);
                btProcessed.setTextColor(Color.WHITE);
                showFragment(haveInterviewFragment);
                break;
        }
    }

    /**
     * 切换Fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

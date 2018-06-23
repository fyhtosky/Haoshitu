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
import com.sj.yinjiaoyun.shituwang.fragment.AlreadyProcessedFragment;
import com.sj.yinjiaoyun.shituwang.fragment.HaveInvitedFragment;
import com.sj.yinjiaoyun.shituwang.fragment.THaveInteviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 面试管理
 */
public class InterviewManagementActivity extends AppCompatActivity {


    @BindView(R.id.bt_have_invited)
    Button btHaveInvited;
    @BindView(R.id.bt_for_interviews)
    Button btForInterviews;
    @BindView(R.id.bt_have_interviews)
    Button btHaveInterviews;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private FragmentManager manager;
    //待处理的Fragment
    private HaveInvitedFragment haveInvitedFragment;
    //已处理的Fragment
    private AlreadyProcessedFragment alreadyProcessedFragment;
    //已面试的Fragment
    private THaveInteviewFragment tHaveInteviewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_management);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        manager = getSupportFragmentManager();
        haveInvitedFragment=new HaveInvitedFragment();
        alreadyProcessedFragment=new AlreadyProcessedFragment();
        tHaveInteviewFragment=new THaveInteviewFragment();
        showFragment(haveInvitedFragment);
    }

    @OnClick({R.id.rl_back,  R.id.bt_have_invited, R.id.bt_for_interviews, R.id.bt_have_interviews})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_have_invited:
                btHaveInvited.setBackgroundResource(R.drawable.ll_bac1);
                btHaveInvited.setTextColor(Color.parseColor("#24C789"));
                btForInterviews.setBackgroundResource(R.drawable.ll_bac);
                btForInterviews.setTextColor(Color.WHITE);
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac);
                btHaveInterviews.setTextColor(Color.WHITE);
                showFragment(haveInvitedFragment);
                break;
            case R.id.bt_for_interviews:
                btForInterviews.setBackgroundResource(R.drawable.ll_bac1);
                btForInterviews.setTextColor(Color.parseColor("#24C789"));
                btHaveInvited.setBackgroundResource(R.drawable.ll_bac);
                btHaveInvited.setTextColor(Color.WHITE);
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac);
                btHaveInterviews.setTextColor(Color.WHITE);
                showFragment(alreadyProcessedFragment);
                break;
            case R.id.bt_have_interviews:
                btHaveInterviews.setBackgroundResource(R.drawable.ll_bac1);
                btHaveInterviews.setTextColor(Color.parseColor("#24C789"));
                btHaveInvited.setBackgroundResource(R.drawable.ll_bac);
                btHaveInvited.setTextColor(Color.WHITE);
                btForInterviews.setBackgroundResource(R.drawable.ll_bac);
                btForInterviews.setTextColor(Color.WHITE);
                showFragment(tHaveInteviewFragment);
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

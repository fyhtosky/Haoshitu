package com.sj.yinjiaoyun.shituwang.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.InterviewDeatilActivity;
import com.sj.yinjiaoyun.shituwang.activity.IterviewActivity;
import com.sj.yinjiaoyun.shituwang.activity.TachterInterviewActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StudiesBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by Administrator on 2018/1/19.
 * 老師端 已终止的Fragment
 */

public class TeacherTerminatedFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //登录的id
    private int id;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    //数据源的集合
    private List<StudiesBean.DataBean> list = new ArrayList<>();
    private ForegroundColorSpan span;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pending_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        span = new ForegroundColorSpan(Color.GREEN);
        loginId = PreferencesUtils.getSharePreInt(getContext(), Const.LOGINID);
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        rv.addItemDecoration(new GrayDecoration());
        rv.setAdapter(new BaseQuickAdapter<StudiesBean.DataBean>(R.layout.termination_fragment_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final StudiesBean.DataBean dataBean) {
                CircleImageView imageView = baseViewHolder.getView(R.id.circle_picture);
                //展示老师的头像
                if (dataBean.getStudentDeatil().getHead() != null && !"".equals(dataBean.getStudentDeatil().getHead())) {
                    PicassoUtils.LoadPathCorners(getContext(), dataBean.getStudentDeatil().getHead(), 70, 70, imageView);
                } else {
                    PicassoUtils.LoadCorners(getContext(), R.drawable.master, 70, 70, imageView);
                }
                //展示用户名
                baseViewHolder.setText(R.id.tv_termination_tutor, dataBean.getStudentDeatil().getRealName());
                //更新时间
                if (dataBean.getUpdateTime() != null && !TextUtils.isEmpty(dataBean.getUpdateTime())) {
                    baseViewHolder.setText(R.id.tv_time, TimeRender.FriendlyDate(Long.parseLong(dataBean.getUpdateTime())));
                }
                  //显示类型:123（面试终止） 124 实习终止
                switch (dataBean.getCurrentStatus()){
                    case 123:
                        baseViewHolder.setText(R.id.tv_termination_type, "面试" );
                        break;
                    case 124:
                        baseViewHolder.setText(R.id.tv_termination_type, "实习" );
                        break;
                }

                //添加点击事件
                baseViewHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentStatus=dataBean.getCurrentStatus();
                        if (currentStatus == 100) {
                            TachterInterviewActivity.StartActivity(getContext(), dataBean.getId());
                        } else if (currentStatus == 101 || currentStatus == 102 || currentStatus == 103 || currentStatus == 104) {
                            IterviewActivity.StartActivity(getContext(), dataBean.getId());
                        } else {
                            InterviewDeatilActivity.StartActivity(getContext(), dataBean.getId());
                        }
                    }
                });
            }

        });
        getData();
    }
    /**
     * 获取数据源
     */
    private void getData() {
        String params = "?usrId=" + String.valueOf(id) + "&isStu=0&status=123,124";
        HttpClient.get(this, Api.GET_STUDY + params, new CallBack<StudiesBean>() {
            @Override
            public void onSuccess(StudiesBean result) {
                if (result == null) {
                    return;
                }
                llNet.setVisibility(View.GONE);
                if (result.getStatus() == 200) {
                    list.clear();
                    list.addAll(result.getData());
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    rv.getAdapter().notifyDataSetChanged();
                } else {
                    ToastUtil.showShortToast(getContext(), result.getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    if(list.size()==0){
                        llNet.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }



    @OnClick(R.id.bt_reload)
    public void onViewClicked() {
        getData();
    }
}

package com.sj.yinjiaoyun.shituwang.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
import com.sj.yinjiaoyun.shituwang.activity.InterviewInvitationActivity;
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
 * Created by ${沈军 961784535@qq.com} on 2017/7/6.
 * 已处理中的已接受界面
 */
public class AcceptedFragment extends Fragment {
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
        rv.setAdapter(new BaseQuickAdapter<StudiesBean.DataBean>(R.layout.accept_fragment_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final StudiesBean.DataBean dataBean) {
                CircleImageView imageView = baseViewHolder.getView(R.id.circle_picture);
                //展示老师的头像
                if (dataBean.getTeacher().getImgUrl() != null && !TextUtils.isEmpty(dataBean.getTeacher().getImgUrl())) {
                    PicassoUtils.LoadPathCorners(getContext(), dataBean.getTeacher().getImgUrl(), 70, 70, imageView);
                } else {
                    PicassoUtils.LoadCorners(getContext(), R.drawable.master, 70, 70, imageView);
                }
                String rsx = "来自";
                SpannableStringBuilder builder = new SpannableStringBuilder(rsx + dataBean.getTeacher().getRealName() + "的面试邀请");
                builder.setSpan(span, rsx.length(), dataBean.getTeacher().getRealName().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                baseViewHolder.setText(R.id.tv_info, builder);
                //更新时间
                if (dataBean.getUpdateTime() != null && !TextUtils.isEmpty(dataBean.getUpdateTime())) {
                    baseViewHolder.setText(R.id.tv_time, TimeRender.FriendlyDate(Long.parseLong(dataBean.getUpdateTime())));
                }
                //面试
                baseViewHolder.setText(R.id.tv_interview, "跟师学习");
                //面试时间
                if (dataBean.getInterviewTime() != null && !TextUtils.isEmpty(dataBean.getInterviewTime())) {
                    baseViewHolder.setText(R.id.tv_interview_time, TimeRender.getBirthday2(Long.parseLong(dataBean.getInterviewTime())));
                }
                //面试地址
                if (dataBean.getInterviewAddr() != null && !TextUtils.isEmpty(dataBean.getInterviewAddr())) {
                    baseViewHolder.setText(R.id.tv_interview_address, dataBean.getInterviewAddr());
                }
                //添加点击事件
                //添加点击事件
                baseViewHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InterviewInvitationActivity.StartActivity(getContext(), dataBean.getId());
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
        String params = "?usrId=" + String.valueOf(id) + "&isStu=1&status=103,115";
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

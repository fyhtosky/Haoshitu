package com.sj.yinjiaoyun.shituwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.CollectedMyActivity;
import com.sj.yinjiaoyun.shituwang.activity.EvaStudentActivity;
import com.sj.yinjiaoyun.shituwang.activity.InterviewManagementActivity;
import com.sj.yinjiaoyun.shituwang.activity.NoticeActivity;
import com.sj.yinjiaoyun.shituwang.activity.SettingActivity;
import com.sj.yinjiaoyun.shituwang.activity.TeachterRecordActivity;
import com.sj.yinjiaoyun.shituwang.bean.TeacherPersonalCenterDetailBean;
import com.sj.yinjiaoyun.shituwang.bean.UnreadNoticeBean;
import com.sj.yinjiaoyun.shituwang.event.InfoEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.CreditScoreView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/11.
 * 老师端的个人中心
 */
public class TeacherPersonalCenterFragment extends Fragment {

    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.credit_store_mine)
    CreditScoreView creditStoreMine;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    //登录的id
    private int id;
    //雷达图需要的数据源
    private ArrayList<String> titleList = new ArrayList<>();
    private List<Double> data = new ArrayList<>();
    public static TeacherPersonalCenterFragment newInstance() {
        Bundle args = new Bundle();
        TeacherPersonalCenterFragment fragment = new TeacherPersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_personal_center_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        Logger.d("登录的id:" + id);

    }

    @Override
    public void onResume() {
        super.onResume();
        getTeacherDeatil();
        findUnreadNotice();
    }

    /**
     * 个人中心中获取老师的基本信息
     */
    private void getTeacherDeatil() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        HttpClient.post(this, Api.GET_TEACHER_CENTER, map, new CallBack<TeacherPersonalCenterDetailBean>() {
            @Override
            public void onSuccess(TeacherPersonalCenterDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    showTeacherDeatil(result);
                } else {
                    ToastUtil.showShortToast(getContext(), result.getMsg());
                }
            }

        });
    }
    /**
     * 查找未读的消息数
     */
    private void findUnreadNotice() {
        HashMap<String,String>map=new HashMap<>();
        map.put("receiverId",String.valueOf(id));
        map.put("receiverType","1");
        HttpClient.post(this, Api.FIND_UNREAD_NOTICE, map, new CallBack<UnreadNoticeBean>() {
            @Override
            public void onSuccess(UnreadNoticeBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    if(result.getData()>0){
                        ivNotice.setVisibility(View.VISIBLE);
                    }else {
                        ivNotice.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 展示数据
     * @param result
     */
    private void showTeacherDeatil(@NonNull TeacherPersonalCenterDetailBean result) {
        if (result.getData() == null) {
            return;
        }
        //显示头像
        if (result.getData().getImgUrl() != null && !TextUtils.isEmpty(result.getData().getImgUrl())) {
            PicassoUtils.LoadPathCorners(getContext(), result.getData().getImgUrl(), 75, 75, circlePicture);
        } else {
            PicassoUtils.LoadCorners(getContext(), R.drawable.master, 75, 75, circlePicture);
        }

        //显示学生姓名
        if (result.getData().getRealName() != null && !TextUtils.isEmpty(result.getData().getRealName())) {
            tvName.setText(result.getData().getRealName());
        }
        //显示性别
        if ("1".equals(result.getData().getSex())) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //展示职位
        tvPost.setText(result.getData().getPositionId());
        //展示雷达图
        /**
         * 展示雷达图
         */
        titleList.clear();
        data.clear();
        data.add(result.getData().getCitizenship());
        titleList.add("公民素养");
        data.add(result.getData().getMoralTrait());
        titleList.add("道德品质");
        data.add(result.getData().getTaste());
        titleList.add("审美与表现");
        data.add(result.getData().getStudy());
        titleList.add("学习与兴趣");
        data.add(result.getData().getLogicalMathe());
        titleList.add("数理智能");
        data.add(result.getData().getSelfCognitive());
        titleList.add("自我认知智能");
        data.add(result.getData().getSubjectAttainment());
        titleList.add("学科素养");
        data.add(result.getData().getInteraction());
        titleList.add("人际交往");
        data.add(result.getData().getSpace());
        titleList.add("空间智能");
        if (titleList.size() > 0 && data.size() > 0 && titleList.size() == data.size()) {
            //设置点的数量
            creditStoreMine.setCount(titleList.size());
            //设置标题的数据源
            creditStoreMine.setTitles(titleList);
           //设置各维度的值
            creditStoreMine.setData(data);
            creditStoreMine.setVisibility(View.VISIBLE);
        }
        scrollView.smoothScrollTo(0,0);
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.circle_picture, R.id.rl_setting,R.id.rl_notice, R.id.tv_my_collect, R.id.tv_interview_management, R.id.tv_evaluation_student})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle_picture:
                startActivity(new Intent(getContext(), TeachterRecordActivity.class));
                break;
            //设置
            case R.id.rl_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            //消息
            case R.id.rl_notice:
                startActivity(new Intent(getContext(), NoticeActivity.class));
                break;

            //收藏我的
            case R.id.tv_my_collect:
                startActivity(new Intent(getContext(), CollectedMyActivity.class));
                break;
            //面试管理
            case R.id.tv_interview_management:
                startActivity(new Intent(getContext(), InterviewManagementActivity.class));
                break;
            //评价学徒
            case R.id.tv_evaluation_student:
                startActivity(new Intent(getContext(), EvaStudentActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接受系统推送的通知
     * @param infoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(InfoEvent infoEvent) {
        if (org.jivesoftware.smack.packet.Message.Type.chat == infoEvent.getType()) {
            if(!"C01".equals(infoEvent.getInfoType())){
                return;
            }
            if (!("5tch_" + id).equals(infoEvent.getTo())) {
                return;
            }
            //接受到系统推送的通知提示
            ivNotice.setVisibility(View.VISIBLE);
        }
    }
}

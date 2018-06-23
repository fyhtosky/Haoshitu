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
import com.sj.yinjiaoyun.shituwang.activity.JobManagementActivity;
import com.sj.yinjiaoyun.shituwang.activity.MyCollectActivity;
import com.sj.yinjiaoyun.shituwang.activity.MyRecordActivity;
import com.sj.yinjiaoyun.shituwang.activity.NoticeActivity;
import com.sj.yinjiaoyun.shituwang.activity.SettingActivity;
import com.sj.yinjiaoyun.shituwang.activity.WithTeachterEvalucationActivity;
import com.sj.yinjiaoyun.shituwang.bean.StudentPersonalCenterDetailBean;
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
 * Created by ${沈军 961784535@qq.com} on 2017/6/23.
 * 学生身份的个人中心
 */
public class StudentPersonalCenterFragment extends Fragment {
    @BindView(R.id.circle_picture)
    CircleImageView circlePicture;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.credit_store_mine)
    CreditScoreView creditStoreMine;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    //登录的uuid
    private String uuid;
    //登录的id
    private int id;
    //雷达图需要的数据源
    private ArrayList<String> titleList = new ArrayList<>();
    private List<Double> data = new ArrayList<>();

    /**
     * 创建实例
     * @return
     */
    public static StudentPersonalCenterFragment newInstance() {
        Bundle args = new Bundle();
        StudentPersonalCenterFragment fragment = new StudentPersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * 绘制Fragment的界面
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_personal_center_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    /**
     * 初始化
     */
    private void init() {
        uuid = PreferencesUtils.getSharePreStr(getContext(), Const.UUID);
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        Logger.d("登录的uuid:" + uuid);


    }
    @Override
    public void onResume() {
        super.onResume();
        getStudentDeatil();
        findUnreadNotice();
    }
    /**
     * 获取学生的详细信息
     */
    private void getStudentDeatil() {
        String params = "?uuid=" + uuid;
        HttpClient.get(this, Api.GET_STUDENT_CENTER + params, new CallBack<StudentPersonalCenterDetailBean>() {
            @Override
            public void onSuccess(StudentPersonalCenterDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    showStudentDeatil(result);
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
        map.put("receiverType","0");
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
     * 展示界面数据
     * @param result
     */
    private void showStudentDeatil(@NonNull StudentPersonalCenterDetailBean result) {
        if (result.getData() == null) {
            return;
        }
        //显示头像
        if (result.getData().getHead() != null && !TextUtils.isEmpty(result.getData().getHead())) {
            PicassoUtils.LoadPathCorners(getContext(), result.getData().getHead(), 75, 75, circlePicture);
        } else {
            PicassoUtils.LoadCorners(getContext(), R.drawable.wukong, 75, 75, circlePicture);
        }
        //显示学生姓名
        if (result.getData().getRealName() != null && !TextUtils.isEmpty(result.getData().getRealName())) {
            tvName.setText(result.getData().getRealName());
        }
        //显示性别
        if (result.getData().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        //显示求职状态
        if (result.getData().getWorkStatus() == 1) {
            tvState.setText("寻找师父 ");
        } else {
            tvState.setText("暂不考虑");
        }
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
        scrollView.smoothScrollTo(0, 0);
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.circle_picture, R.id.rl_setting,R.id.rl_notice, R.id.tv_my_collect, R.id.tv_job_management, R.id.tv_teacher_evaluation})
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转我的档案
            case R.id.circle_picture:
                startActivity(new Intent(getContext(), MyRecordActivity.class));
                break;
            //设置
            case R.id.rl_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            //消息
            case R.id.rl_notice:
                startActivity(new Intent(getContext(), NoticeActivity.class));
                break;
            //我的收藏
            case R.id.tv_my_collect:
                startActivity(new Intent(getContext(), MyCollectActivity.class));
                break;
            //求职管理
            case R.id.tv_job_management:
                startActivity(new Intent(getContext(), JobManagementActivity.class));
                break;
            //跟师评价
            case R.id.tv_teacher_evaluation:
                startActivity(new Intent(getContext(), WithTeachterEvalucationActivity.class));
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
            if (!("5stu_" + id).equals(infoEvent.getTo())) {
                return;
            }
            //接受到系统推送的通知提示
            ivNotice.setVisibility(View.VISIBLE);
        }
    }


}

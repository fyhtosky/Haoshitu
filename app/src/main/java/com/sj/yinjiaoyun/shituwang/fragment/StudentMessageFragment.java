package com.sj.yinjiaoyun.shituwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.StudentMessageListAdapter;
import com.sj.yinjiaoyun.shituwang.bean.RecentlyMsgBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.event.MsgEvent;
import com.sj.yinjiaoyun.shituwang.event.NoticeEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.GrayDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/18.
 * 学生的端的IM
 */
public class StudentMessageFragment extends Fragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    //登录的id
    private int id;
    //信息列表的数据源
    private List<RecentlyMsgBean.DataBean> list = new ArrayList<>();
    //适配器
    private StudentMessageListAdapter messageListAdapter;
    //消息的数量
    private int messageCount;
    public static StudentMessageFragment newInstance() {
        Bundle args = new Bundle();
        StudentMessageFragment fragment = new StudentMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_message_fragment, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
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
     * 初始化
     */
    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        recyclerView.addItemDecoration(new GrayDecoration());
        messageListAdapter = new StudentMessageListAdapter(getContext(), list);
        recyclerView.setAdapter(messageListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRecentlyMsg();
    }

    /**
     * 回话列表获取私聊消息通知聊天界面刷新
     * @param msgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEvent msgEvent) {
        if (Message.Type.chat == msgEvent.getType()) {
            Logger.d("StudentMessageFragment:" + msgEvent.getMsgBody() + "  接收者TO:" + msgEvent.getTo() + "  发送者FROM:" + msgEvent.getFrom());
            RecentlyMsgBean.DataBean bean;
            int j = 0;
            if (list.size() > 0) {
                boolean t = false;
                for (int i = 0; i < list.size(); i++) {
                    if (msgEvent.getFrom().equals(list.get(i).getSender())) {
                        j = i;
                        t = true;
                        break;
                    }
                }
                if (!t) {
                    //未知发送者
                    getInfo(msgEvent);
                } else {
                    bean = list.get(j);
                    Logger.d("MessageListFragment:onMessageEvent:" + bean.getSender());
                    list.remove(bean);
                    bean.setSender(msgEvent.getFrom());
                    bean.setReceiver(msgEvent.getTo());
                    bean.setCreateTime(String.valueOf(new Date().getTime()));
                    bean.setMsgCount(bean.getMsgCount() + 1);
                    bean.setMsg(msgEvent.getMsgBody());
                    list.add(0, bean);
                    messageListAdapter.notifyDataSetChanged();
                }

            } else {
                Logger.d("【私聊】发送人不存在最近的消息列表");
                //未知发送者
                getInfo(msgEvent);
            }
            //记录消息的数量
            for (int i=0;i<list.size();i++){
                messageCount+=list.get(i).getMsgCount();
            }
            EventBus.getDefault().post(new NoticeEvent(messageCount));
        }
    }

    /**
     * 聊天列表中获取未知聊天对象的信息
     * @param msgEvent
     */
    private void getInfo(final MsgEvent msgEvent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", msgEvent.getFrom().split("_")[1]);
        HttpClient.post(this, Api.GET_TEACHER_DEATIL, map, new CallBack<TeacherDetailBean>() {
            @Override
            public void onSuccess(TeacherDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200 && result.getData() != null) {
                    RecentlyMsgBean.DataBean dataBean = new RecentlyMsgBean.DataBean();
                    dataBean.setMsgLogo(result.getData().getImgUrl());
                    dataBean.setMsgName(result.getData().getRealName());
                    dataBean.setCreateTime(String.valueOf(new Date().getTime()));
                    dataBean.setReceiver(msgEvent.getTo());
                    dataBean.setSender(msgEvent.getFrom());
                    dataBean.setMsg(msgEvent.getMsgBody());
                    dataBean.setIsNotDisturb(0);
                    dataBean.setSex(result.getData().getSex());
                    dataBean.setPositionName(result.getData().getPositionId());
                    dataBean.setCompanyName(result.getData().getCompanyName());
                    dataBean.setMsgLogo("1");
                    list.add(0, dataBean);
                    messageListAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showShortToast(getContext(), result.getMsg());
                }
            }


        });
    }

    /**
     * 获取最最近会话列表
     */
    private void getRecentlyMsg() {
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverJID", "5stu_" + id);
        HttpClient.post(this, Api.FIND_RECENTLY_MSG, map, new CallBack<RecentlyMsgBean>() {
            @Override
            public void onSuccess(RecentlyMsgBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    list.clear();
                    list.addAll(result.getData());
                    sort();
                    messageListAdapter.notifyDataSetChanged();
                }
                if(list.size()>0){
                    rlHint.setVisibility(View.GONE);
                }else{
                    rlHint.setVisibility(View.VISIBLE);
                }
            }


        });
    }

    /**
     * 最近会话列表排序
     */
    private void sort() {
        //对集合经行排序
        Collections.sort(list, new Comparator<RecentlyMsgBean.DataBean>() {
            @Override
            public int compare(RecentlyMsgBean.DataBean bean, RecentlyMsgBean.DataBean bean1) {
                if (bean.getCreateTime() == null) {
                    return 1;
                }
                if (bean1.getCreateTime() == null) {
                    return 1;
                }
                long time = Long.parseLong(bean.getCreateTime());
                long time1 = Long.parseLong(bean1.getCreateTime());
                if (time > time1) {
                    return -1;
                } else if (time < time1) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });
        Logger.d("数据排序完成:" + list.size());
    }
}

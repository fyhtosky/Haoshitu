package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.EvaluationDetailPersonBean;
import com.sj.yinjiaoyun.shituwang.bean.NoticeListBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.bean.StudyDetailBean;
import com.sj.yinjiaoyun.shituwang.event.InfoEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

public class NoticeActivity extends AppCompatActivity implements OnLoadMoreListener {

    @BindView(R.id.rl_read)
    RelativeLayout rlRead;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    //登录的id
    private int id;
    //查询条数
    private int row = 20;
    //Im接受者的id
    private String IM_ID = "";
    //对象转换成json
    private Gson gson = new Gson();
    //数据源
    private List<NoticeListBean.DataBean.RowsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        loginId = PreferencesUtils.getSharePreInt(NoticeActivity.this, Const.LOGINID);
        id = PreferencesUtils.getSharePreInt(NoticeActivity.this, Const.ID);
        if (loginId == 1) {
            IM_ID = "5stu_" + id;
        } else if (loginId == 2) {
            IM_ID = "5tch_" + id;
        }
        //注册监听器
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //设置布局管理器
        swipeTarget.setLayoutManager(new LinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
        //画分割线
//        swipeTarget.addItemDecoration(new GrayDecoration());
        swipeTarget.setAdapter(new BaseQuickAdapter<NoticeListBean.DataBean.RowsBean>(R.layout.notice_list_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final NoticeListBean.DataBean.RowsBean rowsBean) {
                //展示消息类型
                switch (rowsBean.getType()) {
                    case 0:
                        baseViewHolder.setText(R.id.tv_type, "面试");
                        break;
                    case 1:
                        baseViewHolder.setText(R.id.tv_type, "评价");
                        break;
                    case 2:
                        baseViewHolder.setText(R.id.tv_type, "更换导师");
                        break;
                    case 3:
                        baseViewHolder.setText(R.id.tv_type, "禁用账号");
                        break;
                }
                //展示时间
                baseViewHolder.setText(R.id.tv_time, TimeRender.FriendlyDate(rowsBean.getCreateTime()));
                //标识未读
                if (rowsBean.getHasRead() == 0) {
                    //未读
                    baseViewHolder.setVisible(R.id.iv_notice, true);
                } else {
                    //已读
                    baseViewHolder.setVisible(R.id.iv_notice, false);
                }
                //展示消息
                baseViewHolder.setText(R.id.tv_content, rowsBean.getMsg());
                //添加点击事件
                baseViewHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rowsBean.getHasRead() == 0) {
                            //通知标记已读
                            UpdateNoticeMsg(String.valueOf(rowsBean.getId()), false, null);
                        }
                        //展示消息类型
                        switch (rowsBean.getType()) {
                            case 0:
                            case 2:
                                //跳转面试详情
                                if (loginId == 1) {
                                    InterviewInvitationActivity.StartActivity(NoticeActivity.this, rowsBean.getBusinessId());
                                } else {
                                    getStudyDeatil(rowsBean.getBusinessId());
                                }
                                break;
                            case 1:
                                findEva(rowsBean.getBusinessId());
                                break;
                        }

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        findNoticeList(false);
    }


    /**
     * 获取通知的列表
     *
     * @param isLoadMore
     */
    private void findNoticeList(final boolean isLoadMore) {
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverId", String.valueOf(id));
        if (loginId == 1) {
            map.put("receiverType", "0");
        } else if (loginId == 2) {
            map.put("receiverType", "1");
        }
        map.put("rows", String.valueOf(row));
        if (isLoadMore) {
            if (list.size() > 0) {
                map.put("createTimeStr", String.valueOf(list.get(list.size() - 1).getCreateTime()));
            }
        }
        HttpClient.post(this, Api.FIND_NOTICE_LIST, map, new CallBack<NoticeListBean>() {
            @Override
            public void onSuccess(NoticeListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result == null) {
                    return;
                }
                llNet.setVisibility(View.GONE);
                if (!isLoadMore) {
                    list.clear();
                }
                if (result.getData() != null && result.getData().getRows() != null) {
                    list.addAll(result.getData().getRows());
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getHasRead() == 0) {
                            rlRead.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    swipeTarget.getAdapter().notifyDataSetChanged();
                }
                if (list.size() > 0) {
                    rlHint.setVisibility(View.GONE);
                } else {
                    rlHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                swipeToLoadLayout.setLoadingMore(false);
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    if (list.size() == 0) {
                        llNet.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 清除未读通知
     *
     * @param msgId
     * @param isAll
     * @param idJson
     */
    private void UpdateNoticeMsg(String msgId, final boolean isAll, String idJson) {
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverId", String.valueOf(id));
        if (loginId == 1) {
            map.put("receiverType", "0");
        } else if (loginId == 2) {
            map.put("receiverType", "1");
        }
        if (isAll) {
            map.put("idsJSON", idJson);
        } else {
            map.put("Id", msgId);
        }
        HttpClient.post(this, Api.UPDATE_NOTICE_MSG, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }

                if (!result.isOk()) {
                    ToastUtil.showLongToast(NoticeActivity.this, result.getMsg());
                } else {
                    if (isAll) {
                        rlRead.setVisibility(View.GONE);
                        for (int i=0;i<list.size();i++){
                                list.get(i).setHasRead(1);
                        }
                    }else {
                        for (int i=0;i<list.size();i++){
                            if(list.get(i).getReceiverId()==id){
                                list.get(i).setHasRead(1);
                            }
                        }
                    }
                    swipeTarget.getAdapter().notifyDataSetChanged();

                }
            }
        });
    }

    /**
     * 获取面试详情的状态码
     *
     * @param businessId
     */
    private void getStudyDeatil(final int businessId) {
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
            return;
        }
        int searchBy = 0;
        if (loginId == 1) {
            searchBy = 1;
        } else if (loginId == 2) {
            searchBy = 0;
        }
        String params = "?id=" + String.valueOf(businessId) + "&searchBy=" + String.valueOf(searchBy);
        HttpClient.get(this, Api.GET_STUDY_DEATIL + params, new CallBack<StudyDetailBean>() {
            @Override
            public void onSuccess(StudyDetailBean result) {
                if (result == null) {

                    return;
                }
                if (result.getStatus() == 200) {
                    int currentStatus = result.getData().getCurrentStatus();
                    if (currentStatus == 100) {
                        TachterInterviewActivity.StartActivity(NoticeActivity.this, businessId);
                    } else if (currentStatus == 101 || currentStatus == 102 || currentStatus == 103 || currentStatus == 104) {
                        IterviewActivity.StartActivity(NoticeActivity.this, businessId);
                    } else {
                        InterviewDeatilActivity.StartActivity(NoticeActivity.this, businessId);
                    }
                }
            }
        });
    }

    /**
     * 获取被评价人的信息
     */
    private void findEva(int businessId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(businessId));
//        0:老师评价 ；1：学生评价
        if (loginId == 1) {
            map.put("isTch", "0");
        } else {
            map.put("isTch", "1");
        }
        HttpClient.post(this, Api.FIND_EVA, map, new CallBack<EvaluationDetailPersonBean>() {
            @Override
            public void onSuccess(EvaluationDetailPersonBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    String data = new Gson().toJson(result.getData());
                    StuEvaBean.DataBean.RowsBean rowsBean = gson.fromJson(data, StuEvaBean.DataBean.RowsBean.class);
                    if (loginId == 1) {
                        EvaluationListActivity.StartActivity(NoticeActivity.this, rowsBean);
                    } else {
                        StuEvaluationListActivity.StartActivity(NoticeActivity.this, rowsBean);
                    }
                }
            }

        });
    }

    @Override
    public void onLoadMore() {
        row += 10;
        findNoticeList(true);
    }


    @OnClick({R.id.rl_back, R.id.rl_read, R.id.bt_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //表示全部已读
            case R.id.rl_read:
                List<String> stringList = new ArrayList<>();
                stringList.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getHasRead() == 0) {
                        stringList.add(String.valueOf(list.get(i).getId()));
                    }
                }
                if (stringList.size() > 0) {
                    UpdateNoticeMsg("", true, new Gson().toJson(stringList));
                    findNoticeList(false);
                }
                break;
            case R.id.bt_reload:
                findNoticeList(false);
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
     *
     * @param infoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(InfoEvent infoEvent) {
        if (Message.Type.chat == infoEvent.getType()) {
            if (!"C01".equals(infoEvent.getInfoType())) {
                return;
            }
            if (!IM_ID.equals(infoEvent.getTo())) {
                return;
            }
            Logger.d("NoticeActivity:" + infoEvent.getMsgBody() + "  接收者TO:" + infoEvent.getTo() + "  发送者FROM:" + infoEvent.getFrom());
            //将xmpp协议发送消息的转换成JavaBean对象
            NoticeListBean.DataBean.RowsBean rowsBean = gson.fromJson(infoEvent.getMsgBody(), NoticeListBean.DataBean.RowsBean.class);
            list.add(rowsBean);
            swipeTarget.getAdapter().notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

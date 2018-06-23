package com.sj.yinjiaoyun.shituwang.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.InterviewDeatilActivity;
import com.sj.yinjiaoyun.shituwang.activity.IterviewActivity;
import com.sj.yinjiaoyun.shituwang.activity.StudentResumeActivity;
import com.sj.yinjiaoyun.shituwang.activity.TachterInterviewActivity;
import com.sj.yinjiaoyun.shituwang.activity.TeachterRecordActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ChatMsgBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.bean.StudyDetailBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.event.AdapterSendMsgEvent;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DocManager;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/8/7.
 */
public class TeachterChatAdapter extends BaseAdapter {
    private Activity context;
    private List<ChatMsgBean.DataBean.RowsBean> list;
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //学生档案的信息
    private StudentRecordBean.DataBean stuDataBean;
    private SparseArray<View> sparseArray=new SparseArray<>();
    private int id;
    //数据交互的 面试详情对象
    private StudyDetailBean detailBean;
    //自己
    private String receiver;

    //教育经历的集合
    private List<StudentRecordBean.DataBean.ResumesBean> educationList = new ArrayList<>();
    //期望工作的集合
    private List<StudentRecordBean.DataBean.ResumesBean> expectList = new ArrayList<>();
    //下载协议的管理类
    private DocManager docManager;

    public TeachterChatAdapter(Activity context,  List<ChatMsgBean.DataBean.RowsBean> list,
                               TeacherDetailBean.DataBean dataBean, StudentRecordBean.DataBean stuDataBean) {
        this.context = context;
        this.list = list;
        this.dataBean = dataBean;
        this.stuDataBean = stuDataBean;
        init();
    }

    private void init() {
        docManager=new DocManager(context);
        id = PreferencesUtils.getSharePreInt(context, Const.ID);
        receiver = "5tch_" + id;
        educationList.clear();
        //获取教育经历的集合
        for (int i = 0; i < stuDataBean.getResumes().size(); i++) {
            //获取期望工作的集合
            if (stuDataBean.getResumes().get(i).getResumeType() == 1) {
                expectList.add(stuDataBean.getResumes().get(i));
            }
            if (stuDataBean.getResumes().get(i).getResumeType() == 2) {
                educationList.add(stuDataBean.getResumes().get(i));
            }
        }
        sort();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public ChatMsgBean.DataBean.RowsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TeachterChatHolder holder;
        if (sparseArray.get(position) == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.teachter_chat_item, parent, false);
            holder = new TeachterChatHolder(convertView);
            sparseArray.put(position, convertView);
            convertView.setTag(holder);
        } else {
            convertView = sparseArray.get(position);
            holder = (TeachterChatHolder) convertView.getTag();
        }
        final ChatMsgBean.DataBean.RowsBean rowsBean = list.get(position);
        /**
         * 加载数据
         */
        //设置默认状态
        holder.chatTime.setVisibility(View.GONE);
        holder.item.setVisibility(View.GONE);
        holder.chartToContainer.setVisibility(View.GONE);
        holder.chartFromContainer.setVisibility(View.GONE);
        holder.llCard.setVisibility(View.GONE);
        holder.tvLabelState.setVisibility(View.GONE);
        holder.rlAgree.setVisibility(View.GONE);
        holder.tvLabelState.setTextColor(Color.WHITE);
        holder.tvExchange.setText("互换");
        holder.tvLabelState.getBackground().setCallback(null);
        System.gc();
        //显示聊天创建的时间(隔5秒钟显示一次)
        if (position == 0) {
            holder.chatTime.setVisibility(View.VISIBLE);
            holder.chatTime.setText(TimeRender.FormatChat(rowsBean.getCreateTime()));
        } else {
            if (rowsBean.getCreateTime() - list.get(position - 1).getCreateTime() >= 300000) {
                holder.chatTime.setVisibility(View.VISIBLE);
                holder.chatTime.setText(TimeRender.FormatChat(rowsBean.getCreateTime()));
            } else {
                holder.chatTime.setVisibility(View.GONE);
            }
        }
        notifyDataSetChanged();
        //展示消息的类型
        String msg = "";
        String type = "";
        //展示职位和公司
        String post = "";
        String company = "";
        Map<String, String> mapstr = null;
        if (rowsBean.getMsg() != null) {
            mapstr = new Gson().fromJson(rowsBean.getMsg(), new TypeToken<HashMap<String, String>>() {
            }.getType());
            type = mapstr.get("type");
        }
        final Map<String, String> finalMapstr = mapstr;
        /**
         * 学生操作的状态
         */
        if (!receiver.equals(rowsBean.getSender())) {
            switch (type) {
                case "S01":
                    /**
                     * 一般信息
                     */
                    holder.chartToContainer.setVisibility(View.GONE);//隐藏右侧布局
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (stuDataBean.getHead() != null && !TextUtils.isEmpty(stuDataBean.getHead())) {
                        PicassoUtils.LoadImg(context, stuDataBean.getHead(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.wukong, holder.fromIcon);
                    }
                    assert mapstr != null;
                    msg = mapstr.get("msg");
                    holder.fromContent.setText(msg);
                    break;
                case "S02":
                    /**
                     * 学生发送的跟师请求(发送卡片和生产跟师学习记录)
                     * 学生卡片
                     */
                    holder.rlMy.setVisibility(View.VISIBLE);
                    //展示学生档案信息
                    showRecord(holder);
                    break;

                case "S10":
                    /**
                     * 学生发送的交换电话请求
                     * 请求交换电话的卡片
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (stuDataBean.getHead() != null && !TextUtils.isEmpty(stuDataBean.getHead())) {
                        PicassoUtils.LoadImg(context, stuDataBean.getHead(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.wukong, holder.fromIcon);
                    }
                    msg = "我想要和您交换手机号,直接电话联系，你是否同意。";
                    holder.tvCardInfo.setText(msg);
                    //0待处理状态 1已接受 2已拒绝
                    if ("1".equals(rowsBean.getStatus())) {
                        //已接受
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                        holder.btRefused.setText("已接受");
                    } else if ("2".equals(rowsBean.getStatus())) {
                        //已拒绝
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                    } else {
                        //待处理
                        //拒绝交换电话的请求
                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T11"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //接受交换电话的请求
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T12"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "S11":
                    /**
                     * 学生拒绝交换电话；
                     */
                    msg = "对方拒绝了您的交换电话请求 ";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S12":
                    /**
                     * 学生接受交换电话
                     * 学生**的手机号：***
                     */
                    msg = stuDataBean.getRealName() + "的手机号：" + (stuDataBean.getMobile()==null ?"":stuDataBean.getMobile());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_phone_exchange);
                    break;
                case "S20":
                    /**
                     * 学生交换微信请求
                     * 请求交换微信的卡片
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (stuDataBean.getHead() != null && !TextUtils.isEmpty(stuDataBean.getHead())) {
                        PicassoUtils.LoadImg(context, stuDataBean.getHead(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.wukong, holder.fromIcon);
                    }
                    msg = "我想要和您交换微信,直接微信联系，你是否同意。";
                    holder.tvCardInfo.setText(msg);
                    //0待处理状态 1已接受 2已拒绝
                    if ("1".equals(rowsBean.getStatus())) {
                        //已接受
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                        holder.btRefused.setText("已接受");
                    } else if ("2".equals(rowsBean.getStatus())) {
                        //已拒绝
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                    } else {
                        //待处理
                        //老师拒绝交换微信的请求
                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T21"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //老师接受交换微信
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T22"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "S21":
                    /**
                     * 学生拒绝交换微信；
                     */
                    msg = "对方拒绝了您交换微信的请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S22":
                    /**
                     * 学生接受老师交换微信请求
                     * 学生**的微信号：***
                     */
                    msg = stuDataBean.getRealName() + "的微信号：" + (stuDataBean.getWeChat()==null ? "":stuDataBean.getWeChat());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_wechat_exchange);
                    break;
                case "S30":
                    /**
                     * 学生发送的交换协议请求
                     * 请求交换协议的卡片
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (stuDataBean.getHead() != null && !TextUtils.isEmpty(stuDataBean.getHead())) {
                        PicassoUtils.LoadImg(context, stuDataBean.getHead(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.wukong, holder.fromIcon);
                    }
                    msg = "我想要和您交换协议,校企须知，您是否同意。";
                    holder.tvCardInfo.setText(msg);
                    //0待处理状态 1已接受 2已拒绝
                    if ("1".equals(rowsBean.getStatus())) {
                        //已接受
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                        holder.btRefused.setText("已接受");
                    } else if ("2".equals(rowsBean.getStatus())) {
                        //已拒绝
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                    } else {
                        //待处理
                        //老师拒绝交换协议
                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T31"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //老师接受交换协议
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "T32"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "S31":
                    /**
                     * 学生拒绝老师交换协议请求
                     */
                    msg = "对方拒绝了您的交换协议请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S32":
                    /**
                     * 学生接受老师交换协议请求
                     * 对方接受了您的交换协议请求
                     *查看协议卡片
                     */
                    msg = "交换协议：" + dataBean.getCompanyName();
                    holder.rlAgree.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.tvAgree.setText(msg);
                    holder.btAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            context.startActivity(new Intent(context, AgreementActivity.class));
                            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                                return;
                            }
                            docManager.getAgrement(stuDataBean.getSchoolId(),dataBean.getCompanyId());
                        }
                    });
                    break;
                case "S41":
                    msg = "对方接受了面试邀请";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S42":
                    msg = "对方拒绝了面试邀请";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                default:
                    holder.tvLabelState.setVisibility(View.GONE);
                    holder.fromContent.setText("");
                    break;
            }
            /**
             *老师的操作的状态
             */
        } else {
            //显示用户的LOGo
            if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.toIcon);
            } else {
                PicassoUtils.LoadImg(context, R.drawable.master, holder.toIcon);
            }
            switch (type) {
                case "T01":
                    holder.chartToContainer.setVisibility(View.VISIBLE);
                    holder.chartFromContainer.setVisibility(View.GONE);
                    assert mapstr != null;
                    msg = mapstr.get("msg");
                    holder.toContent.setText(msg);
                    break;
                case "T02":
                    /**
                     * 老师感兴趣发送的请求；
                     * 显示老师的档案
                     */
                    assert mapstr != null;
                    msg = mapstr.get("msg");
                    holder.item.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.circleView);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.circleView);
                    }
                    //展示消息名字
                    if (dataBean.getRealName() != null && !TextUtils.isEmpty(dataBean.getRealName())) {
                        holder.tvName.setText(dataBean.getRealName());
                    } else {
                        holder.tvName.setText("");
                    }
                    //展示公司和职位
                    post = dataBean.getPositionId();
                    company = dataBean.getCompanyName();
                    if (!TextUtils.isEmpty(post) && !TextUtils.isEmpty(company)) {
                        holder.tvPost.setText(post + "  " + company);
                    } else if (!TextUtils.isEmpty(post)) {
                        holder.tvPost.setText(post);
                    } else if (!TextUtils.isEmpty(company)) {
                        holder.tvPost.setText(company);
                    } else {
                        holder.tvPost.setText("");
                    }
                    holder.tvMessageState.setText(msg);
                    //跳转老师档案的界面
                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //网络状态判断
                            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                                return;
                            }
                            context.startActivity(new Intent(context, TeachterRecordActivity.class));

                        }
                    });
                    break;
                case "T03":
                    /**
                     * 老师发送的不适合
                     * ***将你设置为不合适
                     */
                    msg = "您将"+stuDataBean.getRealName() + "设置为不适合";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T04":
                    /**
                     * 老师发送取消不适合
                     * ***取消了对你不合适的设置
                     */
                    msg = "您取消了"+stuDataBean.getRealName() + "不合适的设置";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T10":
                    msg = "交换电话请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T11":
                    msg = "您拒绝了对方的交换电话请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T12":
                    msg = "你接受了对方的交换电话请求";
                    msg = stuDataBean.getRealName() + "的手机号：" + (stuDataBean.getMobile() == null ? "" : stuDataBean.getMobile());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_phone_exchange);
                    break;
                case "T20":
                    msg = "交换微信请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T21":
                    msg = "您拒绝了对方的交换微信请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T22":
                    msg = "你接受了对方的交换微信请求";
                    msg = stuDataBean.getRealName() + "的微信：" + (stuDataBean.getWeChat() == null ? "" : stuDataBean.getWeChat());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_wechat_exchange);
                    break;
                case "T30":
                    msg = "交换协议请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T31":
                    msg = "您拒绝了对方的交换协议请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T32":
                    msg = "师徒制协议";
                    msg = "交换协议：" + dataBean.getCompanyName();
                    holder.rlAgree.setVisibility(View.VISIBLE);
                    holder.tvAgree.setText(msg);
                    holder.btAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            context.startActivity(new Intent(context, AgreementActivity.class));
                            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                                return;
                            }
                            docManager.getAgrement(stuDataBean.getSchoolId(),dataBean.getCompanyId());
                        }
                    });
                    break;
                case "T41":
                    msg = "面试邀请已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    msg = "面试邀请：" + dataBean.getCompanyName();
                    holder.rlAgree.setVisibility(View.VISIBLE);
                    holder.tvAgree.setText(msg);
                    //跳转面试邀请的界面
                    holder.btAgree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                getStudyDeatil(finalMapstr);
//                                IterviewActivity.StartActivity(context,Integer.parseInt(finalMapstr.get("businessId")));

                            }
                        });


                    break;
                case "T42":
                    msg = "您取消了面试邀请";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;

            }
            System.gc();
        }
        return convertView;
    }
    /**
     * 屏蔽listitem的所有事件
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


    /**
     * 展示我的档案
     * @param holder
     */
    private void showRecord(TeachterChatHolder holder) {
        if (stuDataBean == null) {
            return;
        }
        /**
         * 基本信息
         */

        //展示学生的头像
        if (stuDataBean.getHead() != null && !"".equals(stuDataBean.getHead())) {
            PicassoUtils.LoadPathCorners(context, stuDataBean.getHead(), 70, 70, holder.circleViewMy);
        } else {
            PicassoUtils.LoadCorners(context, R.drawable.wukong, 70, 70, holder.circleViewMy);
        }
        //展示姓名
        if (stuDataBean.getRealName() != null) {
            holder.tvNameMy.setText(stuDataBean.getRealName());

        }
        //显示性别
        if (stuDataBean.getSex() == 1) {
            holder.ivSex.setImageResource(R.mipmap.male);
        } else {
            holder.ivSex.setImageResource(R.mipmap.woman);
        }
        //展示年龄 地址 学历
        String education ="";
        //地址赋值
        String addres="";
        if(educationList.size()>0){
            Map<String, String> map = new Gson().fromJson(educationList.get(educationList.size() - 1).getResume(), new TypeToken<HashMap<String, String>>() {
            }.getType());
            //展示学历
            String code = map.get("diplomas");
            if (MyApplication.map != null) {
                if (!TextUtils.isEmpty(code)) {
                    String[] educations = code.split(",");
                    education=MyApplication.map.get(educations[educations.length - 1]).getName();
                    //学历
                    if(!TextUtils.isEmpty(education)){
                        holder.tvEducation.setText(education);
                    }
                }
            }

        }
        //展示地区
        if(stuDataBean.getAddressNow()!=null && !TextUtils.isEmpty(stuDataBean.getAddressNow())){
            addres=stuDataBean.getAddressNow().split(",")[0];
            holder.tvAddress.setText("地区："+addres);
        }


        /**
         * 期望工作
         */
        if(expectList.size()>0){
            String resume=expectList.get(expectList.size()-1).getResume();
            if(resume!=null && !TextUtils.isEmpty(resume)){
                Map<String,String> mapstr=new Gson().fromJson(resume, new TypeToken<HashMap<String,String>>(){}.getType());
                if(MyApplication.map!=null){
                    String  expectPosition=mapstr.get("expectPosition");
                    String expectSalary=mapstr.get("expectSalary");

                    if(!TextUtils.isEmpty(expectPosition)){
                        //期望职业
                        String [] position=expectPosition.split(",");
                        holder.tvPostMy.setText("期望："+MyApplication.map.get(position[position.length-1]).getName());
                    }
                    if(!TextUtils.isEmpty(expectSalary)){
                        //期望薪资
                        String []salary=expectSalary.split(",");
                        holder.tvSalaryMy.setText(MyApplication.map.get(salary[salary.length-1]).getName());
                    }
                }
            }
        }
        //评价
        if(stuDataBean.getEvaluationLabels()!=null&& !TextUtils.isEmpty(stuDataBean.getEvaluationLabels())){
            holder.tagTrait.setVisibility(View.VISIBLE);
            String labels=stuDataBean.getEvaluationLabels().substring(1,stuDataBean.getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            holder.tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(context)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            holder.tagTrait.setVisibility(View.GONE);
        }
        //展示描述
        if(stuDataBean.getDescription()!=null && !TextUtils.isEmpty(stuDataBean.getDescription())){
            holder.tvDescription.setText(stuDataBean.getDescription());
        }else{
            holder.tvDescription.setText("");
        }
        holder.rlMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                    return;
                }
                StudentResumeActivity.StartActivity(context,stuDataBean.getId());
            }
        });
    }
    /**
     * 获取面试详情的状态码
     * @param finalMapstr
     */
    private void getStudyDeatil(final Map<String, String> finalMapstr) {
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        String params = "?id=" + finalMapstr.get("businessId") + "&searchBy=0";
        HttpClient.get(this, Api.GET_STUDY_DEATIL + params, new CallBack<StudyDetailBean>() {
            @Override
            public void onSuccess(StudyDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    int currentStatus=result.getData().getCurrentStatus();
                    if(currentStatus==100){
                        TachterInterviewActivity.StartActivity(context,Integer.parseInt(finalMapstr.get("businessId")));
                    }else if(currentStatus==101 || currentStatus==102 || currentStatus==103 || currentStatus==104){
                        IterviewActivity.StartActivity(context,Integer.parseInt(finalMapstr.get("businessId")));
                    }else {
                        InterviewDeatilActivity.StartActivity(context,Integer.parseInt(finalMapstr.get("businessId")));
                    }
                }
            }
        });
    }
    /**
     * 教育经历按照时间排序
     */
    private void sort() {
        //对集合经行排序
        Collections.sort(educationList, new Comparator<StudentRecordBean.DataBean.ResumesBean>() {
            @Override
            public int compare(StudentRecordBean.DataBean.ResumesBean bean, StudentRecordBean.DataBean.ResumesBean bean1) {
                long time = bean.getUpdateTime();
                long time1 = bean1.getUpdateTime();
                if (time > time1) {
                    return 1;
                } else if (time < time1) {
                    return -1;
                } else {
                    return 0;
                }

            }
        });
        Logger.d("数据排序完成:" + educationList.toString());
    }
    class TeachterChatHolder {
        @BindView(R.id.chat_time)
        TextView chatTime;
        @BindView(R.id.circleView_my)
        CircleImageView circleViewMy;
        @BindView(R.id.rl_title_my)
        RelativeLayout rlTitleMy;
        @BindView(R.id.tv_name_my)
        TextView tvNameMy;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.tv_education)
        TextView tvEducation;
        @BindView(R.id.tv_salary_my)
        TextView tvSalaryMy;
        @BindView(R.id.tv_post_my)
        TextView tvPostMy;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tag_trait)
        TagFlowLayout tagTrait;
        @BindView(R.id.iv_dirvider)
        ImageView ivDirvider;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.rl_my)
        RelativeLayout rlMy;
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.rl_title)
        RelativeLayout rlTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_salary)
        TextView tvSalary;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_message_state)
        TextView tvMessageState;
        @BindView(R.id.item)
        LinearLayout item;
        @BindView(R.id.tv_label_state)
        TextView tvLabelState;
        @BindView(R.id.tv_agree)
        TextView tvAgree;
        @BindView(R.id.bt_agree)
        Button btAgree;
        @BindView(R.id.rl_agree)
        RelativeLayout rlAgree;
        @BindView(R.id.from_icon)
        CircleImageView fromIcon;
        @BindView(R.id.from_content)
        TextView fromContent;
        @BindView(R.id.tv_card_info)
        TextView tvCardInfo;
        @BindView(R.id.tv_refuse)
        TextView tvRefuse;
        @BindView(R.id.tv_exchange)
        TextView tvExchange;
        @BindView(R.id.ll_card_button)
        LinearLayout llCardButton;
        @BindView(R.id.bt_refused)
        Button btRefused;
        @BindView(R.id.ll_card)
        LinearLayout llCard;
        @BindView(R.id.chart_from_container)
        LinearLayout chartFromContainer;
        @BindView(R.id.to_icon)
        CircleImageView toIcon;
        @BindView(R.id.to_content)
        TextView toContent;
        @BindView(R.id.chart_to_container)
        RelativeLayout chartToContainer;
        @BindView(R.id.rl_chat)
        RelativeLayout rlChat;

        TeachterChatHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

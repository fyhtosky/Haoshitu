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
import com.sj.yinjiaoyun.shituwang.activity.InterviewInvitationActivity;
import com.sj.yinjiaoyun.shituwang.activity.MyRecordActivity;
import com.sj.yinjiaoyun.shituwang.activity.TeacherDetailActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ChatMsgBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
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
import com.sj.yinjiaoyun.shituwang.utils.PopReason;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

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
 * Created by ${沈军 961784535@qq.com} on 2017/7/24.
 * 聊天记录的适配器
 */
public class ChatRecordAdapter extends BaseAdapter {

    private Activity context;
    private List<ChatMsgBean.DataBean.RowsBean> list;
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //学生档案的信息
    private StudentRecordBean.DataBean stuDataBean;
    private SparseArray<View> sparseArray=new SparseArray<>();
    //原因的选择器
    private PopReason popReason;
    private List<String> stringList = Arrays.asList("实习过远","方向不对口","薪资过低","已有实习师父");
    private View iterview;
    private int id;
    //数据交互的 面试详情对象
    private StudyDetailBean detailBean;
    //自己
    private String receiver;
    private String receiverImg;
    private String receiverName;
    //教育经历的集合
    private List<StudentRecordBean.DataBean.ResumesBean> educationList = new ArrayList<>();
    //期望工作的集合
    private List<StudentRecordBean.DataBean.ResumesBean> expectList = new ArrayList<>();
    //下载协议的管理类
    private DocManager docManager;

    public ChatRecordAdapter(Activity context, View iterview, List<ChatMsgBean.DataBean.RowsBean> list,
                             TeacherDetailBean.DataBean dataBean,StudentRecordBean.DataBean stuDataBean) {
        this.context = context;
        this.iterview = iterview;
        this.list = list;
        this.dataBean = dataBean;
        this.stuDataBean=stuDataBean;
        popReason = new PopReason(context);
        init();
    }


    private void init() {
        docManager=new DocManager(context);
        id = PreferencesUtils.getSharePreInt(context, Const.ID);
        receiver = "5stu_" + id;
        receiverImg = PreferencesUtils.getSharePreStr(context, Const.USERiMG);
        receiverName = PreferencesUtils.getSharePreStr(context, Const.NAME);
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
        final ChatHolder holder;
        if (sparseArray.get(position) == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
            holder = new ChatHolder(convertView);
            sparseArray.put(position, convertView);
            convertView.setTag(holder);
        } else {
            convertView = sparseArray.get(position);
            holder = (ChatHolder) convertView.getTag();
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
        /**
         * 老师操作的状态
         */
        if (!receiver.equals(rowsBean.getSender())) {
            final Map<String, String> finalMapstr = mapstr;
            switch (type) {
                case "T01":
                    /**
                     * 一般信息
                     */
                    holder.chartToContainer.setVisibility(View.GONE);//隐藏右侧布局
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.fromIcon);
                    }
                    msg = mapstr != null ? mapstr.get("msg") : null;
                    if(msg==null){
                        msg="";
                    }
                    holder.fromContent.setText(msg);
                    break;
                case "T02":
                    /**
                     * 老师感兴趣发送的请求；
                     */
                    msg = mapstr != null ? mapstr.get("msg") : null;
                    if(msg==null){
                        msg="";
                    }
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
                    //跳转老师详情的界面
                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转界面
                            //网络状态判断
                            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                                return;
                            }
                            Intent intent = new Intent(context, TeacherDetailActivity.class);
                            intent.putExtra("id", dataBean.getId());
                            context.startActivity(intent);
                        }
                    });
                    break;
                case "T03":
                    /**
                     * 老师发送的不适合
                     * ***将你设置为不合适
                     */
                    msg = dataBean.getRealName() + "将你设置为不适合";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T04":
                    /**
                     * 老师发送取消不适合
                     * ***取消了对你不合适的设置
                     */
                    msg = dataBean.getRealName() + "取消了对你不合适的设置";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T10":
                    /**
                     * 老师发送的交换电话请求
                     * 请求交换电话的卡片
                     */

                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.fromIcon);
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
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S11"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //接受交换电话的请求
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S12"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "T11":
                    /**
                     * 老师拒绝学生交换电话请求
                     */
                    msg = "对方拒绝了您的交换电话请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T12":
                    /**
                     * 老师接受学生交换电话请求
                     * 老师**的手机号：***
                     */
                    msg = dataBean.getRealName() + "的手机号：" + (dataBean.getMobile()==null ?"":dataBean.getMobile());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_phone_exchange);
                    break;
                case "T20":
                    /**
                     * 老师接受学生交换微信请求
                     * 请求交换微信的卡片
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.fromIcon);
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
                        //学生拒绝交换微信的请求
                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S21"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //学生接受交换微信
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S22"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "T21":
                    /**
                     * 老师拒绝学生交换微信请求
                     */
                    msg = "对方拒绝了您交换微信的请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T22":
                    /**
                     * 老师接受学生交换微信请求
                     * 老师**的微信号：***
                     */
                    msg = dataBean.getRealName() + "的微信号：" + (dataBean.getWeChat()==null ?"":dataBean.getWeChat());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_wechat_exchange);
                    break;
                case "T30":
                    /**
                     * 老师发送的交换协议请求
                     * 请求交换协议的卡片
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.fromContent.setVisibility(View.GONE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.fromIcon);
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
                        //学生拒绝交换协议
                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S31"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);

                            }
                        });
                        //学生接受交换协议
                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), "S32"));
                                holder.llCardButton.setVisibility(View.GONE);
                                holder.btRefused.setVisibility(View.VISIBLE);
                                holder.btRefused.setText("已接受");

                            }
                        });
                    }
                    notifyDataSetChanged();
                    break;
                case "T31":
                    /**
                     * 老师拒绝老学生换协议请求
                     */
                    msg = "对方拒绝了您交换协议的请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "T32":
                    /**
                     * 老师接受老学生换协议请求
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
                case "T41":
                    /**
                     * 老师邀请面试
                     * 面试邀请卡片（接受，拒绝）
                     *（更新上一条面试邀请卡片的状态）
                     */
                    holder.chartFromContainer.setVisibility(View.VISIBLE);
                    holder.llCard.setVisibility(View.VISIBLE);
                    msg = "面试邀请:" + dataBean.getCompanyName();
                    //显示用户的LOGo
                    if (dataBean.getImgUrl() != null && !TextUtils.isEmpty(dataBean.getImgUrl())) {
                        PicassoUtils.LoadImg(context, dataBean.getImgUrl(), holder.fromIcon);
                    } else {
                        PicassoUtils.LoadImg(context, R.drawable.master, holder.fromIcon);
                    }
                    holder.tvCardInfo.setText(msg);
                     if ("102".equals(rowsBean.getStatus())) {
                        //已拒绝
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                    } else if("104".equals(rowsBean.getStatus())){
                        //老师取消面试邀请的
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                        holder.btRefused.setText("已取消");
                    }
// else if("101".equals(rowsBean.getStatus()) || rowsBean.getStatus()==null) {
//                         //邀请状态待操作
//                        holder.tvCardInfo.setText(msg);
//                        holder.tvExchange.setText("同意");
//                        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //取消面试邀请
//                                popReason.show(iterview, stringList, new PhoneCallBack() {
//                                    @Override
//                                    public void setContent(String content) {
//                                        updateStudy(content, holder.llCardButton, holder.btRefused, finalMapstr, "S42",rowsBean);
//                                    }
//                                });
//
//                            }
//                        });
//                        //接受面试邀请
//                        holder.tvExchange.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                updateStudy("", holder.llCardButton, holder.btRefused, finalMapstr, "S41",rowsBean);
//                            }
//                        });
//                    }
                     else{
                        //学生接受面试邀请
                        //已接受
                        holder.llCardButton.setVisibility(View.GONE);
                        holder.btRefused.setVisibility(View.VISIBLE);
                        holder.btRefused.setText("查看");
                        holder.btRefused.setBackgroundColor(Color.parseColor("#24C789"));
                        //查看面试邀请
                        holder.btRefused.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert finalMapstr != null;
                                InterviewInvitationActivity.StartActivity(context, Integer.parseInt(finalMapstr.get("businessId")));
                            }
                        });

                    }

                    break;
                case "T42":
                    msg = "对方取消了面试邀请";
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
             * 学生操作的状态
             */
        } else {
            //显示用户的LOGo
            if (receiverImg != null && !TextUtils.isEmpty(receiverImg)) {
                PicassoUtils.LoadImg(context, receiverImg, holder.toIcon);
            } else {
                PicassoUtils.LoadImg(context, R.drawable.wukong, holder.toIcon);
            }
            switch (type) {
                /**
                 * 学生操作的状态
                 */
                case "S01":
                    holder.chartToContainer.setVisibility(View.VISIBLE);
                    holder.chartFromContainer.setVisibility(View.GONE);
                    assert mapstr != null;
                    msg = mapstr.get("msg");
                    holder.toContent.setText(msg);
                    break;
                case "S02":
                    msg = "我的档案";
                    holder.rlMy.setVisibility(View.VISIBLE);
                    //展示我的个人档案信息
                    showRecord(holder);
                    break;
                case "S10":
                    msg = "交换电话请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S11":
                    msg = "你拒绝了对方的交换电话请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S12":
                    msg = "你接受了对方的交换电话请求";
                    msg = dataBean.getRealName() + "的手机号：" + (dataBean.getMobile() == null ? "" : dataBean.getMobile());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_phone_exchange);
                    break;
                case "S20":
                    msg = "交换微信请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S21":
                    msg = "你拒绝了对方的交换微信请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S22":
                    msg = "你接受了对方的交换微信请求";
                    msg = dataBean.getRealName() + "的微信：" + (dataBean.getWeChat() == null ? "" : dataBean.getWeChat());
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setTextColor(Color.BLACK);
                    holder.tvLabelState.setBackgroundResource(R.mipmap.im_wechat_exchange);
                    break;
                case "S30":
                    msg = "交换协议请求已发送";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S31":
                    msg = "你拒绝了对方的交换协议请求";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S32":
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
                case "S41":
                    msg = "你接受了对方的面试邀请";
                    holder.tvLabelState.setVisibility(View.VISIBLE);
                    holder.tvLabelState.setText(msg);
                    holder.tvLabelState.setBackgroundResource(R.drawable.btn_start_gray);
                    break;
                case "S42":
                    msg = "你拒绝了对方的面试邀请";
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
     * 展示我的档案
     * @param holder
     */
    private void showRecord(ChatHolder holder) {
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
                //网络状态判断
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                    return;
                }
                context.startActivity(new Intent(context, MyRecordActivity.class));
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


    /**
     * 学生拒绝面试邀请保存取消的原因
     *
     * @param content
     * @param llCardButton
     * @param btRefused
     * @param finalMapstr
     */
    private void updateStudy(final String content, final LinearLayout llCardButton, final Button btRefused, final Map<String, String> finalMapstr, final String msgType,ChatMsgBean.DataBean.RowsBean rowsBean) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", finalMapstr.get("businessId"));
        map.put("stuId", rowsBean.getReceiver().split("_")[1]);
        map.put("tchId", rowsBean.getSender().split("_")[1]);
        map.put("interviewGoal", "1");
        //学生接受面试邀请
        if (msgType.equals("S41")) {
            map.put("optFlag", String.valueOf(1));
        } else {
            //学生拒绝面试邀请
            map.put("optFlag", String.valueOf(0));
            map.put("interviewRefuseNote", content);
        }

        HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), msgType));
                    llCardButton.setVisibility(View.GONE);
                    btRefused.setVisibility(View.VISIBLE);
                    if (msgType.equals("S41")) {
                        btRefused.setText("查看");
                        btRefused.setBackgroundColor(Color.parseColor("#24C789"));
                        //查看面试邀请
                        btRefused.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                InterviewInvitationActivity.StartActivity(context, Integer.parseInt(finalMapstr.get("businessId")));

                            }
                        });
                    }
                    notifyDataSetChanged();
                } else {
                    ToastUtil.showShortToast(context, result.getMsg());
                }
            }

        });

//        String params = "?id=" + finalMapstr.get("businessId") + "&searchBy=0";
//        HttpClient.get(this, Api.GET_STUDY_DEATIL + params, new CallBack<StudyDetailBean>() {
//            @Override
//            public void onSuccess(StudyDetailBean result) {
//                if (result == null) {
//                    return;
//                }
//                if (result.getStatus() == 200) {
//                    detailBean = result;
//                    HashMap<String, String> map = new HashMap<>();
//                    map.put("id", String.valueOf(detailBean.getData().getId()));
//                    map.put("stuId", String.valueOf(detailBean.getData().getStuId()));
//                    map.put("tchId", String.valueOf(detailBean.getData().getTchId()));
//                    map.put("interviewGoal", "1");
//                    //学生接受面试邀请
//                    if (msgType.equals("S41")) {
//                        map.put("optFlag", String.valueOf(1));
//                    } else {
//                        //学生拒绝面试邀请
//                        map.put("optFlag", String.valueOf(0));
//                        map.put("interviewRefuseNote", content);
//                    }
//
//                    HttpClient.post(this, Api.UPDATE_STUDY, map, new CallBack<ReturnBean>() {
//                        @Override
//                        public void onSuccess(ReturnBean result) {
//                            if (result == null) {
//                                return;
//                            }
//                            if (result.getStatus() == 200) {
//                                EventBus.getDefault().post(new AdapterSendMsgEvent("", finalMapstr.get("businessId"), msgType));
//                                llCardButton.setVisibility(View.GONE);
//                                btRefused.setVisibility(View.VISIBLE);
//                                if (msgType.equals("S41")) {
//                                    btRefused.setText("查看");
//                                    btRefused.setBackgroundColor(Color.parseColor("#24C789"));
//                                    //查看面试邀请
//                                    btRefused.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            InterviewInvitationActivity.StartActivity(context, Integer.parseInt(finalMapstr.get("businessId")));
//
//                                        }
//                                    });
//                                }
//                                notifyDataSetChanged();
//                            } else {
//                                ToastUtil.showShortToast(context, result.getMsg());
//                            }
//                        }
//
//                    });
//
//
//                } else {
//                    ToastUtil.showShortToast(context, result.getMsg());
//                }
//            }
//
//
//        });

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

    class ChatHolder {
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
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_post_my)
        TextView tvPostMy;
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

        ChatHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}

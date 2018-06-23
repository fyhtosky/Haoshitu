package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.StudentChatActivity;
import com.sj.yinjiaoyun.shituwang.bean.RecentlyMsgBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/20.
 */
public class StudentMessageListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RecentlyMsgBean.DataBean> list;
    //对象转换成json
    private Gson gson=new Gson();
    private TeacherDetailBean teacherDetailBean;


    public StudentMessageListAdapter(Context context, List<RecentlyMsgBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_message_list_item, parent, false);
        return new StudentMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final StudentMessageHolder messageHolder = (StudentMessageHolder) holder;
        final RecentlyMsgBean.DataBean dataBean=list.get(position);
        //显示头像
        if(dataBean.getMsgLogo()!=null&& !TextUtils.isEmpty(dataBean.getMsgLogo())){
            PicassoUtils.LoadPathCorners(context,dataBean.getMsgLogo(),messageHolder.circleView);
        }else{
            PicassoUtils.LoadCorners(context, R.drawable.master, 60, 60, messageHolder.circleView);
        }
        //展示消息名字
        if(dataBean.getMsgName()!=null && !TextUtils.isEmpty(dataBean.getMsgName())){
            messageHolder.tvName.setText(dataBean.getMsgName());
        }else{
            messageHolder.tvName.setText("");
        }
       //展示消息时间
        if(dataBean.getCreateTime()!=null && !TextUtils.isEmpty(dataBean.getCreateTime())){
            messageHolder.tvTime.setText(TimeRender.FormatString(Long.parseLong(dataBean.getCreateTime())));
        }else{
            messageHolder.tvTime.setText("");
        }
        //展示公司和职位
        String post="";
        String company="";
         post=dataBean.getPositionName();
          company=dataBean.getCompanyName();
        if(!TextUtils.isEmpty(post)&& !TextUtils.isEmpty(company)){
            messageHolder.tvPost.setText(post+"  "+company);
        }else if(!TextUtils.isEmpty(post)){
            messageHolder.tvPost.setText(post);
        }else if(!TextUtils.isEmpty(company)){
            messageHolder.tvPost.setText(company);
        }else {
            messageHolder.tvPost.setText("");
        }
       //展示消息的类型
        String msg="";
        String type="";
        if(dataBean.getMsg()!=null){
            Map<String, String> mapstr = gson.fromJson(dataBean.getMsg(), new TypeToken<HashMap<String, String>>() {
            }.getType());
            if(mapstr==null || mapstr.size()<=0){
                return;
            }
            type= mapstr.get("type");
//            S01:学生发送消息；
//            S02：学生跟师请求；
//            S10：学生交换电话请求；
//            S11：学生拒绝交换电话；
//            S12：学生接受交换电话；
//            S20: 学生交换微信请求；
//            S21：学生拒绝交换微信；
//            S22：学生接受交换微信；
//            S30: 学生交换协议请求；
//            S31：学生拒绝交换协议；
//            S32：学生接受交换协议；
//            S41：学生接受面试邀请；
//            S42：学生拒绝面试邀请；

//            T01：老师发送消息；
//            T02：老师感兴趣发送的请求；
//            T03：老师发送的不适合；
//            T10：老师交换电话请求；
//            T11：老师拒绝交换电话；
//            T12：老师接受交换电话；
//            T20: 老师交换微信请；
//            T21：老师拒绝交换微信；
//            T22：老师接受交换微信；
//            T30: 老师交换协议请求；
//            T31：老师拒绝交换协议；
//            T32：老师接受交换协议；
//            T41：老师邀请面试；
//            T42：老师取消面试
            switch (type){
                /**
                 * 学生操作的状态
                 */
                case "S01":
                    msg=mapstr.get("msg");
                    break;
                case "S02":
                    msg="我的档案";
                    break;
                case "S10":
                    msg="交换电话请求已发送";
                    break;
                case "S11":
                    msg="你拒绝了对方的交换电话请求";
                    break;
                case "S12":
                    msg="你接受了对方的交换电话请求";
                    break;
                case "S20":
                    msg="交换微信请求已发送";
                    break;
                case "S21":
                    msg="你拒绝了对方的交换微信请求";
                    break;
                case "S22":
                    msg="你接受了对方的交换微信请求";
                    break;
                case "S30":
                    msg="交换协议请求已发送";
                    break;
                case "S31":
                    msg="你拒绝了对方的交换协议请求";
                    break;
                case "S32":
                    msg="师徒制协议";
                    break;
                case "S41":
                    msg="你接受了对方的面试邀请";
                    break;
                case "S42":
                    msg="你拒绝了对方的面试邀请";
                    break;
                /**
                 * 老师操作的状态
                 */

                case  "T01":
                   msg=mapstr.get("msg");
                  break;
                case "T02":
                    msg=dataBean.getMsgName()+"的档案";
                    break;
                case  "T03":
                    msg="老师发送的不适合";
                    break;
                case "T04":
                    msg="对方取消了对你不合适的设置";
                    break;
                case  "T10":
                    msg="对方请求交换电话";
                    break;
                case  "T11":
                    msg="对方拒绝了您的交换电话请求";
                    break;
                case  "T12":
                    msg="对方接受了您的交换电话请求";
                    break;
                case  "T20":
                    msg="对方请求交换微信";
                    break;
                case  "T21":
                    msg="对方拒绝了您交换微信的请求";
                    break;
                case "T22":
                    msg="对方接受了您交换微信的请求";
                    break;
                case "T30":
                    msg="对方请求交换协议";
                    break;
                case "T31":
                    msg="对方拒绝了您交换协议的请求";
                    break;
                case "T32":
                    msg="对方接受了您的交换协议请求";
                    break;
                case "T41":
                    msg="对方给您发送了面试邀请";
                    break;
                case "T42":
                    msg="对方取消了面试邀请";
                    break;
            }

        }
        if(dataBean.getIsNotDisturb()==1){
            msg="老师将你设置了不合格";
        }
        messageHolder.tvMessageState.setText(msg);
        //展示消息数量
        if(dataBean.getMsgCount()==0){
            messageHolder.tvMessageSize.setVisibility(View.GONE);
        }else {
            messageHolder.tvMessageSize.setVisibility(View.VISIBLE);
            messageHolder.tvMessageSize.setText(""+dataBean.getMsgCount());
        }
        //添加点击事件
        messageHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTeacherDetail(dataBean);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    /**
     * 获取聊天对象的详细信息
     */
    private void getTeacherDetail(final RecentlyMsgBean.DataBean dataBean) {
        if(TextUtils.isEmpty(dataBean.getSender())){
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("id",dataBean.getSender().split("_")[1]);
        HttpClient.post(this, Api.GET_TEACHER_DEATIL, map, new CallBack<TeacherDetailBean>() {
            @Override
            public void onSuccess(TeacherDetailBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200 && result.getData() != null) {
                    teacherDetailBean=result;
                    //获取学生档案的信息
                    getStudentRecord(dataBean);
                } else {
                    ToastUtil.showShortToast(context,result.getMsg());
                }
            }


        });
    }

    /**
     * 获取学生的相关信息
     */
    private void getStudentRecord(final RecentlyMsgBean.DataBean dataBean) {
        String params = "?studentId=" + String.valueOf(dataBean.getReceiver().split("_")[1]);
        HttpClient.get(this, Api.GTE_STUDENT_RECORD + params, new CallBack<StudentRecordBean>() {
            @Override
            public void onSuccess(StudentRecordBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200  && result.getData() != null) {
                    StudentChatActivity.StartActivity(context,dataBean.getSender(),teacherDetailBean.getData(),result.getData(),String.valueOf(dataBean.getUserContactId()));
                } else {
                    ToastUtil.showShortToast(context, result.getMsg());
                }

            }

        });
    }

    class StudentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circleView)
        CircleImageView circleView;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_message_state)
        TextView tvMessageState;
        @BindView(R.id.tv_message_size)
        TextView tvMessageSize;
        @BindView(R.id.item)
        LinearLayout item;

        public StudentMessageHolder(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }
    }
}

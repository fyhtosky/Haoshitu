package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.WorkListAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.DBmsgBean;
import com.sj.yinjiaoyun.shituwang.bean.MsgBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.bean.UserContactBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.CreditScoreView;
import com.sj.yinjiaoyun.shituwang.view.MyScrollview;
import com.sj.yinjiaoyun.shituwang.view.NoScrollListView;
import com.sj.yinjiaoyun.shituwang.xmppmanager.XmppUtil;
import com.squareup.picasso.Picasso;

import org.jivesoftware.smack.XMPPException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师详情界面
 */
public class TeacherDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_teacher_ablity)
    TextView tvTeacherAblity;
    @BindView(R.id.tag_ablity)
    TagFlowLayout tagAblity;
    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.tv_skil)
    TextView tvSkil;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.rv_work)
    NoScrollListView rvWork;
    @BindView(R.id.tv_require_desc)
    TextView tvRequireDesc;
    @BindView(R.id.iv_company_img)
    ImageView ivCompanyImg;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.credit_view)
    CreditScoreView creditView;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.bt_study)
    Button btStudy;
    @BindView(R.id.scrollView)
    MyScrollview scrollView;
    @BindView(R.id.rl_net)
    RelativeLayout rlNet;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    //老师的id
    private int teacherId;
    //登录的id
    private int id;
    //雷达图所需要的数据源
    private ArrayList<String> titleList = new ArrayList<>();
    private List<Double> data = new ArrayList<>();
    //工作经历的数据源
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean> workList = new ArrayList<>();
    private WorkListAdapter workListAdapter;
    //标示收藏的
    private int isEnshrine;
    private boolean isShowLike = false;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    //是否显示
    //会话沟通的id
    private String contactId;
    //老师的基本信息
    private TeacherDetailBean.DataBean dataBean;
    //    是否跟师学习    0：不学习   ； 1：已经参与学习
    private int isStudy;
    //IM发送消息封装的实体类
    private MsgBean msgBean;
    //DB保存消息的实体类
    private DBmsgBean dBmsgBean;
    //对象转换成json
    private Gson gson = new Gson();
    private String stuId = "";
    private String tchId = "";
    //表示老师是否被禁用
    private int IsDisabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        loginId = PreferencesUtils.getSharePreInt(TeacherDetailActivity.this, Const.LOGINID);
        id = PreferencesUtils.getSharePreInt(TeacherDetailActivity.this, Const.ID);
        stuId = "5stu_" + id;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            teacherId = bundle.getInt("id");
            tchId = "5tch_" + teacherId;
        }
        Logger.d("传值操作：id=" + id + ",teacherId=" + teacherId);
        workListAdapter = new WorkListAdapter(TeacherDetailActivity.this, workList);
        rvWork.setAdapter(workListAdapter);
        getTeacherDetail();
    }

    /**
     * 发送请求获取老师的详情
     */
    private void getTeacherDetail() {
        HashMap<String, String> map = new HashMap<>();
        if (loginId == 1) {//学生登录
            map.put("id", String.valueOf(teacherId));
            map.put("studentId", String.valueOf(id));
        } else if (loginId == 2) {//老师登录
            map.put("id", String.valueOf(id));
        }
        HttpClient.post(this, Api.GET_TEACHER_DEATIL, map, new CallBack<TeacherDetailBean>() {
            @Override
            public void onSuccess(TeacherDetailBean result) {
                if (result == null) {
                    return;
                }
                rlNet.setVisibility(View.GONE);
                 llLayout.setVisibility(View.VISIBLE);
                if (result.getStatus() == 200 && result.getData() != null) {
                    dataBean = result.getData();
                    contactId = result.getData().getContactId();
                    isStudy = result.getData().getIsStudy();
                    displayData(result);
                    workList.clear();
                    if (result.getData().getTchResumeVO().getTchWorkExps() != null) {
                        //工作经历
                        Logger.d("工作经历:" + result.getData().getTchResumeVO().getTchWorkExps().size());
                        workList.addAll(result.getData().getTchResumeVO().getTchWorkExps());
                        workListAdapter.notifyDataSetChanged();
                    }

                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    rlNet.setVisibility(View.VISIBLE);
                    llLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 界面展示老师详细信息
     * @param result
     */
    private void displayData(@NonNull TeacherDetailBean result) {
        if (result.getData() == null) {
            return;
        }
        //展示雷达图
        if (result.getData().getTchResumeVO() != null && result.getData().getTchResumeVO().getTchAbilities() != null) {
            titleList.clear();
            data.clear();
            for (int i = 0; i < result.getData().getTchResumeVO().getTchAbilities().size(); i++) {
                titleList.add(result.getData().getTchResumeVO().getTchAbilities().get(i).getAbilityName());
                data.add(Double.parseDouble((result.getData().getTchResumeVO().getTchAbilities().get(i).getAbilityValue() == null ? "0" : result.getData().getTchResumeVO().getTchAbilities().get(i).getAbilityValue())));
            }
            if (titleList.size() > 0 && data.size() > 0 && titleList.size() == data.size()) {
                //设置点的数量
                creditView.setCount(titleList.size());
                //设置标题的数据源
                creditView.setTitles(titleList);
                //设置各维度的值
                creditView.setData(data);
                creditView.setVisibility(View.VISIBLE);
            }
        }
        //标题
        tvTitle.setText(result.getData().getRealName());
        isEnshrine = result.getData().getIsEnshrine();
        //收藏
        if (isEnshrine == 0) {
            //未收藏
            isShowLike = false;
            ivCollect.setImageResource(R.mipmap.like);
        } else {
            //收藏
            isShowLike = true;
            ivCollect.setImageResource(R.mipmap.like_full);
        }
        //展示老师的头像
        if (result.getData().getImgUrl() != null && !"".equals(result.getData().getImgUrl())) {
            PicassoUtils.LoadPathCorners(TeacherDetailActivity.this, result.getData().getImgUrl(), 70, 70, circleView);
        } else {
            PicassoUtils.LoadCorners(TeacherDetailActivity.this, R.drawable.master, 70, 70, circleView);
        }
        //老师的姓名
        tvTeacherName.setText(result.getData().getRealName());
        String oldchar = ",";
        String newchar = "|";
        //展示地址及职位
        if (result.getData().getAddressNow() != null && !TextUtils.isEmpty(result.getData().getAddressNow())) {
            tvTeacherAblity.setText(result.getData().getAddressNow().replace(oldchar, newchar) + "|" + result.getData().getPositionId());
        } else {
            tvRequireDesc.setText(result.getData().getPositionId());
        }
        //评价
        if (result.getData().getEvaluationLabels() != null && !TextUtils.isEmpty(result.getData().getEvaluationLabels())) {
            tagAblity.setVisibility(View.VISIBLE);
            String labels = result.getData().getEvaluationLabels().substring(1, result.getData().getEvaluationLabels().length() - 1);
            String[] str = labels.split(",");
            Logger.d("labels==list" + Arrays.asList(str).toString());
            tagAblity.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(TeacherDetailActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1, s.length() - 1));
                    return view;
                }


            });
        } else {
            tagAblity.setVisibility(View.GONE);
        }

        //技能标签
        if (result.getData().getTchResumeVO() != null && result.getData().getTchResumeVO().getTchSkills() != null) {
            tvSkil.setText("技能标签(" + result.getData().getTchResumeVO().getTchSkills().size() + ")");
            tagSkill.setAdapter(new TagAdapter<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean>(result.getData().getTchResumeVO().getTchSkills()) {
                @Override
                public View getView(FlowLayout parent, int position, TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean tchSkillsBean) {
                    View view = LayoutInflater.from(TeacherDetailActivity.this)
                            .inflate(R.layout.item_textview, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(tchSkillsBean.getSkillName());
                    return view;
                }
            });
        } else {
            tvSkil.setText("技能标签(0)");
        }
        //诉求描述
        if (result.getData().getTchResumeVO().getTchRequireDesc() != null && !TextUtils.isEmpty(result.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc())) {
            tvRequireDesc.setVisibility(View.VISIBLE);
            tvRequireDesc.setText("       " + result.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc());
        } else {
            tvRequireDesc.setVisibility(View.GONE);
        }
        //所在公司
        Picasso.with(TeacherDetailActivity.this).load(result.getData().getCompanyImg()).error(R.mipmap.logo).into(ivCompanyImg);
        tvCompanyName.setText(result.getData().getCompanyName());
        tvCompanyScale.setText(result.getData().getType() + "|" + result.getData().getScale());
        scrollView.smoothScrollTo(0, 0);
        //是否可以跟师学习
        /**
         * 是否禁用 0：未禁用 1：已禁用
         */
        IsDisabled=result.getData().getIsDisabled();
        if(IsDisabled==1){
            btStudy.setEnabled(false);
            btStudy.setText("该导师已离任，请选择新导师");
//            btStudy.setTextColor(Color.BLACK);
            btStudy.setBackgroundResource(R.drawable.btn_chat_gray);
        }
        //收否显示收藏的按钮
        if (loginId == 1) {//学生登录
            rlCollect.setVisibility(View.VISIBLE);
            btStudy.setVisibility(View.VISIBLE);
        } else if (loginId == 2) {//老师登录
            rlCollect.setVisibility(View.GONE);
            btStudy.setVisibility(View.GONE);
        }
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.rl_back, R.id.rl_collect, R.id.bt_study, R.id.rl_net_back, R.id.bt_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //收藏
            case R.id.rl_collect:
                if(IsDisabled==1){
                    ToastUtil.showShortToast(TeacherDetailActivity.this,"该导师已离任，不可进行收藏");
                    return;
                }
                isShowLike = !isShowLike;
                if (isShowLike) {
                    isEnshrine = 0;
                } else {
                    isEnshrine = 1;
                }
                //网络状态判断
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(), "网络异常，请稍后再试吧。");
                    return;
                }
                modifyCollect();
                break;
            //跟师学习
            case R.id.bt_study:
                if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                    NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                    return;
                }
                if (contactId != null && TextUtils.isEmpty(contactId)) {
                    //直接跳转聊天详情界面
                    getStudentRecord();
                } else {
                    //第一次发起沟通
                    startUserContact();
                }
                break;
            case R.id.rl_net_back:
                finish();
                break;
            case R.id.bt_reload:
                getTeacherDetail();
                break;
        }
    }

    /**
     * 发起第一沟通
     */
    private void startUserContact() {
        HashMap<String, String> map = new HashMap<>();
        map.put("stuId", String.valueOf(id));
        map.put("tchId", String.valueOf(teacherId));
        HttpClient.post(this, Api.START_USER_CONTACT, map, new CallBack<UserContactBean>() {
            @Override
            public void onSuccess(UserContactBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    contactId = result.getData().getId();
                    if (result.getData().getFlag() == 0) {
                        //直接跳转聊天详情界面
                        getStudentRecord();
                    } else {
                        if (isStudy == 0) {
                            //发起跟师学习
                            withStudy();
                        } else {
                            //直接跳转聊天详情界面
                            getStudentRecord();
                        }
                    }
                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 跟师学习
     */
    private void withStudy() {
        HashMap<String, String> map = new HashMap<>();
        map.put("stuId", String.valueOf(id));
        map.put("tchId", String.valueOf(teacherId));
        HttpClient.post(this, Api.STUDY_BY_STU, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, "跟师学习成功");
                    sendMsgText("", "", "S02");
                    getStudentRecord();

                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }
            }


        });
    }

    /**
     * 获取学生的相关信息
     */
    private void getStudentRecord() {
        String params = "?studentId=" + String.valueOf(id);
        HttpClient.get(this, Api.GTE_STUDENT_RECORD + params, new CallBack<StudentRecordBean>() {
            @Override
            public void onSuccess(StudentRecordBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200 && result.getData() != null) {
                    StudentChatActivity.StartActivity(TeacherDetailActivity.this, tchId, dataBean, result.getData(), contactId);
                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }

            }

        });
    }

    /**
     * 修改收藏状态
     */
    private void modifyCollect() {
        HashMap<String, String> map = new HashMap<>();
        map.put("stuUserId", String.valueOf(id));
        map.put("tchTeacherId", String.valueOf(teacherId));
        map.put("isDelete", String.valueOf(isEnshrine));
        HttpClient.post(this, Api.SET_ENSHRINE, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    //收藏
                    if (isEnshrine == 1) {
                        ivCollect.setImageResource(R.mipmap.like);
                    } else {
                        ivCollect.setImageResource(R.mipmap.like_full);
                    }
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }
            }

        });
    }


    /**
     * 发送消息
     * 跟师学习的消息
     * @param content
     * @param businessId
     * @param type
     */
    private void sendMsgText(String content, String businessId, String type) {
        if (MyApplication.xmppConnection != null && MyApplication.xmppConnection.isAuthenticated()) {
            //封装xmpp发送的格式
            msgBean = new MsgBean();
            msgBean.setType(type);
            msgBean.setMsg(content);
            msgBean.setSessionId(Integer.parseInt(contactId));
            msgBean.setBusinessId(businessId);
            //封装本地消息的保存DB的形式
            dBmsgBean = new DBmsgBean();
            dBmsgBean.setBusinessId(businessId);
            dBmsgBean.setMsg(content);
            dBmsgBean.setType(type);
            //xmpp发送
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        XmppUtil.sendMessage(MyApplication.xmppConnection, gson.toJson(msgBean).replace("type", "msgType"), stuId, tchId);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        ToastUtil.showShortToast(TeacherDetailActivity.this, "发送失败");
                    }
                }
            }).start();
            //将消息记录保存后台
            SaveChat(gson.toJson(dBmsgBean), stuId, tchId, msgBean.getType());
        } else {
            ToastUtil.showShortToast(TeacherDetailActivity.this, "登录服务器出错，请重新登录");
        }

    }

    /**
     *  保存聊天记录
     * 提交后台服务器保存
     * @param message
     * @param sender
     * @param receiver
     * @param msgType
     */
    private void SaveChat(String message, String sender, String receiver, String msgType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userContactId", contactId);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("msg", message);
        hashMap.put("msgType", msgType);//私聊

        HttpClient.post(this, Api.SAVE_CHAT_MSG, hashMap, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if (result == null) {
                    return;
                }
                if (result.getStatus() == 200) {
                    Logger.d("消息保存服务器成功");
                } else {
                    ToastUtil.showShortToast(TeacherDetailActivity.this, result.getMsg());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

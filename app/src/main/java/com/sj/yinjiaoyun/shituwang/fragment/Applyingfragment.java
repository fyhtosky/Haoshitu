package com.sj.yinjiaoyun.shituwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.InterviewDeatilActivity;
import com.sj.yinjiaoyun.shituwang.activity.IterviewActivity;
import com.sj.yinjiaoyun.shituwang.activity.TachterInterviewActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.StudiesBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaopan.android.net.NetworkUtils;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/14.
 * 老师端的求职管理的申请中的Fragment
 */
public class Applyingfragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //登录的id
    private int id;
    //数据源的集合
    private List<StudiesBean.DataBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.applying_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(getContext(), Const.ID);
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //画分割线
        rv.addItemDecoration(new GrayDecoration());
        rv.setAdapter(new BaseQuickAdapter<StudiesBean.DataBean>(R.layout.apply_fragment_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final StudiesBean.DataBean dataBean) {
                if (dataBean.getStudentDeatil() == null) {
                    return;
                }
                CircleImageView circleView = baseViewHolder.getView(R.id.circleView);
                //展示用户名
                baseViewHolder.setText(R.id.tv_name, dataBean.getStudentDeatil().getRealName());
                ImageView ivSex = baseViewHolder.getView(R.id.iv_sex);
                //展示性别
                if (dataBean.getStudentDeatil().getSex() == 1) {
                    ivSex.setImageResource(R.mipmap.male);
                } else {
                    ivSex.setImageResource(R.mipmap.woman);
                }
                //展示学历
                String education = "";
                //展示地址
                String address = "";
                //地址赋值
                if (dataBean.getStudentDeatil().getAddressNow() != null && !TextUtils.isEmpty(dataBean.getStudentDeatil().getAddressNow())) {
                  String [] addArr=dataBean.getStudentDeatil().getAddressNow().split(",");
                  if(addArr.length>0){
                      if(addArr.length==1){
                          address=addArr[0];
                      }else {
                          address = addArr[1];
                      }
                  }
                }

                //展示职位
                String post = "";
//                档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
                if (dataBean.getStudentDeatil().getResumes() != null && dataBean.getStudentDeatil().getResumes().size() > 0) {
                    for (StudiesBean.DataBean.StudentDeatilBean.ResumesBean resumesBean : dataBean.getStudentDeatil().getResumes()) {
                        //展示薪资及职位
                        if (resumesBean.getResumeType() == 1) {
                            Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                            }.getType());
                            //获取职位
                            String expectPosition = mapstr.get("expectPosition");
                            if (MyApplication.map != null) {
                                if (expectPosition != null && !TextUtils.isEmpty(expectPosition)) {
                                    String[] poss = expectPosition.split(",");
                                    //期望职位赋值
                                    post = MyApplication.map.get(poss[poss.length - 1]).getName();
                                }
                            }
                        } else if (resumesBean.getResumeType() == 2) {
                            Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                            }.getType());
                            //获取学历的code
                            String diplomas = mapstr.get("diplomas");
                            if (MyApplication.map != null) {
                                if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                                    String[] dips = diplomas.split(",");
                                    //学历赋值
                                    education = MyApplication.map.get(dips[dips.length - 1]).getName();
                                }
                            }
                        }
                    }
                }
                //控件显示学历和地址
                if (!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)) {
                    baseViewHolder.setText(R.id.tv_address, education + "|" + address);
                } else if (TextUtils.isEmpty(education)) {
                    baseViewHolder.setText(R.id.tv_address, address);
                } else if (TextUtils.isEmpty(address)) {
                    baseViewHolder.setText(R.id.tv_address, education);
                } else {
                    baseViewHolder.setText(R.id.tv_address, "");
                }
                //控件展示时间
                if (dataBean.getUpdateTime() != null && !TextUtils.isEmpty(dataBean.getUpdateTime())) {
                    baseViewHolder.setText(R.id.tv_time, TimeRender.FriendlyDate(Long.parseLong(dataBean.getUpdateTime())));
                } else {
                    baseViewHolder.setText(R.id.tv_time, "");
                }
                //空间展示职位
                if (!TextUtils.isEmpty(post)) {
                    baseViewHolder.setText(R.id.tv_post, "期望：" + post);
                } else {
                    baseViewHolder.setText(R.id.tv_post, "");
                }

                //显示头像
                if (dataBean.getStudentDeatil().getHead() != null && !TextUtils.isEmpty(dataBean.getStudentDeatil().getHead())) {
                    PicassoUtils.LoadPathCorners(getContext(), dataBean.getStudentDeatil().getHead(), circleView);
                } else {
                    PicassoUtils.LoadCorners(getContext(), R.drawable.wukong, 60, 60, circleView);
                }
                TagFlowLayout tagTrait = baseViewHolder.getView(R.id.tag_trait);
                //评价
                if (dataBean.getStudentDeatil().getEvaluationLabels() != null && !TextUtils.isEmpty(dataBean.getStudentDeatil().getEvaluationLabels())) {
                    tagTrait.setVisibility(View.VISIBLE);
                    String labels = dataBean.getStudentDeatil().getEvaluationLabels().substring(1, dataBean.getStudentDeatil().getEvaluationLabels().length() - 1);
                    String[] str = labels.split(",");
                    Logger.d("labels==list" + Arrays.asList(str).toString());
                    tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.item_textview_skill, parent, false);
                            TextView tv = (TextView) view.findViewById(R.id.tv);
                            tv.setText(s.substring(1, s.length() - 1));
                            return view;
                        }

                    });
                } else {
                    tagTrait.setVisibility(View.GONE);
                }
                //添加点击时间
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

    }

    /**
     * 获取数据源
     */
    private void getData() {
        String params = "?usrId=" + String.valueOf(id) + "&isStu=0&status=100";
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
                if(!NetworkUtils.isConnectedByState(MyApplication.getContext())){
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

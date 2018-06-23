package com.sj.yinjiaoyun.shituwang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
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
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.TeachterCollectBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
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
 * 老师端的收藏我的
 */
public class CollectedMyActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_hint)
    RelativeLayout rlHint;
    @BindView(R.id.ll_net)
    LinearLayout llNet;
    //登录的id
    private int id;
    //数据源
    private List<TeachterCollectBean.DataBean.RowsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected_my);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(CollectedMyActivity.this, Const.ID);
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(CollectedMyActivity.this, LinearLayoutManager.VERTICAL, false));
        //画分割线
        rv.addItemDecoration(new GrayDecoration());
        rv.setAdapter(new BaseQuickAdapter<TeachterCollectBean.DataBean.RowsBean>(R.layout.tch_collect_liet_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final TeachterCollectBean.DataBean.RowsBean dataBean) {
                CircleImageView circleView = baseViewHolder.getView(R.id.circleView);
                //展示用户名
                baseViewHolder.setText(R.id.tv_name, dataBean.getStuUserVO().getRealName());
                ImageView ivSex = baseViewHolder.getView(R.id.iv_sex);
                //展示性别
                if (dataBean.getStuUserVO().getSex() == 1) {
                    ivSex.setImageResource(R.mipmap.male);
                } else {
                    ivSex.setImageResource(R.mipmap.woman);
                }
                //展示学历
                String education = "";
                //展示地址
                String address = "";
                //展示职位
                String post = "";
                //展示薪资
                String salary = "";
                //地址赋值
                if (dataBean.getStuUserVO().getAddressNow() != null && !TextUtils.isEmpty(dataBean.getStuUserVO().getAddressNow())) {
//                    address=dataBean.getStuUserVO().getAddressNow().split(",")[1];
                    String[] add = dataBean.getStuUserVO().getAddressNow().split(",");
                    if (add.length < 3) {
                        address = add[add.length - 1];
                    } else {
                        address = dataBean.getStuUserVO().getAddressNow().split(",")[1];
                    }
                }
//                档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
                if (dataBean.getStuUserVO().getResumes() != null && dataBean.getStuUserVO().getResumes().size() > 0) {
                    for (TeachterCollectBean.DataBean.RowsBean.StuUserVOBean.ResumesBean resumesBean : dataBean.getStuUserVO().getResumes()) {
                        //展示薪资及职位
                        if (resumesBean.getResumeType() == 1) {
                            Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                            }.getType());
                            //获取职位
                            String expectPosition = mapstr.get("expectPosition");
                            //薪资
                            String expectSalary = mapstr.get("expectSalary");
                            if (MyApplication.map != null) {
                                if (expectPosition != null && !TextUtils.isEmpty(expectPosition)) {
                                    String[] poss = expectPosition.split(",");
                                    //期望职位赋值
                                    post = MyApplication.map.get(poss[poss.length - 1]).getName();
                                }
                                if (expectSalary != null && !TextUtils.isEmpty(expectSalary)) {
                                    String[] salarys = expectSalary.split(",");
                                    //薪资赋值
                                    salary = MyApplication.map.get(salarys[salarys.length - 1]).getName();
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
                //控件展示薪资
                baseViewHolder.setText(R.id.tv_salary, salary);

                //空间展示职位
                if (!TextUtils.isEmpty(post)) {
                    baseViewHolder.setText(R.id.tv_post, "期望：" + post);
                } else {
                    baseViewHolder.setText(R.id.tv_post, "");
                }

                //显示头像
                if (dataBean.getStuUserVO().getHead() != null && !TextUtils.isEmpty(dataBean.getStuUserVO().getHead())) {
                    PicassoUtils.LoadPathCorners(CollectedMyActivity.this, dataBean.getStuUserVO().getHead(), circleView);
                } else {
                    PicassoUtils.LoadCorners(CollectedMyActivity.this, R.drawable.wukong, 60, 60, circleView);
                }
                TagFlowLayout tagTrait = baseViewHolder.getView(R.id.tag_trait);
                //评价
                if (dataBean.getStuUserVO().getEvaluationLabels() != null && !TextUtils.isEmpty(dataBean.getStuUserVO().getEvaluationLabels())) {
                    tagTrait.setVisibility(View.VISIBLE);
                    String labels = dataBean.getStuUserVO().getEvaluationLabels().substring(1, dataBean.getStuUserVO().getEvaluationLabels().length() - 1);
                    String[] str = labels.split(",");
                    Logger.d("labels==list" + Arrays.asList(str).toString());
                    tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            View view = LayoutInflater.from(CollectedMyActivity.this)
                                    .inflate(R.layout.item_textview_skill, parent, false);
                            TextView tv = (TextView) view.findViewById(R.id.tv);
                            tv.setText(s.substring(1, s.length() - 1));
                            return view;
                        }

                    });
                } else {
                    tagTrait.setVisibility(View.GONE);
                }
                //添加点击事件
                baseViewHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转学生档案的界面
                        StudentResumeActivity.StartActivity(CollectedMyActivity.this, dataBean.getStuUserId());
                    }
                });
            }


        });
        findTeachterCollect(false);
    }

    /**
     * 查询老师的收藏
     *
     * @param isLoadMore
     */
    private void findTeachterCollect(final boolean isLoadMore) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tchTeacherId", String.valueOf(id));
        HttpClient.post(this, Api.TEACHTER_COLLECT, map, new CallBack<TeachterCollectBean>() {
            @Override
            public void onSuccess(TeachterCollectBean result) {
                if (result == null) {
                    return;
                }
                if (!isLoadMore) {
                    list.clear();
                }
                //影藏网络无效的界面
                llNet.setVisibility(View.GONE);
                if (result.getData() != null && result.getData().getRows() != null) {
                    list.addAll(result.getData().getRows());
                    if (list.size() > 0) {
                        rlHint.setVisibility(View.GONE);
                    } else {
                        rlHint.setVisibility(View.VISIBLE);
                    }
                    rv.getAdapter().notifyDataSetChanged();
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

    @OnClick({R.id.rl_back,R.id.bt_reload})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_reload:
                findTeachterCollect(false);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

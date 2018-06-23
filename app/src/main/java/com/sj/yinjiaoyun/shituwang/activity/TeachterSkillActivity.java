package com.sj.yinjiaoyun.shituwang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.callback.ChildrenCallBack;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopWorkState;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师端的专业技能
 */
public class TeachterSkillActivity extends AppCompatActivity {
    @BindView(R.id.et_skill)
    EditText etSkill;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.rl)
    RelativeLayout rl;
    //登录的id
    private int id;
    //档案信息的对象
    private TeacherDetailBean bean;
    //专业技能的集合
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean> skillList = new ArrayList<>();
    //技能标签的选择器
    private PopWorkState popWorkState;
    //技能熟练度的数据源
    private List<Children> list = new ArrayList<>();
    //存放删除技能的id
    private List<String>integerList=new ArrayList<>();
    //存放添加技能的字符串
    private List<String>strList=new ArrayList<>();
    private Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachter_skill);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        list.clear();
        integerList.clear();
        strList.clear();
        //技能熟练度
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0008".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }

        }
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(TeachterSkillActivity.this, Const.ID);
        popWorkState = new PopWorkState(TeachterSkillActivity.this);
        skillList.clear();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (TeacherDetailBean) bundle.getSerializable("teacherDetailBean");
            if (bean == null) {
                return;
            }

            tagSkill.setAdapter(new TagAdapter<TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean>(skillList) {
                @Override
                public View getView(FlowLayout parent, final int position, TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean resumenBean) {
                    View view = LayoutInflater.from(TeachterSkillActivity.this)
                            .inflate(R.layout.tag_item_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv_skill);
                    RelativeLayout rlItem = (RelativeLayout) view.findViewById(R.id.rl_delete);
                    if (resumenBean != null ){
                        String skill = "";
                        String[] levels =resumenBean.getSkillLevel().split(",");
                        String skillLevel = levels[levels.length - 1];
                        Logger.d("skillLevel=" + skillLevel);
                        if (MyApplication.map != null) {
                            if (MyApplication.map.get(skillLevel) != null) {
                                Logger.d("TeachterSkillActivity：" + MyApplication.map.get(skillLevel).toString());
                                skill = MyApplication.map.get(skillLevel).getName();
                            }
                        }
                        tv.setText(resumenBean.getSkillName() + " " + skill);
                    }
                    //添加删除监听器
                    rlItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(skillList.get(position).getResumeId()!=null){
                                integerList.add(skillList.get(position).getResumeId());
                            }
                            if(strList.contains(skillList.get(position))){
                                strList.remove(skillList.get(position));
                            }
                            skillList.remove(position);
                            tagSkill.getAdapter().notifyDataChanged();
                        }
                    });
                    return view;
                }
            });
            if(bean.getData().getTchResumeVO().getTchSkills()!=null){
                Logger.d("专业技能:" + bean.getData().getTchResumeVO().getTchSkills().size());
                skillList.addAll(bean.getData().getTchResumeVO().getTchSkills());
                tagSkill.getAdapter().notifyDataChanged();
            }
        }

    }
    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, TeacherDetailBean teacherDetailBean) {
        Intent intent = new Intent(context, TeachterSkillActivity.class);
        intent.putExtra("teacherDetailBean", teacherDetailBean);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back, R.id.tv_add, R.id.bt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_add:
                //隐藏软键盘
                hideSoftInputView();
                final String skill=etSkill.getText().toString().trim();
                if (!TextUtils.isEmpty(skill)) {
                    if (skill.length() <= 10) {
                        popWorkState.pop(rl, list, new ChildrenCallBack() {
                            @Override
                            public void setChildren(Children content) {
                                TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean bean;
                                if (content.getName().equals("精通")) {
                                     bean=new TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean();
                                    bean.setSkillName(etSkill.getText().toString().trim());
                                    bean.setSkillLevel("R0008,P0039");
                                }else if (content.getName().equals("熟练")) {
                                     bean=new TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean();
                                     bean.setSkillName(etSkill.getText().toString().trim());
                                     bean.setSkillLevel("R0008,P0040");
                                }else {
                                    bean=new TeacherDetailBean.DataBean.TchResumeVOBean.TchSkillsBean();
                                    bean.setSkillName(etSkill.getText().toString().trim());
                                    bean.setSkillLevel("R0008,P0041");

                                }
                                skillList.add(bean);
                                strList.add(gson.toJson(bean));
                                tagSkill.getAdapter().notifyDataChanged();
                                etSkill.setText("");
                            }


                        });
                    }else{
                        ToastUtil.showShortToast(TeachterSkillActivity.this, "技能长度为10个字符以内");
                    }
                }else{
                    ToastUtil.showShortToast(TeachterSkillActivity.this, "请填写技能名称");
                }
                break;
            case R.id.bt_save:
                saveSkill();
                break;
        }
    }
    /**
     * 更新能力标签
     */
    private void saveSkill() {
        String skills="";
        String removeSkillIds="";
        if(integerList.size()>0){
            removeSkillIds=integerList.toString();
        }
        if(strList.size()>0){
            skills=strList.toString();
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
        Logger.d("删除技能的id:"+skills+"添加的技能："+strList.toString());
        HashMap<String,String>map=new HashMap<>();
        map.put("tchTeacherId",String.valueOf(id));
        map.put("skills",skills);
        map.put("removeSkillIds",removeSkillIds);
        HttpClient.post(this, Api.ADD_TEACHTER_SKILL, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    finish();
                }else {
                    ToastUtil.showShortToast(TeachterSkillActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(TeachterSkillActivity.this,message);
            }

        });
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                assert manager != null;
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

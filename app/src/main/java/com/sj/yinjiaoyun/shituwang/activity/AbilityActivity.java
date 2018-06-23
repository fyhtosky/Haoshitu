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
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 综合能力的界面
 */
public class AbilityActivity extends AppCompatActivity {

    @BindView(R.id.et_skill)
    EditText etSkill;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.rl)
    RelativeLayout rl;
    //登录的id
    private int id;
    private TeacherDetailBean bean;
    //综合能力的数据源
    private List<TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean> Abilitylist=new ArrayList<>();
    //存放删除综合能力的id
    private List<String>integerList=new ArrayList<>();
    //存放添加综合能力的字符串
    private List<String>strList=new ArrayList<>();
    //综合能力的选择器
    private PopWorkState popWorkState;
    //综合能力的维度值的数据源
    private List<String>list= Arrays.asList("60","65","70","75","80","85","90","95","100");
    private Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        popWorkState = new PopWorkState(AbilityActivity.this);
        id = PreferencesUtils.getSharePreInt(AbilityActivity.this, Const.ID);
        Abilitylist.clear();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (TeacherDetailBean) bundle.getSerializable("teacherDetailBean");
            if (bean == null) {
                return;
            }

            tagSkill.setAdapter(new TagAdapter<TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean>(Abilitylist) {
                @Override
                public View getView(FlowLayout parent, final int position, TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean bean) {
                    View view = LayoutInflater.from(AbilityActivity.this)
                            .inflate(R.layout.tag_item_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv_skill);
                    RelativeLayout rlItem = (RelativeLayout) view.findViewById(R.id.rl_delete);
                    tv.setText(bean.getAbilityName()+ " " + bean.getAbilityValue());

                    //添加删除监听器
                    rlItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Abilitylist.get(position).getResumeId()!=null){
                                integerList.add(Abilitylist.get(position).getResumeId());
                            }
                            if(strList.contains(Abilitylist.get(position))){
                                strList.remove(Abilitylist.get(position));
                            }
                            Abilitylist.remove(position);
                            tagSkill.getAdapter().notifyDataChanged();
                        }
                    });
                    return view;
                }
            });
            if (bean.getData().getTchResumeVO().getTchAbilities() != null) {
                //综合能力
                Logger.d("综合能力:" + bean.getData().getTchResumeVO().getTchAbilities().size());
                Abilitylist.addAll(bean.getData().getTchResumeVO().getTchAbilities());
                tagSkill.getAdapter().notifyDataChanged();
            }
        }



    }

    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, TeacherDetailBean teacherDetailBean) {
        Intent intent = new Intent(context, AbilityActivity.class);
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
                        if(Abilitylist.size()>7){
                            ToastUtil.showShortToast(AbilityActivity.this,"综合能力项不能超过6项");
                            return;
                        }
                        popWorkState.popString(rl, list, new PhoneCallBack() {
                            @Override
                            public void setContent(String content) {
                                TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean bean=new TeacherDetailBean.DataBean.TchResumeVOBean.TchAbilitiesBean();
                                bean.setAbilityName(etSkill.getText().toString().trim());
                                bean.setAbilityValue(content);
                                Abilitylist.add(bean);
                                strList.add(gson.toJson(bean));
                                tagSkill.getAdapter().notifyDataChanged();
                                etSkill.setText("");
                            }
                        });
                    }else{
                        ToastUtil.showShortToast(AbilityActivity.this, "能力长度为10个字符以内");
                    }
                }else{
                    ToastUtil.showShortToast(AbilityActivity.this, "请填写能力名称");
                }
                break;
            case R.id.bt_save:
                if(Abilitylist.size()>3 && Abilitylist.size()<7){
                    saveAbility();
                }else {
                    ToastUtil.showShortToast(AbilityActivity.this,"综合能力只能填4-6项");
                }

                break;
        }
    }

    /**
     * 更新能力标签
     */
    private void saveAbility() {
        String removeAbilityIds="";
        String abilitys="";
        if(integerList.size()>0){
            removeAbilityIds=integerList.toString();
        }
        if(strList.size()>0){
            abilitys=strList.toString();
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
        Logger.d("删除技能的id:"+integerList.toString()+"添加的技能："+strList.toString());
        HashMap<String,String>map=new HashMap<>();
        map.put("tchTeacherId",String.valueOf(id));
        map.put("abilitys",abilitys);
        map.put("removeAbilityIds",removeAbilityIds);
        HttpClient.post(this, Api.ADD_ABILITY, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    finish();
                }else {
                    ToastUtil.showShortToast(AbilityActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(AbilityActivity.this,message);
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

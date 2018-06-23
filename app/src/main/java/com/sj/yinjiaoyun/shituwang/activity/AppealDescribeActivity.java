package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师界面的诉求描述
 */
public class AppealDescribeActivity extends AppCompatActivity  implements TextWatcher {
    @BindView(R.id.et_self)
    EditText etSelf;
    @BindView(R.id.tv_text_size)
    TextView tvTextSize;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private TeacherDetailBean bean;
    //登录的id
    private int id;
    private String description="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_describe);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        id = PreferencesUtils.getSharePreInt(AppealDescribeActivity.this, Const.ID);
        etSelf.addTextChangedListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (TeacherDetailBean) bundle.getSerializable("teacherDetailBean");
            if (bean == null) {
                return;
            }
            if(bean.getData().getTchResumeVO().getTchRequireDesc()==null ||bean.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc()==null){
                description="";
            }else{
                description=bean.getData().getTchResumeVO().getTchRequireDesc().getRequireDesc();
            }

            if (description != null && !TextUtils.isEmpty(description)) {
                etSelf.setText(description);
                etSelf.setSelection(etSelf.getText().toString().length());
                tvTextSize.setText(etSelf.getText().toString().trim().length() + "/100字");
            }
            String text=etSelf.getText().toString().trim().length() + "/200字";
            tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
    }
    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, TeacherDetailBean teacherDetailBean) {
        Intent intent = new Intent(context, AppealDescribeActivity.class);
        intent.putExtra("teacherDetailBean", teacherDetailBean);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_confirm:
                if (etSelf.getText().toString().trim().length() <= 200) {
                      updateRequre();
                } else {
                    tvHint.setText("自我描述不能超过200个字");
                }
                break;
        }
    }

    /**
     * 更新诉求描述
     */
    private void updateRequre() {
        if(TextUtils.isEmpty(etSelf.getText().toString().trim())){
            ToastUtil.showShortToast(AppealDescribeActivity.this,"请填写诉求描述");
            return;
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
        description=etSelf.getText().toString().trim();
        HashMap<String,String>map=new HashMap<>();
        if(bean!=null){
            map.put("requireDesc",description);
            if(bean.getData().getTchResumeVO().getTchRequireDesc()!=null){
                map.put("resumeId",String.valueOf(bean.getData().getTchResumeVO().getTchRequireDesc().getResumeId()));
            }
            map.put("tchTeacherId",String.valueOf(id));
            HttpClient.post(this, Api.ADD_REQUIRE, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        finish();
                    } else {
                        ToastUtil.showShortToast(AppealDescribeActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(AppealDescribeActivity.this,message);
                }

            });
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvTextSize.setText(etSelf.getText().toString().trim().length() + "/200字");
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text=etSelf.getText().toString().trim().length() + "/200字";
        tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

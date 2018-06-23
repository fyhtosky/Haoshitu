package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.callback.ChildrenCallBack;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopWorkState;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ProvincePicker;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 期望工作的界面
 */
public class ExpectWorkActivity extends AppCompatActivity {
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.rl_expect)
    RelativeLayout rlExpect;
    //包含期望工作信息的字符串
    private StudentRecordBean.DataBean.ResumesBean ResumesBean;
    //薪资区间的数据源
    private List<Children> list = new ArrayList<>();
    //工作状态的选择
    private PopWorkState popPay;
    //地址
    private String address;
    //行业
    private String expectBusiness;
    //职位
    private String expectPosition;
    //薪资
    private String expectSalary;
    //期望行业回调的实体类
    private Children childrenIndustry;
    //期望职位回调的实体类
    private Children childrenPost;
    //薪资区间回调的实体类
    private Children childrenPay;
    //城市选择器
    private ProvincePicker provincePicker;
    //登录的id
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expect_work);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        list.clear();
        //薪资区间数据源
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0005".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }

        }
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(ExpectWorkActivity.this, Const.ID);
        provincePicker=new ProvincePicker(ExpectWorkActivity.this);
        popPay = new PopWorkState(ExpectWorkActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ResumesBean = (StudentRecordBean.DataBean.ResumesBean) bundle.getSerializable("resume");
            if(ResumesBean==null){
                return;
            }
            String resume=ResumesBean.getResume();
            if(resume!=null && !TextUtils.isEmpty(resume)){
                Map<String,String> mapstr=new Gson().fromJson(resume, new TypeToken<HashMap<String,String>>(){}.getType());
                if(MyApplication.map!=null){
                    address=mapstr.get("address");
                    expectBusiness=mapstr.get("expectBusiness");
                    expectPosition=mapstr.get("expectPosition");
                    expectSalary=mapstr.get("expectSalary");
                    //工作城市
                    tvCity.setText(address);
                    if(!TextUtils.isEmpty(expectBusiness)){
                        //期望行业
                        String [] business=expectBusiness.split(",");
                        tvIndustry.setText(MyApplication.map.get(business[business.length-1]).getName());
                    }
                    if(!TextUtils.isEmpty(expectPosition)){
                        //期望职业
                        String [] position=expectPosition.split(",");
                        tvPost.setText(MyApplication.map.get(position[position.length-1]).getName());
                    }
                   if(!TextUtils.isEmpty(expectSalary)){
                       //期望薪资
                       String []salary=expectSalary.split(",");
                       tvPay.setText(MyApplication.map.get(salary[salary.length-1]).getName());
                   }


                }
            }
        }
    }


    /*
   * 启动Activity
   * @param context
   */
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, ExpectWorkActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_industry, R.id.rl_post, R.id.rl_city, R.id.rl_pay, R.id.bt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //期望行业
            case R.id.rl_industry:
                startActivityForResult(new Intent(ExpectWorkActivity.this,ExpectIndustryActivity.class),0);
                break;
            case R.id.rl_post:
                //期望职位
                startActivityForResult(new Intent(ExpectWorkActivity.this,ExpectedPositionActivity.class),1);
                break;
            case R.id.rl_city:
                provincePicker.ProvincePickerShow(new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvCity.setText(content);
                    }
                });
                break;
            case R.id.rl_pay:
                popPay.pop(rlExpect, list, new ChildrenCallBack() {
                    @Override
                    public void setChildren(Children content) {
                        childrenPay=content;
                        tvPay.setText(content.getName());
                    }


                });
                break;
            case R.id.bt_save:
                saveWork();
                break;
        }
    }

    /**
     * 保存期望工作
     */
    private void saveWork() {
        if(MyApplication.map!=null){
            if(childrenIndustry!=null){
                expectBusiness="R0002"+","+childrenIndustry.getParentCode()+","+childrenIndustry.getCode();
            }
           if(childrenPost!=null){
               Children postChildre=MyApplication.map.get(childrenPost.getParentCode());
               expectPosition="R0001"+","+postChildre.getParentCode()+","+childrenPost.getParentCode()+","+childrenPost.getCode();
           }
          if(childrenPay!=null){
              expectSalary=childrenPay.getCode();
          }
        }
        if(TextUtils.isEmpty(tvIndustry.getText().toString().trim())){
            ToastUtil.showShortToast(ExpectWorkActivity.this,"请选择期望行业");
            return;
        }
        if(TextUtils.isEmpty(tvPost.getText().toString().trim())){
            ToastUtil.showShortToast(ExpectWorkActivity.this,"请选择期望职位");
            return;
        }
        if(TextUtils.isEmpty(tvCity.getText().toString().trim())){
            ToastUtil.showShortToast(ExpectWorkActivity.this,"请选择城市");
            return;
        }
        if(TextUtils.isEmpty(tvPay.getText().toString().trim())){
            ToastUtil.showShortToast(ExpectWorkActivity.this,"请选择薪资区间");
            return;
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        if (ResumesBean != null) {
            map.put("id", String.valueOf(ResumesBean.getId()));
        }
        map.put("stuUserId",String.valueOf(id));
        map.put("expectBusiness",expectBusiness);
        map.put("expectPosition",expectPosition);
        map.put("address",tvCity.getText().toString().trim());
        map.put("expectSalary",expectSalary);
        HttpClient.post(this, Api.SAVE_EXPECT_WORK, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(ExpectWorkActivity.this,"保存成功");
                    finish();
                }else {
                    ToastUtil.showShortToast(ExpectWorkActivity.this,result.getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(ExpectWorkActivity.this,message);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode==RESULT_OK){
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    childrenIndustry = (Children) bundle.getSerializable(Const.CHILDREN);
                    assert childrenIndustry != null;
                    tvIndustry.setText(childrenIndustry.getName());
                    Logger.d("chlidren"+childrenIndustry.toString());
                }
            }
        }else if(requestCode==1){
            if(resultCode==RESULT_OK){
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    childrenPost = (Children) bundle.getSerializable(Const.CHILDREN);
                    assert childrenPost != null;
                    tvPost.setText(childrenPost.getName());
                    Logger.d("chlidren"+childrenPost.toString());
                }
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

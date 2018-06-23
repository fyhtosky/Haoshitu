package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.sj.yinjiaoyun.shituwang.utils.PopName;
import com.sj.yinjiaoyun.shituwang.utils.PopWorkState;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.DoubleDatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 工作经历的界面
 */
public class WorkExperienceActivity extends AppCompatActivity {

    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_post_name)
    TextView tvPostName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_industry)
    TextView tvCompanyIndustry;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bt_delete)
    Button btDelete;
    //工作类型
    private PopWorkState popWorkState;
    //工作类型的数据源
    private List<Children> list = new ArrayList<>();
    //职位名称的弹框
    private PopName popName;
    private String postName = "";
    private String companyName = "";
    //工作描述
    private String description="";
    //包含工作经历信息的字符串
    private StudentRecordBean.DataBean.ResumesBean resumesBean ;
    //工作类型
    private String workType="";
    //职位类型
    private String positionType="";
    //公司行业
    private String companyBusiness="";

    //期望行业的回调
    private Children childrenBusiness;
    //职位类型回调
    private Children childreType;
    //开始时间
    private String beginTime="";
    //结束时间
    private String endTime="";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    //登录的id
    private int id;
    private String time1="",time2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        list.clear();
        //工作类型的数据源
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0007".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }

        }
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(WorkExperienceActivity.this, Const.ID);
        popWorkState = new PopWorkState(WorkExperienceActivity.this);
        popName = new PopName(WorkExperienceActivity.this);
        Bundle bundle = getIntent().getExtras();
        resumesBean = null;
        if (bundle != null) {
            resumesBean = (StudentRecordBean.DataBean.ResumesBean) bundle.getSerializable("resume");
            if (resumesBean == null) {
                 btDelete.setVisibility(View.GONE);
                return;
            }
            String resume = resumesBean.getResume();
            if (resume != null && !TextUtils.isEmpty(resume)) {
                Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                }.getType());
                //工作类型
                workType = mapstr.get("workType");
                if (MyApplication.map != null) {
                    if (!TextUtils.isEmpty(workType)) {
                        String[] types = workType.split(",");
                        //展示工作类型
                        tvWork.setText(MyApplication.map.get(types[types.length - 1]).getName());
                    }
                }
                //职位类型
                positionType = mapstr.get("positionType");
                if (MyApplication.map != null) {
                    if (!TextUtils.isEmpty(positionType)) {
                        String[] positionTypes = positionType.split(",");
                        //展示工作类型
                        tvPost.setText(MyApplication.map.get(positionTypes[positionTypes.length - 1]).getName());
                    }
                }
                //职位名称
                postName = mapstr.get("positionName");
                tvPostName.setText(postName);
                //公司的名字
                companyName = mapstr.get("companyName");
                tvCompanyName.setText(companyName);
                //公司行业
                companyBusiness = mapstr.get("companyBusiness");
                if (MyApplication.map != null) {
                    if (!TextUtils.isEmpty(companyBusiness)) {
                        String[] Business = companyBusiness.split(",");
                        tvCompanyIndustry.setText(MyApplication.map.get(Business[Business.length - 1]).getName());
                    }
                }
                // 任职时间
                beginTime = mapstr.get("beginTime");
                endTime = mapstr.get("endTime");
                if (endTime != null&&!TextUtils.isEmpty(endTime)) {
                    Logger.d("beginTime=" + beginTime + ",endTime=" + endTime);
                    tvPay.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至" + TimeRender.getFormat(Long.parseLong(endTime)));
                } else {
                    Logger.d("beginTime=" + beginTime);
                    tvPay.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至 至今");
                }
                //工作描述
                description = mapstr.get("workDesc");
                if (description != null && !TextUtils.isEmpty(description)) {
                    tvSelf.setText("[已填写]");
                } else {
                    tvSelf.setText("[未填写]");
                }
            }
        }
    }


    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, WorkExperienceActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_work, R.id.rl_post, R.id.rl_post_name, R.id.rl_company_name, R.id.rl_company_industry, R.id.rl_pay, R.id.rl_self, R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_work:
                popWorkState.pop(rl, list, new ChildrenCallBack() {
                    @Override
                    public void setChildren(Children content) {
                        workType=content.getParentCode()+","+content.getCode();
                        tvWork.setText(content.getName());
                    }
                });
                break;
            case R.id.rl_post:
                //期望职位
                startActivityForResult(new Intent(WorkExperienceActivity.this, ExpectedPositionActivity.class), 0);
                break;
            case R.id.rl_post_name:
                popName.pop(rl, "请输入职位名称", postName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()>10){
                            ToastUtil.showShortToast(WorkExperienceActivity.this,"职位名称不能超过10个字符");
                            return;
                        }
                        tvPostName.setText(content);
                    }
                });
                break;
            case R.id.rl_company_name:
                popName.pop(rl, "请输入公司名称", companyName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()>15){
                            ToastUtil.showShortToast(WorkExperienceActivity.this,"公司名称不能超过15个字符");
                            return;
                        }
                        tvCompanyName.setText(content);
                    }
                });
                break;
            case R.id.rl_company_industry:
                //期望行业
                startActivityForResult(new Intent(WorkExperienceActivity.this, ExpectIndustryActivity.class), 2);
                break;
            case R.id.rl_pay:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
               DoubleDatePickerDialog doubleDatePickerDialog= new DoubleDatePickerDialog(WorkExperienceActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String startmonth="";
                        String endMonth="";
                        if((startMonthOfYear+1)<10){
                            startmonth="0"+(startMonthOfYear + 1);
                        }else{
                            startmonth=""+(startMonthOfYear + 1);
                        }
                        if((endMonthOfYear + 1)<10){
                            endMonth="0"+(endMonthOfYear + 1);
                        }else{
                            endMonth=""+(endMonthOfYear + 1);

                        }
                         time1=startYear + "-" + startmonth;
                         time2=endYear + "-" + endMonth;
                        try {
                            if(sdf.parse(time1).getTime()/1000>=sdf.parse(time2).getTime()/1000){
                                ToastUtil.showShortToast(WorkExperienceActivity.this,"结束时间不能为开始时间之前");
                                return;
                            }
                            beginTime = String.valueOf(sdf.parse(time1).getTime()/1000);
                            if(time2.equals(sdf.format(new Date()))){
                                time2="至今";
                            }else{
                                endTime= String.valueOf(sdf.parse(time2).getTime()/1000);
                            }
                            String textString = time1 + " 至 " + time2;
                            tvPay.setText(textString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false);
               doubleDatePickerDialog.show();
                break;
            case R.id.rl_self:
                Intent intent = new Intent(WorkExperienceActivity.this, WorkDescriptionActivity.class);
                intent.putExtra("description", description);
                startActivityForResult(intent, 1);
                break;
            case R.id.bt_save:
                saveWork();
                break;
            case R.id.bt_delete:
                deleteWork();
                break;
        }
    }

    /**
     * 添加或者更新    学生工作经验（3）
     */
    private void saveWork() {
        if(TextUtils.isEmpty(tvWork.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请选择工作类型");
            return;
        }
        if(TextUtils.isEmpty(tvPost.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请选择职位类型");
            return;
        }
        if(TextUtils.isEmpty(tvPostName.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请输入职位名称");
            return;
        }
        if(TextUtils.isEmpty(tvCompanyName.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请输入公司名称");
            return;
        }
        if(TextUtils.isEmpty(tvCompanyIndustry.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请选择公司行业");
            return;
        }
        if("请选择 至 请选择".equals(tvPay.getText().toString().trim())){
            ToastUtil.showShortToast(WorkExperienceActivity.this,"请选择时间");
            return;
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        if (resumesBean != null) {
            map.put("id", String.valueOf(resumesBean.getId()));
        }
        map.put("stuUserId",String.valueOf(id));
        map.put("beginTime",beginTime);
        if(endTime==null){
            endTime="";
        }
        if(time2.equals("至今")){
            map.put("endTime","");
        }else {
            map.put("endTime",endTime);
        }
        map.put("workType",workType);
        map.put("positionType",positionType);
        map.put("positionName",tvPostName.getText().toString().trim());
        map.put("companyName",tvCompanyName.getText().toString().trim());
        map.put("companyBusiness",companyBusiness);
        if(description==null){
            description="";
        }
        map.put("workDesc",description);
        HttpClient.post(this, Api.ADD_UPDATE_WORK, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(WorkExperienceActivity.this,"保存成功");
                    finish();
                }else {
                    ToastUtil.showShortToast(WorkExperienceActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(WorkExperienceActivity.this,message);
            }

        });
    }

    /**
     * 删除工作经历
     */
    private void deleteWork() {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        if (resumesBean != null) {
            map.put("stuUserId", String.valueOf(id));
            map.put("id", String.valueOf(resumesBean.getId()));
            HttpClient.post(this, Api.DELETE_RESUME_BY_ID, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        ToastUtil.showShortToast(WorkExperienceActivity.this,"保存成功");
                        finish();
                    } else {
                        ToastUtil.showShortToast(WorkExperienceActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(WorkExperienceActivity.this,message);
                }

            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    childreType = (Children) bundle.getSerializable(Const.CHILDREN);
                    assert childreType != null;
                    tvPost.setText(childreType.getName());
                    Logger.d("chlidren" + childreType.toString());
                    if(MyApplication.map!=null){
                        //公司类型
                        if(childreType!=null){
                            Children postChildre=MyApplication.map.get(childreType.getParentCode());
                            positionType="R0001"+","+postChildre.getParentCode()+","+childreType.getParentCode()+","+childreType.getCode();
                        }
                    }
                }
            }
        } else if (requestCode == 1) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                description = bundle.getString("self");
                Logger.d("自我描述:" + description);
                if (description != null && !TextUtils.isEmpty(description)) {
                    tvSelf.setText("[已填写]");
                } else {
                    tvSelf.setText("[未填写]");
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    childrenBusiness = (Children) bundle.getSerializable(Const.CHILDREN);
                    assert childrenBusiness != null;
                    tvCompanyIndustry.setText(childrenBusiness.getName());
                    if(MyApplication.map!=null){
                        //公司行业
                        if(childrenBusiness!=null){
                            companyBusiness ="R0002"+","+childrenBusiness.getParentCode()+","+childrenBusiness.getCode();
                        }

                    }
                    Logger.d("chlidren" + childrenBusiness.toString());
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

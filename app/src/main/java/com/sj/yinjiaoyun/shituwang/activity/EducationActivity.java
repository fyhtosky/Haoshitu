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
 * 教育经历的界面
 */
public class EducationActivity extends AppCompatActivity {
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bt_delete)
    Button btDelete;
    //包含教育经历信息的字符串
    private StudentRecordBean.DataBean.ResumesBean resumesBean;
    //姓名选择器
    private PopName popName;
    //学校
    private String shcool = "";
    //专业
    private String majorName = "";
    //学历
    private String education = "";
    //学历选择
    private PopWorkState popWorkState;
    //学历选择的数据源
    private List<Children> list = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    //登录的id
    private int id;
    //开始时间
    private String beginTime=" ";
    //结束时间
    private String endTime="";
    private String time1="",time2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(EducationActivity.this, Const.ID);
        list.clear();
        //学历选择数据源
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0006".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }
        }
        popName = new PopName(EducationActivity.this);
        popWorkState = new PopWorkState(EducationActivity.this);
        Bundle bundle = getIntent().getExtras();
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
                shcool = mapstr.get("collegeName");
                //展示学校
                tvSchool.setText(shcool);
                //专业
                majorName = mapstr.get("majorName");
                tvMajor.setText(majorName);
                //展示学历
                education = mapstr.get("diplomas");
                if (MyApplication.map != null) {
                    if (!TextUtils.isEmpty(education)) {
                        String[] educations = education.split(",");
                        tvEducation.setText(MyApplication.map.get(educations[educations.length - 1]).getName());
                    }
                }
                beginTime = mapstr.get("beginTime");
                endTime = mapstr.get("endTime");
                //展示就读时间
                if (endTime != null&& !TextUtils.isEmpty(endTime)) {
                    Logger.d("beginTime=" + beginTime + ",endTime=" + endTime);
                    tvPay.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至" + TimeRender.getFormat(Long.parseLong(endTime)));
                } else {
                    Logger.d("beginTime=" + beginTime);
                    tvPay.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至 至今");
                }
            }
        }
    }

    /*
 * 启动Activity
 * @param context
 */
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, EducationActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_school, R.id.rl_major, R.id.rl_education, R.id.rl_pay, R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            //编辑学校名称
            case R.id.rl_school:
                popName.pop(rl, "请输入学校名称", shcool, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                       if(content.length()>12){
                           ToastUtil.showShortToast(EducationActivity.this,"学校名称不能超过12个字符");
                           return;
                       }
                        tvSchool.setText(content);
                    }
                });
                break;
            //编辑专业名称
            case R.id.rl_major:
                popName.pop(rl, "请输入专业名称", majorName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()>12){
                            ToastUtil.showShortToast(EducationActivity.this,"专业名称不能超过12个字符");
                            return;
                        }
                        tvMajor.setText(content);
                    }
                });
                break;
            //选择学历
            case R.id.rl_education:
                popWorkState.pop(rl, list, new ChildrenCallBack() {
                    @Override
                    public void setChildren(Children content) {
                        education=content.getParentCode()+","+content.getCode();
                        tvEducation.setText(content.getName());
                    }

                });
                break;
            case R.id.rl_pay:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(EducationActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String startmonth;
                        String endMonth;
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
                                ToastUtil.showShortToast(EducationActivity.this,"结束时间不能为开始时间之前");
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
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
                break;
            case R.id.bt_save:
                saveEducation();
                break;
            case R.id.bt_delete:
                deleteEducation();
                break;
        }
    }

    /**
     * 删除教育经历
     */
    private void deleteEducation() {
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
                        ToastUtil.showShortToast(EducationActivity.this,"删除成功");
                        finish();
                    } else {
                        ToastUtil.showShortToast(EducationActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(EducationActivity.this,message);
                }

            });
        }
    }

    /**
     * 保存教育经历
     */
    private void saveEducation() {
            String time = tvPay.getText().toString().trim();
            if(TextUtils.isEmpty(tvSchool.getText().toString().trim())){
                ToastUtil.showShortToast(EducationActivity.this,"请输入学校名称");
                return;
            }
            if(TextUtils.isEmpty(tvMajor.getText().toString().trim())){
                ToastUtil.showShortToast(EducationActivity.this,"请输入专业名称");
                return;
            }
            if(TextUtils.isEmpty(tvEducation.getText().toString().trim())){
                ToastUtil.showShortToast(EducationActivity.this,"请选择学历");
                return;
            }
           if("请选择 至 请选择".equals(time)){
               ToastUtil.showShortToast(EducationActivity.this,"请选择时间");
               return;
           }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        Logger.d("beginTime="+beginTime+",endTime="+endTime);
            HashMap<String, String> map = new HashMap<>();
            if (resumesBean != null) {
                map.put("id", String.valueOf(resumesBean.getId()));
            }
            map.put("stuUserId",String.valueOf(id));
            map.put("beginTime",beginTime);
            if(endTime==null){
                endTime="";
             }
            if("至今".equals(time2)){
                map.put("endTime","");
            }else{
                map.put("endTime",endTime);
            }

            map.put("collegeName",tvSchool.getText().toString().trim());
            map.put("majorName",tvMajor.getText().toString().trim());
            map.put("diplomas",education);
            HttpClient.post(this, Api.ADD_UODATE_EDUCATION, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                     if(result==null){
                         return;
                     }
                    if(result.getStatus()==200){
                        ToastUtil.showShortToast(EducationActivity.this,"保存成功");
                        finish();
                    }else {
                        ToastUtil.showShortToast(EducationActivity.this,result.getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(EducationActivity.this,message);
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

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
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StudentRecordBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopName;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;
import com.sj.yinjiaoyun.shituwang.view.DoubleDatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 培训经历的界面
 */
public class TrainExperienceActivity extends AppCompatActivity {

    @BindView(R.id.tv_train_agency)
    TextView tvTrainAgency;
    @BindView(R.id.tv_train_time)
    TextView tvTrainTime;
    @BindView(R.id.tv_train_course)
    TextView tvTrainCourse;
    @BindView(R.id.tv_lcs_name)
    TextView tvLcsName;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bt_delete)
    Button btDelete;
    //培训界面的弹框
    private PopName popName;
    //包含培训经验的字符串
    private StudentRecordBean.DataBean.ResumesBean resumeBean;
    //培训机构
    private String trainingOrgName;
    //培训课程
    private String trainingCourseName;
    //证书名称
    private String trainingCertificateName;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    //登录的id
    private int id;
    private String beginTime="";
    private String endTime="";
    private String time1="",time2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_experience);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(TrainExperienceActivity.this, Const.ID);
        popName = new PopName(TrainExperienceActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resumeBean = (StudentRecordBean.DataBean.ResumesBean) bundle.getSerializable("resume");
            if(resumeBean==null){
                btDelete.setVisibility(View.GONE);
                return;
            }
            String resume = resumeBean.getResume();
            if (resume != null && !TextUtils.isEmpty(resume)) {
                Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                }.getType());
                //培训机构
                trainingOrgName = mapstr.get("trainingOrgName");
                tvTrainAgency.setText(trainingOrgName);
                //培训时间
                 beginTime = mapstr.get("beginTime");
                endTime = mapstr.get("endTime");
                if (endTime != null&& !TextUtils.isEmpty(endTime)) {
                    tvTrainTime.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + " 至 " + TimeRender.getFormat(Long.parseLong(endTime)));
                } else {
                    tvTrainTime.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + " 至 至今");
                }
                //培训课程
                trainingCourseName = mapstr.get("trainingCourseName");
                tvTrainCourse.setText(trainingCourseName);
                //证书名称
                trainingCertificateName = mapstr.get("trainingCertificateName");
                tvLcsName.setText(trainingCertificateName);
            }
        }
    }

    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, TrainExperienceActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_train_agency, R.id.rl_train_time, R.id.rl_train_course, R.id.rl_lcs_name, R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_train_agency:
                popName.pop(rl, "请输入培训机构名称", tvTrainAgency.getText().toString().trim(), new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvTrainAgency.setText(content);
                    }
                });
                break;
            case R.id.rl_train_time:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(TrainExperienceActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

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
                                ToastUtil.showShortToast(TrainExperienceActivity.this,"结束时间不能为开始时间之前");
                                return;
                            }
                            beginTime = String.valueOf(sdf.parse(time1).getTime()/1000);
                            if(time2.equals(sdf.format(new Date()))){
                                time2="至今";
                            }else{
                                endTime= String.valueOf(sdf.parse(time2).getTime()/1000);
                            }
                            String textString = time1 + " 至 " + time2;
                            tvTrainTime.setText(textString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
                break;
            case R.id.rl_train_course:
                popName.pop(rl, "请输入课程名称", tvTrainCourse.getText().toString().trim(), new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvTrainCourse.setText(content);
                    }
                });
                break;
            case R.id.rl_lcs_name:
                popName.pop(rl, "请输入证书名称", tvLcsName.getText().toString().trim(), new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvLcsName.setText(content);
                    }
                });
                break;
            case R.id.bt_save:
                updateTrain();
                break;
            case R.id.bt_delete:
                deleteTrain();
                break;
        }
    }

    /**
     * 更新或者保存培训经历
     */
    private void updateTrain() {
        if (TextUtils.isEmpty(tvTrainAgency.getText().toString().trim())) {
            ToastUtil.showShortToast(TrainExperienceActivity.this, "请输入培训机构名称");
            return;
        }
        if("请选择 至 请选择".equals(tvTrainTime.getText().toString().trim())){
            ToastUtil.showShortToast(TrainExperienceActivity.this,"请选择时间");
            return;
        }
        if (TextUtils.isEmpty(tvTrainCourse.getText().toString().trim())) {
            ToastUtil.showShortToast(TrainExperienceActivity.this, "请输入课程名称");
            return;
        }
        if (TextUtils.isEmpty(tvLcsName.getText().toString().trim())) {
            ToastUtil.showShortToast(TrainExperienceActivity.this, "请输入证书名称");
            return;
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        if (resumeBean != null) {
            map.put("id", String.valueOf(resumeBean.getId()));
        }
        map.put("stuUserId",String.valueOf(id));
        map.put("beginTime",beginTime);
        if(endTime==null){
            endTime="";
        }
        if("至今".equals(time2)){
            map.put("endTime","");
        }else {
            map.put("endTime",endTime);
        }
        map.put("trainingOrgName",tvTrainAgency.getText().toString().trim());
        map.put("trainingCourseName",tvTrainCourse.getText().toString().trim());
        map.put("trainingCertificateName",tvLcsName.getText().toString().trim());
        HttpClient.post(this, Api.ADD_UPDATE_TRAIN, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    finish();
                }else {
                    ToastUtil.showShortToast(TrainExperienceActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(TrainExperienceActivity.this,message);
            }

        });
    }

    /**
     * 删除培训经历
     */
    private void deleteTrain() {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        if (resumeBean != null) {
            map.put("stuUserId", String.valueOf(id));
            map.put("id", String.valueOf(resumeBean.getId()));
            HttpClient.post(this, Api.DELETE_RESUME_BY_ID, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        finish();
                    } else {
                        ToastUtil.showShortToast(TrainExperienceActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(TrainExperienceActivity.this,message);
                }

            });
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

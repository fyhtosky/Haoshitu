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

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师端的工作实习经历的界面
 */
public class TeachterWorkActivity extends AppCompatActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_post_name)
    TextView tvPostName;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean bean;
    private String beginTime="";
    private String endTime="";
    private String companyName="";
    private String post="";
    //业绩描述
    private String positionDesc="";
    //职位名称的弹框
    private PopName popName;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    //登录的id
    private int id;
    private String time1="",time2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachter_work);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(TeachterWorkActivity.this, Const.ID);
        popName=new PopName(TeachterWorkActivity.this);
        Bundle bundle = getIntent().getExtras();
        bean = null;
        if (bundle != null) {
            bean = (TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean) bundle.getSerializable("resume");
            if (bean == null) {
                btDelete.setVisibility(View.GONE);
                return;
            }
            beginTime =bean.getBeginDate();
            if(bean.getEndDate()!=null && !TextUtils.isEmpty(bean.getBeginDate())){
                endTime = bean.getEndDate();
            }else{
                endTime = "";
            }

            //显示工作的时间
                if(!TextUtils.isEmpty(beginTime)&& !TextUtils.isEmpty(endTime)){
                    tvTime.setText(TimeRender.getFormat(Long.parseLong(beginTime))+"-"+
                            TimeRender.getFormat(Long.parseLong(endTime)));
                }else{
                    if(!TextUtils.isEmpty(beginTime)){
                        tvTime.setText(TimeRender.getFormat(Long.parseLong(beginTime))+"-至今");
                    }
                }
           //显示所在公司
           companyName=bean.getCompanyName();
            if(!TextUtils.isEmpty(companyName)){
                tvCompany.setText(companyName);
            }
            //职位名称
            post=bean.getPosition();
            if(!TextUtils.isEmpty(post)){
                tvPostName.setText(post);
            }
            //业绩描述
            positionDesc=bean.getPositionDesc();
            if(!TextUtils.isEmpty(positionDesc)){
                tvSelf.setText("[已填写]");
            }else{
                tvSelf.setText("[非必填]");
            }
        }

    }


    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean resume) {
        Intent intent = new Intent(context, TeachterWorkActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back, R.id.rl_time, R.id.rl_company, R.id.rl_post_name, R.id.rl_self,R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_time:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(TeachterWorkActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

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
                                ToastUtil.showShortToast(TeachterWorkActivity.this,"结束时间不能为开始时间之前");
                                return;
                            }
                            beginTime = String.valueOf(sdf.parse(time1).getTime()/1000);
                            if(time2.equals(sdf.format(new Date()))){
                                time2="至今";
                            }else{
                                endTime= String.valueOf(sdf.parse(time2).getTime()/1000);
                            }
                            String textString = time1 + " 至 " + time2;
                            tvTime.setText(textString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
                break;
            case R.id.rl_company:
                popName.pop(rl, "请输入公司名称", companyName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()<=15){
                            tvCompany.setText(content);
                        }else {
                            ToastUtil.showShortToast(TeachterWorkActivity.this,"公司名称不能超过15个字符");
                        }

                    }
                });
                break;
            case R.id.rl_post_name:
                popName.pop(rl, "请输入职位名称", post, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()<=10){
                            tvPostName.setText(content);
                        }else {
                            ToastUtil.showShortToast(TeachterWorkActivity.this,"职位名称不能超过10个字符");
                        }

                    }
                });
                break;
            case R.id.rl_self:
                Intent intent=new Intent(TeachterWorkActivity.this,PerformanceDescriptionActivity.class);
                intent.putExtra("description",positionDesc);
                startActivityForResult(intent,1);
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
     * 删除老师的工作经历
     */
    private void deleteWork() {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
        HashMap<String,String>map=new HashMap<>();
        if (bean != null) {
            map.put("tchTeacherId", String.valueOf(id));
            map.put("id", String.valueOf(bean.getResumeId()));
            HttpClient.post(this, Api.DELETE_TEACHTER_WORK, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        finish();
                    } else {
                        ToastUtil.showShortToast(TeachterWorkActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(TeachterWorkActivity.this,message);
                }

            });
        }
    }

    /**
     * 保存老师的工作经历
     */
    private void saveWork() {
        if("请选择 至 请选择".equals(tvTime.getText().toString().trim())){
            ToastUtil.showShortToast(TeachterWorkActivity.this,"请选择时间");
            return;
        }
        if(TextUtils.isEmpty(tvCompany.getText().toString().trim())){
            ToastUtil.showShortToast(TeachterWorkActivity.this,"请输入公司名称");
            return;
        }
        if(TextUtils.isEmpty(tvPostName.getText().toString().trim())){
            ToastUtil.showShortToast(TeachterWorkActivity.this,"请输入职位名称");
            return;
        }
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
          companyName=tvCompany.getText().toString().trim();
         post=tvPostName.getText().toString().trim();
        HashMap<String,String>map=new HashMap<>();
        if (bean != null) {
            map.put("id", String.valueOf(bean.getResumeId()));
        }
        map.put("beginDate",beginTime);
        if(time2.equals("至今")){
            map.put("endDate","");
        }else {
            map.put("endDate",endTime);
        }
        map.put("resumeType","2");
        map.put("companyName",companyName);
        map.put("position",post);
        map.put("tchTeacherId",String.valueOf(id));
        map.put("positionDesc",positionDesc);
        HttpClient.post(this, Api.UODATE_TEACHTER_WORK, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    finish();
                }else {
                    ToastUtil.showShortToast(TeachterWorkActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(TeachterWorkActivity.this,message);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                positionDesc=bundle.getString("self");
                Logger.d("业绩描述:"+positionDesc);
                if (positionDesc != null && !TextUtils.isEmpty(positionDesc)) {
                    tvSelf.setText("[已填写]");
                } else {
                    tvSelf.setText("[非必填]");
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

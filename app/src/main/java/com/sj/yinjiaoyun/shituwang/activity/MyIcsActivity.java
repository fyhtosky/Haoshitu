package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
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
 * 我的证书的界面
 */
public class MyIcsActivity extends AppCompatActivity {

    @BindView(R.id.tv_ics_name)
    TextView tvIcsName;
    @BindView(R.id.tv_lcs_time)
    TextView tvLcsTime;
    @BindView(R.id.tv_lcs_unit)
    TextView tvLcsUnit;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bt_delete)
    Button btDelete;
    //登录的id
    private int id;
    //包含我的证书信息的字符串
    private StudentRecordBean.DataBean.ResumesBean resumeBean;
    //学校的学则
    private PopName popName;
    //证书名称
    private String lcsName = "";
    //发证单位
    private String unit = "";
    //日期和时间选择对话框
    private OptionsPickerView pvNoLinkOptions;
    private List<String> years = new ArrayList<>();
    private List<String> months = new ArrayList<>();
    private List<String> days = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    private int year;
    private  Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ics);
        ButterKnife.bind(this);
        init();
        getNoLinkData();
        initTimePicker();
    }

    private void init() {
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(MyIcsActivity.this, Const.ID);
        popName = new PopName(MyIcsActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resumeBean = (StudentRecordBean.DataBean.ResumesBean) bundle.getSerializable("resume");
            assert resumeBean != null;
            Logger.d("传递对象：" + resumeBean.toString());
            String resume = resumeBean.getResume();
            if (resume != null && !TextUtils.isEmpty(resume)) {
                Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                }.getType());
                //证书名称
                lcsName = mapstr.get("certificateName");
                tvIcsName.setText(lcsName);
                //发证时间
                String awardTime = mapstr.get("awardTime");
                Logger.d("awardTime=" + awardTime);
                tvLcsTime.setText(TimeRender.getFormat(Long.parseLong(awardTime)));
                //发证单位
                unit = mapstr.get("certificateOrgName");
                tvLcsUnit.setText(unit);


            }
        }else{
            btDelete.setVisibility(View.GONE);
        }
    }

    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, MyIcsActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    /***
     * 时间选择器的数据源
     */
    private void getNoLinkData() {
        for (int i = 1950; i <= year; i++) {
            years.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                months.add("0" + String.valueOf(i));
            } else {
                months.add(String.valueOf(i));
            }

        }
        for (int i = 1; i <= 30; i++) {
            if (i < 10) {
                days.add("0" + String.valueOf(i));
            } else {
                days.add(String.valueOf(i));
            }
        }

    }

    /**
     * 时间的选择器
     */
    private void initTimePicker() {
        pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = years.get(options1) + "-"
                        + months.get(option2);
                tvLcsTime.setText(tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setSubmitColor(Color.parseColor("#24C789"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#a0a0a0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#eeeeee"))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("", "", "")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(10, 5, 10)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvNoLinkOptions.setNPicker(years, months, days);//添加数据源

    }

    @OnClick({R.id.rl_back, R.id.rl_ics_name, R.id.rl_lcs_time, R.id.rl_lcs_unit, R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_ics_name:
                popName.pop(rl, "请输入证书名称", lcsName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvIcsName.setText(content);
                    }
                });
                break;
            case R.id.rl_lcs_time:
                pvNoLinkOptions.show();
                break;
            case R.id.rl_lcs_unit:
                popName.pop(rl, "请输入发证单位名称", unit, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvLcsUnit.setText(content);
                    }
                });
                break;
            case R.id.bt_save:
                addorUpdateCertificate();
                break;
            case R.id.bt_delete:
                deleteCertificate();
                break;
        }
    }

    /**
     * 删除我的证书
     */
    private void deleteCertificate() {
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
                        ToastUtil.showShortToast(MyIcsActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(MyIcsActivity.this,message);
                }

            });
        }

    }

    /**
     * 添加或者更新   学生我的证书（6）
     */
    private void addorUpdateCertificate() {
        try {
            String time = tvLcsTime.getText().toString().trim();
            if (TextUtils.isEmpty(tvIcsName.getText().toString().trim())) {
                ToastUtil.showShortToast(MyIcsActivity.this, "请输入证书名称");
                return;
            }
            Date date = sdf.parse(time);
            if (TextUtils.isEmpty(tvLcsUnit.getText().toString().trim())) {
                ToastUtil.showShortToast(MyIcsActivity.this, "请输入发证单位名称");
                return;
            }
            //网络状态判断
            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            if (resumeBean != null) {
                map.put("id", String.valueOf(resumeBean.getId()));
            }
            map.put("stuUserId", String.valueOf(id));
            map.put("certificateName", tvIcsName.getText().toString().trim());
            map.put("certificateOrgName", tvLcsUnit.getText().toString().trim());
            map.put("awardTime", String.valueOf(date.getTime() / 1000));
            HttpClient.post(this, Api.ADD_UPDATE_CERTINFICATE, map, new CallBack<ReturnBean>() {

                @Override
                public void onSuccess(ReturnBean result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getStatus() == 200) {
                        finish();
                    } else {
                        ToastUtil.showShortToast(MyIcsActivity.this, result.getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(MyIcsActivity.this,message);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
            ToastUtil.showShortToast(MyIcsActivity.this, "请选择时间");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

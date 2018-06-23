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
 * 项目经验的界面
 */
public class ProjectExperienceActivity extends AppCompatActivity {

    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.tv_project_describe)
    TextView tvProjectDescribe;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bt_delete)
    Button btDelete;
    //项目名称的弹框
    private PopName popName;
    //项目描述
    private String description="";
    //包含项目经验的字符串
    private StudentRecordBean.DataBean.ResumesBean resumesBean;
    //项目名称
    private String projectName;
    //职位类别
    private String position="";
    //职位类别的回调实体类
    private Children childreType;
    private String beginTime="";
    private String endTime="";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());//小写的mm表示的是分钟
    //登录的id
    private int id;
    private String time1="",time2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_experience);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(ProjectExperienceActivity.this, Const.ID);
        popName = new PopName(ProjectExperienceActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resumesBean = (StudentRecordBean.DataBean.ResumesBean) bundle.getSerializable("resume");
            if(resumesBean==null){
                btDelete.setVisibility(View.GONE);
                return;
            }
            String resume=resumesBean.getResume();
            if (resume != null && !TextUtils.isEmpty(resume)) {
                Map<String, String> mapstr = new Gson().fromJson(resume, new TypeToken<HashMap<String, String>>() {
                }.getType());
                //项目名称
                projectName = mapstr.get("projectName");
                tvProjectName.setText(projectName);
                //项目时间
                 beginTime = mapstr.get("beginTime");
                 endTime = mapstr.get("endTime");
                if (endTime != null && !TextUtils.isEmpty(endTime)) {
                    tvProjectTime.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至 " + TimeRender.getFormat(Long.parseLong(endTime)));
                } else {
                    tvProjectTime.setText(TimeRender.getFormat(Long.parseLong(beginTime)) + "至 至今");
                }
                //项目类别
                position = mapstr.get("position");
                if (MyApplication.map != null) {
                    if (!TextUtils.isEmpty(position)) {
                        String[] positions = position.split(",");
                        tvProjectType.setText(MyApplication.map.get(positions[positions.length - 1]).getName());
                    }
                }
                //项目描述
                description = mapstr.get("projectDesc");
                if (description != null && !TextUtils.isEmpty(description)) {
                    tvProjectDescribe.setText("[已填写]");
                } else {
                    tvProjectDescribe.setText("[未填写]");
                }
            }
        }
    }

    /*
* 启动Activity
* @param context
*/
    public static void StartActivity(Context context, StudentRecordBean.DataBean.ResumesBean resume) {
        Intent intent = new Intent(context, ProjectExperienceActivity.class);
        intent.putExtra("resume", resume);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_project_name, R.id.rl_project_time, R.id.rl_project_type, R.id.rl_project_describe, R.id.bt_save, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_project_name:
                popName.pop(rl, "请输入项目名称", tvProjectName.getText().toString().trim(), new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvProjectName.setText(content);
                    }
                });
                break;
            case R.id.rl_project_time:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(ProjectExperienceActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

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
                                ToastUtil.showShortToast(ProjectExperienceActivity.this,"结束时间不能为开始时间之前");
                                return;
                            }
                            beginTime = String.valueOf(sdf.parse(time1).getTime()/1000);
                            if(time2.equals(sdf.format(new Date()))){
                                time2="至今";
                            }else{
                                endTime= String.valueOf(sdf.parse(time2).getTime()/1000);
                            }
                            String textString = time1 + " 至 " + time2;
                            tvProjectTime.setText(textString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
                break;
            case R.id.rl_project_type:
                //期望职位
                startActivityForResult(new Intent(ProjectExperienceActivity.this, ExpectedPositionActivity.class), 0);
                break;
            case R.id.rl_project_describe:
                Intent intent = new Intent(ProjectExperienceActivity.this, SelfDescriptionActivity.class);
                intent.putExtra("description", description);
                startActivityForResult(intent, 1);
                break;
            case R.id.bt_save:
                saveProject();
                break;
            case R.id.bt_delete:
                deleteProject();
                break;
        }
    }

    /**
     * 添加或者更新   学生项目经验（4）
     */
    private void saveProject() {
      if(TextUtils.isEmpty(tvProjectName.getText().toString().trim())){
          ToastUtil.showShortToast(ProjectExperienceActivity.this,"请输入项目名称");
          return;
      }
        if("请选择 至 请选择".equals(tvProjectTime.getText().toString().trim())){
            ToastUtil.showShortToast(ProjectExperienceActivity.this,"请选择时间");
            return;
        }
        if(TextUtils.isEmpty(tvProjectType.getText().toString().trim())){
            ToastUtil.showShortToast(ProjectExperienceActivity.this,"请选择职位类别");
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
        if("至今".equals(time2)){
            map.put("endTime","");
        }else{
            map.put("endTime",endTime);
        }
        map.put("projectName",tvProjectName.getText().toString().trim());
        map.put("position",position);
        if(description==null){
            description="";
        }
        map.put("projectDesc",description);
        HttpClient.post(this, Api.ADD_UPDATE_PROJECT, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    finish();
                }else {
                    ToastUtil.showShortToast(ProjectExperienceActivity.this,result.getMsg());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtil.showShortToast(ProjectExperienceActivity.this,message);
            }

        });
    }

    /**
     * 删除项目经验
     */
    private void deleteProject() {
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
                        finish();
                    } else {
                        ToastUtil.showShortToast(ProjectExperienceActivity.this, result.getMsg());
                    }
                }
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtil.showShortToast(ProjectExperienceActivity.this,message);
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
                    tvProjectType.setText(childreType.getName());
                    Logger.d("chlidren" + childreType.toString());
                    if(MyApplication.map!=null){
                        //公司类型
                        if(childreType!=null){
                            Children postChildre=MyApplication.map.get(childreType.getParentCode());
                            position="R0001"+","+postChildre.getParentCode()+","+childreType.getParentCode()+","+childreType.getCode();
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
                    tvProjectDescribe.setText("[已填写]");
                } else {
                    tvProjectDescribe.setText("[未填写]");
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

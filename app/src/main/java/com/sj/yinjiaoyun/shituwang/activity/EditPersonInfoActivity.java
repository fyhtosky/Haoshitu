package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.JsonBean;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.GetJsonDataUtil;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopName;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 编辑个人资料
 */
public class EditPersonInfoActivity extends AppCompatActivity {

    @BindView(R.id.CircleImageView)
    de.hdodenhof.circleimageview.CircleImageView CircleImageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rl_edit_info)
    RelativeLayout rlEditInfo;
    private String realName;
    private String address;
    private int birthday;
    private String head;
    private int sex;
    //自我描述
    private String description="";
    //请求码
    public static int requestCode = 0;
    //登录的id
    private int id;
    //   修改姓名的对话框
    private PopName popName;
    //日期和时间选择对话框
    private OptionsPickerView  pvNoLinkOptions;
    //省市区的选择器
    private OptionsPickerView  provincePicker;
    private List<String> years = new ArrayList<>();
    private List<String> months = new ArrayList<>();
    private List<String> days = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
   private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//小写的mm表示的是分钟
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
        init();
        getNoLinkData();
        initTimePicker();
        initJsonData();
        intProvincePicker();
    }

    /**
     * 解析省市区的三级联动
     */
    private void initJsonData() {
        String jsonData = GetJsonDataUtil.getJson(this,"province.json");//获取assets目录下的json文件数据
         options1Items.clear();
        options1Items.addAll(GetJsonDataUtil.parseData(jsonData));
        for (int i=0;i<options1Items.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<options1Items.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = options1Items.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (options1Items.get(i).getCityList().get(c).getArea() == null
                        ||options1Items.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < options1Items.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = options1Items.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
            Logger.d("所有的数据："+options1Items.size()+"，城市："+options2Items.size()+"，区域："+options3Items.size());
        }
    }
    /**
     * 省市区的选择器
     */
    private void  intProvincePicker(){
        provincePicker = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"-"+
                        options2Items.get(options1).get(options2)+"-"+
                        options3Items.get(options1).get(options2).get(options3);
             tvAddress.setText(tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleText("城市选择")
                .setSubmitColor(Color.parseColor("#24C789"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#a0a0a0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#eeeeee"))//标题背景颜色 Night mode
                .setDividerColor(Color.parseColor("#eeeeee"))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setOutSideCancelable(false)// default is true
                .build();


        provincePicker.setPicker(options1Items, options2Items,options3Items);//三级选择器
    }
    /**
     * 时间的选择器
     */
    private void initTimePicker() {
        pvNoLinkOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = years.get(options1)+"-"
                        + months.get(option2)+"-"
                        + days.get(options3);
                tvBirthday.setText(tx);
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

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(EditPersonInfoActivity.this, Const.ID);
        popName = new PopName(EditPersonInfoActivity.this);
        String rgs = ",";
        String ss = "-";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            realName = bundle.getString("realName");
            address = bundle.getString("address");
            birthday = bundle.getInt("birthday");
            head = bundle.getString("head");
            sex = bundle.getInt("sex");
            description = bundle.getString("description");
            if (realName != null) {
                tvName.setText(realName);
            }
            if (address != null && !TextUtils.isEmpty(address)) {
                tvAddress.setText(address.replace(rgs, ss));
            }
            tvBirthday.setText(TimeRender.getBirthday(birthday));
            if (head != null && !TextUtils.isEmpty(head)) {
                PicassoUtils.LoadPathCorners(EditPersonInfoActivity.this, head, 60, 60, CircleImageView);
            } else {
                PicassoUtils.LoadCorners(EditPersonInfoActivity.this, R.drawable.wukong, 60, 60, CircleImageView);
            }
            if (sex == 0) {
                rb1.setChecked(true);
            } else {
                rb2.setChecked(true);
            }

            if (description != null && !TextUtils.isEmpty(description)) {
                tvSelf.setText("[已填写]");
            } else {
                tvSelf.setText("[未填写]");
            }
            rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb1:
                            rb1.setChecked(true);
                            sex=0;
                            break;
                        case R.id.rb2:
                            rb2.setChecked(true);
                            sex=1;
                            break;
                    }
                }
            });
        }
    }

    /*
    * 启动Activity
    * @param context
    */
    public static void StartActivity(Context context, String realName, int birthday, String head, String address, int sex, String description) {
        Intent intent = new Intent(context, EditPersonInfoActivity.class);
        intent.putExtra("realName", realName);
        intent.putExtra("birthday", birthday);
        intent.putExtra("head", head);
        intent.putExtra("address", address);
        intent.putExtra("sex", sex);
        intent.putExtra("description", description);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.rl_head, R.id.rl_name, R.id.rl_birthday, R.id.rl_address, R.id.rl_self, R.id.bt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_head:
                startActivityForResult(new Intent(EditPersonInfoActivity.this, ChangeHeadPictureActivity.class), requestCode);
                break;
            case R.id.rl_name:
                popName.pop(rlEditInfo,"请输入姓名" ,realName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()>5){
                            ToastUtil.showShortToast(EditPersonInfoActivity.this,"姓名为5个字符以内");
                            return;
                        }
                        tvName.setText(content);
                    }
                });
                break;
            case R.id.rl_birthday:
                pvNoLinkOptions.show();
                break;
            case R.id.rl_address:
                provincePicker.show();
                break;
            case R.id.rl_self:
                if(description==null){
                    description="";
                }
                Intent intent=new Intent(EditPersonInfoActivity.this,SelfDescriptionActivity.class);
                intent.putExtra("description",description);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_save:
                //提交个人资料
                updateBaseInfo();
                break;
        }
    }

    /**
     * 提交资料的界面
     */
    private void updateBaseInfo() {
        try {
            String time=tvBirthday.getText().toString().trim();
             if(TextUtils.isEmpty(time)){
                 ToastUtil.showShortToast(EditPersonInfoActivity.this,"请选择出生年月");
                 return;
             }
            if(TextUtils.isEmpty(tvAddress.getText().toString().trim())){
                ToastUtil.showShortToast(EditPersonInfoActivity.this,"请选择居住地");
                return;
            }
            if(description==null || TextUtils.isEmpty(description)){
                ToastUtil.showShortToast(EditPersonInfoActivity.this,"请填写自我描述");
                return;
            }
            //网络状态判断
            if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
                NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
                return;
            }
            HashMap<String,String>map=new HashMap<>();
            map.put("id",String.valueOf(id));
            map.put("head",head);
            map.put("realName",tvName.getText().toString().trim());
            map.put("sex",String.valueOf(sex));
            map.put("birthday",String.valueOf(sdf.parse(time).getTime()/1000));
            map.put("addressNow",tvAddress.getText().toString().trim().replace("-",","));
            map.put("description",description);
            HttpClient.post(this, Api.UPDATE_STUDENT_BASE_INFO, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                     if(result==null){
                         return;
                     }
                    if(result.getStatus()==200){
                        //修改的用户名则本地保存
                        PreferencesUtils.putSharePre(EditPersonInfoActivity.this,Const.NAME,tvName.getText().toString());
                        EditPersonInfoActivity.this.finish();
                    }else{
                        ToastUtil.showShortToast(EditPersonInfoActivity.this,result.getMsg());
                    }
                }


            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                head = bundle.getString(Const.USERiMG);
                if (head != null && !TextUtils.isEmpty(head)) {
                    PicassoUtils.LoadPathCorners(EditPersonInfoActivity.this, head, 60, 60, CircleImageView);
                } else {
                    PicassoUtils.LoadCorners(EditPersonInfoActivity.this, R.drawable.master, 60, 60, CircleImageView);
                }

            }

        }else if(requestCode==1){
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                description=bundle.getString("self");
                Logger.d("自我描述:"+description);
                if (description != null && !TextUtils.isEmpty(description)) {
                    tvSelf.setText("[已填写]");
                } else {
                    tvSelf.setText("[未填写]");
                }
            }
        }
    }

    /***
     * 时间选择器的数据源
     */
    private void getNoLinkData() {
      for(int i=1950;i<=2010;i++){
          years.add(String.valueOf(i));
      }
        for(int i=1;i<=12;i++){
            if(i<10){
                months.add("0"+String.valueOf(i));
            }else{
                months.add(String.valueOf(i));
            }

        }
        for (int i=1;i<=30;i++){
            if(i<10){
                days.add("0"+String.valueOf(i));
            }else{
                days.add(String.valueOf(i));
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

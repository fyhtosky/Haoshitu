package com.sj.yinjiaoyun.shituwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.activity.ChangeHeadPictureActivity;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PopName;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ProvincePicker;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 老师端的编辑个人资料
 */
public class TeachterEditInfoActivity extends AppCompatActivity {

    @BindView(R.id.CircleImageView)
    de.hdodenhof.circleimageview.CircleImageView CircleImageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    //请求码
    public static int requestCode = 0;
    @BindView(R.id.rl)
    RelativeLayout rl;
    //登录的id
    private int id;
    //   修改姓名的对话框
    private PopName popName;
    private String realName="";
    private String head="";
    //性别的标示
    private String sex="";
    private String address="";
    //公司的名字
    private String companyName;
    //城市选择器
    private ProvincePicker provincePicker;
    private String post="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachter_edit_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id = PreferencesUtils.getSharePreInt(TeachterEditInfoActivity.this, Const.ID);
        provincePicker = new ProvincePicker(TeachterEditInfoActivity.this);
        popName = new PopName(TeachterEditInfoActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            realName=bundle.getString("realName");
            head=bundle.getString("head");
            sex=bundle.getString("sex");
            post=bundle.getString("positionId");
            address=bundle.getString("address");
            companyName=bundle.getString("companyName");
            //展示用户名
            if(!TextUtils.isEmpty(realName)){
                tvName.setText(realName);
            }
            //展示头像
            if(!TextUtils.isEmpty(head)){
                PicassoUtils.LoadPathCorners(TeachterEditInfoActivity.this, head, 60, 60, CircleImageView);
            }else{
                PicassoUtils.LoadCorners(TeachterEditInfoActivity.this, R.drawable.master, 60, 60, CircleImageView);
            }
            //展示性别
            if ("0".equals(sex)) {
                rb1.setChecked(true);
            } else {
                rb2.setChecked(true);
            }
          //展示职位
            if(!TextUtils.isEmpty(post)){
                tvPost.setText(post);
            }
            //展示地址
            if(!TextUtils.isEmpty(address)){
                tvAddress.setText(address);
            }
            //展示公司
            if(!TextUtils.isEmpty(companyName)){
                tvCompany.setText(companyName);
            }
        }
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        rb1.setChecked(true);
                        sex="0";
                        break;
                    case R.id.rb2:
                        rb2.setChecked(true);
                        sex="1";
                        break;
                }
            }
        });

    }


    /*
  * 启动Activity
  * @param context
  */
    public static void StartActivity(Context context, String realName,  String head, String sex, String address,String post, String companyName) {
        Intent intent = new Intent(context, TeachterEditInfoActivity.class);
        intent.putExtra("realName", realName);
        intent.putExtra("head", head);
        intent.putExtra("sex", sex);
        intent.putExtra("positionId", post);
        intent.putExtra("address", address);
        intent.putExtra("companyName", companyName);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back, R.id.rl_head, R.id.rl_name, R.id.rl_post, R.id.rl_address, R.id.bt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_head:
                startActivityForResult(new Intent(TeachterEditInfoActivity.this, ChangeHeadPictureActivity.class), requestCode);
                break;
            case R.id.rl_name:
                popName.pop(rl, "请输入姓名", realName, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        if(content.length()>5){
                            ToastUtil.showShortToast(TeachterEditInfoActivity.this,"姓名为5个字符以内");
                            return;
                        }
                        tvName.setText(content);
                    }
                });
                break;

            case R.id.rl_post:
                popName.pop(rl, "请输入职位", post, new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvPost.setText(content);
                    }
                });
                break;
            case R.id.rl_address:
                provincePicker.ProvincePickerShow(new PhoneCallBack() {
                    @Override
                    public void setContent(String content) {
                        tvAddress.setText(content);
                    }
                });
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
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
        }
            HashMap<String,String> map=new HashMap<>();
            map.put("id",String.valueOf(id));
            map.put("realName",tvName.getText().toString().trim());
            map.put("addressNow",tvAddress.getText().toString().trim());
            map.put("sex",String.valueOf(sex));
            map.put("position",tvPost.getText().toString().trim());
           if(head==null){
               head="";
           }
            map.put("imgUrl",head);
            HttpClient.post(this, Api.UPDATE_TEACHTER_BASE_INFO, map, new CallBack<ReturnBean>() {
                @Override
                public void onSuccess(ReturnBean result) {
                    if(result==null){
                        return;
                    }
                    if(result.getStatus()==200){
                        //修改的用户名则本地保存
                        PreferencesUtils.putSharePre(TeachterEditInfoActivity.this,Const.NAME,tvName.getText().toString());
                        TeachterEditInfoActivity.this.finish();
                    }else{
                        ToastUtil.showShortToast(TeachterEditInfoActivity.this,result.getMsg());
                    }
                }


            });

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
                    PicassoUtils.LoadPathCorners(TeachterEditInfoActivity.this, head, 60, 60, CircleImageView);
                } else {
                    PicassoUtils.LoadCorners(TeachterEditInfoActivity.this, R.drawable.master, 60, 60, CircleImageView);
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

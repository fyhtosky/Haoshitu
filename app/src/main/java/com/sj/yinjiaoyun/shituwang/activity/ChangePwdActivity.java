package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.AppManager;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.net.NetworkUtils;

/**
 * 修改密码的界面
 */
public class ChangePwdActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    //登录的id
   private int id;
    //标示登录省份(1是学生2是老师)
    private int loginId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取登录的id
        id= PreferencesUtils.getSharePreInt(ChangePwdActivity.this, Const.ID);
        loginId = PreferencesUtils.getSharePreInt(ChangePwdActivity.this, Const.LOGINID);
        Logger.d("loginId=" + loginId+"身份id:"+id);
        etOldPwd.setOnFocusChangeListener(this);
        etNewPwd.setOnFocusChangeListener(this);
        etConfirmPwd.setOnFocusChangeListener(this);
    }

    @OnClick({R.id.rl_back, R.id.bt_confirm_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_confirm_change:
                if(checkOldePwd(etOldPwd.getText().toString().trim())){
                    if(checkPwd(etNewPwd.getText().toString().trim())){
                        if(!TextUtils.isEmpty(etConfirmPwd.getText().toString().trim())){
                            if(etConfirmPwd.getText().toString().trim().equals(etNewPwd.getText().toString().trim())){
                                commitChange();
                            }else{
                                ToastUtil.showShortToast(ChangePwdActivity.this,"新密码和确认密码不一致");
                            }
                        }else{
                            ToastUtil.showShortToast(ChangePwdActivity.this,"请输入确认密码");
                        }
                    }
                }
                break;
        }
    }

    /**
     * 发送请求提交修改密码
     */
    private void commitChange() {
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        if (loginId == 1) {//学生登录
            UpdateStudentPwd();
        } else if (loginId == 2) {//老师登录
            UpdateTeacherPWD();
        }
    }

    /**
     * 修改老师的密码的接口
     */
    private void UpdateTeacherPWD(){
        HashMap<String,String>map=new HashMap<>();
        map.put("oldPwd",etOldPwd.getText().toString().trim());
        map.put("newPwd",etNewPwd.getText().toString().trim());
        map.put("againPwd",etConfirmPwd.getText().toString().trim());
        map.put("isTeacher","true");
        map.put("id",String.valueOf(id));
        HttpClient.post(this, Api.UPDATE_TEACHER_PWD, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(ChangePwdActivity.this,"密码修改成功，即将跳转身份选择界面");
                    //偏好设置存储登录状态
                    PreferencesUtils.putSharePre(ChangePwdActivity.this, Const.LOGIN_STATUS,false);
                    PreferencesUtils.putSharePre(ChangePwdActivity.this,Const.ISSHOW,false);
                    //存储身份标识
                    PreferencesUtils.putSharePre(ChangePwdActivity.this, Const.LOGINID,loginId);
                    AppManager.getAppManager().finishAllActivity();
                    startActivity(new Intent(ChangePwdActivity.this,LoginActivity.class));
                    finish();
                }else{
                    ToastUtil.showShortToast(ChangePwdActivity.this,result.getMsg());
                }
            }


        });
    }

    /**
     * 修改学生的密码接口
     */
    private void  UpdateStudentPwd(){
        HashMap<String,String>map=new HashMap<>();
        map.put("id",String.valueOf(id));
        map.put("oldPwd",etOldPwd.getText().toString().trim());
        map.put("newPwd",etNewPwd.getText().toString().trim());
        map.put("againPwd",etConfirmPwd.getText().toString().trim());
        HttpClient.post(this, Api.UPDATE_STUDENT_PWD, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(ChangePwdActivity.this,"密码修改成功，即将跳转身份选择界面");
                    //偏好设置存储登录状态
                    PreferencesUtils.putSharePre(ChangePwdActivity.this, Const.LOGIN_STATUS,false);
                    PreferencesUtils.putSharePre(ChangePwdActivity.this,Const.ISSHOW,false);
                    AppManager.getAppManager().finishAllActivity();
                    startActivity(new Intent(ChangePwdActivity.this,SelectiveIdentityActivity.class));
                    finish();
                }else{
                    ToastUtil.showShortToast(ChangePwdActivity.this,result.getMsg());
                }
            }


        });
    }
    /**
     * EditText是否获得焦点监听器
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_old_pwd:
                if(!hasFocus){
                    checkOldePwd(etOldPwd.getText().toString().trim());
                }
                break;
            case R.id.et_new_pwd:
                if(!hasFocus){
                    checkPwd(etNewPwd.getText().toString().trim());
                }
                break;
            case R.id.et_confirm_pwd:
                if(!hasFocus){
                    if(checkOldePwd(etOldPwd.getText().toString().trim())){
                        if(checkPwd(etNewPwd.getText().toString().trim())){
                            if(!TextUtils.isEmpty(etConfirmPwd.getText().toString().trim())){
                                if(!etConfirmPwd.getText().toString().trim().equals(etNewPwd.getText().toString().trim())){
                                    ToastUtil.showShortToast(ChangePwdActivity.this,"新密码和确认密码不一致");
                                }
                            }else{
                                ToastUtil.showShortToast(ChangePwdActivity.this,"请输入确认密码");
                            }
                        }
                    }
                }
                break;
        }
    }
    /**
     * 判断旧密码
     * @param password
     * @return
     */
    private boolean checkOldePwd(String password){
        if(TextUtils.isEmpty(password)){
            ToastUtil.showShortToast(ChangePwdActivity.this, "请输入旧密码");
            return false;
        }
        return true;
    }
    /**
     * 校验Editview编辑框密码是否合格
     *
     * @param password
     * @return
     */
    private Boolean checkPwd(String password) {
        int length = password.length();
        if (password.equals("")) {
            ToastUtil.showShortToast(ChangePwdActivity.this, "密码不能为空");
            return false;
        }
        if (length < 6) {
            ToastUtil.showShortToast(ChangePwdActivity.this, "密码不能小于6位");
            return false;
        }
        if (length > 20) {
            ToastUtil.showShortToast(ChangePwdActivity.this, "密码最大支持20个字符");
            return false;
        }
        if (password.indexOf(" ") > 0) {
            ToastUtil.showShortToast(ChangePwdActivity.this, "密码不支持空格");
            return false;
        }
        String regex = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6}$";
        if (!password.matches(regex)) {
            ToastUtil.showShortToast(ChangePwdActivity.this, "密码必须含有字母、数字两种及以上的组合");
            return false;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

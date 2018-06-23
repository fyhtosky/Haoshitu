package com.sj.yinjiaoyun.shituwang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.bean.IPBean;
import com.sj.yinjiaoyun.shituwang.cityPickter.CityPicker;
import com.sj.yinjiaoyun.shituwang.cityPickter.CityPickerListener;
import com.sj.yinjiaoyun.shituwang.event.QueryTeacherEvent;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.MyFragmentHandler;
import com.sj.yinjiaoyun.shituwang.utils.MyIpUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * Created by ${沈军 961784535@qq.com} on 2017/6/16.
 * 学生推荐界面的Fragment
 */
public class StudentRecommendFragment extends Fragment implements CityPickerListener {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_text)
    EditText etText;
    private FragmentManager manager;
    private FindTeacherFragment findTeacherFragment;
    private CityPicker cityPicker;
    private  InputMethodManager imm;
    private String ip="";
    private Handler handler=new MyFragmentHandler(StudentRecommendFragment.this);
    public static StudentRecommendFragment newInstance() {
        Bundle args = new Bundle();
        StudentRecommendFragment fragment = new StudentRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_recommend_fragment, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }
    /**
     * 切换Fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }
    /**
     * 初始化
     */
    private void init() {
        manager = getChildFragmentManager();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        cityPicker = new CityPicker(getActivity(), this);
        findTeacherFragment=FindTeacherFragment.newInstance();
        showFragment(findTeacherFragment);
        new Thread(){
            @Override
            public void run() {
               ip=MyIpUtil.getNetIp();
               handler.sendEmptyMessage(1);
            }
        }.start();
       //点击软键盘上的回车键才会触发
        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //发送消息通知发现老师的界面筛选
                    EventBus.getDefault().post(new QueryTeacherEvent(tvCity.getText().toString().trim(),etText.getText().toString().trim()));
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * 根据网络连接的ip获取当前的位置
     */
    public void getDictionary() {
        String params="?ip="+ ip;
        HttpClient.get(this, Api.GET_DICTIONARY+params, new CallBack<IPBean>() {
            @Override
            public void onSuccess(IPBean result) {
                 if(result==null){
                     return;
                 }
               if(result.getCode()!=1){
                     if("中国".equals(result.getData().getCountry())){
                         tvCity.setText(result.getData().getCity());
                     }else {
                         tvCity.setText("全国");
                     }
                   //发送消息通知发现老师的界面筛选
                   EventBus.getDefault().post(new QueryTeacherEvent(tvCity.getText().toString().trim(),etText.getText().toString().trim()));
                   Logger.d(result.toString());
               }
            }


        });
    }
    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.iv_location,R.id.tv_city})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relativeLayout:
            case R.id.tv_city:
                cityPicker.show();
                break;
        }
    }
    /**
     * 选择城市的回调
     * @param name
     */
    @Override
    public void getCity(String name) {
        if(name==null || name.equals("")){
            return;
        }
        String []provinces=name.split(" ");
        if(name.contains("全省/市")){
            tvCity.setText(name.split(" ")[0]);
        }else if(name.contains("全市/区")){
            tvCity.setText(name.split(" ")[1]);
        }else {
            tvCity.setText(provinces[provinces.length-1]);
        }
        //发送消息通知发现老师的界面筛选
        EventBus.getDefault().post(new QueryTeacherEvent(name,etText.getText().toString().trim()));
    }
    /**
     * 清除Handler防泄漏
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}

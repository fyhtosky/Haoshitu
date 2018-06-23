package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.EvaBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 老师端的查看评价
 */
public class StuLookEvaluationActivity extends AppCompatActivity {

    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tag_trait)
    TagFlowLayout tagTrait;
    @BindView(R.id.rb_deep)
    RatingBar rbDeep;
    @BindView(R.id.tv_deep)
    TextView tvDeep;
    @BindView(R.id.rb_attitude)
    RatingBar rbAttitude;
    @BindView(R.id.tv_attitude)
    TextView tvAttitude;
    @BindView(R.id.rb_ability)
    RatingBar rbAbility;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.tag_skill)
    TagFlowLayout tagSkill;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    //评价的主键id
    private int id;
    //评价项的数据源
    private List<Children> list = new ArrayList<>();
    //选择标签的集合
    private List<Children>codeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_look_evaluation);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        list.clear();
        codeList.clear();
        //老师的评价项
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0011".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }

        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id=bundle.getInt("id");
            getdata();
        }
    }
    /**
     * 获取查看评价的数据
     */
    private void getdata() {
        HashMap<String,String> map=new HashMap<>();
        map.put("id",String.valueOf(id));
        HttpClient.post(this, Api.FIND_EVA_BYE_ID, map, new CallBack<EvaBean>() {
            @Override
            public void onSuccess(EvaBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    String labels = result.getData().getLabels().substring(1, result.getData().getLabels().length() - 1);
                    List<String>codes=getResult(labels);
                    Logger.d("StuLookEvaluationActivity:codes" + codes.toString());
                    for (int i=0;i<list.size();i++){
                        for (int j=0;j<codes.size();j++){
                            if(codes.get(j).equals(list.get(i).getCode())){
                                codeList.add(list.get(i));
                            }
                        }
                    }
                    showDetail(result.getData());
                }else {
                    ToastUtil.showShortToast(StuLookEvaluationActivity.this,result.getMsg());
                }
            }


        });
    }
    /**
     * 展示老师的信息
     * @param dataBean
     */
    private void showDetail(EvaBean.DataBean dataBean) {
        //显示头像
        if(dataBean.getStudent().getHead()!=null&& !TextUtils.isEmpty(dataBean.getStudent().getHead())){
            PicassoUtils.LoadPathCorners(StuLookEvaluationActivity.this,dataBean.getStudent().getHead(),circleView);
        }else{
            PicassoUtils.LoadCorners(StuLookEvaluationActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //名字
        tvName.setText(dataBean.getStudent().getRealName());
        //展示性别
        if (dataBean.getStudent().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        String education="";
        //展示地址
        String address="";
        //地址赋值
        if(dataBean.getStudent().getAddressNow()!=null && !TextUtils.isEmpty(dataBean.getStudent().getAddressNow())){
            address=dataBean.getStudent().getAddressNow().split(",")[0];
        }
        //展示薪资
        String salary="";
        //展示职位
        String post="";
//        档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if(dataBean.getStudent().getResumes()!=null && dataBean.getStudent().getResumes().size()>0){
            for ( EvaBean.DataBean.StudentBean.ResumesBean resumesBean:dataBean.getStudent().getResumes()){
                //展示薪资及职位
                if(resumesBean.getResumeType()==1){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取职位
                    String expectPosition=mapstr.get("expectPosition");
                    //薪资
                    String expectSalary=mapstr.get("expectSalary");
                    if(MyApplication.map!=null){
                        if(expectPosition!=null && !TextUtils.isEmpty(expectPosition)){
                            String []poss=expectPosition.split(",");
                            //期望职位赋值
                            post=MyApplication.map.get(poss[poss.length-1]).getName();
                        }
                        if(expectSalary!=null && !TextUtils.isEmpty(expectSalary)){
                            String []salarys=expectSalary.split(",");
                            //薪资赋值
                            salary=MyApplication.map.get(salarys[salarys.length-1]).getName();
                        }
                    }


                }else if(resumesBean.getResumeType()==2){
                    Map<String, String> mapstr = new Gson().fromJson(resumesBean.getResume(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    //获取学历的code
                    String  diplomas = mapstr.get("diplomas");
                    if(MyApplication.map!=null) {
                        if (diplomas != null && !TextUtils.isEmpty(diplomas)) {
                            String[] dips = diplomas.split(",");
                            //学历赋值
                            education = MyApplication.map.get(dips[dips.length - 1]).getName();
                        }
                    }
                }
            }
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(salary)){
            tvSalary.setText(salary);
        }else {
            tvSalary.setText("");
        }

        //控件显示学历和地址
        if(!TextUtils.isEmpty(education) && !TextUtils.isEmpty(address)){
            tvAddress.setText(education+"|"+address);
        } else  if(TextUtils.isEmpty(education)){
            tvAddress.setText(address);
        }else if(TextUtils.isEmpty(address)){
            tvAddress.setText(education);
        }else {
            tvAddress.setText("");
        }
        //控件展示薪资
        if(!TextUtils.isEmpty(post)){
            tvPost.setText("期望:"+post);
        }else{
            tvPost.setText("");
        }

        //技能标签
        if(dataBean.getStudent().getEvaluationLabels()!=null&& !TextUtils.isEmpty(dataBean.getStudent().getEvaluationLabels())){
            tagTrait.setVisibility(View.VISIBLE);
            String labels=dataBean.getStudent().getEvaluationLabels().substring(1,dataBean.getStudent().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StuLookEvaluationActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            tagTrait.setVisibility(View.GONE);
        }
        //展示维度值
        rbDeep.setRating((float) dataBean.getParameter1());
        String text="";
        switch (dataBean.getParameter1()){
            case 1:
                text="非常差";
                break;
            case 2:
                text="一般";
                break;
            case 3:
                text="一般";
                break;
            case 4:
                text="一般";
                break;
            case 5:
                text="非常好";
                break;
        }
        tvDeep.setText(text);
        rbAttitude.setRating((float) dataBean.getParameter2());
        String text1="";
        switch (dataBean.getParameter2()){
            case 1:
                text1="非常差";
                break;
            case 2:
                text1="一般";
                break;
            case 3:
                text1="一般";
                break;
            case 41:
                text1="一般";
                break;
            case 5:
                text1="非常好";
                break;
        }
        tvAttitude.setText(text1);
        rbAbility.setRating((float) dataBean.getParameter3());
        String text2="";
        switch (dataBean.getParameter3()){
            case 1:
                text2="非常差";
                break;
            case 2:
                text2="一般";
                break;
            case 3:
                text2="一般";
                break;
            case 4:
                text2="一般";
                break;
            case 5:
                text2="非常好";
                break;
        }
        tvAbility.setText(text2);
        Logger.d("LookEvaluationActivity:codeList"+codeList.toString());
        //展示技能标签
        tagSkill.setAdapter(new TagAdapter<Children>(codeList) {
            @Override
            public View getView(FlowLayout parent, int position, Children s) {
                View view = LayoutInflater.from(StuLookEvaluationActivity.this)
                        .inflate(R.layout.item_textview_eva, parent, false);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(s.getName());
                return view;
            }

        });
        //展示评价
        tvSelf.setText(dataBean.getNotes());
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
    public static void StartActivity(Context context, int id) {
        Intent intent = new Intent(context, StuLookEvaluationActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
    /**
     *
     * @param text  不包括中括号的字符串
     * @return
     */
    private List<String> getResult(String text) {
        List<String> result = new ArrayList<>();
        //通过引号进行匹配的正则表达式
        String regex = "\\\"([^\\\"]*)\\\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        //通过引号进行匹配出的结果的集合
        List<String> textResult = new ArrayList<>();
        while (matcher.find()) {
            textResult.add(matcher.group());
        }

        //讲匹配的所有结果通过逗号进行拆分，取出以后后面的字符串
        for (int i = 0; i < textResult.size(); i++) {
            result.add(textResult.get(i).split(",")[1].replace("\"",""));
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

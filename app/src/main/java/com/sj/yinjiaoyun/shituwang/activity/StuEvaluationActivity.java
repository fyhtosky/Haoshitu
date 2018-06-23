package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.bean.ReturnBean;
import com.sj.yinjiaoyun.shituwang.bean.StuEvaBean;
import com.sj.yinjiaoyun.shituwang.flowLayout.FlowLayout;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagAdapter;
import com.sj.yinjiaoyun.shituwang.flowLayout.TagFlowLayout;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 老师端的去评价界面
 */
public class StuEvaluationActivity extends AppCompatActivity implements TextWatcher {

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
    @BindView(R.id.et_self)
    EditText etSelf;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_text_size)
    TextView tvTextSize;
    private StuEvaBean.DataBean.RowsBean rowsBean;
    //评价项的数据源
    private List<Children> list = new ArrayList<>();
    //选择标签的集合
    private List<String>codeList=new ArrayList<>();
    //显示专业深度的维度值
    private int deep=0;
    private int attitude=0;
    private int ability=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_evaluation);
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
        etSelf.addTextChangedListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rowsBean = (StuEvaBean.DataBean.RowsBean) bundle.getSerializable("rowsBean");
            if (rowsBean != null) {
                showDetail(rowsBean);
            }
        }
        rbDeep.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                deep= (int) rating;
                String text="";
                switch ((int)rating){
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
            }
        });
        rbAttitude.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                attitude= (int) rating;
                String text="";
                switch ((int)rating){
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
                tvAttitude.setText(text);

            }
        });
        rbAbility.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ability= (int) rating;
                String text="";
                switch ((int)rating){
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
                tvAbility.setText(text);
            }
        });
        //展示学徒的评价项的标签
        tagSkill.setAdapter(new TagAdapter<Children>(list) {
            @Override
            public View getView(FlowLayout parent, int position, Children s) {
                View view = LayoutInflater.from(StuEvaluationActivity.this)
                        .inflate(R.layout.item_reason, parent, false);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(s.getName());
                return view;
            }

        });
        //给标签布局添加点击事件
        tagSkill.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String code="\""+list.get(position).getParentCode()+","+list.get(position).getCode()+"\"";
                if(!codeList.contains(code)){
                    codeList.add(code);
                }else {
                    codeList.remove(code);
                }

                return true;
            }
        });
    }

    private void showDetail(StuEvaBean.DataBean.RowsBean rowsBean) {
        //显示头像
        if(rowsBean.getStudent().getHead()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getHead())){
            PicassoUtils.LoadPathCorners(StuEvaluationActivity.this,rowsBean.getStudent().getHead(),circleView);
        }else{
            PicassoUtils.LoadCorners(StuEvaluationActivity.this, R.drawable.wukong, 60, 60, circleView);
        }
        //名字
       tvName.setText(rowsBean.getStudent().getRealName());
        //展示性别
        if (rowsBean.getStudent().getSex() == 1) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.woman);
        }
        String education="";
        //展示地址
        String address="";
        //地址赋值
        if(rowsBean.getStudent().getAddressNow()!=null && !TextUtils.isEmpty(rowsBean.getStudent().getAddressNow())){
            address=rowsBean.getStudent().getAddressNow().split(",")[0];
        }
        //展示薪资
        String salary="";
        //展示职位
        String post="";
//        档案类型    0:综合能力 1：专业技能 2：工作经历 3：诉求描述
        if(rowsBean.getStudent().getResumes()!=null && rowsBean.getStudent().getResumes().size()>0){
            for ( StuEvaBean.DataBean.RowsBean.StudentBean.ResumesBean resumesBean:rowsBean.getStudent().getResumes()){
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
        if(rowsBean.getStudent().getEvaluationLabels()!=null&& !TextUtils.isEmpty(rowsBean.getStudent().getEvaluationLabels())){
            tagTrait.setVisibility(View.VISIBLE);
            String labels=rowsBean.getStudent().getEvaluationLabels().substring(1,rowsBean.getStudent().getEvaluationLabels().length()-1);
            String []str=labels.split(",");
            Logger.d("labels==list"+ Arrays.asList(str).toString());
            tagTrait.setAdapter(new TagAdapter<String>(Arrays.asList(str)) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View view = LayoutInflater.from(StuEvaluationActivity.this)
                            .inflate(R.layout.item_textview_skill, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    tv.setText(s.substring(1,s.length()-1));
                    return view;
                }

            });
        }else{
            tagTrait.setVisibility(View.GONE);
        }

    }

    public static void StartActivity(Context context, StuEvaBean.DataBean.RowsBean rowsBean) {
        Intent intent = new Intent(context, StuEvaluationActivity.class);
        intent.putExtra("rowsBean", rowsBean);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_submit:
                if(rowsBean!=null){
                    addEva();
                }
                break;
        }
    }
    /**
     * 添加评价
     */
    private void addEva() {
        if(deep==0){
            ToastUtil.showShortToast(StuEvaluationActivity.this,"请选择专业深度星级");
            return;
        }
        if(attitude==0){
            ToastUtil.showShortToast(StuEvaluationActivity.this,"请选择授课态度星级");
            return;
        }
        if(ability==0){
            ToastUtil.showShortToast(StuEvaluationActivity.this,"请选择技术能力星级");
            return;
        }
        Logger.d("codeList:"+codeList.toString());
        if(codeList.size()==0){
            ToastUtil.showShortToast(StuEvaluationActivity.this,"评价标签必须选择一个");
            return;
        }
        if(TextUtils.isEmpty(etSelf.getText().toString().trim())){
            ToastUtil.showShortToast(StuEvaluationActivity.this,"请输入您对学徒的评价");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        map.put("tchId",String.valueOf(rowsBean.getTchId()));
        map.put("stuId",String.valueOf(rowsBean.getStuId()));
//        0:老师评价 ；1：学生评价
        map.put("isTch","0");
        map.put("evaluateId",String.valueOf(rowsBean.getId()));
        map.put("parameter1",String.valueOf(deep));
        map.put("parameter2",String.valueOf(attitude));
        map.put("parameter3",String.valueOf(ability));
        map.put("labels",codeList.toString());
        map.put("notes",etSelf.getText().toString().trim());
        HttpClient.post(this, Api.ADD_EVA, map, new CallBack<ReturnBean>() {
            @Override
            public void onSuccess(ReturnBean result) {
                if(result==null){
                    return;
                }
                if(result.getStatus()==200){
                    ToastUtil.showShortToast(StuEvaluationActivity.this,"评价成功");
                    finish();
                }else {
                    ToastUtil.showShortToast(StuEvaluationActivity.this,result.getMsg());
                }
            }


        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text=etSelf.getText().toString().trim().length() + "/100字";
        tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

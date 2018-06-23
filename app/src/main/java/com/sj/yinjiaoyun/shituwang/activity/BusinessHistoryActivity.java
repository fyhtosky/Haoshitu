package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.TeacherDetailBean;
import com.sj.yinjiaoyun.shituwang.utils.PicassoUtils;
import com.sj.yinjiaoyun.shituwang.utils.TimeRender;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 学生段查看老师的工作经历的界面
 */
public class BusinessHistoryActivity extends AppCompatActivity {

    @BindView(R.id.circleView)
    CircleImageView circleView;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_work_des)
    TextView tvWorkDes;
    //数据源的传递
    private TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean tchWorkExpsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_history);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            tchWorkExpsBean= (TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean) bundle.getSerializable("Bean");
            if(tchWorkExpsBean!=null){
                //展示数据
                show(tchWorkExpsBean);
            }
        }
    }

    private void show(TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean tchWorkExpsBean) {
        //展示头像
        PicassoUtils.LoadCorners(BusinessHistoryActivity.this,R.mipmap.cpmpany,circleView);
        //展示职位
        tvPost.setText(tchWorkExpsBean.getPosition());
        //展示公司
        tvCompanyName.setText(tchWorkExpsBean.getCompanyName());
        //显示工作的时间
        if(tchWorkExpsBean.getBeginDate()!=null&&tchWorkExpsBean.getEndDate()!=null){
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())&& !TextUtils.isEmpty(tchWorkExpsBean.getEndDate())){
                tvDate.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-"+TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getEndDate())));
            }
        }else{
            if(!TextUtils.isEmpty(tchWorkExpsBean.getBeginDate())){
                tvDate.setText(TimeRender.getFormat(Long.parseLong(tchWorkExpsBean.getBeginDate()))+"-至今");
            }
        }
        //展示说明
        if(tchWorkExpsBean.getPositionDesc()!=null && !TextUtils.isEmpty(tchWorkExpsBean.getPositionDesc())){
            tvWorkDes.setText(tchWorkExpsBean.getPositionDesc());
        }else {
            tvWorkDes.setText("");
        }
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
    public static void openActivity(Context context, Class<?> cls,TeacherDetailBean.DataBean.TchResumeVOBean.TchWorkExpsBean workExpsBean) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("Bean",workExpsBean);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.GuidePageAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页面
 */
public class GuideActivity extends AppCompatActivity {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.guide_ll_point)
    LinearLayout guideLlPoint;
    @BindView(R.id.guide_ib_start)
    Button guideIbStart;
    //引导页图片的集合
    private List<Integer> imageList = Arrays.asList(R.mipmap.page01, R.mipmap.page02, R.mipmap.page03, R.mipmap.page04);
    private List<View> list = new ArrayList<>();
    //实例化原点View
    private ImageView iv_point;
    private ImageView []ivPointArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
        guideIbStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,SelectiveIdentityActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化轮播点
     */
    private void initPoint() {
        ivPointArray=new ImageView[imageList.size()];
        // 为引导点提供布局参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    for (int i=0;i<imageList.size();i++){
        iv_point=new ImageView(this);
        iv_point.setLayoutParams(mParams);
        iv_point.setPadding(10,0,10,0);
        ivPointArray[i]=iv_point;
        if (i == 0){
            iv_point.setImageResource(R.drawable.dot);
        }else{
            iv_point.setImageResource(R.drawable.gray_dot);
        }
        //将数组中的ImageView加入到ViewGroup
        guideLlPoint.addView(ivPointArray[i]);
    }

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageList.size(); i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imageList.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //将ImageView加入到集合中
            list.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(list));
        //给viewPager添加监听器
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //循环设置当前页的标记图
                int length = ivPointArray.length;
                for (int i = 0;i<length;i++){
                    ivPointArray[position].setImageResource(R.drawable.dot);
                    if (position != i){
                        ivPointArray[i].setImageResource(R.drawable.gray_dot);
                    }
                }
                //判断是否是最后一页，若是则显示按钮
                if (position == ivPointArray.length - 1){
                    guideIbStart.setVisibility(View.VISIBLE);
                }else {
                    guideIbStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

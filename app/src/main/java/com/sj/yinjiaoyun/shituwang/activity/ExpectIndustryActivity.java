package com.sj.yinjiaoyun.shituwang.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Children;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DensityUtils;
import com.sj.yinjiaoyun.shituwang.utils.OtherUtils;
import com.sj.yinjiaoyun.shituwang.view.DirviderDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 期望行业的界面
 */
public class ExpectIndustryActivity extends AppCompatActivity {


    @BindView(R.id.rv_industry)
    RecyclerView rvIndustry;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rl_activity)
    RelativeLayout rlActivity;
    @BindView(R.id.view_bac)
    View viewBac;
    @BindView(R.id.rv_industry_item)
    RecyclerView rvIndustryItem;
    //行业的数据源
    private List<Children> list = new ArrayList<>();
    //二级菜单的数据源
    private List<Children> childrenList = new ArrayList<>();
    /**
     * 二级菜单显示状态
     */
    boolean mIsFolderViewShow = false;
    private DirviderDecoration dirviderDecoration;
    //第一此显示
    private boolean isShow=false;
    /**
     * 二级菜单的显示隐藏动画
     */
    AnimatorSet inAnimatorSet = new AnimatorSet();
    AnimatorSet outAnimatorSet = new AnimatorSet();
    private SparseBooleanArray array = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expect_industry);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        dirviderDecoration = new DirviderDecoration();
        list.clear();
//        array.put(0,true);
        //一级菜单
        rvIndustry.setLayoutManager(new LinearLayoutManager(ExpectIndustryActivity.this, LinearLayoutManager.VERTICAL, false));
        rvIndustry.addItemDecoration(dirviderDecoration);
        rvIndustry.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item, list) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final Children dataBean) {
                final int position = list.indexOf(dataBean);
                baseViewHolder.setText(R.id.tv_industry,dataBean.getName());
                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        childrenList.clear();
                        childrenList.addAll(dataBean.getChildren());
                        for (int i = 0; i < array.size(); i++) {
                            array.put(i,false);

                        }
                        array.put(position,true);
                        //二级菜单的动画控制
                        if(mIsFolderViewShow) {
//                            baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                            outAnimatorSet.start();
                            mIsFolderViewShow = false;
                        } else {
                            if(!isShow){
                                rvIndustryItem.setVisibility(View.VISIBLE);
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                                outAnimatorSet.start();
                                isShow=true;
                            }else{
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.GRAY);
                                inAnimatorSet.start();
                                mIsFolderViewShow = true;
                            }
                        }
                        rvIndustryItem.getAdapter().notifyDataSetChanged();
                        notifyDataSetChanged();

                    }
                });
                //获取对应的状态操作
                if (array.get(position)) {
                    baseViewHolder.setTextColor(R.id.tv_industry, Color.parseColor("#24C789"));
                } else {
                    baseViewHolder.setTextColor(R.id.tv_industry, Color.GRAY);
                }
            }
        });
        //数据源
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0002".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }
            rvIndustry.getAdapter().notifyDataSetChanged();
        }
         SecondaryMenu();
         initAnimation(viewBac);
    }

    private void SecondaryMenu() {
        rvIndustryItem.setLayoutManager(new LinearLayoutManager(ExpectIndustryActivity.this, LinearLayoutManager.VERTICAL, false));
        rvIndustryItem.removeItemDecoration(dirviderDecoration);
        rvIndustryItem.addItemDecoration(dirviderDecoration);
        rvIndustryItem.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item, childrenList) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final Children dataBean) {
                baseViewHolder.setText(R.id.tv_industry,dataBean.getName());
                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                        Intent intent = new Intent();
                        intent.putExtra(Const.CHILDREN, dataBean);
                        ExpectIndustryActivity.this.setResult(RESULT_OK, intent);
                        ExpectIndustryActivity.this.finish();
                    }
                });
            }
        });


    }





    private void initAnimation(View dimLayout) {
        ObjectAnimator alphaInAnimator, alphaOutAnimator, transInAnimator, transOutAnimator;
        int width = DensityUtils.dp2px(this, 100);
        alphaInAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0.3f, 0f);
        alphaOutAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0f, 0.3f);
        transInAnimator = ObjectAnimator.ofFloat(rvIndustryItem, "translationX", width, OtherUtils.getWidthInPx(this));
        transOutAnimator = ObjectAnimator.ofFloat(rvIndustryItem, "translationX", OtherUtils.getWidthInPx(this), width);

        LinearInterpolator linearInterpolator = new LinearInterpolator();

        inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
        inAnimatorSet.setDuration(300);
        inAnimatorSet.setInterpolator(linearInterpolator);
        outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
        outAnimatorSet.setDuration(300);
        outAnimatorSet.setInterpolator(linearInterpolator);
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}

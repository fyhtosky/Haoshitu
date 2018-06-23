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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
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
 * 期望职位的界面
 */
public class ExpectedPositionActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv_1)
    RecyclerView rv1;
    @BindView(R.id.rv_3)
    RecyclerView rv3;
    @BindView(R.id.view_bac)
    View viewBac;
    @BindView(R.id.view_bac3)
    View viewBac3;

    //行业的数据源
    private List<Children> list = new ArrayList<>();
    //二级菜单的数据源
    private List<Children> childrenList = new ArrayList<>();
    //三级菜单的数据源
    private List<Children> childrens = new ArrayList<>();
    /**
     * 二级菜单显示状态
     */
    boolean mIsFolderViewShow = false;
    boolean mIsFolderViewShow3 = false;
    private DirviderDecoration dirviderDecoration;
    //第一此显示
    private boolean isShow = false;
    private boolean isShow3 = false;

    /**
     * 二级菜单的显示隐藏动画
     */
    AnimatorSet inAnimatorSet = new AnimatorSet();
    AnimatorSet outAnimatorSet = new AnimatorSet();
    /**
     * 三级菜单的动画
     */
   private AnimatorSet inSet = new AnimatorSet();
   private AnimatorSet outSet = new AnimatorSet();
    //存储一级列表的状态
    private SparseBooleanArray array = new SparseBooleanArray();
    //存储二级列表的状态
    private SparseBooleanArray array1 = new SparseBooleanArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_position);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        dirviderDecoration = new DirviderDecoration();
        list.clear();
//        array.put(0,true);
        //一级菜单
        rv.setLayoutManager(new LinearLayoutManager(ExpectedPositionActivity.this, LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(dirviderDecoration);
        rv.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item, list) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final Children dataBean) {
                final int position = list.indexOf(dataBean);
                baseViewHolder.setText(R.id.tv_industry,dataBean.getName());
                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        childrenList.clear();
                        childrenList.addAll(dataBean.getChildren());
                        for (int i = 0; i < list.size(); i++) {
                            array.put(i,false);
                        }
                        array.put(position,true);
//                        array1.put(0,true);
                        //二级菜单的动画控制
                        if (mIsFolderViewShow) {
                            //一级菜单颜色变化
//                            baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                            outAnimatorSet.start();
                            mIsFolderViewShow = false;
                        } else {
                            if (!isShow) {
                                rv1.setVisibility(View.VISIBLE);
                                //一级菜单颜色变化
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                                outAnimatorSet.start();
                                isShow = true;
                            } else {
                                //一级菜单颜色变化
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.GRAY);
                                inAnimatorSet.start();
                                mIsFolderViewShow = true;
                            }
                        }
                     rv1.getAdapter().notifyDataSetChanged();
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
        //职位类别
        if (MyApplication.allCodesBean != null) {
            for (int i = 0; i < MyApplication.allCodesBean.getData().size(); i++) {
                if ("R0001".equals(MyApplication.allCodesBean.getData().get(i).getCode())) {
                    list.addAll(MyApplication.allCodesBean.getData().get(i).getChildren());
                }
            }
            Logger.d("职位类别的数量："+list.size());
            rv.getAdapter().notifyDataSetChanged();
        }
        SecondaryMenu();
        threeMenu();
        initAnimation();
    }


    private void SecondaryMenu() {
        rv1.setLayoutManager(new LinearLayoutManager(ExpectedPositionActivity.this, LinearLayoutManager.VERTICAL, false));
        rv1.removeItemDecoration(dirviderDecoration);
        rv1.addItemDecoration(dirviderDecoration);
        rv1.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item, childrenList) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final Children dataBean) {
                final int index = childrenList.indexOf(dataBean);
                baseViewHolder.setText(R.id.tv_industry,dataBean.getName());
                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        childrens.clear();
                        childrens.addAll(dataBean.getChildren());
                        for (int i = 0; i < childrenList.size(); i++) {
                            array1.put(i,false);
                        }
                        array1.put(index,true);
                        //三级菜单的动画控制
                        if (mIsFolderViewShow3) {
//                            baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                            outSet.start();
                            mIsFolderViewShow3 = false;
                        } else {
                            if (!isShow3) {
                                rv3.setVisibility(View.VISIBLE);
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                                outSet.start();
                                isShow3 = true;
                            } else {
//                                baseViewHolder.setTextColor(R.id.tv_industry,Color.GRAY);
                                inSet.start();
                                mIsFolderViewShow3 = true;
                            }
                        }
                        rv3.getAdapter().notifyDataSetChanged();
                        notifyDataSetChanged();
                    }
                });
                //获取对应的状态操作
                if (array1.get(index)) {
                    baseViewHolder.setTextColor(R.id.tv_industry, Color.parseColor("#24C789"));
                } else {
                    baseViewHolder.setTextColor(R.id.tv_industry, Color.GRAY);
                }
            }
        });
    }

    private void threeMenu() {
        rv3.setLayoutManager(new LinearLayoutManager(ExpectedPositionActivity.this, LinearLayoutManager.VERTICAL, false));
        rv3.removeItemDecoration(dirviderDecoration);
        rv3.addItemDecoration(dirviderDecoration);
        rv3.setAdapter(new BaseQuickAdapter<Children>(R.layout.rv_industry_item, childrens) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final Children dataBean) {
                baseViewHolder.setText(R.id.tv_industry,dataBean.getName());
                baseViewHolder.setOnClickListener(R.id.rl_industry_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseViewHolder.setTextColor(R.id.tv_industry,Color.parseColor("#24C789"));
                        Intent intent = new Intent();
                        intent.putExtra(Const.CHILDREN, dataBean);
                        ExpectedPositionActivity.this.setResult(RESULT_OK, intent);
                        ExpectedPositionActivity.this.finish();
                    }
                });
            }
        });
    }

    private void initAnimation() {
        //二级菜单的动画
        ObjectAnimator alphaInAnimator, alphaOutAnimator, transInAnimator, transOutAnimator;
        int width = DensityUtils.dp2px(this, 80);
        alphaInAnimator = ObjectAnimator.ofFloat(viewBac, "alpha", 0.3f, 0f);
        alphaOutAnimator = ObjectAnimator.ofFloat(viewBac, "alpha", 0f, 0.3f);
        transInAnimator = ObjectAnimator.ofFloat(rv1, "translationX", width, OtherUtils.getWidthInPx(this));
        transOutAnimator = ObjectAnimator.ofFloat(rv1, "translationX", OtherUtils.getWidthInPx(this), width);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
        inAnimatorSet.setDuration(300);
        inAnimatorSet.setInterpolator(linearInterpolator);
        outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
        outAnimatorSet.setDuration(300);
        outAnimatorSet.setInterpolator(linearInterpolator);
        //三级菜单的动画
        ObjectAnimator alphaInAnimator3, alphaOutAnimator3, transInAnimator3, transOutAnimator3;
        int width3 = DensityUtils.dp2px(this, 180);
        alphaInAnimator3 = ObjectAnimator.ofFloat(viewBac3, "alpha", 0.6f, 0f);
        alphaOutAnimator3 = ObjectAnimator.ofFloat(viewBac3, "alpha", 0f, 0.6f);
        transInAnimator3 = ObjectAnimator.ofFloat(rv3, "translationX", width3, OtherUtils.getWidthInPx(this));
        transOutAnimator3 = ObjectAnimator.ofFloat(rv3, "translationX", OtherUtils.getWidthInPx(this), width3);
        LinearInterpolator linearInterpolator3 = new LinearInterpolator();
        inSet.play(transInAnimator3).with(alphaInAnimator3);
        inSet.setDuration(300);
        inSet.setInterpolator(linearInterpolator3);
        outSet.play(transOutAnimator3).with(alphaOutAnimator3);
        outSet.setDuration(300);
        outSet.setInterpolator(linearInterpolator3);
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

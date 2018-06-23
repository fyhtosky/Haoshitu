package com.sj.yinjiaoyun.shituwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.sj.yinjiaoyun.shituwang.R;


/**
 * Created by Administrator on 2016/11/25.
 * 下拉刷新框架的头部
 * 下拉刷新
 */

public class TwitterRefreshHeaderView extends SwipeRefreshHeaderLayout {
    //控件
    private ImageView ivArrow;
    private ImageView ivSuccess;
    private TextView tvRefresh;
    private ProgressBar progressBar;
    //头部的高度
    private int mHeaderHeight;
    //是否是下拉的状态
    private boolean rotated = false;
   //往上拉时的动画
   private Animation rotateUp;
   //往下拉时的动画
   private Animation rotateDown;
    public TwitterRefreshHeaderView(Context context) {
        super(context);
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
//        Logger.d("TwitterRefreshHeaderView==>构造方法");
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_twitter);
        rotateUp= AnimationUtils.loadAnimation(context,R.anim.rotate_up);
        rotateDown= AnimationUtils.loadAnimation(context,R.anim.rotate_down);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //控件初始化
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        ivArrow= (ImageView) findViewById(R.id.ivArrow);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        Logger.d("TwitterRefreshHeaderView.onFinishInflate()");
    }

    /**
     * 刷新时对应的状态
     */
    @Override
    public void onRefresh() {
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        tvRefresh.setText("刷新中...");
//        Logger.d("TwitterRefreshHeaderView.onRefresh()");
    }
    @Override
    public void onPrepare() {

        Log.d("TwitterRefreshHeader", "onPrepare()");
//        Logger.d("TwitterRefreshHeaderView.onPrepare()");

    }

    /**
     * 拉动过程的判断
     * @param y
     * @param isComplete
     * @param automatic
     */
    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivArrow.setVisibility(VISIBLE);
            progressBar.setVisibility(GONE);
            ivSuccess.setVisibility(GONE);
            if (y > mHeaderHeight) {
                tvRefresh.setText("正在刷新");
                if (!rotated) {
                    ivArrow.clearAnimation();
                    Log.d("onMove",""+rotateUp.hashCode());
                    ivArrow.startAnimation(rotateUp);
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                if (rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateDown);
                    rotated = false;
                }

                tvRefresh.setText("下拉刷新");
            }
        }
//        Logger.d("TwitterRefreshHeaderView.onMove()");
    }
    @Override
    public void onRelease() {
        Log.d("TwitterRefreshHeader", "onRelease()");
//        Logger.d("TwitterRefreshHeaderView.onRelease()");
    }

    /**
     * 刷新完成时的状态
     */
    @Override
    public void onComplete() {
        rotated = false;
        ivSuccess.setVisibility(VISIBLE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        tvRefresh.setText("刷新完成");
//        Logger.d("TwitterRefreshHeaderView.onComplete()");
    }

    /**
     *重置状态
     */
    @Override
    public void onReset() {
        rotated = false;
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(GONE);
//        Logger.d("TwitterRefreshHeaderView.onReset()");
    }

}

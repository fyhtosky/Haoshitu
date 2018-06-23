package com.sj.yinjiaoyun.shituwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreFooterLayout;
import com.sj.yinjiaoyun.shituwang.R;


/**
 * Created by Administrator on 2016/11/25.
 * 下拉刷新框架的底部
 * 加载更多
 */

public class ClassicLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    //控件
    private TextView tvLoadMore;
    private ImageView ivSuccess;
    private ProgressBar progressBar;
   //底部的高度
    private int mFooterHeight;
    public ClassicLoadMoreFooterView(Context context) {
        super(context);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_classic);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //控件初始化
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }
    @Override
    public void onPrepare() {
        ivSuccess.setVisibility(GONE);
    }
    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivSuccess.setVisibility(GONE);
            progressBar.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                tvLoadMore.setText("释放加载更多");
            } else {
                tvLoadMore.setText("下拉加载更多");
            }
        }
    }

    @Override
    public void onLoadMore() {
        tvLoadMore.setText("加载中...");
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
        ivSuccess.setVisibility(VISIBLE);
    }

    @Override
    public void onReset() {
        ivSuccess.setVisibility(GONE);
    }
}

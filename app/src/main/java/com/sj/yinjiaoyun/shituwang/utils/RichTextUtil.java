package com.sj.yinjiaoyun.shituwang.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/17.
 * 富文本的工具类
 */
public class RichTextUtil {
    private static SpannableStringBuilder spBuilder;
    private static  ForegroundColorSpan span;




    /**
     *
     * @param wholeStr 全部文字
     * @param highlightStr 改变颜色的文字
     * @param color 颜色
     */
    public static SpannableStringBuilder fillColor( String wholeStr, String highlightStr, int color) {
        span = new ForegroundColorSpan(color);
        try {
            if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
                if (wholeStr.contains(highlightStr)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                    int start = wholeStr.indexOf(highlightStr);
                    int end = start + highlightStr.length();
                    spBuilder = new SpannableStringBuilder(wholeStr);
                    spBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return spBuilder;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
        }
        return null;

    }
}

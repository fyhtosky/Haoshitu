package com.sj.yinjiaoyun.shituwang.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/4.
 * Edittext 过滤表情的工具类
 */
public class EditViewUtil {
    public static void setProhibitEmoji(EditText et, Context context) {
        InputFilter[] filters = { getInputFilterProhibitEmoji(context) };
        et.setFilters(filters);
    }
    public static InputFilter getInputFilterProhibitEmoji(final Context context) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint,context)) {
                        buffer.append(codePoint);
                    } else {
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };

        return filter;
    }
    public static boolean getIsEmoji(char codePoint,Context context) {
        if (codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD){
            return false;
        }
        ToastUtil.showShortToast(context,"不支持发送表情");
        return true;
    }
}

package com.sj.yinjiaoyun.shituwang.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/24.
 */
public class MyEdittext extends EditText {
    private static final int ID_PASTE = android.R.id.paste;
    public MyEdittext(Context context) {
        super(context);
    }

    public MyEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {

        if(id == ID_PASTE ){
            Logger.d( "onTextContextMenuItem: "+id);
            ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            assert clip != null;
            String value = clip.getText().toString();
            Editable edit = getEditableText();
            int index = this.getSelectionStart();
            edit.insert(index,  value);// 光标所在位置插入文字
            return  true;
        }

        return super.onTextContextMenuItem(id);
    }
}

package com.sj.yinjiaoyun.shituwang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.utils.RichTextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 业绩描述的界面
 */
public class PerformanceDescriptionActivity extends AppCompatActivity  implements TextWatcher {

    @BindView(R.id.et_self)
    EditText etSelf;
    @BindView(R.id.tv_text_size)
    TextView tvTextSize;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_description);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        etSelf.addTextChangedListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            description = bundle.getString("description");
            if (description != null && !TextUtils.isEmpty(description)) {
                etSelf.setText(description);
                etSelf.setSelection(etSelf.getText().toString().length());
                tvTextSize.setText(etSelf.getText().toString().trim().length() + "/100字");

            }
    }
        String text=etSelf.getText().toString().trim().length() + "/100字";
        tvTextSize.setText(RichTextUtil.fillColor(text,text.split("/")[0], Color.parseColor("#24C789")));
    }
    @OnClick({R.id.rl_back, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_confirm:
                if (etSelf.getText().toString().trim().length() <= 100) {
                    Intent intent = new Intent();
                    intent.putExtra("self", etSelf.getText().toString().trim());
                    this.setResult(1, intent);
                    finish();
                } else {
                    tvHint.setText("自我描述不能超过100个字");
                }
                break;
        }
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

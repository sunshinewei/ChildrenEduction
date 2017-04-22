package com.example.administrator.childreneduction.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;



public class MainActivity extends EduBaseActivity {
    private long start;
    private boolean isThemeDay = false;

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        setAPPTheme();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
//        mTextView = (TextView) findViewById(R.id.tv_main);
//        mButton = (Button) findViewById(R.id.but_theme);
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setAPPTheme();
//                recreate();
//            }
//        });
    }

    public void setAPPTheme() {
        if (isThemeDay) {
            setTheme(R.style.commonTheme_Night);
            isThemeDay = true;
        } else {
            setTheme(R.style.commonTheme_Day);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - start >= 3000) {
            showToast("请再按一次退出！");
            start = currentTimeMillis;
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

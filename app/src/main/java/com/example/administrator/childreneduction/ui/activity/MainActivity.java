package com.example.administrator.childreneduction.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;


public class MainActivity extends EduBaseActivity {
    private long start;

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTextView= (TextView) findViewById(R.id.tv_main);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long  currentTimeMillis= System.currentTimeMillis();
        if (currentTimeMillis-start>=3000){
            showToast("请再按一次退出！");
            start=currentTimeMillis;
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

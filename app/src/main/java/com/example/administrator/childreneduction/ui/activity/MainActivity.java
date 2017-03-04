package com.example.administrator.childreneduction.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.administrator.childreneduction.R;
import com.example.baselibrary.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private long start;
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

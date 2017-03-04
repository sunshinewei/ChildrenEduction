package com.example.baselibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/4 0004.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    protected BaseActivity mBaseActivity;

    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBaseActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), null);
        initView(inflate);
        return inflate;
    }

    public abstract int getLayoutId();
    public abstract void initView(View viewRoot);

    @Override
    public void showProgress(@Nullable String msg) {
        mBaseActivity.showProgress(msg);
    }

    @Override
    public void hideProgress() {
        mBaseActivity.hideProgress();
    }

    @Override
    public void showToast(String msg) {
        mBaseActivity.showToast(msg);
    }

    @Override
    public void showAlert(String msg) {
        mBaseActivity.showAlert(msg);
    }

    @Override
    public void showLongToast(String msg) {
        mBaseActivity.showLongToast(msg);
    }
}

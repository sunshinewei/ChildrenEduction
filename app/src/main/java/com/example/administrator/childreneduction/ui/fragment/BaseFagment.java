package com.example.administrator.childreneduction.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.childreneduction.App;

/**
 * BaseFragment
 * Created by Administrator on 2017/3/9 0009.
 */

public abstract class BaseFagment extends Fragment{
    private boolean isCreateView;//view加载完毕时的标志
    private boolean isVisible;//对用户可见时的标志

    private LayoutInflater mLayoutInflater;
    public BaseFagment() {
        super();

        mLayoutInflater=LayoutInflater.from(App.getContext());//.....
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isVisible=true;
            lazyLoad();
        }else{
            isVisible=false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(getLayOutID(), null);
        View  mRootView= addToolBar(mView);
        initView(mRootView);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCreateView=true;
        lazyLoad();
    }

    /**
     * 当Fragment界面呈现给用户后，加载数据。
     */
    private  void lazyLoad(){
        if (isVisible && isCreateView){
            initData();
            isVisible=false;
            isCreateView=false;
        }
    }

    /**
     *Fragment添加ToolBar
     * @param mRootView
     */
    private View addToolBar(View mRootView){
        LinearLayout mLinearLayout=new LinearLayout(App.getContext());//...........
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        if (isToolBar()){
            View inflate = mLayoutInflater.inflate(getToolBarID(), null);
            mLinearLayout.addView(inflate);
            mLinearLayout.addView(mRootView);
            return mLinearLayout;
        }else
            return mRootView;

    }


    public abstract boolean isToolBar();
    public abstract int getToolBarID();
    public abstract int getLayOutID();
    public abstract void initView(View mRootView);
    public abstract void initData();

}

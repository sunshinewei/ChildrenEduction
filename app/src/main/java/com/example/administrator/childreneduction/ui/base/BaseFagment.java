package com.example.administrator.childreneduction.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.childreneduction.da.component.FragmentComponent;
import com.example.baselibrary.base.BaseView;

/**
 * BaseFragment
 * Created by Administrator on 2017/3/9 0009.
 */

public abstract class BaseFagment extends Fragment implements BaseView{
    private boolean isCreateView;//view加载完毕时的标志
    private boolean isVisible;//对用户可见时的标志
    public FragmentComponent mFragmentComponent;
    private LayoutInflater mLayoutInflater;
    public BaseFagment() {
//        initFragmentComponent();
//        mLayoutInflater=LayoutInflater.from(mFragmentComponent.getActivityContext());//.....
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
//        ButterKnife.bind(this,mView);
//        View  mRootView= addToolBar(mView);
        initView(mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCreateView=true;

        initData();
//        lazyLoad();
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
        LinearLayout mLinearLayout=new LinearLayout(mFragmentComponent.getActivityContext());//...........
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
//            View inflate = mLayoutInflater.inflate(getToolBarID(), null);
            return mRootView;
    }


    /**
     *
     */
//    public void initFragmentComponent(){
//        mFragmentComponent=DaggerFragmentComponent.builder()
//                .aPPComponent(App.getAppComponent())
//                .fragmentModule(new FragmentModule(this))
//                .build();
//    }

    public abstract int getLayOutID();
    public abstract void initView(View mRootView);
    public abstract void initData();

    @Override
    public void showProgress(@Nullable String msg) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlert(String msg) {

    }

    @Override
    public void showLongToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }
}

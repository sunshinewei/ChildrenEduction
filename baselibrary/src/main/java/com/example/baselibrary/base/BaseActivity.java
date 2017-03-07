package com.example.baselibrary.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityManager.getInstance().addActivity(this);
        setContentView(setLayout());
        mContext = this;
    }

    /**
     * 添加页面布局xml文件
     * @return
     */
    public abstract int setLayout() ;

    /**
     * 初始化布局文件中的控件
     */
    public abstract void initView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * @param msg
     */
    public void showLongToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * @param msg
     */
    @Override
    public void showProgress(String msg){

    }
    @Override
    public void hideProgress(){

    }
    /**
     * 弹出提示信息对话框
     * @param msg
     */
    @Override
    public void showAlert(String msg){
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * @param clazz
     */
    public void startToActivity(Class clazz){
        Intent intent=new Intent(mContext,clazz);
        mContext.startActivity(intent);
    }
    /**
     * @param clazz
     * @param bundle
     */
    public void startToActivity(Class clazz,Bundle bundle){
        Intent intent=new Intent(mContext,clazz);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    /**
     * @return
     */
    public Bundle getBundle(){
        return getIntent().getExtras();
    }
}

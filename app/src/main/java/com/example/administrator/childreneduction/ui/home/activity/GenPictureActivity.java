package com.example.administrator.childreneduction.ui.home.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.widgets.picture.Article;
import com.example.administrator.childreneduction.widgets.picture.GeneratePictureView;
import com.example.baselibrary.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.administrator.childreneduction.R.id.gpv;
import static com.example.administrator.childreneduction.R.id.rb_day;
import static com.example.administrator.childreneduction.R.id.rb_night;

/**
 * Created by Administrator on 2017/5/30.
 * 生成图片
 */

public class GenPictureActivity extends BaseActivity {

    private RadioButton mRbDay;
    private RadioButton mRbNight;
    private GeneratePictureView mGpv;

    private static final String SAVE_PIC_PATH= Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/Camera" : "";
    //保存
    private static final int SAVE = 0;
    //分享
    private static final int SHARE = 1;
    //操作失败
    private static final int FAILE = 2;
    ProgressDialog pd;
    private Handler mHandler = new Handler_Capture(this);
    class Handler_Capture extends Handler{
        WeakReference<Activity> weakReference;
        public Handler_Capture(Activity activity){
            weakReference = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get()!=null){
                Bundle data = null;
                switch (msg.what){
                    case SAVE:
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"图片已保存在相册!",Toast.LENGTH_LONG).show();
                        break;
                    case SHARE:
                        data = msg.getData();
                        pd.dismiss();
                        shareMsg("分享卡片","分享卡片title","分享卡片内容",data.getString("path"));
                        break;
                    case FAILE:
                        String strData = (String) msg.obj;
                        Toast.makeText(getApplicationContext(),strData,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }



    @Override
    public int setLayout() {
        return R.layout.activity_picture;
    }

    @Override
    public void initView() {
        mRbDay = (RadioButton) findViewById(rb_day);
        mRbNight = (RadioButton) findViewById(rb_night);
        mGpv = (GeneratePictureView) findViewById(gpv);

        initData();
    }

    private void initData(){
//        ArticleTable article = (ArticleTable) getIntent().getSerializableExtra("data");
//        String title = article.getA_title();
//        String data = article.getA_content();
        Article article= (Article) getIntent().getSerializableExtra("data");

        System.out.println("articeel"+article.strData+article.strTitle);
        pd = new ProgressDialog(this);
        pd.setMessage("请稍后...");
        String substring =null;
        if(article.strData!=null && article.strData.length()>12){
             substring=article.strData.substring(12, article.strData.length() - 2);
        }
        System.out.println("substring"+substring);
        initRadioButton(article.strTitle, article.strTitle);
        //初始化控件填充内容
        mGpv.init( article.strTitle, article.strTitle,"weilong","@163");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initRadioButton(final String data, final String title) {

        mRbDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRbNight.setChecked(false);
                mRbDay.setChecked(true);
                mGpv.changeDay(data,"aaa","Mr.Zk","via zhangke3016");
            }
        });
        mRbNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRbDay.setChecked(false);
                mRbNight.setChecked(true);
                mGpv.changeNight(data, title,"Mr.Zk","via zhangke3016");
            }
        });
    }

    /**
     * 保存
     * @param v
     */
    public void Save(View v){
        if (TextUtils.isEmpty(SAVE_PIC_PATH))
            return;
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
                    final File realFile = saveBitmap(sdf.format(new Date())+".jpg");
                    if (realFile == null){
                        Message message = mHandler.obtainMessage(FAILE);
                        message.obj = "保存失败,文件过大!";
                        message.sendToTarget();
                    }else{
                        // 其次把文件插入到系统图库
                        try {
                            MediaStore.Images.Media.insertImage(GenPictureActivity.this.getContentResolver(),
                                    realFile.getAbsolutePath(), realFile.getName(), null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        realFile.delete();

                        Message message = mHandler.obtainMessage(SAVE);
                        Bundle data = new Bundle();
                        data.putString("path",realFile.getAbsolutePath());
                        message.setData(data);
                        message.sendToTarget();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    Message message = mHandler.obtainMessage(FAILE);
                    message.obj = "保存失败,"+e.getMessage();
                    message.sendToTarget();
                }finally {
                    pd.dismiss();
                }
            }
        }).start();
    }
    /**
     * 分享
     * @param view
     */
    public void Share(View view){
        if (TextUtils.isEmpty(SAVE_PIC_PATH))
            return;
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final File realFile = saveBitmap("share.jpg");
                    if (realFile == null){
                        Message message = mHandler.obtainMessage(FAILE);
                        message.obj = "分享失败,文件过大!";
                        message.sendToTarget();
                    }else{
                        Message message = mHandler.obtainMessage(SHARE);
                        Bundle data = new Bundle();
                        data.putString("path",realFile.getAbsolutePath());
                        message.setData(data);
                        message.sendToTarget();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message message = mHandler.obtainMessage(FAILE);
                    message.obj = "分享失败,"+e.getMessage();
                    message.sendToTarget();
                }finally {
                    pd.dismiss();
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 分享功能
     *
     *
     * @param activityTitle
     *            Activity的名字
     * @param msgTitle
     *            消息标题
     * @param msgText
     *            消息内容
     * @param imgPath
     *            图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 保存图片到文件
     * @param fileName  文件名称
     * @return
     * @throws Exception
     */
    private File saveBitmap(String fileName) throws Exception{
        Bitmap bitmap = mGpv.getScreen();
        if (bitmap == null)
            return null;
        File file = new File(SAVE_PIC_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        final File realFile = new File(file, fileName);
        if (!realFile.exists()){
            realFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(realFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc(); // 通知系统回收
        }
        return realFile;
    }
}

package com.example.administrator.childreneduction.ui.me.activity;

import android.Manifest;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.widgets.BothWayProgressBar;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class VideoActivity extends EduBaseActivity implements SurfaceHolder.Callback, View.OnTouchListener, BothWayProgressBar.OnProgressEndListener {
    private static final int LISTENER_START = 200;
    private static final String TAG = "MainActivity";
    //预览SurfaceView
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    //底部"按住拍"按钮
    private View mStartButton;
    //进度条
    private BothWayProgressBar mProgressBar;
    //进度条线程
    private Thread mProgressThread;
    //录制视频
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    //屏幕分辨率
    private int videoWidth, videoHeight;
    //判断是否正在录制
    private boolean isRecording;
    //段视频保存的目录
    private File mTargetFile;
    //当前进度/时间
    private int mProgress;
    //录制最大时间
    public static final int MAX_TIME = 10;
    //是否上滑取消
    private boolean isCancel;
    //手势处理, 主要用于变焦 (双击放大缩小)
    private GestureDetector mDetector;
    //是否放大
    private boolean isZoomIn = false;


    private MyHandler mHandler;
//    private TextView mTvTip;
    private boolean isRunning;

    private TextView mMainTvTip;
//    private SurfaceView mMainSurfaceView;
//    private RelativeLayout mMainPressControl;
//    private BothWayProgressBar mMainProgressBar;

    @Override
    public int setLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        mMainTvTip = (TextView) findViewById(R.id.main_tv_tip);
//        mMainSurfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
//        mMainPressControl = (RelativeLayout) findViewById(R.id.main_press_control);
//        mMainProgressBar = (BothWayProgressBar) findViewById(R.id.main_progress_bar);


        videoWidth = 640;
        videoHeight = 480;
        mSurfaceView = (SurfaceView) findViewById(R.id.main_surface_view);

        mDetector = new GestureDetector(this, new ZoomGestureListener());
        /**
         * 单独处理mSurfaceView的双击事件
         */
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mSurfaceHolder = mSurfaceView.getHolder();
        //设置屏幕分辨率
        mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mStartButton = findViewById(R.id.main_press_control);
        mMainTvTip = (TextView) findViewById(R.id.main_tv_tip);

        mStartButton.setOnTouchListener(this);
        //自定义双向进度条
        mProgressBar = (BothWayProgressBar) findViewById(R.
                id.main_progress_bar);
        mProgressBar.setOnProgressEndListener(this);
        mHandler = new MyHandler(this);
        mMediaRecorder = new MediaRecorder();
        initpermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }
    private void initpermission(){
        // 在Fragment：
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_CONTACTS
                )
    .callback(this)
    .start();

    }

    ///////////////////////////////////////////////////////////////////////////
    // SurfaceView回调
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    /**
     * 开启预览
     *
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        Log.d(TAG, "startPreView: ");

        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }

        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                //实现Camera自动对焦
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            Log.d(TAG, "surfaceDestroyed: ");
            //停止预览并释放摄像头资源
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // 进度条结束后的回调方法
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onProgressEndListener() {
        //视频停止录制
        stopRecordSave();
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        if (mMediaRecorder != null) {
            //没有外置存储, 直接停止录制
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(this, "手机没有外围存储设备", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                //mMediaRecorder.reset();
                mCamera.unlock();
                mMediaRecorder.setCamera(mCamera);
                //从相机采集视频
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 从麦克采集音频信息
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // TODO: 2016/10/20  设置视频格式
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                /*
                应该去掉，在android4.0以上有的的手机不适配。
                mMediaRecorder.setVideoSize(videoWidth, videoHeight);
                //每秒的帧数
                mMediaRecorder.setVideoFrameRate(24);
                */
                //编码格式
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // 设置帧频率，然后就清晰了
                mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024 * 100);
                // TODO: 2016/10/20 临时写个文件地址, 稍候该!!!

                File targetDir = Environment.
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);

                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                mTargetFile = new File(externalStorageDirectory,
                        SystemClock.currentThreadTimeMillis() + ".mp4");

                mMediaRecorder.setOutputFile(mTargetFile.getAbsolutePath());
                mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
                //解决录制视频, 播放器横向问题
                mMediaRecorder.setOrientationHint(90);

                mMediaRecorder.prepare();
                //正式录制
                mMediaRecorder.start();
                isRecording = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 停止录制 并且保存
     */
    private void stopRecordSave() {
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            Toast.makeText(this, "视频已经放至" + mTargetFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Intent mItent=new Intent();
            mItent.putExtra("PATH",mTargetFile.getAbsolutePath());
            setResult(Content.RESULT_VIDEO,mItent);
            finish();

        }
    }

    /**
     * 停止录制, 不保存
     */
    private void stopRecordUnSave() {
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            if (mTargetFile.exists()) {
                //不保存直接删掉
                mTargetFile.delete();
            }
        }
    }

    /**
     * 相机变焦
     *
     * @param zoomValue
     */
    public void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {//判断是否支持
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }

    }

    /**
     * hanlder
     */
    private static class MyHandler extends Handler {
        private WeakReference<VideoActivity> mReference;
        private VideoActivity mActivity;

        public MyHandler(VideoActivity activity) {
            mReference = new WeakReference<VideoActivity>(activity);
            mActivity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mActivity.mProgressBar.setProgress(mActivity.mProgress);
                    break;
            }

        }
    }

    /**
     * 触摸事件的触发
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        float ey = event.getY();
        float ex = event.getX();
        //只监听中间的按钮处
        int vW = v.getWidth();
        int left = LISTENER_START;
        int right = vW - LISTENER_START;

        float downY = 0;

        switch (v.getId()) {
            case R.id.main_press_control: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (ex > left && ex < right) {
                            mProgressBar.setCancel(false);
                            //显示上滑取消
                            mMainTvTip.setVisibility(View.VISIBLE);
                            mMainTvTip.setText("↑ 上滑取消");
                            //记录按下的Y坐标
                            downY = ey;
                            // TODO: 2016/10/20 开始录制视频, 进度条开始走
                            mProgressBar.setVisibility(View.VISIBLE);
                            //开始录制
                            Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
                            startRecord();

                            mProgressThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        mProgress = 0;
                                        isRunning = true;
                                        while (isRunning) {
                                            mProgress++;
                                            mHandler.obtainMessage(0).sendToTarget();
                                            Thread.sleep(20);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            mProgressThread.start();
                            ret = true;
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        if (ex > left && ex < right) {
                            mMainTvTip.setVisibility(View.INVISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                            //判断是否为录制结束, 或者为成功录制(时间过短)
                            if (!isCancel) {
                                if (mProgress < 50) {
                                    //时间太短不保存
                                    stopRecordUnSave();
                                    Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                //停止录制
                                stopRecordSave();
                            } else {
                                //现在是取消状态,不保存
                                stopRecordUnSave();
                                isCancel = false;
                                Toast.makeText(this, "取消录制", Toast.LENGTH_SHORT).show();
                                mProgressBar.setCancel(false);
                            }

                            ret = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (ex > left && ex < right) {
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                isCancel = true;
                                mProgressBar.setCancel(true);
                            }
                        }
                        break;
                }
                break;

            }

        }
        return ret;
    }

    // 变焦手势处理类
    class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        //双击手势事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            Log.d(TAG, "onDoubleTap: 双击事件");
            if (mMediaRecorder != null) {
                if (!isZoomIn) {
                    setZoom(20);
                    isZoomIn = true;
                } else {
                    setZoom(0);
                    isZoomIn = false;
                }
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera!=null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }
    }
}

package com.example.administrator.childreneduction.ui.vedio.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.baselibrary.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/21.
 */

public class VideoPlayerActivity extends BaseActivity implements EasyVideoCallback {
    private EasyVideoPlayer mEvpActVpPlayer;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,VideoPlayerActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_videoplayer;
    }

    @Override
    public void initView() {
        mEvpActVpPlayer = (EasyVideoPlayer) findViewById(R.id.evp_act_vp_player);

        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        VedioTable vedio =(VedioTable) intent.getSerializableExtra("VEDIO");
        mEvpActVpPlayer.setCallback(this);
        mEvpActVpPlayer.setSource(Uri.parse(vedio.getV_url()));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        mEvpActVpPlayer.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}

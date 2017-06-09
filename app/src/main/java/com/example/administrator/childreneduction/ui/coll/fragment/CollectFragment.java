package com.example.administrator.childreneduction.ui.coll.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;

/**
 * Created by Administrator on 2017/5/7.
 * 收藏
 */

public class CollectFragment extends BaseFagment {

    private TextView mTvFragCollArt;
    private TextView mTvFragCollVideo;
    private FrameLayout mFramFramCollView;
    //文章
    private VideoCollFragment mVideoCollFragment;
    //视频
    private ArticleCollFragment mArticleCollFragment;

    private  FragmentManager mManager;

    public static CollectFragment newInstance(){
        return new CollectFragment();
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_collect;
    }

    @Override
    public void initView(View mRootView) {
        mTvFragCollArt = (TextView) mRootView.findViewById(R.id.tv_frag_coll_art);
        mTvFragCollVideo = (TextView) mRootView.findViewById(R.id.tv_frag_coll_video);
        mFramFramCollView = (FrameLayout) mRootView.findViewById(R.id.fram_fram_coll_view);
    }

    @Override
    public void initData() {
        mArticleCollFragment = ArticleCollFragment.newInstance();
        mVideoCollFragment = VideoCollFragment.newInsatance();
        mTvFragCollArt.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));
        mTvFragCollVideo.setTextColor(getResources().getColor(R.color.color_text_blank_800));
        mManager = getChildFragmentManager();
        mManager.beginTransaction()
                .add(R.id.fram_fram_coll_view, mArticleCollFragment)
                .add(R.id.fram_fram_coll_view, mVideoCollFragment)
                .hide(mVideoCollFragment)
                .hide(mArticleCollFragment)
                .show(mArticleCollFragment)
                .commit();

        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener(){
        //文章
        mTvFragCollArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvFragCollArt.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));
                mTvFragCollVideo.setTextColor(getResources().getColor(R.color.color_text_blank_800));
                mManager = getChildFragmentManager();
                mManager.beginTransaction()
//                        .add(R.id.fram_fram_coll_view, mArticleCollFragment)
//                        .add(R.id.fram_fram_coll_view, mVideoCollFragment)
                        .hide(mVideoCollFragment)
                        .show(mArticleCollFragment)
                        .commit();
            }
        });
        //视频
        mTvFragCollVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvFragCollArt.setTextColor(getResources().getColor(R.color.color_text_blank_800));
                mTvFragCollVideo.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));
                mManager = getChildFragmentManager();
                mManager.beginTransaction()
//                .add(R.id.fram_fram_coll_view, mArticleCollFragment)
//                .add(R.id.fram_fram_coll_view, mVideoCollFragment)
                        .hide(mArticleCollFragment)
                        .show(mVideoCollFragment)
                        .commit();
            }
        });
    }


}

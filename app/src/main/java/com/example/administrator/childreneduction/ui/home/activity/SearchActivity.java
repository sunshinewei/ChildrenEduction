package com.example.administrator.childreneduction.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LabelBean;
import com.example.administrator.childreneduction.ui.adapter.LabelAdater;
import com.example.administrator.childreneduction.ui.home.fragment.SearchArticleFragment;
import com.example.administrator.childreneduction.ui.home.fragment.SearchVideoFragment;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 * 搜索Activity
 */

public class SearchActivity extends BaseActivity {

    private EditText mTvActivityLabel;
    private TextView mTvActSearchDelete;
    private RelativeLayout mRelActSearch;
    private RecyclerView mRecyActSearchItem;
    private LinearLayout mVpFragment;
    private TextView mTvFragSearArt;
    private TextView mTvFragSearVideo;
    private ViewPager mVpActSearch;


    private LabelAdater mLabelAdater;
    private SearchAdapter mSearchAdapter;

    private SearchVideoFragment mVideoFragment;
    private SearchArticleFragment mArticleFragment;
    private FragmentManager mFragmentManager;

    private ArrayList<Fragment> mFragments;
    private VPAdapter mVPAdapter;

    public static Intent createIntent(Context mContext) {
        return new Intent(mContext, SearchActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mTvActivityLabel = (EditText) findViewById(R.id.tv_activity_label);
        mTvActSearchDelete = (TextView) findViewById(R.id.tv_act_search_delete);
        mRelActSearch = (RelativeLayout) findViewById(R.id.rel_act_search);
        mRecyActSearchItem = (RecyclerView) findViewById(R.id.recy_act_search_item);
        mVpFragment = (LinearLayout) findViewById(R.id.vp_fragment);
        mTvFragSearArt = (TextView) findViewById(R.id.tv_frag_sear_art);
        mTvFragSearVideo = (TextView) findViewById(R.id.tv_frag_sear_video);
        mVpActSearch = (ViewPager) findViewById(R.id.vp_act_search);

        setListener();
        initData();
    }


    /**
     *
     */
    private void initData() {
        mLabelAdater = new LabelAdater(this);
        mSearchAdapter = new SearchAdapter();
        mVideoFragment = SearchVideoFragment.newInstance();
        mArticleFragment = SearchArticleFragment.newInstance();
        mFragmentManager = getSupportFragmentManager();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyActSearchItem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL));
        mRecyActSearchItem.setLayoutManager(layoutManager);
        final ArrayList<LabelBean> labes = mLabelAdater.getLabes();
        if (labes != null) {
            mSearchAdapter.addData(labes);
        }
        mRecyActSearchItem.setAdapter(mSearchAdapter);

        vpAddFragment();
        //条目点击监听
        mSearchAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {
                LabelBean bean = labes.get(position);
                mTvActivityLabel.setText(bean.getName());

                mRecyActSearchItem.setVisibility(View.GONE);
                mVpFragment.setVisibility(View.VISIBLE);

                //切换fragemnt
//                mFragmentManager.beginTransaction()
//                        .hide(mVideoFragment)
//                        .show(mArticleFragment)
//                        .commit();
                Bundle mBundle = new Bundle();
                mBundle.putString(Content.SEARCH_LABEL, bean.getName());
                mVideoFragment.setArguments(mBundle);
                mArticleFragment.setArguments(mBundle);
            }
        });
    }


    /**
     * 给ViewPager添加Fragment
     */
    private void vpAddFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(mArticleFragment);
        mFragments.add(mVideoFragment);
        mVPAdapter = new VPAdapter(mFragmentManager, mFragments);
        mVpActSearch.setAdapter(mVPAdapter);
        mVpActSearch.setCurrentItem(0);
    }

    /**
     *
     */
    public void setListener() {
        //删除
        mTvActSearchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvActivityLabel.setText("");
                mRecyActSearchItem.setVisibility(View.VISIBLE);
                mVpFragment.setVisibility(View.GONE);
            }
        });
        //文本搜索
        mTvActivityLabel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) mTvActivityLabel.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            SearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }

                return false;
            }
        });

    }

    /**
     * ViewPagerAdapter
     */
    class VPAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public VPAdapter(FragmentManager fm, List<Fragment> mlist) {
            super(fm);
            this.mFragments = mlist;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments != null ? mFragments.size() : 0;
        }
    }


    //标签条目Adapter
    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SViewHolder> implements View.OnClickListener {

        private OnClickListener mOnClickListener;
        private List<LabelBean> mLabelBeen;

        @Override
        public SViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(SearchActivity.this).inflate(R.layout.adapter_search_label, parent, false);
            inflate.setOnClickListener(this);
            return new SViewHolder(inflate);
        }

        public void addData(List<LabelBean> mLabelBeen) {
            this.mLabelBeen = mLabelBeen;
        }


        @Override
        public void onBindViewHolder(SViewHolder holder, int position) {
            holder.itemView.setTag(position);

            LabelBean bean = mLabelBeen.get(position);
            holder.mTvAdaSearch.setText(bean.getName());
        }

        @Override
        public int getItemCount() {
            return mLabelBeen != null ? mLabelBeen.size() : 0;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
            }
        }

        /**
         * 设置点击监听
         *
         * @param onClickListener
         */
        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        class SViewHolder extends RecyclerView.ViewHolder {
            private TextView mTvAdaSearch;

            public SViewHolder(View itemView) {
                super(itemView);
                mTvAdaSearch = (TextView) itemView.findViewById(R.id.tv_ada_search);
            }
        }
    }
}

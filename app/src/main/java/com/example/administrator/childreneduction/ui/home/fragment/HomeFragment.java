package com.example.administrator.childreneduction.ui.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.activity.LookArticleActivity;
import com.example.administrator.childreneduction.ui.home.adapter.HomeAdapter;
import com.example.administrator.childreneduction.ui.home.iview.HomeFragmentUI;
import com.example.administrator.childreneduction.ui.home.presenter.HomeFragmentPresenter;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.listener.OnLongClickListener;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;

import java.util.List;

import static com.example.administrator.childreneduction.App.mContext;

/**
 * Created by Administrator on 2017/5/7.
 */

public class HomeFragment extends BaseFagment implements HomeFragmentUI{

    private RecyclerView mRecyFragHomeItem;
    private HomeAdapter mHomeAdapter;

    private HomeFragmentPresenter mFragmentPresenter;

    @Override
    public int getLayOutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragHomeItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_home_item);
    }

    @Override
    public void initData() {
        mFragmentPresenter=new HomeFragmentPresenter(this);
        mFragmentPresenter.getArticle(getContext());
    }

    /**
     * @param list
     */
    private void initRecyclerView(final List<ArticleTable> list){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragHomeItem.setLayoutManager(manager);
        mHomeAdapter=new HomeAdapter(getContext(),list);
        mRecyFragHomeItem.setAdapter(mHomeAdapter);
        mRecyFragHomeItem.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));

        //点击监听
        mHomeAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {
                //查看每个条目的文章
                ArticleTable articleTable = list.get(position);
                Intent intent = LookArticleActivity.createIntent(getContext());
                intent.putExtra(Content.ARTICLE_INFO,articleTable);
                startActivity(intent);
            }
        });

        mHomeAdapter.setLongClickListener(new OnLongClickListener() {
            @Override
            public void setLongClickListener(View view, int position) {

            }
        });
    }

    @Override
    public void getArticle_ok(List<ArticleTable> list) {
        initRecyclerView(list);
    }
}

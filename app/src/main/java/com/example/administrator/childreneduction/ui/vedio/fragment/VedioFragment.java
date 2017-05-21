package com.example.administrator.childreneduction.ui.vedio.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.vedio.activity.VideoPlayerActivity;
import com.example.administrator.childreneduction.ui.vedio.adapter.VedioAdapter;
import com.example.administrator.childreneduction.ui.vedio.iview.VedioFragmentUI;
import com.example.administrator.childreneduction.ui.vedio.presenter.VedioFragmentPresenter;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class VedioFragment extends BaseFagment implements VedioFragmentUI {
    private RecyclerView mRecyFragVedioItem;

    private VedioAdapter mVedioAdapter;

    private VedioFragmentPresenter mFragmentPresenter;

    public static VedioFragment newInstance(){
        return new VedioFragment();
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_vedio;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragVedioItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_vedio_item);
    }

    private void initRecyclerView(final List<VedioTable> vedioTable){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragVedioItem.setLayoutManager(manager);
        mRecyFragVedioItem.addItemDecoration(new RecycleViewDivider(getContext(), DividerItemDecoration.VERTICAL));
        mVedioAdapter=new VedioAdapter(getContext(),vedioTable);
        mRecyFragVedioItem.setAdapter(mVedioAdapter);

        mVedioAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {
                Toast.makeText(getContext(),"点击了"+position,Toast.LENGTH_LONG);

                VedioTable vedio = vedioTable.get(position);
                if (vedioTable!=null){
                    Intent intent = VideoPlayerActivity.createIntent(getContext());
                    intent.putExtra("VEDIO", vedio);
                    startActivity(intent);
                }
            }
        });
    }



    @Override
    public void initData() {
        mFragmentPresenter=new VedioFragmentPresenter(this);
        mFragmentPresenter.video_item(getContext());
    }

    @Override
    public void video_item_ok(List<VedioTable> vedioTable) {
        if (vedioTable!=null){
            initRecyclerView(vedioTable);
        }
    }

    @Override
    public void video_item_fail() {

    }
}

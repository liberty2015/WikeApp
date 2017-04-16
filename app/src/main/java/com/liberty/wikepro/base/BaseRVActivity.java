package com.liberty.wikepro.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnLoadMoreListener;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LinJinFeng on 2016/12/23.
 */

public abstract class BaseRVActivity<T1 extends BaseContact.BasePresenter,T2>
        extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener ,OnLoadMoreListener {

    @BindView(R.id.list)
    protected RecyclerView list;
    protected BaseRecyclerAdapter<T2> mAdapter;

//    @BindView(R.id.error)
    protected ErrorEmptyLayout errorEmptyLayout;

//    @BindView(R.id.swipe)
    protected SwipeRefreshLayout refreshLayout;

    @Override
    protected void initViews() {
        refreshLayout= ButterKnife.findById(this,R.id.swipe);
        if (refreshLayout!=null){
            refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
            refreshLayout.setOnRefreshListener(this);
        }
        errorEmptyLayout=ButterKnife.findById(this,R.id.error);
    }

    protected void initAdapter(BaseRecyclerAdapter<T2> adapter,OnRecyclerItemClickListener listener,
                               boolean refreshable, boolean loadMoreable){
        if (list!=null){
            list.setLayoutManager(new LinearLayoutManager(this));
//            list.addItemDecoration(new DividerDecoration
//                      (ContextCompat.getColor(getHoldActivity(),R.color.common_divider_narrow),1,0,0));
            if (adapter!=null){
                list.setAdapter(adapter);
                mAdapter=adapter;
            }
            if (listener!=null){
                list.addOnItemTouchListener(listener);
            }
            if (loadMoreable){
                mAdapter.setMore(R.layout.common_more_view,this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshLayout!=null){
                if (refreshable){
                    refreshLayout.setOnRefreshListener(this);
                }else {
                    refreshLayout.setEnabled(false);
                }
            }
        }
    }

    protected void initAdapter(BaseRecyclerAdapter<T2> adapter, OnRecyclerItemClickListener listener,
                               boolean refreshable, boolean loadMoreable, GridLayoutManager manager){
        if (list!=null){
            if (manager==null) throw new NullPointerException("GridManager can't be null!");
            list.setLayoutManager(manager);
//            list.addItemDecoration(new DividerDecoration(ContextCompat.
//                    getColor(getHoldActivity(),R.color.common_divider_narrow),1,0,0));
            if (adapter!=null){
                list.setAdapter(adapter);
                mAdapter=adapter;
            }
            if (listener!=null){
                list.addOnItemTouchListener(listener);
            }
            if (loadMoreable){
                mAdapter.setMore(R.layout.common_more_view,this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshLayout!=null){
                if (refreshable){
                    refreshLayout.setOnRefreshListener(this);
                }else {
                    refreshLayout.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onRefresh() {

    }
}

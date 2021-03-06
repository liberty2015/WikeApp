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

public abstract class BaseRVFragment<T1 extends BaseContact.BasePresenter,T2> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener ,OnLoadMoreListener {
    /**
     * 若要继承该类，请务必使用common_list_fragment布局，
     * 或者自己在布局中添加RecyclerView、SwipeRefreshLayout
     * 和ErrorEmptyLayout。后期计划将它们封装成一个容器类
     */
    protected T1 mPresenter;

    @BindView(R.id.list)
    protected RecyclerView list;
    protected BaseRecyclerAdapter<T2> mAdapter;

//    @BindView(R.id.error)
    protected ErrorEmptyLayout errorEmptyLayout;
//
//    @BindView(R.id.swipe)
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public void attachView() {
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void initView() {
        refreshLayout= ButterKnife.findById(getHoldActivity(),R.id.swipe);
        if (refreshLayout!=null){
            refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//            refreshLayout.setOnRefreshListener(this);
        }
//        list=ButterKnife.findById(getHoldActivity(),R.id.list);
        errorEmptyLayout=ButterKnife.findById(getHoldActivity(),R.id.error);
    }

    protected void initAdapter(BaseRecyclerAdapter<T2> adapter,OnRecyclerItemClickListener listener,
                               boolean refreshable, boolean loadMoreable){
        if (list!=null){
            if (list.getLayoutManager()==null){
                list.setLayoutManager(new LinearLayoutManager(getHoldActivity()));
            }
//            list.addItemDecoration(new DividerDecoration(ContextCompat.getColor(getHoldActivity(),R.color.common_divider_narrow),1,0,0));
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }


}

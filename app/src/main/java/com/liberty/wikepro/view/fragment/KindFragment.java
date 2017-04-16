package com.liberty.wikepro.view.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.KindContact;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.presenter.KindPresenter;
import com.liberty.wikepro.view.activity.CatagoryActivity;
import com.liberty.wikepro.view.widget.adapter.KindAdapter;

import java.util.List;

import javax.inject.Inject;

public class KindFragment extends BaseRVFragment<KindContact.Presenter,itemType> implements KindContact.View {

    @Inject
    KindPresenter kindPresenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.common_list_fragment_layout;
    }

    @Override
    protected void initData() {
        KindAdapter adapter=new KindAdapter(getHoldActivity());
        errorEmptyLayout.setVisibility(View.GONE);
        kindPresenter.attachView(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getHoldActivity(),3);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridHeaderSpan(3));
        initAdapter(adapter,
                new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                itemType item = mAdapter.getItem(position);
                if (item instanceof Type){
                    Intent intent=new Intent(getHoldActivity(), CatagoryActivity.class);
                    intent.putExtra("type",(Type)item);
                    startActivity(intent);
                }
            }
        },false,false,gridLayoutManager);
        kindPresenter.getTypeList();
    }

    @Override
    protected void setFragmentComponent(ApplicationComponent component) {
        super.setFragmentComponent(component);
        DaggerMainComponent.builder().applicationComponent(component)
//                .applicationModule(new ApplicationModule(WikeApplication.getInstance()))
                .build().inject(this);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        kindPresenter.getTypeList();
    }

    @Override
    public void showTypeList(List<itemType> types) {
        mAdapter.clear();
        mAdapter.addAll(types);
    }

    @Override
    public void showError() {
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.NETWORK_ERROR);
    }

    @Override
    public void complete() {
        if (errorEmptyLayout.getLayoutType()!=ErrorEmptyLayout.HIDE_LAYOUT)
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        refreshLayout.setRefreshing(false);
    }
}

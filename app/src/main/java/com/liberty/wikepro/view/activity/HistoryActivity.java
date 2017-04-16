package com.liberty.wikepro.view.activity;

import android.graphics.Color;
import android.view.View;

import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.HistoryContact;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.presenter.HistoryPresenter;
import com.liberty.wikepro.view.widget.adapter.HistoryAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class HistoryActivity extends BaseRVActivity<HistoryContact.Presenter,itemType> implements HistoryContact.View{

    @Inject
    HistoryPresenter presenter;

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("历史记录");
        mCommonToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerMainComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initData() {
        initAdapter(new HistoryAdapter(this),null,false,false);
        presenter.attachView(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        refreshLayout.setEnabled(false);
//        initAdapter();
        presenter.getHistoryList(WikeApplication.getInstance().getStudent().getId());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.common_list_activity_layout;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        if (errorEmptyLayout.getLayoutType()!= ErrorEmptyLayout.HIDE_LAYOUT){
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        }
    }

    @Override
    public void showHistoryList(List<itemType> histories) {
        mAdapter.addAll(histories);
    }
}

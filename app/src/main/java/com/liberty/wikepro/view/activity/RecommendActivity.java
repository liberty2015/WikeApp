package com.liberty.wikepro.view.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.MainActivity;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerLoginComponent;
import com.liberty.wikepro.contact.RecommendContact;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.presenter.RecommendPresenter;
import com.liberty.wikepro.view.widget.adapter.RecommendAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/24.
 */

public class RecommendActivity extends BaseRVActivity<RecommendContact.Presenter,itemType> implements RecommendContact.View{

    @Inject
    RecommendPresenter recommendPresenter;

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("请选择您感兴趣的方向");
        mCommonToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void initData() {
        recommendPresenter.attachView(this);
        WikeApplication.getInstance().initStudent();
        final RecommendAdapter adapter=new RecommendAdapter(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridHeaderSpan(3));
        initAdapter(adapter, new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);

            }
        },false,false,gridLayoutManager);
        adapter.addFooter(new BaseRecyclerAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                View footer= LayoutInflater.from(RecommendActivity.this).inflate(R.layout.mine_footer,viewGroup,false);
                return footer;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setBackgroundColor(Color.WHITE);
                Button btn= (Button) headerView.findViewById(R.id.logout);
                btn.setText("确定");
                GradientDrawable background = (GradientDrawable) btn.getBackground();
                background.setTint(getResources().getColor(R.color.colorPrimary));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecommendActivity.this.showDialog();
                        recommendPresenter.sendRecommendTypes(WikeApplication.getInstance().getStudent().getId(),adapter.getTypes());

                    }
                });
            }
        });
        recommendPresenter.getTypeList();
    }

    @Override
    protected void initViews() {
        super.initViews();
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        DaggerLoginComponent.builder()
                .applicationComponent(component)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.common_list_activity_layout;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }

    @Override
    public void showTypeList(List<itemType> types) {
        mAdapter.clear();
        mAdapter.addAll(types);
    }

    @Override
    public void recommendSuccess() {
        dismissDialog();
        finish();
        startOtherActivity(MainActivity.class);
    }

    @Override
    public void recommendFail() {

    }
}

package com.liberty.wikepro.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.adapter.divider.DividerDecoration;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerCourseComponent;
import com.liberty.wikepro.contact.CatagoryContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.presenter.CatagoryPresenter;
import com.liberty.wikepro.view.widget.adapter.CatagoryAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/3.
 */

public class CatagoryActivity extends BaseRVActivity<CatagoryContact.Presenter,Course> implements CatagoryContact.View{

    @Inject
    CatagoryPresenter presenter;

    private Type type;
    private int limit=10,offset=0;

    @Override
    public void onLoadMore() {
        Log.d("xxxxx","---onLoadMore---");
        presenter.loadMore(WikeApplication.getInstance().getStudent().getId(),
                type.getId(),limit,(++offset)*limit);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        presenter.refresh(WikeApplication.getInstance().getStudent().getId(),type.getId());
    }

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle(type.getName());
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
    protected void initData() {
        type=getIntent().getParcelableExtra("type");
        initAdapter(new CatagoryAdapter(this), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                Course item = mAdapter.getItem(position);
                if (item.getHasStudy()==0){
                    ImageView cover= (ImageView) ((BaseHolder)holder).getView(R.id.cover);
                    Intent intent=new Intent(CatagoryActivity.this, CourseDetailActivity.class);
                    Bundle option = ActivityOptionsCompat.makeSceneTransitionAnimation(CatagoryActivity.this, cover, "shared_img").toBundle();
                    intent.putExtra("course",item);
                    ActivityCompat.startActivity(CatagoryActivity.this,intent,option);
                }else if (item.getHasStudy()==1){
                    Intent intent=new Intent(CatagoryActivity.this, CourseVideoActivity.class);
                    intent.putExtra("course",item);
                    startActivity(intent);
                }
            }
        },true,true);
        list.addItemDecoration(new DividerDecoration(ContextCompat.
                    getColor(this,R.color.common_divider_narrow),1,0,0));
        presenter.attachView(this);
        presenter.refresh(WikeApplication.getInstance().getStudent().getId(),type.getId());
    }

    @Override
    protected void initViews() {
        super.initViews();
//        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.common_list_activity_layout;
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerCourseComponent.builder()
                .applicationComponent(component)
                .build()
                .inject(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }



    @Override
    public void refresh(List<Course> courses) {
        if (errorEmptyLayout.getLayoutType()== ErrorEmptyLayout.NETWORK_LOADING){
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        }
        mAdapter.clear();
        refreshLayout.setRefreshing(false);
        mAdapter.addAll(courses);
    }

    @Override
    public void loadMore(List<Course> courses) {
        mAdapter.addAll(courses);
    }
}

package com.liberty.wikepro.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.MyCourseContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.presenter.MyCoursePresenter;
import com.liberty.wikepro.view.widget.adapter.CatagoryAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/4.
 */

public class MyCourseActivity extends BaseRVActivity<MyCourseContact.Presenter,Course> implements MyCourseContact.View{

    @Inject
    MyCoursePresenter presenter;

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("我的课程");
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
        initAdapter(new CatagoryAdapter(this), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                Course item = mAdapter.getItem(position);
                Intent intent=new Intent(MyCourseActivity.this, CourseVideoActivity.class);
                intent.putExtra("course",item);
                startActivity(intent);
            }
        },false,false);
        presenter.attachView(this);
        presenter.getMyCourses(WikeApplication.getInstance().getStudent().getId());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.common_list_activity_layout;
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerMainComponent.builder()
                .applicationComponent(component)
//                .applicationModule(new ApplicationModule(WikeApplication.getInstance()))
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
    public void showMyCourses(List<Course> courses) {
        if (errorEmptyLayout.getLayoutType()== ErrorEmptyLayout.NETWORK_LOADING){
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        }
        mAdapter.addAll(courses);

    }
}

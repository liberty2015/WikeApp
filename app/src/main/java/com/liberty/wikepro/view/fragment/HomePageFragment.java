package com.liberty.wikepro.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.adapter.divider.MarginDecoration;
import com.liberty.libertylibrary.widget.Banner;
import com.liberty.libertylibrary.widget.BannerContainer;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.contact.HomeContact;
import com.liberty.wikepro.model.bean.Catalog;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.view.activity.CourseDetailActivity;
import com.liberty.wikepro.view.widget.adapter.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Created by LinJinFeng
 */
public class HomePageFragment extends BaseRVFragment<HomeContact.Presenter,itemType> implements HomeContact.View{

    private BannerContainer bannerContainer;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_list_fragment_layout;
    }

    @Override
    protected void initData() {
        final HomePageAdapter adapter=new HomePageAdapter(getHoldActivity());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getHoldActivity(),2);
        HomePageAdapter.MultiSpan gridHeaderSpan = adapter.obtainGridHeaderSpan(2);
        gridLayoutManager.setSpanSizeLookup(gridHeaderSpan);
        initAdapter(adapter, new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                itemType item = adapter.getItem(position-1);
                if (item instanceof Course){
                    Course course= (Course) item;
                    if (course.getSpanCount()==2){
                        ImageView cover= (ImageView) ((BaseHolder)holder).getView(R.id.cover);
                        Intent intent=new Intent(getHoldActivity(), CourseDetailActivity.class);
                        Bundle option = ActivityOptionsCompat.makeSceneTransitionAnimation(getHoldActivity(), cover, "shared_img").toBundle();
                        ActivityCompat.startActivity(getHoldActivity(),intent,option);
                    }else if (course.getSpanCount()==1){
                        ImageView cover= (ImageView) ((BaseHolder)holder).getView(R.id.courseCover);
                        Intent intent=new Intent(getHoldActivity(), CourseDetailActivity.class);
                        Bundle option = ActivityOptionsCompat.makeSceneTransitionAnimation(getHoldActivity(), cover, "shared_img").toBundle();
                        ActivityCompat.startActivity(getHoldActivity(),intent,option);
                    }
                }

            }
        },true,true,gridLayoutManager);
        mAdapter.addHeader(new BaseRecyclerAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                return LayoutInflater.from(getHoldActivity()).inflate(R.layout.home_header,viewGroup,false);
            }

            @Override
            public void onBindView(View headerView) {
                bannerContainer= (BannerContainer) headerView.findViewById(R.id.bannerContainer);
            }
        });
        list.addItemDecoration(new MarginDecoration(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()),gridHeaderSpan));
        complete();
        initTestData();
    }

    private void initTestData(){
        List<itemType> courseList=new ArrayList<>();
        Catalog catalog=new Catalog();
        catalog.setTitle("为您推荐");
        catalog.setResId(R.drawable.ic_star);
        courseList.add(catalog);
        for (int i=0;i<8;i++){
            Course course=new Course();
            course.setCname("Java 入门");
            course.setPdev("http://pic.uuhy.com/uploads/2011/10/15/Macro-Photos15.jpg");
            course.setSpanCount(1);
            courseList.add(course);

        }
        Catalog catalog1=new Catalog();
        catalog1.setTitle("新课推荐");
        catalog1.setResId(R.drawable.ic_up);
        courseList.add(catalog1);
//        CourseList courseList1=new CourseList();
//        List<Course> courses=new ArrayList<>();
        for (int i=0;i<8;i++){
            Course course=new Course();
            course.setCname("Android 入门");
            course.setPdev("http://i-7.vcimg.com/crop/9809a42ef083af7a4d682334d91a2e7b373087(600x)/thumb.jpg");
            course.setSpanCount(2);
            courseList.add(course);
        }
//        courseList1.setCourses(courses);
//        courseList.add(courseList1);
        mAdapter.addAll(courseList);
    }

    @Override
    public void showBanner(Banner banner) {
        bannerContainer.setBanner(banner);
    }

    @Override
    public void showCourseList(List<Course> courses) {
        mAdapter.addAll(courses);
    }

    @Override
    public void showError() {
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.NO_DATA);
    }

    @Override
    public void complete() {
        if (errorEmptyLayout.getLayoutType()!= ErrorEmptyLayout.HIDE_LAYOUT){
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        }
        if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
    }


}

package com.liberty.wikepro.view.fragment;


import android.content.Intent;
import android.net.Uri;
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
import com.liberty.libertylibrary.widget.BannerViewPager;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.HomeContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.presenter.HomePresenter;
import com.liberty.wikepro.view.activity.CourseDetailActivity;
import com.liberty.wikepro.view.activity.CourseVideoActivity;
import com.liberty.wikepro.view.widget.adapter.HomePageAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Created by LinJinFeng
 */
public class HomePageFragment extends BaseRVFragment<HomeContact.Presenter,itemType> implements HomeContact.View{

    private BannerContainer bannerContainer;

    @Inject
    HomePresenter homePresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_list_fragment_layout;
    }

    @Override
    protected void initData() {
        homePresenter.attachView(this);
        final HomePageAdapter adapter=new HomePageAdapter(getHoldActivity());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getHoldActivity(),2);
        HomePageAdapter.MultiSpan gridHeaderSpan = adapter.obtainGridHeaderSpan(2);
        gridLayoutManager.setSpanSizeLookup(gridHeaderSpan);
        initAdapter(adapter, new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                itemType item = adapter.getItem(position);
                if (item instanceof Course){
                    Course course= (Course) item;
                    if (course.getSpanCount()==2){
                        if (course.getHasStudy()==0){
                            ImageView cover= (ImageView) ((BaseHolder)holder).getView(R.id.cover);
//                            cover.getLocationOnScreen();
                            Intent intent=new Intent(getHoldActivity(), CourseDetailActivity.class);
                            Bundle option = ActivityOptionsCompat.makeSceneTransitionAnimation(getHoldActivity(), cover, "shared_img").toBundle();
                            intent.putExtra("course",course);
                            ActivityCompat.startActivity(getHoldActivity(),intent,option);
                        }else if (course.getHasStudy()==1){
                            Intent intent=new Intent(getHoldActivity(), CourseVideoActivity.class);
                            intent.putExtra("course",course);
                            startActivity(intent);
                        }
                    }else if (course.getSpanCount()==1){
                        if (course.getHasStudy()==0) {
                            ImageView cover = (ImageView) ((BaseHolder) holder).getView(R.id.courseCover);
                            Intent intent = new Intent(getHoldActivity(), CourseDetailActivity.class);
                            Bundle option = ActivityOptionsCompat.makeSceneTransitionAnimation(getHoldActivity(), cover, "shared_img").toBundle();
                            intent.putExtra("course", course);
                            ActivityCompat.startActivity(getHoldActivity(), intent, option);
                        }else if (course.getHasStudy()==1){
                            Intent intent=new Intent(getHoldActivity(), CourseVideoActivity.class);
                            intent.putExtra("course",course);
                            startActivity(intent);
                        }
                    }
                }

            }
        },true,false,gridLayoutManager);
        mAdapter.addHeader(new BaseRecyclerAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                return LayoutInflater.from(getHoldActivity()).inflate(R.layout.home_header,viewGroup,false);
            }

            @Override
            public void onBindView(View headerView) {
                bannerContainer= (BannerContainer) headerView.findViewById(R.id.bannerContainer);
                bannerContainer.setBannerItemClick(new BannerViewPager.OnItemClickListener() {
                    @Override
                    public void onItemClick(Banner banner, int position) {
                        Intent intent=new Intent();
                        intent.setAction("com.liberty.wikepro.VIEW");
//                        intent.setAction(Intent.ACTION_VIEW);
                        String url="wikeApp://wikeHost"+"/"+banner.getPhotos().get(position).getContentUrl();
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }
        });
        list.addItemDecoration(new MarginDecoration(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()),gridHeaderSpan));
        complete();
//        initTestData();
        homePresenter.getCourseList(WikeApplication.getInstance().getStudent());
        homePresenter.getBanner();

    }

    @Override
    public void showBanner(Banner banner) {
        bannerContainer.setBanner(banner);
    }

    @Override
    public void showCourseList(List<itemType> courses) {
        mAdapter.clear();
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

    @Override
    protected void setFragmentComponent(ApplicationComponent component) {
        DaggerMainComponent.builder().applicationComponent(component)
//                .applicationModule(new ApplicationModule(WikeApplication.getInstance()))
                .build().inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        homePresenter.getCourseList(WikeApplication.getInstance().getStudent());
    }
}

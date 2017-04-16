package com.liberty.wikepro.view.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerCourseComponent;
import com.liberty.wikepro.contact.CourseContact;
import com.liberty.wikepro.contact.CourseDetailContact;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.presenter.CourseDetailPresenter;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.StatusBarCompat;
import com.liberty.wikepro.view.widget.adapter.ChapterVideoAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by LinJinFeng on 2017/2/22.
 */

public class CourseDetailActivity extends BaseRVActivity<CourseContact.Presenter,itemType> implements CourseDetailContact.View{

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.startStudy)
    FloatingActionButton startStudy;

    TextView teacherName,teacherJob;

    View courseDetailHeader;

    ImageView headerImg;

    Course course;

    @Inject
    CourseDetailPresenter detailPresenter;

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitleTextColor(Color.WHITE);
        mCommonToolbar.setTitle(course.getCname());
//        try {
//            Field textView= Toolbar.class.getDeclaredField("mTitleTextView");
//            textView.setAccessible(true);
//            TextView mTitleTextView= (TextView) textView.get(mCommonToolbar);
//            float radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
//            float dx= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
//            float dy=dx;
//            mTitleTextView.setTranslationZ(dx);
//            mTitleTextView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//            mTitleTextView.setShadowLayer(radius,dx,dy,Color.BLACK);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        initAdapter(new ChapterVideoAdapter(this), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);

            }
        },false,false);
        course=getIntent().getParcelableExtra("course");
    }

    @Override
    protected void initViews() {
        detailPresenter.attachView(this);
        StatusBarCompat.getTranslucentForCollapsingToolbarLayout(this,mCommonToolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        courseDetailHeader= LayoutInflater.from(this).inflate(R.layout.course_detail_header,list,false);
        mAdapter.addHeader(new BaseRecyclerAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                return courseDetailHeader;
            }

            @Override
            public void onBindView(View headerView) {
                headerImg = (ImageView) headerView.findViewById(R.id.headerImg);
                ImageUtil.getCircleImageIntoImageView(CourseDetailActivity.this,headerImg,
                        "http://static.oschina.net/uploads/space/2015/0629/162814_ow45_1767531.jpg",true);
                teacherName= (TextView) headerView.findViewById(R.id.teacherName);
                teacherJob= (TextView) headerView.findViewById(R.id.teacherJob);
            }
        });
        ((TextView)courseDetailHeader.findViewById(R.id.cdescription)).setText(course.getDescribtion());
        TextView clevel=((TextView)courseDetailHeader.findViewById(R.id.clevel));
        switch (course.getClevel()){
            case 0:{
                clevel.setBackground(getResources().getDrawable(R.drawable.easy));
                clevel.setText("简单");
            }
            break;
            case 1:{
                clevel.setBackground(getResources().getDrawable(R.drawable.middle));
                clevel.setText("中等");
            }
            break;
            case 2:{
                clevel.setBackground(getResources().getDrawable(R.drawable.hard));
                clevel.setText("困难");
            }
            break;
        }
        startStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPresenter.firstCourse(course.getId(), WikeApplication.getInstance().getStudent().getId());
            }
        });
        ImageUtil.getCircleImageIntoImageView(this,(ImageView) findViewById(R.id.cover),WikeApi.getInstance().getImageUrl(course.getPdev()),false);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            collapsingToolbarLayout.setTransitionGroup(false);
            getWindow().setSharedElementEnterTransition(
                    initSharedTransition());
            getWindow().setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.changebounds_arc));
            getWindow().setEnterTransition(initContentTransition());
            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.return_slide));
        }
        detailPresenter.getTeacherAndChapters(course.getUid(),course.getId());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition initSharedTransition(){
        Transition transition=TransitionInflater.from(this).inflateTransition(R.transition.changebounds_arc);
        return transition;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition initContentTransition(){
        Transition transition=TransitionInflater.from(this).inflateTransition(R.transition.slide);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
//                startStudy.animate()
//                        .scaleX(1)
//                        .scaleY(1)
//                        .start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        return transition;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.course_detail_layout;
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerCourseComponent
                .builder()
                .applicationComponent(component)
                .build()
                .inject(this);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void setTeacherAndChapters(User user, List<Chapter> chapters) {
        mAdapter.addAll(chapters);
        teacherName.setText(user.getName());
        teacherJob.setText(user.getJob());
    }

    @Override
    public void firstCourse() {
        Bundle data=new Bundle();
        data.putParcelable("course",course);
        startOtherActivity(CourseVideoActivity.class,data);
    }

//    @Override
//    public void showChapters(List<Chapter> chapters) {
//
//    }
}

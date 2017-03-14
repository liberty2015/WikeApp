package com.liberty.wikepro.view.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.contact.CourseContact;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.StatusBarCompat;
import com.liberty.wikepro.view.widget.adapter.ChapterVideoAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LinJinFeng on 2017/2/22.
 */

public class CourseDetailActivity extends BaseRVActivity<CourseContact.Presenter,itemType> {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.startStudy)
    FloatingActionButton startStudy;

    View courseDetailHeader;

    @Override
    protected void initToolbar() {
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
        initAdapter(new ChapterVideoAdapter(this), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);

            }
        },false,false);
        initTestData();
    }

    @Override
    protected void initViews() {
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            View decorView=getWindow().getDecorView();
//            int option=View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            WindowManager.LayoutParams params=getWindow().getAttributes();
//            params.flags=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|params.flags;
//        }
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
                ImageView headerImg = (ImageView) headerView.findViewById(R.id.headerImg);
                ImageUtil.getCircleImageIntoImageView(CourseDetailActivity.this,headerImg,
                        "http://static.oschina.net/uploads/space/2015/0629/162814_ow45_1767531.jpg",true);
            }
        });
        startStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherActivity(CourseVideoActivity.class);
            }
        });
        Picasso.with(this).load("http://pic.uuhy.com/uploads/2011/10/15/Macro-Photos15.jpg").into((ImageView) findViewById(R.id.cover));
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            collapsingToolbarLayout.setTransitionGroup(false);
            getWindow().setSharedElementEnterTransition(
                    initSharedTransition());
            getWindow().setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.changebounds_arc));
            getWindow().setEnterTransition(initContentTransition());
            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.return_slide));
        }
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
                startStudy.animate()
                        .scaleX(1)
                        .scaleY(1)
                        .start();
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

    private void initTestData(){
        List<itemType> itemTypes=new ArrayList<>();
        for (int i=0;i<5;i++){
            Chapter ch=new Chapter();
            itemTypes.add(ch);
            for (int j=0;j<3;j++){
                CVideo cvideo=new CVideo();
                itemTypes.add(cvideo);
            }
        }
        mAdapter.addAll(itemTypes);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.course_detail_layout;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.liberty.wikepro.view.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseRVActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerCourseComponent;
import com.liberty.wikepro.contact.ScoreContact;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Score;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.presenter.ScorePresenter;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.view.widget.adapter.ScoreAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by liberty on 2017/4/8.
 */

public class ScoreActivity extends BaseRVActivity<ScorePresenter,Chapter> implements ScoreContact.View {

    @BindView(R.id.score)
    TextView scoreTV;
    @BindView(R.id.headerImg)
    AppCompatImageView headerImg;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    Score score;
    Course course;

    @Inject
    ScorePresenter scorePresenter;

    @Override
    protected void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mCommonToolbar.setTitle("我的评分");
        mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        score=getIntent().getParcelableExtra("score");
        course=getIntent().getParcelableExtra("course");
        ImageUtil.getCircleImageIntoImageView(this,headerImg, WikeApi.getInstance().getImageUrl(WikeApplication.getInstance().getStudent().getHead_img()),true);
        initAdapter(new ScoreAdapter(this), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
            }
        },false,false);
//        list.addItemDecoration(new MarginDecoration());
        scorePresenter.getScoreDetail(WikeApplication.getInstance().getStudent().getId(),course.getId());
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerCourseComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Float[] nums=splitNum(score.getScore(), (int) ((70/1000f)*100));
        CounterRunnable runnable=new CounterRunnable(scoreTV,nums,70);
        scoreTV.removeCallbacks(runnable);
        scoreTV.post(runnable);
    }

    @Override
    protected void initViews() {
        scorePresenter.attachView(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)>=appBarLayout.getTotalScrollRange()){
                    collapsingToolbar.setTitle("我的评分");
                }else {
                    collapsingToolbar.setTitle("");
                }
            }
        });
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.score_layout;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showScoreDetail(List<Chapter> itemTypes) {
        mAdapter.addAll(itemTypes);
    }

    private class CounterRunnable implements Runnable{

        private TextView view;
        private Float[]nums;
        private long perTime;

        private int i=0;

        public CounterRunnable(TextView view,Float[]nums,long perTime){
            this.view=view;
            this.nums=nums;
            this.perTime=perTime;
        }

        @Override
        public void run() {
            if (i>nums.length-1){
                view.removeCallbacks(CounterRunnable.this);
                return;
            }
            view.setText(NumberFormat(nums[i++],1));
            view.removeCallbacks(CounterRunnable.this);
            view.postDelayed(CounterRunnable.this,perTime);
        }
    }

    private static Float[] splitNum(float num,int count){
        Random random=new Random();
        float numTmp=num;
        float sum=0;
        LinkedList<Float> nums=new LinkedList<>();
        nums.add(0f);
        while (true){
            float nextFloat=NumberFormatFloat((random.nextFloat()*num*2.0f)/count,1);
            if (numTmp-nextFloat>=0){
                sum=NumberFormatFloat(sum+nextFloat,2);
                nums.add(sum);
                numTmp-=nextFloat;
            }else {
                nums.add(num);
                return nums.toArray(new Float[0]);
            }
        }
    }

    private static String NumberFormat(float f,int m){
        return String.format("%."+m+"f",f);
    }

    private static float NumberFormatFloat(float f,int m){
        String strFloat=String.format("%."+m+"f",f);
        return Float.parseFloat(strFloat);
    }
}

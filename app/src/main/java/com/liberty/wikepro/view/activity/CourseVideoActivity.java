package com.liberty.wikepro.view.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liberty.videolibrary.NetworkVideoView;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.view.fragment.ChapterListFragment;
import com.liberty.wikepro.view.fragment.CourseDetailFragment;
import com.liberty.wikepro.view.widget.adapter.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseVideoActivity extends BaseActivity {

    @BindView(R.id.videoView)
    NetworkVideoView videoView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.playBtn)
    LinearLayout playBtn;



    @Override
    protected void initToolbar() {
//        mCommonToolbar.setTitle("hahaha");
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
        try {
            videoView.setPlayUrl("http://7xln7a.media1.z0.glb.clouddn.com/lihg3ecb8bvb5IimEIrAMgYjkCrc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset==0){
                    playBtn.setVisibility(View.GONE);
                }else if (Math.abs(verticalOffset)>=appBarLayout.getTotalScrollRange()){
                    playBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        List<BaseFragment> fragments=new ArrayList<>();
        fragments.add(new ChapterListFragment());
        fragments.add(new CourseDetailFragment());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments,new String[]{"列表","详情"}));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        videoView.setOnFullScreenListener(new NetworkVideoView.onFullScreenListener() {
            @Override
            public void onFullScreen(boolean isFullScreen, View videoView) {
                if (isFullScreen){
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    collapsingToolbarLayout.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                }else {
                    viewPager.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.getLayoutParams().height=
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250,getResources().getDisplayMetrics());
                }
            }
        });
        videoView.setOnPlayPauseListener(new NetworkVideoView.onPlayPauseListener() {
            @Override
            public void onPlay() {
                getSupportActionBar().hide();
                AppBarLayout.LayoutParams layoutParams =
                        (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                layoutParams.setScrollFlags(0);
            }

            @Override
            public void onPause() {
                getSupportActionBar().show();
                ((TextView)playBtn.findViewById(R.id.clickTxt)).setText("继续播放");
                AppBarLayout.LayoutParams layoutParams =
                        (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL| AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(true,true);
                videoView.play();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
        }else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (videoView.getIsFullScreen()){
                videoView.expandShrink(false);
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_course_video;
    }
}

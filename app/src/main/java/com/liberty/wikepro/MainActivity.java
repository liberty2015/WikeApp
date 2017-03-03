package com.liberty.wikepro;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.view.fragment.KindFragment;
import com.liberty.wikepro.view.fragment.HomePageFragment;
import com.liberty.wikepro.view.fragment.MineFragment;
import com.liberty.wikepro.view.widget.NavBtn;
import com.liberty.wikepro.view.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private Handler mHandler=new Handler();

    private List<BaseFragment> fragmentList;

    @BindView(R.id.homePage)
    NavBtn homePage;
    @BindView(R.id.kind)
    NavBtn course;
    @BindView(R.id.mine)
    NavBtn mine;

    String[] titles;

    private boolean pressAgain=false;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mCommonToolbar.setTitle(titles[0]);
        homePage.setIsSelect(true);
    }

    @Override
    protected void initData() {
        titles=getResources().getStringArray(R.array.main_arr);
    }

    @Override
    protected void initViews() {
        fragmentList=new ArrayList<>();
        HomePageFragment homePageFragment=new HomePageFragment();
        KindFragment courseFragment=new KindFragment();
        MineFragment mineFragment=new MineFragment();
        viewPager.setOffscreenPageLimit(3);
        fragmentList.add(homePageFragment);
        fragmentList.add(courseFragment);
        fragmentList.add(mineFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setNavTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    private void setNavTitle(int position){
        mCommonToolbar.setTitle(titles[position]);
        homePage.setIsSelect(false);
        course.setIsSelect(false);
        mine.setIsSelect(false);
        switch (position){
            case 0:{
                homePage.setIsSelect(true);
            }
            break;
            case 1:{
                course.setIsSelect(true);
            }
            break;
            case 2:{
                mine.setIsSelect(true);
            }
            break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (pressAgain){
            finish();
        }else {
            pressAgain=true;
            showToast("再按一次退出应用！");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressAgain=false;
                }
            },2000);
        }
    }

    @OnClick({
            R.id.homePage,
            R.id.kind,
            R.id.mine
    })
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.homePage:{
                viewPager.setCurrentItem(0);
            }
            break;
            case R.id.kind:{
                viewPager.setCurrentItem(1);
            }
            break;
            case R.id.mine:{
                viewPager.setCurrentItem(2);
            }
            break;
        }
    }
}

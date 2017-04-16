package com.liberty.wikepro;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.model.AppPreferenceHelper;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.view.activity.LoginActivity;
import com.liberty.wikepro.view.activity.SearchActivity;
import com.liberty.wikepro.view.fragment.HomePageFragment;
import com.liberty.wikepro.view.fragment.KindFragment;
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

    private AppCompatImageView searchImg;

    protected Student student;

    private boolean pressAgain=false;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mCommonToolbar.setTitle(titles[0]);
        searchImg=new AppCompatImageView(this);
        searchImg.setImageResource(R.drawable.ic_search);
        ViewGroup.MarginLayoutParams params=new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.RIGHT);
        params.rightMargin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
        mCommonToolbar.addView(searchImg,params);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherActivity(SearchActivity.class);
            }
        });
        homePage.setIsSelect(true);
    }



    @Override
    protected void initData() {
        student=new Student();
        student.setId(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getInt("id",0));
        student.setName(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("name",""));
        student.setNickname(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("nickName",""));
        student.setPhone(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("phone",""));
        student.setPassword(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("password",""));
        student.setEmail(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("email",""));
        student.setSelf_describe(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("self_describe",""));
        student.setHead_img(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("head_img",""));
        student.setPage_img(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("page_img",""));
        student.setJob(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("job",""));
        student.setGender(AppPreferenceHelper.getInstance(this,LoginActivity.LoginPre).getInt("gender",1));
        WikeApplication.getInstance().setStudent(student);
        titles=getResources().getStringArray(R.array.main_arr);
    }

//    public static Student getStudent() {
//        return student;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("student",student);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        student=savedInstanceState.getParcelable("student");
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
                setSearchImgVisuality(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    private void setSearchImgVisuality(int position){
        if (position<2){
            searchImg.setVisibility(View.VISIBLE);
        }else {
            searchImg.setVisibility(View.GONE);
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("xxxxxxx","----onPause----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("xxxxxxx","----onStop----");
    }
}

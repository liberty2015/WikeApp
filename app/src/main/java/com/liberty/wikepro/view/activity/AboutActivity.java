package com.liberty.wikepro.view.activity;

import android.graphics.Color;
import android.view.View;

import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;

/**
 * Created by liberty on 2017/3/18.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("关于我们");
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
    protected int setLayoutId() {
        return R.layout.activity_about;
    }
}

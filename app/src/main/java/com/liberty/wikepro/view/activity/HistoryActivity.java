package com.liberty.wikepro.view.activity;

import android.graphics.Color;
import android.view.View;

import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVActivity;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class HistoryActivity extends BaseRVActivity {
    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("历史记录");
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

    }

    @Override
    protected void initViews() {
        refreshLayout.setEnabled(false);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.common_list_activity_layout;
    }

    @Override
    public void onLoadMore() {

    }
}

package com.liberty.wikepro.view.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;

import java.lang.reflect.Field;

/**
 * Created by liberty on 2017/4/15.
 */

public class SearchActivity extends BaseActivity
//        implements View.OnKeyListener
{

    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.searchView);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
//                Class<? extends Toolbar> aClass = mCommonToolbar.getClass();
//                try {
//                    Field mCollapseButtonView = aClass.getField("mCollapseButtonView");
//                    AppCompatImageView collapseView= (AppCompatImageView) mCollapseButtonView.get(mCommonToolbar);
//                    collapseView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            finish();
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
        searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("搜索课程");
        MenuItemCompat.expandActionView(searchItem);
        Class<? extends Toolbar> aClass = mCommonToolbar.getClass();
        try {
            Field mCollapseButtonView = aClass.getDeclaredField("mCollapseButtonView");
            mCollapseButtonView.setAccessible(true);
            AppCompatImageButton collapseView= (AppCompatImageButton) mCollapseButtonView.get(mCommonToolbar);
            collapseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager methodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    methodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(),0,0);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    protected void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
            onBackPressed();
        return super.onKeyDown(keyCode, event);
    }

    //    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK){
//            onBackPressed();
//            return true;
//        }
//        return false;
//    }
}

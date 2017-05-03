package com.liberty.wikepro.view.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.SearchContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Search;
import com.liberty.wikepro.presenter.SearchPresenter;
import com.liberty.wikepro.view.widget.adapter.RecentAdapter;
import com.liberty.wikepro.view.widget.adapter.SearchAdapter;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by liberty on 2017/4/15.
 */

public class SearchActivity extends BaseActivity implements SearchContact.View
//        implements View.OnKeyListener
{

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.recent)
    RecyclerView recent;
    @BindView(R.id.historyContainer)
    RelativeLayout historyContainer;
    @BindView(R.id.delete)
    AppCompatImageView trash;
    private SearchView searchView;
    private SearchAdapter searchAdapter;
    private RecentAdapter recentAdapter;

    @Inject
    SearchPresenter searchPresenter;

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                Search search=new Search();
                search.setQuery(query);
                recentAdapter.add(search);
                searchPresenter.addQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    list.setVisibility(View.GONE);
                    historyContainer.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                list.setVisibility(View.GONE);
                historyContainer.setVisibility(View.VISIBLE);
                return true;
            }
        });
        searchView.setIconifiedByDefault(false);
        return true;
    }

    private void search(String query){
        searchPresenter.search(query);
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
        searchPresenter.attachView(this);
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        DaggerMainComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initViews() {
        searchAdapter=new SearchAdapter(this);
        list.setAdapter(searchAdapter);
        list.addOnItemTouchListener(new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                Course course=searchAdapter.getItem(position);
                if (course.getHasStudy()==0){
                    Intent intent=new Intent(SearchActivity.this, CourseDetailActivity.class);
                    intent.putExtra("course",course);
                    startActivity(intent);
                }else if (course.getHasStudy()==1){
                    Intent intent=new Intent(SearchActivity.this, CourseVideoActivity.class);
                    intent.putExtra("course",course);
                    startActivity(intent);
                }
            }
        });
        list.setLayoutManager(new LinearLayoutManager(this));
        recentAdapter=new RecentAdapter(this);
        recent.setAdapter(recentAdapter);
        recent.addOnItemTouchListener(new OnRecyclerItemClickListener(recent) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                search(recentAdapter.getItem(position).getQuery());
                searchView.setQuery(recentAdapter.getItem(position).getQuery(),false);
            }
        });
        recent.setLayoutManager(new LinearLayoutManager(this));
        searchPresenter.getRecents();
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentAdapter.clear();
                historyContainer.setVisibility(View.GONE);
                searchPresenter.deleteRecent();
            }
        });
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

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showSearchResult(List<Course> courses) {
        historyContainer.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        searchAdapter.clear();
        searchAdapter.addAll(courses);
    }

    @Override
    public void showRecentQuery(List<Search> searches) {
        if (searches.size()>0){
            recentAdapter.addAll(searches);
        }else {
            historyContainer.setVisibility(View.GONE);
        }
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

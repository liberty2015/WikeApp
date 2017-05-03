package com.liberty.wikepro.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.SearchContact;
import com.liberty.wikepro.model.SearchModel;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.SearchElement;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/16.
 */

public class SearchPresenter extends BasePresenter<SearchContact.View> implements SearchContact.Presenter {

    @Inject
    SearchModel searchModel;

    @Inject
    public SearchPresenter(){}

    @Override
    public void addQuery(String query) {
        searchModel.addQuery(query);
    }

    @Override
    public void deleteRecent() {
        searchModel.deleteRecent();
    }

    @Override
    public void search(final String query) {
        searchModel.search(query, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Gson gson=new Gson();
                List<Course> searches=gson.fromJson(result,new TypeToken<List<Course>>(){}.getType());
                SearchElement element=new SearchElement();
                element.setQuery(query);
                for (Course course:searches){
                    course.setSearchElement(element);
                }
                mView.showSearchResult(searches);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void getRecents() {
        mView.showRecentQuery(searchModel.getRecents());
    }
}

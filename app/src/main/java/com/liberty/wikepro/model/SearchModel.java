package com.liberty.wikepro.model;

import android.content.Context;

import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.model.bean.Search;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/16.
 */

public class SearchModel implements SearchM {

    private Context context;

    @Inject
    public SearchModel(Context context){
        this.context=context;
    }

    @Override
    public void search(String query, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id", WikeApplication.getInstance().getStudent().getId());
        params.add("search",query);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().search(), params, impl);
    }

    @Override
    public List<Search> getRecents() {
        List<Search> searches = AppDbHelper.getInstance(context).selectSearchs();
        return searches;
    }

    @Override
    public void addQuery(String query) {
        Search search=new Search();
        search.setQuery(query);
        AppDbHelper.getInstance(context).insertQuery(search);
    }

    @Override
    public void deleteRecent() {
        AppDbHelper.getInstance(context).deleteSearch();
    }
}

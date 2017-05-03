package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Search;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.List;

/**
 * Created by liberty on 2017/4/16.
 */

public interface SearchM {
    void search(String query, OkHttpUtil.OkHttpResponseIMPL impl);

    List<Search> getRecents();

    void addQuery(String query);

    void deleteRecent();
}

package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Search;

import java.util.List;

/**
 * Created by liberty on 2017/4/16.
 */

public interface SearchContact {
    interface View extends BaseContact.BaseView{
        void showSearchResult(List<Course> courses);

        void showRecentQuery(List<Search> searches);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{

        void addQuery(String query);

        void deleteRecent();

        void search(String query);

        void getRecents();
    }
}

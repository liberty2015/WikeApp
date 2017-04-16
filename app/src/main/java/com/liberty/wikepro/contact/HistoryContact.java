package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.itemType;

import java.util.List;

/**
 * Created by liberty on 2017/4/5.
 */

public interface HistoryContact {
    interface View extends BaseContact.BaseView{
        void showHistoryList(List<itemType> histories);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getHistoryList(int stu_id);
    }
}

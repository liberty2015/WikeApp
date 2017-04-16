package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.model.bean.itemType;

import java.util.List;

/**
 * Created by liberty on 2017/3/24.
 */

public interface RecommendContact {
    interface View extends BaseContact.BaseView{
        void showTypeList(List<itemType> types);

        void recommendSuccess();
        void recommendFail();
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getTypeList();
        void sendRecommendTypes(int stu_id,List<Type> types);
    }
}

package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.itemType;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/18.
 */

public interface KindContact {
    interface View extends BaseContact.BaseView{
        void showTypeList(List<itemType> types);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getTypeList();
    }
}

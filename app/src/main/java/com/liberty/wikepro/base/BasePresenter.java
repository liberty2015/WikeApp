package com.liberty.wikepro.base;

/**
 * Created by LinJinFeng on 2016/12/23.
 */

public class BasePresenter<T extends BaseContact.BaseView> implements BaseContact.BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        this.mView=null;
    }
}

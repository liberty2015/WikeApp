package com.liberty.wikepro.base;

/**
 * Created by LinJinFeng on 2017/2/13.
 */

public interface BaseContact {
    interface BasePresenter<T>{

        void attachView(T view);

        void detachView();
    }

    interface BaseView{

        void showError();

        void complete();
    }
}

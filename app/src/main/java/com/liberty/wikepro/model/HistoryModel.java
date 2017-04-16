package com.liberty.wikepro.model;

import android.content.Context;

import com.liberty.wikepro.model.bean.history;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/5.
 */

public class HistoryModel implements HistoryM {

    private Context context;

    @Inject
    public HistoryModel(Context context){
        this.context=context;
    }

    @Override
    public List<history> getHistoryList(int stu_id) {
        history history=new history();
        history.setStu_id(stu_id);
        return AppDbHelper.getInstance(context).selectBeansByTime(history);
    }
}

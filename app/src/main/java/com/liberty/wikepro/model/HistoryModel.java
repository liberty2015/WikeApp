package com.liberty.wikepro.model;

import android.content.Context;

import com.liberty.wikepro.model.bean.history;

import java.util.Date;
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
        List<history> histories = AppDbHelper.getInstance(context).selectBeansByTime(history);
        for (int i = 0; i < histories.size(); i++) {
            for (int j=0;j<histories.size()-i-1;j++){
                com.liberty.wikepro.model.bean.history history1=histories.get(j);
                Date date1=history1.getTime();
                com.liberty.wikepro.model.bean.history history2=histories.get(j+1);
                Date date2=history2.getTime();
                if (date1.compareTo(date2)<0){
                    history tmp=history2;
                    histories.set(j,history1);
                    histories.set(i,tmp);
                }
            }
        }
        return histories;
    }
}

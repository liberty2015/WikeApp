package com.liberty.wikepro.presenter;

import com.liberty.libertylibrary.util.DateUtil;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.HistoryContact;
import com.liberty.wikepro.model.HistoryModel;
import com.liberty.wikepro.model.bean.DateWrapper;
import com.liberty.wikepro.model.bean.history;
import com.liberty.wikepro.model.bean.itemType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/5.
 */

public class HistoryPresenter extends BasePresenter<HistoryContact.View> implements HistoryContact.Presenter {

    @Inject
    HistoryModel model;

    @Inject
    public HistoryPresenter(){}



    @Override
    public void getHistoryList(int stu_id) {
        mView.complete();
        List<history> historyList = model.getHistoryList(stu_id);
        List<itemType> itemTypes=new ArrayList<>();
        if (historyList.size()>0){
            DateWrapper wrapper=new DateWrapper(historyList.get(0).getTime());
            itemTypes.add(wrapper);
            itemTypes.add(historyList.get(0));
            for (int i=1;i<historyList.size();i++){
                history item=historyList.get(i);
                if (DateUtil.compareDateInOneDate(item.getTime(),historyList.get(i-1).getTime())!=0){
                    DateWrapper wrapper1=new DateWrapper(item.getTime());
                    itemTypes.add(wrapper1);
                }
                itemTypes.add(item);
            }
        }
        mView.showHistoryList(itemTypes);
    }
}

package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Chapter;

import java.util.List;

/**
 * Created by liberty on 2017/4/8.
 */

public interface ScoreContact {
    interface View extends BaseContact.BaseView{
        void showScoreDetail(List<Chapter> itemTypes);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getScoreDetail(int stu_id,int cid);
    }
}

package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.history;

import java.util.List;

/**
 * Created by liberty on 2017/4/5.
 */

public interface HistoryM {
    List<history> getHistoryList(int stu_id);
}

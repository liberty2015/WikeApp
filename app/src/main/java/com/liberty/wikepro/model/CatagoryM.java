package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/4/3.
 */

public interface CatagoryM {
    void refresh(int stu_id,int id, OkHttpUtil.OkHttpResponseIMPL impl);

    void loadMore(int stu_id,int id,int limit,int offset,OkHttpUtil.OkHttpResponseIMPL impl);
}

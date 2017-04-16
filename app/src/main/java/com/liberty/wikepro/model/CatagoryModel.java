package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/3.
 */

public class CatagoryModel implements CatagoryM {

    @Inject
    public CatagoryModel(){}

    @Override
    public void refresh(int stu_id,int id, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        params.add("type",id);
        params.add("limit",10);
        params.add("offset",0);
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getTypeCourse(),params,impl);
    }

    @Override
    public void loadMore(int stu_id,int id, int limit, int offset, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        params.add("type",id);
        params.add("limit",limit);
        params.add("offset",offset);
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getTypeCourse(),params,impl);
    }
}

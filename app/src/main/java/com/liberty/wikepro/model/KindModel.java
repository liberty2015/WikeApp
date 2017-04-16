package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/19.
 */

public class KindModel implements KindM {

    @Inject
    public KindModel(){}

    @Override
    public void getTypeList(OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getTypeList(), null, impl);
    }
}

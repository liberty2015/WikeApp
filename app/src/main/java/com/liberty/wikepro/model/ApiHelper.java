package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil.OkHttpResponseIMPL;
import com.liberty.wikepro.net.OkHttpUtil.RequestParams;
/**
 * Created by LinJinFeng on 2017/2/20.
 */

public interface ApiHelper {
    void registerByPhone(RequestParams params,OkHttpResponseIMPL impl);
    void registerByEmail(RequestParams params,OkHttpResponseIMPL impl);
    void login(RequestParams params, OkHttpResponseIMPL impl);
}

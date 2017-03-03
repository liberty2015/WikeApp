package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

/**
 * Created by LinJinFeng on 2017/2/20.
 */

public class AppApiHelper implements ApiHelper {

    private static AppApiHelper instance;

    public static AppApiHelper getInstance() {
        if (instance==null){
            synchronized (AppApiHelper.class){
                instance=new AppApiHelper();
            }
        }
        return instance;
    }

    @Override
    public void registerByPhone(OkHttpUtil.RequestParams params, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.getInstance().post(WikeApi.getInstance().registerByPhone(), params,impl);
    }

    @Override
    public void registerByEmail(OkHttpUtil.RequestParams params, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.getInstance().post(WikeApi.getInstance().registerByEmail(), params,impl);
    }

    @Override
    public void login(OkHttpUtil.RequestParams params, OkHttpUtil.OkHttpResponseIMPL impl) {

    }
}

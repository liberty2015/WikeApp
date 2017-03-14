package com.liberty.wikepro.util;

/**
 * Created by liberty on 2017/3/14.
 */

public interface NetChangeObserver {

    void onNetConnected(NetUtils.NetType type);

    void onNetDisconnect();
}

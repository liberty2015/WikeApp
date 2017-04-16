package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;

import java.io.File;

/**
 * Created by liberty on 2017/3/18.
 */

public interface MineM {
    void editUserCover(int id, File head, OkHttpUtil.OkHttpResponseIMPL impl);
}

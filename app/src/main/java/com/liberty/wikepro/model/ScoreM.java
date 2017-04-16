package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/4/8.
 */

public interface ScoreM {

    void getScoreDetail(int stu_id, int cid, OkHttpUtil.OkHttpResponseIMPL impl);
}

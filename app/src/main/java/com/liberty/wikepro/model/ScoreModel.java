package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/8.
 */

public class ScoreModel implements ScoreM {

    @Inject
    public ScoreModel(){}

    @Override
    public void getScoreDetail(int stu_id, int cid, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        params.add("cid",cid);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().particularScore(),params,impl);
    }
}

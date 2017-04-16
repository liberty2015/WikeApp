package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/18.
 */

public class MineModel implements MineM {

    @Inject
    public MineModel(){

    }

    @Override
    public void editUserCover(int id, File head, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("id",Integer.toString(id));
        String[] keys={"image"};
        File[] files={head};
        try {
            OkHttpUtil.getInstance().postFile(WikeApi.getInstance().editUserCover(),files,keys,params,impl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

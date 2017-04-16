package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/24.
 */

public class RecommendModel implements RecommendM {

    @Inject
    public RecommendModel(){}

    @Override
    public void getTypeList(OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getTypeList(), null, impl);
    }

    @Override
    public void sendRecommendTypes(List<Type> types,int stu_id, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        if (stu_id!=0){
            params.add("id",Integer.toString(stu_id));
            for (int i=0;i<types.size();i++){
                String key="type["+i+"]";
                params.add(key,Integer.toString(types.get(i).getId()));
            }
            OkHttpUtil.getInstance().post(WikeApi.getInstance().updateRecommend(),params,impl);
        }

    }
}

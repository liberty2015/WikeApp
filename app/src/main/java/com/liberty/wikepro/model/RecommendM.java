package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.List;

/**
 * Created by liberty on 2017/3/24.
 */

public interface RecommendM {
    void getTypeList(OkHttpUtil.OkHttpResponseIMPL impl);
    void sendRecommendTypes(List<Type> types,int stu_id, OkHttpUtil.OkHttpResponseIMPL impl);
}

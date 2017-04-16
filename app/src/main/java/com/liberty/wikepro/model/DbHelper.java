package com.liberty.wikepro.model;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/20.
 */

public interface DbHelper {
    <T>void insertBean(T bean);

    <M> List<M> selectBeans(Class<M> clazz);

    <N> N selectBean(N n);

    <L> void deleteBean(L bean);
}

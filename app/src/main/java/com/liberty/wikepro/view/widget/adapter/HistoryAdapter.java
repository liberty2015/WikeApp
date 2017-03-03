package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.model.bean.history;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class HistoryAdapter extends BaseRecyclerAdapter<history> {
    public HistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
//        return new BaseHolder<history>() {
//        };
        return null;
    }
}

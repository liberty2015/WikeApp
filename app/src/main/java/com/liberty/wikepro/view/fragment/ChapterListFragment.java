package com.liberty.wikepro.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.contact.CourseListContact;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.view.widget.adapter.ChapterVideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class ChapterListFragment extends BaseRVFragment<CourseListContact.Presenter,itemType> {
    @Override
    protected int getLayoutResId() {
        return R.layout.common_list_fragment_layout;
    }

    @Override
    protected void initData() {
        initAdapter(new ChapterVideoAdapter(getHoldActivity()), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);

            }
        },false,false);
        initTestData();
        mAdapter.addHeader(new BaseRecyclerAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                return LayoutInflater.from(getHoldActivity()).inflate(R.layout.common_header,list,false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    private void initTestData(){
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        List<itemType> itemTypes=new ArrayList<>();
        for (int i=0;i<5;i++){
            Chapter ch=new Chapter();
            itemTypes.add(ch);
            for (int j=0;j<3;j++){
                CVideo cvideo=new CVideo();
                itemTypes.add(cvideo);
            }
        }
        mAdapter.addAll(itemTypes);
    }
}

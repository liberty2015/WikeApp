package com.liberty.wikepro.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.contact.CourseVideoContact;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.view.activity.CourseVideoActivity;
import com.liberty.wikepro.view.widget.adapter.ChapterVideoAdapter;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class ChapterListFragment extends BaseRVFragment<CourseVideoContact.Presenter,itemType> {

    public CVideo currentCVideo;
    public int currentVideoPosition;

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
                itemType itemType=mAdapter.getItem(position);
                if (itemType instanceof CVideo){
                    currentCVideo= (CVideo) itemType;
                    ((CourseVideoActivity)getHoldActivity()).setCVideo(currentCVideo);
                    currentVideoPosition=position;
                }
            }
        },false,false);
//        initTestData();
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

    public void nextVideo(){
        if (currentVideoPosition!=mAdapter.getDataList().size()-1){
            for (int i=currentVideoPosition+1;i<mAdapter.getDataList().size();i++){
                itemType item = mAdapter.getItem(i);
                if (item instanceof CVideo){
                    currentCVideo= (CVideo) item;
                    ((CourseVideoActivity)getHoldActivity()).setCVideo(currentCVideo);
                    currentVideoPosition=i;
                    break;
                }
            }
        }
    }

    public void fillChapters(List<itemType> chapters){
        mAdapter.addAll(chapters);
    }
}

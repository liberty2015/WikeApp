package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.libertylibrary.widget.NestListView.BaseNestedAdapter;
import com.liberty.libertylibrary.widget.NestListView.NestFullListView;
import com.liberty.libertylibrary.widget.NestListView.NestFullViewHolder;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.CTest;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liberty on 2017/4/8.
 */

public class ScoreAdapter extends BaseRecyclerAdapter<Chapter> {
    public ScoreAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
//        switch (viewType){
//            case CHAPTER:{
//                return new BaseHolder<Chapter>(viewGroup, R.layout.chapter_item) {
//                    @Override
//                    public void setData(Chapter item) {
//                        super.setData(item);
//                    }
//                };
//            }
//            case CVIDEO:{
//                return new BaseHolder<CVideo>(viewGroup,R.layout.cvideo_item_2) {
//                    @Override
//                    public void setData(CVideo item) {
//                        super.setData(item);
//                    }
//                };
//            }
//            case CTEST:{
//                return new BaseHolder<CTest>(viewGroup,) {
//                }
//            }
//        }
        return new BaseHolder<Chapter>(viewGroup, R.layout.score_item) {
            @Override
            public void setData(Chapter item) {
                super.setData(item);
                TextView chapterTv=getView(R.id.chapter_name);
                chapterTv.setText(item.getChname());
                if (item.getItemTypes().size()>0){
                    NestFullListView listView=getView(R.id.nestList);
                    listView.setAdpter(new NestAdapter(getmContext(),item.getItemTypes()));
                }else {
                    NestFullListView listView=getView(R.id.nestList);
                    listView.setAdpter(new NestAdapter(getmContext(),new ArrayList<itemType>()));
                }
            }
        };
    }

    private final static int CHAPTER=0x11;
    private final static int CVIDEO=0x12;
    private final static int CTEST=0x13;

//    @Override
//    public int getViewType(int position) {
//        itemType itemType=getItem(position);
//        if (itemType instanceof Chapter){
//            return CHAPTER;
//        }else if (itemType instanceof CVideo){
//            return CVIDEO;
//        }else if (itemType instanceof CTest){
//            return CTEST;
//        }
//        return super.getViewType(position);
//    }

    private class NestAdapter extends BaseNestedAdapter<itemType>{

        @IdRes int OPTIONS=0x123;

        public NestAdapter(Context context, List<itemType> mDatas) {
            super(context, mDatas);
        }

        @Override
        public void onBind(int position, NestFullViewHolder holder) {
            int viewType=getViewType(position);
            switch (viewType){
                case CVIDEO:{
                    TextView textView= (TextView) holder.getmConvertView();
                    CVideo item = (CVideo) getItem(position);
                    textView.setText(item.getVname());
                }
                break;
                case CTEST:{
                    CTest test= (CTest) getItem(position);
                    TextView content=holder.getView(R.id.content);
                    content.setText(test.getCvcontent());
                    LinearLayout group=(LinearLayout)holder.getmConvertView();
                    if (test.getCvtype()>0&&test.getCvtype()<3){
                        ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(
                                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                        int margin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getmContext().getResources().getDisplayMetrics());
                        params.leftMargin=2*margin;
                        LinearLayout optionsContainer= (LinearLayout) group.findViewById(OPTIONS);
                        if (optionsContainer==null){
                            optionsContainer=new LinearLayout(getmContext());
                            optionsContainer.setId(OPTIONS);
                            optionsContainer.setOrientation(LinearLayout.VERTICAL);
                            optionsContainer.setLayoutParams(params);
                        }else {
                            optionsContainer.removeAllViews();
                        }
                        params.bottomMargin=margin;
                        params.topMargin=margin;
                        group.removeView(group.findViewById(OPTIONS));
                        String cvoptions = test.getCvoptions();
                        String[] split = cvoptions.split("///");
                        char a='A';
                        int index=0;
                        for (String option:split){
                            TextView optionTv=new TextView(getmContext());
                            optionTv.setText(a+" "+option);
                            optionsContainer.addView(optionTv,index++,params);
                            a++;
                        }
                        group.addView(optionsContainer,2);
                    }else {
                        if (holder.getView(OPTIONS)!=null)
                            group.removeView(group.findViewById(OPTIONS));
                    }
                    TextView answerTv=holder.getView(R.id.myAnswer);
                    String myanswer="我的回答："+(test.getAnswer()==null?"无":test.getAnswer());
                    answerTv.setText(myanswer);
                    TextView solutionTv=holder.getView(R.id.solution);
                    String solution="正确答案："+(test.getSolution()==null?"无":test.getSolution());
                    solutionTv.setText(solution);
                }
                break;
            }
        }

        @Override
        public View onCreateView(ViewGroup parent, int position) {
            int viewType=getViewType(position);
            switch (viewType){
                case CVIDEO:{
                    TextView cvName=new TextView(getmContext());
                    ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    params.leftMargin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,
                            getmContext().getResources().getDisplayMetrics());
                    int margin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getmContext().getResources().getDisplayMetrics());
                    params.topMargin=margin;
                    params.bottomMargin=margin;
                    cvName.setTextColor(Color.BLACK);
                    cvName.setLayoutParams(params);
                    return cvName;
                }
                case CTEST:{
                    View view=LayoutInflater.from(getmContext()).inflate(R.layout.ctest_item,parent,false);
                    return view;
                }
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return getmDatas().size();
        }

        @Override
        public int getViewType(int position) {
            itemType itemType=getItem(position);
            if (itemType instanceof CVideo){
                return CVIDEO;
            }else if (itemType instanceof CTest){
                return CTEST;
            }
            return super.getViewType(position);
        }

        private final static int CVIDEO=0x12;
        private final static int CTEST=0x13;
    }

}

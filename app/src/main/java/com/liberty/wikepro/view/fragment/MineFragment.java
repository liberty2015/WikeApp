package com.liberty.wikepro.view.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.LinearListCreator;
import com.liberty.wikepro.view.activity.HistoryActivity;
import com.liberty.wikepro.view.activity.PersonActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

//    @BindView(R.id.headerImg)
    ImageView headerImg;

    private View mineHeader;
    @BindView(R.id.emptyRoot)
    ScrollView emptyRoot;

    @Override
    protected int getLayoutResId() {
        return R.layout.empty_layout;
    }


    @Override
    protected void initView() {
        mineHeader=LayoutInflater.from(getHoldActivity()).inflate(R.layout.mine_header,null);
        headerImg=((ImageView)mineHeader.findViewById(R.id.headerImg));
        mineHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics())));
        LinearLayout.LayoutParams defLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60,getResources().getDisplayMetrics()));
        new LinearListCreator.Builder(getHoldActivity(),R.layout.common_list_item)
                .addHeader(mineHeader)
                .forViews(3,defLayoutParams)
                .spaceView()
                .forViews(2,defLayoutParams)
                .addView(LayoutInflater.from(getHoldActivity()).inflate(R.layout.mine_footer,null))
//                .needLine(true)
                .withBaseView(emptyRoot)
                .inflater(new LinearListCreator.OnInflaterCallback() {
                    @Override
                    public void onInflater(int position, View v) {
                        switch (position){
                            case 0:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_person);
                                ((TextView)v.findViewById(R.id.itemName)).setText("个人信息");
                            }
                            break;
                            case 1:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_history);
                                ((TextView)v.findViewById(R.id.itemName)).setText("历史记录");
                            }
                            break;
                            case 2:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_book);
                                ((TextView)v.findViewById(R.id.itemName)).setText("我的课程");
                            }
                            break;
                            case 4:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_about);
                                ((TextView)v.findViewById(R.id.itemName)).setText("关于");
                            }
                            break;
                            case 5:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_clean);
                                ((TextView)v.findViewById(R.id.itemName)).setText("清除缓存");
                            }
                            break;
                        }
                    }
                })
                .withClickCallback(new LinearListCreator.OnClickCallback() {
                    @Override
                    public void onClick(int position, View v) {
                        switch (position){
                            case 0:{
                                startActivity(new Intent(getHoldActivity(), PersonActivity.class));
                            }
                            break;
                            case 1:{
                                startActivity(new Intent(getHoldActivity(), HistoryActivity.class));
                            }
                            break;
                            case 2:{

                            }
                            break;
                            case 4:{

                            }
                            break;
                            case 5:{

                            }
                            break;
                        }
                    }
                }).build();
        ImageUtil.getCircleImageIntoImageView(getHoldActivity(),
                headerImg,"http://www.popoho.com/uploads/allimg/121225/2-121225153517.jpg",true);
        mineHeader.findViewById(R.id.userName).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mineHeader.findViewById(R.id.userJob).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mineHeader.findViewById(R.id.userDescription).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void attachView() {

    }

}

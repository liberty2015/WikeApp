package com.liberty.wikepro.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.ScoreContact;
import com.liberty.wikepro.model.ScoreModel;
import com.liberty.wikepro.model.bean.CTest;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/8.
 */

public class ScorePresenter extends BasePresenter<ScoreContact.View> implements ScoreContact.Presenter {

    @Inject
    ScoreModel scoreModel;

    @Inject
    public ScorePresenter(){}

    @Override
    public void getScoreDetail(int stu_id,int cid) {
        scoreModel.getScoreDetail(stu_id, cid, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Gson gson=new Gson();
                List<Chapter> chapters=gson.fromJson(result,new TypeToken<List<Chapter>>(){}.getType());
                for (Chapter chapter:chapters){
                    List<CVideo> cvideo = chapter.getCvideo();
                    List<itemType> itemTypes=new ArrayList<itemType>();
                    if (cvideo!=null&&cvideo.size()>0){
                        for (CVideo video:cvideo){
                            itemTypes.add(video);
                            List<CTest> cTests=video.getCtest();
                            if (cTests!=null&&cTests.size()>0){
                                for (CTest test:cTests){
                                    itemTypes.add(test);
                                }
                            }
                        }
                    }

                    chapter.setItemTypes(itemTypes);
                }
                mView.showScoreDetail(chapters);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }
}

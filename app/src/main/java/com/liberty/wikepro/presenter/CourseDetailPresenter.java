package com.liberty.wikepro.presenter;

import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.CourseDetailContact;
import com.liberty.wikepro.model.CourseDetailModel;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/27.
 */

public class CourseDetailPresenter extends BasePresenter<CourseDetailContact.View> implements CourseDetailContact.Presenter {

    @Inject
    CourseDetailModel model;

    @Inject
    public CourseDetailPresenter(){}

    @Override
    public void detachView() {

    }

    @Override
    public void getTeacherAndChapters(int uid,int cid) {
        model.getTeacherAndChapters(uid, cid, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
//                    String userJson=object.getJSONObject("user").toString();
//                    String chapterJson=object.getJSONArray("chapter").toString();
//                    Log.d("xxxxx","chapterJson="+chapterJson);
//                    Gson gson=new Gson();
//                    User user=gson.fromJson(userJson,User.class);
//                    List<Chapter> chapters=gson.fromJson(chapterJson,new TypeToken<Chapter>(){}.getType());
                    JSONObject userJson=object.getJSONObject("user");
                    User user=new User();
                    user.setId(!userJson.isNull("id")?userJson.getInt("id"):0);
                    user.setName(!userJson.isNull("name")?userJson.getString("name"):"");
                    user.setDescribtion(!userJson.isNull("describtion")?userJson.getString("describtion"):"");
                    user.setJob(!userJson.isNull("job")?userJson.getString("job"):"");
                    user.setSex(!userJson.isNull("sex")?userJson.getString("sex"):"");
                    JSONArray chapterJson=object.getJSONArray("chapter");
                    List<Chapter> chapters=new ArrayList<Chapter>();
                    for (int i=0;i<chapterJson.length();i++){
                        JSONObject jsonObject=chapterJson.getJSONObject(i);
                        Chapter chapter=new Chapter();
                        chapter.setId(!jsonObject.isNull("id")?Integer.parseInt(jsonObject.getString("id")):0);
                        chapter.setChname(!jsonObject.isNull("chname")?jsonObject.getString("chname"):"");
                        chapter.setChnum(!jsonObject.isNull("chnum")?jsonObject.getInt("chnum"):0);
                        chapters.add(chapter);
                    }
                    mView.setTeacherAndChapters(user,chapters);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void firstCourse(int cid,int id) {
        model.firstCourse(cid, id, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                mView.firstCourse();
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

//    @Override
//    public void getChapters(int cid) {
//
//    }
}

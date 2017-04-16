package com.liberty.wikepro.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.CourseVideoContact;
import com.liberty.wikepro.model.CourseVideoModel;
import com.liberty.wikepro.model.bean.CTest;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Score;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/28.
 */

public class CourseVideoPresenter extends BasePresenter<CourseVideoContact.View>
        implements CourseVideoContact.Presenter {

    @Inject
    CourseVideoModel courseVideoModel;

    @Inject
    public CourseVideoPresenter(){

    }

    @Override
    public void getChapterList(int cid, int stu_id) {
        courseVideoModel.getChapterList(cid, stu_id, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                try {
                    JSONObject data=new JSONObject(result);
                    JSONArray chapterJson=data.getJSONArray("chapters");
                    Gson gson=new Gson();
                    List<Chapter> chapters=gson.fromJson(chapterJson.toString(),new TypeToken<List<Chapter>>(){}.getType());
                    JSONArray videoJson=data.getJSONArray("cvideos");
                    List<CVideo> cVideos=gson.fromJson(videoJson.toString(),new TypeToken<List<CVideo>>(){}.getType());
                    List<itemType> itemTypes=new ArrayList<itemType>();
                    for (int i=0;i<chapters.size();i++){
                        Chapter chapter=chapters.get(i);
                        itemTypes.add(chapter);
                        int j=0;
                        while (j<cVideos.size()){
                            CVideo cVideo=cVideos.get(j);
                            if (chapter.getId()==cVideo.getChid()){
                                itemTypes.add(cVideo);
                                cVideo.setChapter(chapter);
                                cVideos.remove(cVideo);
                            }else {
                                j++;
                            }
                        }
                    }
                    mView.showChapterList(itemTypes);
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
    public void getTeacher(int uid,int cid) {
        courseVideoModel.getTeacher(uid, cid, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Gson gson=new Gson();
                User user=gson.fromJson(result, User.class);
                mView.showTeacher(user);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void getDependentCourses(Course course) {
        courseVideoModel.getDependentCourses(course, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Gson gson=new Gson();
                List<Course> courses=gson.fromJson(result,new TypeToken<List<Course>>(){}.getType());
                mView.showDependentCourses(courses);
//                JSONObject dataJson=new JSONObject(result);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void getVideoTest(CVideo cVideo) {
        courseVideoModel.getVideoTest(cVideo, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Gson gson=new Gson();
                List<CTest> cTests=gson.fromJson(result,new TypeToken<List<CTest>>(){}.getType());
                mView.getVideoTestList(cTests);
            }

            @Override
            public void onAnalyseDataError(String result) {
                List<CTest> cTests=new ArrayList<CTest>();
                mView.getVideoTestList(cTests);
            }
        });
    }

    @Override
    public void applyAnswer(int stu_id, int test_id, String answer, final int flag) {
        courseVideoModel.applyAnswer(stu_id, test_id, answer, flag, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                if (flag==0){
                    mView.applyAnswerRight();
                }else if (flag==1){
                    mView.applyAnswerWrong();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void stuChapterCVideo(int stu_id, int cid, int chid, int cvideo_id) {
        courseVideoModel.stuChapterCVideo(stu_id, cid, chid, cvideo_id, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {

            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void hasScore(int stu_id, int cid) {
        courseVideoModel.hasScore(stu_id, cid, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Score score=new Gson().fromJson(result, Score.class);
                mView.hasScore(score);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void addHistory(Course course, Chapter chapter, CVideo cVideo, int stu_id) {
        courseVideoModel.addHistory(course,chapter,cVideo,stu_id);
    }
}

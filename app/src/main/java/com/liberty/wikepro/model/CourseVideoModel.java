package com.liberty.wikepro.model;

import android.content.Context;

import com.liberty.libertylibrary.util.DateUtil;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.history;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/28.
 */

public class CourseVideoModel implements CourseVideoM {

    private Context context;

    @Inject
    public CourseVideoModel(Context context){
        this.context=context;
    }

    @Override
    public void getChapterList(int cid, int stu_id, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("cid",Integer.toString(cid));
        params.add("id",Integer.toString(stu_id));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getCourseVideo(),params,impl);
    }

    @Override
    public void getTeacher(int uid,int cid, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("uid",Integer.toString(uid));
        params.add("cid",Integer.toString(cid));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getTeacher(),params,impl);
    }

    @Override
    public void getDependentCourses(Course course, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("type",Integer.toString(course.getType()));
        params.add("id",Integer.toString(course.getId()));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getDependentCourses(),params,impl);
    }

    @Override
    public void getVideoTest(CVideo cVideo, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("cvideo_id",Integer.toString(cVideo.getId()));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getVideoTest(),params,impl);
    }

    @Override
    public void applyAnswer(int stu_id, int test_id, String answer,
                            int flag, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        params.add("test_id",test_id);
        params.add("answer",answer);
        params.add("flag",flag);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().applyAnswer(),params,impl);
    }

    @Override
    public void stuChapterCVideo(int stu_id, int cid, int chid, int cvideo_id,
                                 OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu",stu_id);
        params.add("chapter",chid);
        params.add("course",cid);
        params.add("cvideo",cvideo_id);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().stuChapterCVideo(),params,impl);
    }

    @Override
    public void hasScore(int stu_id, int cid, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        params.add("cid",cid);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().hasScore(),params,impl);
    }

    @Override
    public void addHistory(Course course, Chapter chapter, CVideo cVideo, int stu_id){
        history history=new history();
        history.setCourse_id(course.getId());
        history.setCourse(course.getCname());
        history.setChapter_id(chapter.getId());
        history.setChapter(chapter.getChname());
        history.setCvideo_id(cVideo.getId());
        history.setCvideo(cVideo.getVname());
        history.setStu_id(stu_id);
        history.setTime(DateUtil.getCurrentDate());
        history history1 = AppDbHelper.getInstance(context).selectBean(history);
        if (history1!=null){
            AppDbHelper.getInstance(context).updateBean(history);
        }else {
            AppDbHelper.getInstance(context).insertBean(history);
        }
    }
}

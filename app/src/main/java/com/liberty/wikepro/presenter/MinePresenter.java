package com.liberty.wikepro.presenter;

import android.util.Log;

import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.MineContact;
import com.liberty.wikepro.model.MineModel;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/18.
 */

public class MinePresenter extends BasePresenter<MineContact.View> implements MineContact.Presenter {

    @Inject
    MineModel mineModel;

    @Inject
    public MinePresenter(){}

    @Override
    public void attachView(MineContact.View view) {
        mView=view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void editUserCover(final Student student, File head) {
        mineModel.editUserCover(student.getId(), head, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {
                mView.complete();
                mView.showError();
            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                Log.d("xxxxxx","onAnalyseDataSuccess="+result);
                try {
                    JSONObject object=new JSONObject(result);
                    if (!object.isNull("dev")){
                        student.setPage_img(object.getString("dev"));
                    }
                    mView.editUserCoverSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {
                mView.editUserCoverFail();
            }
        });
    }
}

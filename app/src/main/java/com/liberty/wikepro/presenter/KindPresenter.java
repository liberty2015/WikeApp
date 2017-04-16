package com.liberty.wikepro.presenter;

import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.KindContact;
import com.liberty.wikepro.model.KindModel;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.model.bean.direction;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public class KindPresenter extends BasePresenter<KindContact.View> implements KindContact.Presenter {

    @Inject
    KindModel kindModel;

    @Inject
    public KindPresenter(){}

    @Override
    public void getTypeList() {
        kindModel.getTypeList(new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {
                mView.showError();
            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    JSONArray directionJson=null;
                    List<direction> directions=new ArrayList<direction>();
                    if (!object.isNull("direction")){
                        directionJson=object.getJSONArray("direction");
                        for (int i = 0; i < directionJson.length(); i++) {
                            JSONObject jsonObject=directionJson.getJSONObject(i);
                            direction d=new direction();
                            d.setId(jsonObject.isNull("id")?0:jsonObject.getInt("id"));
                            d.setName(jsonObject.isNull("name")?"":jsonObject.getString("name"));
                            directions.add(d);
                        }
                    }
                    List<Type> types=new ArrayList<Type>();
                    if (!object.isNull("type")){
                        JSONArray typesJson=object.getJSONArray("type");
                        for (int i = 0; i < typesJson.length(); i++) {
                            JSONObject jsonObject=typesJson.getJSONObject(i);
                            Type t=new Type();
                            t.setId(jsonObject.isNull("id")?0:jsonObject.getInt("id"));
                            t.setName(jsonObject.isNull("name")?"":jsonObject.getString("name"));
                            t.setDid(jsonObject.isNull("did")?0:jsonObject.getInt("did"));
                            t.setTdev(jsonObject.isNull("tdev")?"":jsonObject.getString("tdev"));
                            types.add(t);
                        }
                    }
                    List<itemType> itemTypes=new ArrayList<itemType>();
                    for (int i = 0; i < directions.size(); i++) {
                        direction d=directions.get(i);
                        itemTypes.add(d);
                        for (int j=0;j<types.size();j++){
                            Type type=types.get(j);
                            if (type.getDid()==d.getId()){
                                itemTypes.add(type);
//                                types.remove(j);
                            }
                        }
                    }
                    mView.showTypeList(itemTypes);
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
    public void attachView(KindContact.View view) {
        mView=view;
    }

    @Override
    public void detachView() {

    }
}

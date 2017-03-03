package com.liberty.wikepro.net;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class WikeApi {

    private static WikeApi instance;

    private boolean hasPort=false;

    private final String base_url="http://10.0.2.2/wike/index.php";

    private final String port=":";

    private final String login_url="";

    private final String registerByPhone_url="/Home/User/registerByPhone";

    private final String registerByEmail_url="/Home/User/registerByEmail";

    public static WikeApi getInstance(){
        if (instance==null){
            instance=new WikeApi();
        }
        return instance;
    }

    public void setHasPort(boolean hasPort) {
        this.hasPort = hasPort;
    }

    public String registerByPhone(){
        return base_url+(hasPort?port:"")+registerByPhone_url;
    }

    public String registerByEmail(){
        return base_url+(hasPort?port:"")+registerByEmail_url;
    }
}

package com.liberty.wikepro.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class TextInputUtil {
    public static boolean isPhone(String phone){
        String rex="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern pattern=Pattern.compile(rex);

        Matcher matcher=pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isEmail(String email){
        String rex="^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$";
        Pattern pattern=Pattern.compile(rex);
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
}

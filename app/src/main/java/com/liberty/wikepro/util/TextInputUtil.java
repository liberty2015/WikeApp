package com.liberty.wikepro.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String md5(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md5=MessageDigest.getInstance("MD5");
        // 计算md5函数
        md5.update(str.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1,md5.digest()).toString(16);
    }
}

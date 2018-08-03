package com.xinyu.beshe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 miaoxinyu on 2018/1/30.
 * @描述 应用版本信息
 */
public class Utils {


    //验证手机号
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }


    //验证密码
    public static boolean isCorrectPassword(String value) {
        if (value.length() >= 6 && value.length()<=16) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
            //  ^[a-zA-Z0-9]{6,10}$   ^[a-zA-Z0-9]{8,15}$
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

}

package com.yyb.yyblib.util;


import android.content.Context;

/**
 * Created by Administrator on 2017/9/1.
 * 获取服务端签名秘钥工具类
 */

public class signUtil {
    //
    public static void O(Context context, String s, int b){//保存后缀
         SharedPreferencesUtil.setString(context,"ABCF1F2F3F4ZXC",s);
    }
    public static void i(Context context, int v , String a){//保存前缀
         SharedPreferencesUtil.setString(context,"jn6rt90ikjhj",a);
    }
    public static void a(Context context, String a1, int b){//私钥
         SharedPreferencesUtil.setString(context,"qwdqasdfsaf",a1);
    }

    public static String O(Context context){//获取后缀
         return SharedPreferencesUtil.getString(context,"ABCF1F2F3F4ZXC","");
    }
    public static String i(Context context){//获取前缀
        return SharedPreferencesUtil.getString(context,"jn6rt90ikjhj","");
    }
    public static String a(Context context){//私钥
        return SharedPreferencesUtil.getString(context,"qwdqasdfsaf","");
    }
}



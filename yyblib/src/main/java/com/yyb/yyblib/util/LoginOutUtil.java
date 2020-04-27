package com.yyb.yyblib.util;

import android.content.Context;
import android.content.Intent;

import com.yyb.yyblib.constant.Constants;

/**
 * Copyright (C)
 * FileName: LoginOutUtil
 * Author: 员外
 * Date: 2019-08-17 13:41
 * Description: TODO<登出清除数据>
 * Version: 1.0
 */
public class LoginOutUtil {

    public void logoutClear(Context context) {
        SharedPreferencesUtil.setString(context, Constants.TOKEN, "");
        //跳转登陆
        Intent it = new Intent();
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("isTokenSkip", true);
        it.setAction("com.android.activity.student.LOGIN_ACTION");
        context.startActivity(it);
    }
}

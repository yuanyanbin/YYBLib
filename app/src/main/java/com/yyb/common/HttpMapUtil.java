package com.yyb.common;

import android.content.Context;
import android.text.TextUtils;


import com.yyb.yyblib.constant.Constants;
import com.yyb.yyblib.util.CommonUtil;
import com.yyb.yyblib.util.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Copyright (C)
 * FileName: HttpMapUtil
 * Author: 员外
 * Date: 2019-08-01 10:32
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class HttpMapUtil {

    public static HashMap<String, String> getMap(Context mContext) {
        HashMap<String, String> netParams = new HashMap<>();
        netParams.put("osType", "2"); //系统类型(1:IOS;2:安卓)
        netParams.put("osVersion", CommonUtil.getSystemVersion());//系统版本
        netParams.put("deviceId", CommonUtil.getDeviceId(mContext));//设备标识
        netParams.put("deviceModel", CommonUtil.getSystemModel()); //设备型号
        netParams.put("appVersion", CommonUtil.getVersionName(mContext)); //应用版本
        String token = SharedPreferencesUtil.getString(mContext, Constants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            netParams.put("token", "visitor"); //游客令牌
        } else {
            netParams.put("token", token); //登录令牌
        }
//        netParams.put("signature", ""); //请求签名
//        netParams.put("appChannel", ""); //应用渠道
//        netParams.put("timestamp", ""); //时间戳
        return netParams;
    }

    public static HashMap<String, Object> getFileHashMap(Context mContext) {
        HashMap<String, Object> netParams = new HashMap<>();
        netParams.put("osType", "2"); //系统类型(1:IOS;2:安卓)
        netParams.put("osVersion", CommonUtil.getSystemVersion());//系统版本
        netParams.put("deviceId", CommonUtil.getDeviceBrand());//设备标识
        netParams.put("deviceModel", CommonUtil.getSystemModel()); //设备型号
        netParams.put("appVersion", CommonUtil.getVersionName(mContext)); //应用版本
        String token = SharedPreferencesUtil.getString(mContext, Constants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            netParams.put("token", "visitor"); //游客令牌
        } else {
            netParams.put("token", token); //登录令牌
        }
        //        netParams.put("signature", ""); //请求签名
//        netParams.put("appChannel", ""); //应用渠道
//        netParams.put("timestamp", ""); //时间戳
        return netParams;
    }
}
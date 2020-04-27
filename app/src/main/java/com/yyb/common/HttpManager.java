package com.yyb.common;

import android.content.Context;

import com.yyb.yyblib.remote.OKHttpRequest;
import com.yyb.yyblib.remote.ReqCallBack;

import java.util.HashMap;

import static com.yyb.yyblib.constant.Constants.TYPE_POST_FORM;

/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2020/4/27 11:47 AM
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class HttpManager {
    public static HttpManager mInstance = null;
    public OKHttpRequest okHttpRequest;
    public Context mContext;

    public HttpManager(Context context) {
        this.mContext = context;
        okHttpRequest = OKHttpRequest.getInstance(context);//new OKHttpRequest();
    }

    public static HttpManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 检查更新
     *
     * @param callBack
     */
    public void getCheckForceUpdate(ReqCallBack<String> callBack) {
        HashMap<String, String> netParams = HttpMapUtil.getMap(mContext);
        okHttpRequest.requestAsyn(HttpConstant.getAppVersion, TYPE_POST_FORM, netParams, callBack);
    }

}

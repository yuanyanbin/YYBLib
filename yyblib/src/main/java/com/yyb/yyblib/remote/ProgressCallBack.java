package com.yyb.yyblib.remote;


import okhttp3.Call;

/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<>
 * Version: 1.0
 */
public abstract class ProgressCallBack<T> implements ReqCallBack<T>{

    public abstract void onProgress(long totalBytes, long remainingBytes, boolean done);
    public abstract void onCall(Call call);
}
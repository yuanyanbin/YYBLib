package com.yyb.yyblib.util;

import com.yyb.yyblib.constant.Constants;

/**
 * Copyright (C)
 * FileName: AppStatusManager
 * Author: 员外
 * Date: 2019-10-25 13:41
 * Description: TODO<AppStatus的管理类，并初始appStatus（APP状态 ）的值为被系统回收>
 * Version: 1.0
 */
public class AppStatusManager {

    public int appStatus = Constants.STATUS_RECYCLE;//APP状态 初始值被系统回收

    public static AppStatusManager appStatusManager;

    private AppStatusManager(){}

    //单例模式
    public static AppStatusManager getInstance() {
        if (appStatusManager == null) {
            appStatusManager = new AppStatusManager();
        }
        return appStatusManager;
    }
    //得到状态
    public int getAppStatus() {
        return appStatus;
    }
    //设置状态
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

}

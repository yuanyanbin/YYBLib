package com.yyb.yyblib.constant;

import okhttp3.MediaType;
/**
 * Copyright (C)
 * FileName: LibConstant
 * Author: 员外
 * Date: 2020/4/27 11:26 AM
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class Constants {
    public static final int STATUS_RECYCLE = -1; //被回收
    public static final int STATUS_NORMAL = 1;    //正常

    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final String TOKEN = "token";//
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");


    //url基地址——测试地址
//    public static final String appBaseUrl = "http://gegeda.vip/studentApp";
    public static final String appBaseUrl = "http://test.gegeda.vip/studentApp";


}

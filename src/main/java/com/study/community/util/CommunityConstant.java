package com.study.community.util;

/**
 *激活状态实例
 */
public interface CommunityConstant {

    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_REPEAT = 1;

    int ACTIVATION_FAILURE = 2;


    /**
     * 默认状态下的登录超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600*12;

    /**
     * 记住状态下的登录超时时间
     */
    int  REMEMBER_EXPIRED_SECONDS = 3600*24*100;
}
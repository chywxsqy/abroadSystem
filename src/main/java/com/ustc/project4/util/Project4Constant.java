package com.ustc.project4.util;

public interface Project4Constant {

    /**
     * code-成功
     */
    int CODE_SUCCESS = 0;

    /**
     * code-失败
     */
    int CODE_FAILURE = 1;

    /**
     * 激活-成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 激活-重复
     */
    int ACTIVATION_REPEATE = 1;

    /**
     * 激活-提供的激活码不正确
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认登录时间-1天
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 24;

    /**
     * “记住我”时的登录时间——30天
     */
    int REMEMBERME_EXPIRED_SECONDS = 3600 * 24 * 30;
}

package com.pers.libs.net

object OKHttpConfig {

    /**
     * URL域名
     */
    @JvmStatic
    var BASE_URL: String = "https://www.wanandroid.com"

    /**
     * 连接超时时间 （s）
     */
    var DEFAULT_TIME_OUT = 30L

    /**
     * 读写超时时间 （s）
     */
    var DEFAULT_WRITE_TIME_OUT = 30L

    /**
     * 读写超时时间 （s）
     */
    var DEFAULT_READ_TIME_OUT = 30L

    /**
     * 是否启用网络缓存
     */
    var CACHE_ENABLE = false

    /**
     * 最大缓存 单位：（M） ，默认 20M
     */
    var MAX_CACHE = 20

    /**
     * 开启后，网络请求失败时会重新请求一次
     */
    var RETRY_ENABLE = true

    /**
     * 网络失败重请求的次数： RETRY_ENABLE = true 时生效，请求次数大于1时，需要通过拦截器实现
     */
    var RETRY_COUNT = 0

    /**
     * 是否启用 Cookie 管理
     */
    var COOKIE_ENABLE = false

    /**
     * 开启后，不校验域名为 HTTPS的证书
     */
    var IGNORE_HTTPS = true

    /**
     * 开启后，屏蔽代理日志，将无法使用Fiddler等抓包工具抓包
     */
    var NO_PROXY = false

    /**
     * Http 固定的请求头参数，不需要修改的通用参数
     */
    var HEADER_PARAMS_MAP: MutableMap<String, String> = HashMap()
        //HTTP 的通用请求头
        set(value) {
            value["Content-Type"] = "application/json;charset=utf-8"
            value["Connection"] = "close"
            field = value
        }


    /**
     * 服务器时间和手机的时间差值
     */
    var SERVER_TIME_DIFFERENCE = 0L


    /**
     * 获取服务器时间
     */
    val serverTime = fun() = System.currentTimeMillis() + SERVER_TIME_DIFFERENCE


}
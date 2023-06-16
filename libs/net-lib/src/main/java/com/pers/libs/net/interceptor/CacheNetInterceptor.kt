package com.pers.libs.net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 网络缓存拦截器，有网络环境下执行  maxStale 缓存时间,单位:秒
 */
class CacheNetInterceptor(private val maxStale: Int) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val builder = response.newBuilder()
        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
        builder.removeHeader("Pragma").removeHeader("Cache-Control")
        val cacheControl = chain.request().cacheControl
        builder.header("Cache-Control", "Public,$cacheControl, max-age=$maxStale")
        //设置允许客户端或服务器中任何一方关闭底层的连接双方都会要求在处理请求后关闭它们的TCP连接。
        builder.addHeader("Connection", "close");
        return builder.build()
    }
}
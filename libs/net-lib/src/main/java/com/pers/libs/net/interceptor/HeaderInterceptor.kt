package com.pers.libs.net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(private val headerParamsMap: MutableMap<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest: Request = chain.request()
        // 新的请求
        val requestBuilder: Request.Builder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method, oldRequest.body)

        //添加公共参数,添加到header中
        if (headerParamsMap.isNotEmpty()) {
            headerParamsMap.map {
                requestBuilder.header(it.key, it.value)
            }
        }
        val newRequest: Request = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}
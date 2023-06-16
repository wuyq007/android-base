package com.pers.libs.net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryInterceptor(private val maxRetry: Int) : Interceptor {

    /**
     * 重试次数
     */
    private var retryNum = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            response = chain.proceed(request)
        }
        return response
    }
}
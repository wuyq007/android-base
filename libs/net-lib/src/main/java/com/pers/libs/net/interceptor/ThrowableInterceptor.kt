package com.pers.libs.net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.net.SocketTimeoutException

class TimeoutIOException(message: String = "请求超时，请检查网络连接") : IOException(message)

/**
 * 网络异常拦截器
 */
class ThrowableInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response?
        try {
            response = chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw TimeoutIOException()
        } catch (e: IOException) {
            throw e
        }
        return response
    }

}
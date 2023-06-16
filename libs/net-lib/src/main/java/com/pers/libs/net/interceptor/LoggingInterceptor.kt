package com.pers.libs.net.interceptor

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.BufferedSource

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC // 设置日志级别
    }
}

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //解密请求体
        decryptRequest(request.body)
        // 发起网络请求
        val response = chain.proceed(request)
        // 解密响应体数据
        decryptResponse(response.body)
        return response

    }

    private fun decryptRequest(body: RequestBody?) {
        if (body == null) {
            return
        }
        val buffer = Buffer()
        body.writeTo(buffer)
        val requestData: ByteArray = buffer.readByteArray()
        val bodyString = String(requestData, Charsets.UTF_8)
        HttpLoggingInterceptor.Logger.DEFAULT.log(bodyString)
    }

    private fun decryptResponse(body: ResponseBody?) {
        if (body == null) {
            return
        }
        val source: BufferedSource = body.source()
        source.request(Long.MAX_VALUE)
        val buffer: Buffer = source.buffer
        val responseData: ByteArray = buffer.clone().readByteArray()
        val bodyString = String(responseData, Charsets.UTF_8)
        HttpLoggingInterceptor.Logger.DEFAULT.log(bodyString)

    }

}
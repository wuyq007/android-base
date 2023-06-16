package com.pers.libs.net.interceptor

import android.os.SystemClock
import com.pers.libs.net.OKHttpConfig
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.System.currentTimeMillis

class TimeInterceptor : Interceptor {

    /**
     * 接口访问服务器的最短耗时
     */
    private var minResponseTime = 0L

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        //接口请求的时间
        val requestTime: Long = SystemClock.elapsedRealtime()
        val response = chain.proceed(request)
        //接口响应的时间
        val responseTime: Long = SystemClock.elapsedRealtime() - requestTime
        val headers: Headers = response.headers
        calibration(responseTime, headers)
        return response
    }

    /**
     * @param responseTime 本次网络请求的耗时时间（ms）
     */
    private fun calibration(responseTime: Long, headers: Headers?) {
        if (headers == null) {
            return
        }
        //如果这一次的请求响应时间小于上一次，则更新本地的时间差
        if (responseTime >= minResponseTime) {
            return
        }
        // 网络请求耗时最短时返回的服务器时间差
        minResponseTime = responseTime

        //从请求头里面获取服务器时间戳，最后3位是000，只精确到10位，也就是说没有毫秒值
        //如果业务接口会固定返回13位时间戳，那么从接口获取服务器时间会更精确
        val serverTime: Long? = headers.getDate("Date")?.time
        if (serverTime != null) {
            if (serverTime.toString().length == 13) {
                //保存时间差：13位时间戳差值
                OKHttpConfig.SERVER_TIME_DIFFERENCE = serverTime - currentTimeMillis()
            }
        }
    }

}
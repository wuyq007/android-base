package com.pers.libs.net.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.pers.libs.base.app.AppConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * 网络缓存拦截器，无网络环境下执行  maxStale 缓存时间,单位:秒
 */
class CacheNoNetInterceptor(private val maxStale: Int) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (!isNetworkConnected()) { //只在无网络时执行
            val tempCacheControl: CacheControl =
                CacheControl.Builder().onlyIfCached().maxStale(maxStale, TimeUnit.SECONDS).build()
            request = request.newBuilder().cacheControl(tempCacheControl).build()
        }
        return chain.proceed(request)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            AppConfig.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}
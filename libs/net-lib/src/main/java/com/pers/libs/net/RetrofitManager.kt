package com.pers.libs.net

import com.pers.libs.base.utils.SDCardUtils
import com.pers.libs.net.interceptor.*
import com.pers.libs.net.ssl.SSLSocketClient
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


class RetrofitManager {

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitManager()
    }

    private var mRetrofit: Retrofit? = null

    private fun init() {
        val httpClient = OkHttpClient.Builder()
        //连接超时时间
        httpClient.connectTimeout(OKHttpConfig.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        //写操作 超时时间
        httpClient.writeTimeout(OKHttpConfig.DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
        //读操作 超时时间
        httpClient.readTimeout(OKHttpConfig.DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)

        //重试
        httpClient.retryOnConnectionFailure(OKHttpConfig.RETRY_ENABLE)
        //网络重试 拦截器
        if (OKHttpConfig.RETRY_ENABLE && OKHttpConfig.RETRY_COUNT > 1) {
            val retryInterceptor = RetryInterceptor(OKHttpConfig.RETRY_COUNT)
            httpClient.addInterceptor(retryInterceptor)
        }

        //请求头拦截器
        if (OKHttpConfig.HEADER_PARAMS_MAP.isNotEmpty()) {
            httpClient.addInterceptor(HeaderInterceptor(OKHttpConfig.HEADER_PARAMS_MAP))
        }

        // 网络缓存拦截
        if (OKHttpConfig.CACHE_ENABLE) {
            //设置缓存路径
            val cacheFile = File(SDCardUtils.SDCardCachePath, "httpCacheData")
            //设置缓存大小 默认20M
            val cache = Cache(cacheFile, (OKHttpConfig.MAX_CACHE * 1024 * 1024).toLong())
            //设置HTTP缓存路径
            httpClient.cache(cache)
            //有网络时的缓存， 20秒内读取缓存
            val cacheInterceptor = CacheNetInterceptor(20)
            httpClient.addNetworkInterceptor(cacheInterceptor)
            //无网络时的缓存，//3 天内的缓存
            val cacheNoNetInterceptor = CacheNoNetInterceptor(3 * 24 * 60 * 60)
            httpClient.addInterceptor(cacheNoNetInterceptor)
        }

        // 自定义：服务器时间拦截器
        httpClient.addInterceptor(TimeInterceptor())
        httpClient.addInterceptor(ThrowableInterceptor())

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getHttpLoggingInterceptor())
            httpClient.addInterceptor(LoggingInterceptor())
        }

        //https证书验证
        if (OKHttpConfig.IGNORE_HTTPS) {
            //校验证书
            val sequenceScope: SSLSocketFactory? = SSLSocketClient.getSSLSocketFactoryAll()
            val x509TrustManager: X509TrustManager? = SSLSocketClient.getTrustManager()
            if (sequenceScope != null && x509TrustManager != null) {
                httpClient.sslSocketFactory(sequenceScope, x509TrustManager)
            }
            //校验域名
            httpClient.hostnameVerifier(SSLSocketClient.getHostnameVerifierAll())
        }

        //屏蔽代理，防止抓包
        if (OKHttpConfig.NO_PROXY) {
            httpClient.proxy(Proxy.NO_PROXY);
        }

        mRetrofit = Retrofit.Builder().client(httpClient.build())
            //返回 String
            .addConverterFactory(ScalarsConverterFactory.create())
            // gson 数据解析器 ，返回 Json 对象
            .addConverterFactory(GsonConverterFactory.create())
            //设置域名
            .baseUrl(OKHttpConfig.BASE_URL).build()

    }


    fun <T> create(service: Class<T>): T {
        if (mRetrofit == null) {
            init()
        }
        return mRetrofit!!.create(service)
    }

}
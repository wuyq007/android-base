package com.pers.libs.net.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class RetrofitCookieManager: CookieJar {

    /**
     * 内存存储
     */
    private val cookieStore: MutableMap<String, List<Cookie>> = HashMap()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //从本地拿取需要的cookie
        val cookies = cookieStore[url.host]
        return cookies ?: ArrayList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        //本地可校验cookie，并根据需要存储
        cookieStore[url.host] = cookies;
    }

    fun clearCookie() {
        cookieStore.clear()
    }

}
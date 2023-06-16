package com.pers.libs.net

import retrofit2.http.*
import java.util.*


interface ApiService {

    @POST("{path}")
    suspend fun <T> post(
        @Path("path") urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>

}
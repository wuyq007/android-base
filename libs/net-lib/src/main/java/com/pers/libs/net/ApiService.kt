package com.pers.libs.net

import kotlinx.coroutines.Deferred
import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET("{path}")
    suspend fun <T> get(
        @Path("path") urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>

    @POST("{path}")
    suspend fun <T> post(
        @Path("path") urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>

}
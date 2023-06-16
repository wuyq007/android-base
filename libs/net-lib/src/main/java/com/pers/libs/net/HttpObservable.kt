package com.pers.libs.net

import com.pers.libs.net.interceptor.TimeoutIOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object HttpObservable {

    internal suspend inline fun <reified T> request(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit = {},
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ): T? = coroutineScope {
        post(url, params, onSuccess, onFail, onTimeout, onError)
    }

    /**
     * 开启一个新的协程，不阻塞外部的协程块
     */
    internal inline fun <reified T> requestAsync(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ) {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        coroutineScope.launch {
            post(url, params, onSuccess, onFail, onTimeout, onError)
            coroutineScope.cancel()
        }
    }

    private suspend inline fun <reified T> post(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit,
        crossinline onError: (Throwable) -> Unit,
    ): T? = coroutineScope {
        var responseData: T? = null;
        val fetchDataFlow: Flow<Result<ResponseBean<T>>> = fetchData(url, params)
        fetchDataFlow.collect { result ->
            if (result.isSuccess) {
                val data: ResponseBean<T>? = result.getOrNull()
                data?.let {
                    if (it.code == 1) {
                        responseData = it.data
                        onSuccess(it.data)
                    } else {
                        onFail(it.code, it.message)
                    }
                }
            } else {
                val error = result.exceptionOrNull()
                if (error != null) {
                    if (error is TimeoutIOException) {
                        onTimeout(error)
                    } else {
                        onError(error)
                    }
                }
            }
        }
        responseData
    }


    private inline fun <reified T> fetchData(
        urlPath: String, params: HashMap<String, Any>
    ): Flow<Result<ResponseBean<T>>> = flow {
        val response: ResponseBean<T> = createPost(urlPath, params)
        if (response.data is List<*>) {
            val listType: Type = object : ParameterizedType {
                override fun getActualTypeArguments(): Array<Type> {
                    return arrayOf(T::class.java)
                }

                override fun getRawType(): Type {
                    return MutableList::class.java
                }

                override fun getOwnerType(): Type? {
                    return null
                }
            }
        } else if (response.data is Map<*, *>) {
            val objectType: Type = object : ParameterizedType {
                override fun getActualTypeArguments(): Array<Type> {
                    return arrayOf()
                }

                override fun getRawType(): Type {
                    return T::class.java
                }

                override fun getOwnerType(): Type? {
                    return null
                }
            }
        }
        emit(Result.success(response))
    }.flowOn(Dispatchers.IO).catch { exception ->
        emit(Result.failure(exception))
    }.flowOn(Dispatchers.IO)


    private suspend fun <T> createPost(
        urlPath: String, params: HashMap<String, Any>
    ): ResponseBean<T> {
        val bodyParams: HashMap<String, Any> = getBaseBodyParam()
        bodyParams.putAll(params)
        return RetrofitManager.getInstance().create(ApiService::class.java)
            .post(urlPath, params, getBaseHeaderParam())
    }


    fun getBaseBodyParam(): HashMap<String, Any> {
        val bodyParams = HashMap<String, Any>()
        //配置body通用参数
        // ...

        return bodyParams
    }


    fun getBaseHeaderParam(): HashMap<String, Any> {
        val headerParams = HashMap<String, Any>()
        //配置Header的通用参数
        // ...

        return headerParams
    }

}
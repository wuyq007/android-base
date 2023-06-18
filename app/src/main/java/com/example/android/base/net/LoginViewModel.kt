package com.example.android.base.net

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.base.net.bean.UserInfo
import com.pers.libs.base.utils.GsonUtils
import com.pers.libs.base.utils.toJson
import com.pers.libs.net.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

object LoginViewModel : ViewModel() {


    fun login(username: String, password: String) {
        viewModelScope.launch {
            val params = HashMap<String, Any>()
            params["username"] = username
            params["password"] = password

            val responseBean: LoginResponse = RetrofitManager.getInstance().create(ApiService::class.java).loginWanAndroid(
                username,password
            ).await()


            Log.e("AAA", "onSuccess userInfo:" + GsonUtils.toJson(responseBean))

//            val userInfo: UserInfo? = HttpObserver.post<UserInfo>("/user/login", params, onSuccess = {
//                Log.e("AAA", "onSuccess userInfo:" + it?.toJson())
//            }, onFail = { errorCode, errorMsg ->
//                Log.e("AAA", "onFail errorCode:$errorCode;errorMsg:$errorMsg")
//            }, onTimeout = {
//                Log.e("AAA", "onTimeout :${it.message}")
//
//            }, onError = {
//                Log.e("AAA", "onError  :${it.message}")
//            })
//
//            userInfo?.let {
//
//            }

        }
    }

}
package com.example.android.base.net.bean

import androidx.lifecycle.LiveData

class UserInfo : LiveData<UserInfo>() {
    var id: Int? = null
    var username: String? = null
    var password: String? = null
    var icon: String? = null
    var type: Int? = null
    var collectIds: List<Int>? = null
}
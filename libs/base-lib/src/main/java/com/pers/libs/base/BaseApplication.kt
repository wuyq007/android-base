package com.pers.libs.base

import androidx.multidex.MultiDexApplication
import com.pers.libs.base.app.AppConfig

open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)


    }
}
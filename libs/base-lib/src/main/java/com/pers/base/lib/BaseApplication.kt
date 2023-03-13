package com.pers.base.lib

import androidx.multidex.MultiDexApplication

open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
    }

}
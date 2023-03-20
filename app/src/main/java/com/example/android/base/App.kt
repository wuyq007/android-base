package com.example.android.base

import com.pers.libs.base.BaseApplication
import com.pers.libs.base.app.addAppLifecycleObserver

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        addAppLifecycleObserver{

        }
    }

}
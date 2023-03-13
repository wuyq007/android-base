package com.pers.base.lib

import android.app.Application
import android.content.Context


class AppConfig {

    companion object {

        private lateinit var application: Application

        fun init(app: Application) {
            application = app
        }

        fun getContext(): Context {
            return application.applicationContext
        }

    }

}
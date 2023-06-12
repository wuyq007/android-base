package com.pers.libs.base.app

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

interface AppLifecycleObserver {
    /**
     * App 进入前台
     */
    fun onAppStarted(){}

    /**
     * App 进入后台
     */
    fun onAppStopped(){}
}

fun Application.addAppLifecycleObserver(block: (Application) -> AppLifecycleObserver): AppLifecycleObserver {
    val observer: AppLifecycleObserver = block(this)
    AppLifecycleCallbacks.addAppLifecycleListener(observer)
    this.registerActivityLifecycleCallbacks(AppLifecycleCallbacks)
    return observer
}

fun removeAppLifecycleObserver(observer: AppLifecycleObserver) {
    AppLifecycleCallbacks.removeAppLifecycleListener(observer)
}

object AppLifecycleCallbacks : ActivityLifecycleCallbacks {

    /**
     * Activity started count
     */
    private var mActivityCount = 0

    /**
     * 是否处于后台
     */
    private var isRunInBackground = false


    private val mAppObserver: MutableList<AppLifecycleObserver> by lazy {
        ArrayList()
    }


    fun addAppLifecycleListener(listener: AppLifecycleObserver): AppLifecycleObserver {
        if (!mAppObserver.contains(listener)) {
            mAppObserver.add(listener)
        }
        return listener
    }

    fun removeAppLifecycleListener(listener: AppLifecycleObserver) {
        mAppObserver.removeIf {
            it == listener
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        mActivityCount++
        if (isRunInBackground) {
            //应用从后台回到前台 需要做的操作
            isRunInBackground = false
            for (listener in mAppObserver) {
                listener.onAppStarted()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        mActivityCount--
        if (mActivityCount == 0) {
            //应用进入后台 需要做的操作
            isRunInBackground = true
            for (listener in mAppObserver) {
                listener.onAppStopped()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}
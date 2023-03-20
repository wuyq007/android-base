package com.pers.libs.base.app

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import kotlinx.coroutines.flow.Flow
import java.sql.Types.NULL

interface AppLifecycleObserver {
    /**
     * App 进入前台
     */
    fun onAppStarted(start: Boolean)

//    /**
//     * App 进入后台
//     */
//    fun onAppStopped()
}

//public fun Application.addAppLifecycleObserver(observer: AppLifecycleObserver): AppLifecycleObserver {
//    AppLifecycleCallbacks.addAppLifecycleListener(observer)
//    registerActivityLifecycleCallbacks(AppLifecycleCallbacks)
//    return observer
//}

//public suspend fun <T> Flow<T>.first(predicate: suspend (T) -> Boolean): T {
//    var result: Any? = NULL
//    collectWhile {
//        if (predicate(it)) {
//            result = it
//            false
//        } else {
//            true
//        }
//    }
//    if (result === NULL) throw NoSuchElementException("Expected at least one element matching the predicate $predicate")
//    return result as T
//}
//public suspend fun <S, T : S> Flow<T>.reduce(operation: suspend (accumulator: S, value: T) -> S): S {
//public fun Application.addAppLifecycleObserver(operation: (test: AppLifecycleObserver) -> AppLifecycleObserver): AppLifecycleObserver {
//    var accumulator: AppLifecycleObserver?  = accumulator
//    AppLifecycleCallbacks.addAppLifecycleListener(accumulator)
//    registerActivityLifecycleCallbacks(AppLifecycleCallbacks)
//    return observer
//}

public fun removeAppLifecycleListener(observer: AppLifecycleObserver) {
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
        ArrayList<AppLifecycleObserver>()
    }


    fun addAppLifecycleListener(listener: AppLifecycleObserver): AppLifecycleObserver {
        if (mAppObserver.contains(listener)) {
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
                listener.onAppStarted(true)
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
                listener.onAppStarted(false)
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}
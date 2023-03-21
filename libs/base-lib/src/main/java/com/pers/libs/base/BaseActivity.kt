package com.pers.libs.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pers.libs.base.app.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var mainJob: Job
    override val coroutineContext: CoroutineContext
        get() = mainJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建 Job （用于管理CoroutineScope中的所有携程）
        mainJob = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 当 Activity 销毁的时候取消该 Scope 管理的 job。 这样该 Scope 内创建的子 Coroutine 都会被自动的取消。
        mainJob.cancel()
        appLifecycleObserver?.let {
            removeAppLifecycleObserver(it)
        }
    }

    private var appLifecycleObserver: AppLifecycleObserver? = null

    /**
     * 监听程序是否退出到后台
     */
    fun enabledAppLifecycleObserver() {
        appLifecycleObserver = AppConfig.application.addAppLifecycleObserver {
            object : AppLifecycleObserver {
                override fun onAppStarted() {
                    onAppStart()
                }

                override fun onAppStopped() {
                    onAppStop()
                }
            }
        }
    }

    open fun onAppStart() {
        Log.e("AAA", "程序回到前台")
    }

    open fun onAppStop() {
        Log.e("AAA", "程序最小化到后台")
    }

}
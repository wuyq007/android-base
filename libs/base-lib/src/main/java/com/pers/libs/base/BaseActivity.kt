package com.pers.libs.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pers.libs.base.app.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        appLifecycleObserver?.let {
            removeAppLifecycleObserver(it)
        }
        super.onDestroy()
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
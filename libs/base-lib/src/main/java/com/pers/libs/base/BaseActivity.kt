package com.pers.libs.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.android.base.base.lib.databinding.ActivityBaseBinding
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.AppLifecycleObserver
import com.pers.libs.base.app.addAppLifecycleObserver
import com.pers.libs.base.app.removeAppLifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: AppCompatActivity

    private lateinit var baseBinding: ActivityBaseBinding

    private lateinit var mainJob: Job
    override val coroutineContext: CoroutineContext
        get() = mainJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = AppConfig.application.applicationContext
        mActivity = this
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(baseBinding.root)
        // 创建 Job （用于管理CoroutineScope中的所有携程）
        mainJob = Job()

        initViews()
    }

    private fun initViews() {
        baseBinding.tvBaseTitleLeft.setOnClickListener { finish() }
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

    /**
     * 隐藏标题栏，如果标题栏默认显示的话
     */
    open fun hideTitleBar() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    /**
     * 沉浸式预留状态栏高度
     */
    open fun setFitsSystemWindows(
        view: View = findViewById<ViewGroup>(android.R.id.content).getChildAt(
            0
        )
    ) {
        view.fitsSystemWindows = true
    }


    protected open fun getTitleText(): String? {
        return baseBinding.tvBaseTitle.text.toString()
    }

    /**
     * 设置标题名称
     */
    protected open fun setTitleText(titleStr: String?) {
        baseBinding.tvBaseTitle.text = titleStr
    }

    /**
     * 隐藏默认标题栏下的分割线
     */
    protected open fun hindTitleDivider(isHind: Boolean = false) {
        if (isHind) {
            baseBinding.baseTitleLayout.cardElevation = 0f
        } else {
            baseBinding.baseTitleLayout.cardElevation = 0f
        }
    }

    /**
     * 设置内容区域，如果不需要 BaseActivity 的默认布局，直接设置 setContentView 即可
     */
    protected open fun setContentLayout(@LayoutRes layoutResId: Int) {
        LayoutInflater.from(this).inflate(layoutResId, baseBinding.baseContentLayout, true)
    }

}
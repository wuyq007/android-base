package com.pers.libs.base.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import com.pers.libs.base.utils.DataStoreUtils
import com.pers.libs.base.utils.CodedUtils
import kotlinx.coroutines.*
import java.util.*

object AppConfig {

    private lateinit var app: Application

    fun init(app: Application) {
        AppConfig.app = app
    }

    /**
     * 全局 Context
     */
    val application: Application by lazy { app }

    /**
     * 获取应用包名
     */
    val packageName: String by lazy { app.packageName }

    /**
     * 获取应用版本名称
     */
    val versionName: String by lazy { Holder.APP_VERSION_NAME }

    /**
     * 获取应用版本Code
     */
    val versionCode: Long by lazy { Holder.APP_VERSION_CODE }

    /**
     * 获取状态栏高度
     */
    val statusBarHeight: Int by lazy { Holder.APP_STATUS_BAR_HEIGHT }

    /**
     * 获取屏幕宽度
     */
    val screenWidth: Int by lazy { Holder.APP_SCREEN_WIDTH }

    /**
     * 获取屏幕高度
     */
    val screenHeight: Int by lazy { Holder.APP_SCREEN_HEIGHT }

    /**
     * 判断当前是否是debug模式
     */
    val isDebug: Boolean by lazy { Holder.APP_IS_DEBUG }

    /**
     * 设备ID
     */
    val deviceId: String by lazy { Holder.ANDROID_DEVICE_UNIQUE_ID }


    @SuppressLint("InternalInsetResource")
    private object Holder {
        private val packageInfo: PackageInfo? by lazy {
            try {
                val pm: PackageManager = app.packageManager
                pm.getPackageInfo(packageName, 0)
            } catch (e: Exception) {
                null
            }
        }
        val APP_VERSION_NAME: String = packageInfo?.versionName ?: "1.0.0"
        val APP_VERSION_CODE: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo?.longVersionCode ?: 1L
        } else {
            (packageInfo?.versionCode)?.toLong() ?: 1L
        }

        val APP_STATUS_BAR_HEIGHT: Int by lazy {
            try {
                val resources: Resources = app.resources
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                resources.getDimensionPixelSize(resourceId)
            } catch (e: Exception) {
                0
            }
        }

        private val displayMetrics: DisplayMetrics? by lazy {
            try {
                val resources: Resources = app.resources
                resources.displayMetrics
            } catch (e: Exception) {
                null
            }
        }
        val APP_SCREEN_WIDTH: Int = displayMetrics?.widthPixels ?: 0
        val APP_SCREEN_HEIGHT: Int = displayMetrics?.heightPixels ?: 0

        val APP_IS_DEBUG: Boolean by lazy {
            val ai: ApplicationInfo = app.applicationInfo
            ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }

        val ANDROID_DEVICE_UNIQUE_ID: String by lazy {
            //协程阻塞执行，同步返回
            runBlocking {
                var uniqueID: String? = DataStoreUtils.getString("ANDROID_DEVICE_UNIQUE_ID")
                if (uniqueID == null || uniqueID == "") {
                    //添加一个标识
                    val identification = "+10086"
                    val systemStr =
                        identification + AppSystemInfo.androidID + AppSystemInfo.systemModel + AppSystemInfo.board + AppSystemInfo.hardware + AppSystemInfo.deviceBrand + AppSystemInfo.deviceManufacturer + AppSystemInfo.fingerprint + AppSystemInfo.buildId + AppSystemInfo.serial
                    val uuid = UUID(
                        systemStr.hashCode().toLong(),
                        AppSystemInfo.serial.hashCode().toLong(),
                    )
                    uniqueID = CodedUtils.md5(systemStr, uuid.toString())
                    DataStoreUtils.saveString("ANDROID_DEVICE_UNIQUE_ID", uniqueID)
                }
                uniqueID
            }
        }
    }

}
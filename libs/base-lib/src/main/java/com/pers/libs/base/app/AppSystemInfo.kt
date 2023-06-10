package com.pers.libs.base.app

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import java.util.*


@SuppressLint("HardwareIds")
object AppSystemInfo {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    @JvmStatic
    val systemLanguage: String by lazy { Locale.getDefault().language ?: "" }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     */
    @JvmStatic
    val systemLanguageList: Array<Locale?>? by lazy { Locale.getAvailableLocales() ?: null }

    /**
     * 获取当前手机系统版本号
     */
    @JvmStatic
    val systemVersion: String by lazy { Build.VERSION.RELEASE ?: "" }

    /**
     * 获取手机型号
     */
    @JvmStatic
    val systemModel: String by lazy { Build.MODEL ?: "" }


    /**
     * 获取主板型号
     */
    @JvmStatic
    val board: String by lazy { Build.BOARD ?: "" }


    /**
     * 获取硬件CPU型号
     */
    @JvmStatic
    val hardware: String by lazy { Build.HARDWARE ?: "" }


    /**
     * 获取手机厂商
     */
    @JvmStatic
    val deviceBrand: String by lazy { Build.BRAND ?: "" }


    /**
     * 获取手机制造商
     */
    @JvmStatic
    val deviceManufacturer: String by lazy { Build.MANUFACTURER ?: "" }


    /**
     * 指纹ID
     */
    @JvmStatic
    val fingerprint: String by lazy { Build.FINGERPRINT ?: "" }

    /**
     * 系列号
     */
    @JvmStatic
    val serial: String by lazy { Build.SERIAL ?: "" }

    /**
     * 设备版本号
     */
    @JvmStatic
    val buildId: String by lazy { Build.ID ?: "" }


    /**
     * AndroidID
     */
    @JvmStatic
    val androidID: String by lazy {
        Settings.Secure.getString(
            AppConfig.application.contentResolver, Settings.Secure.ANDROID_ID
        ) ?: ""
    }

}
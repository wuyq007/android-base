package com.pers.base.lib

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import java.util.*


@SuppressLint("HardwareIds")
object SystemInfo {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    val systemLanguage: String by lazy { Locale.getDefault().language ?: "" }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     */
    val systemLanguageList: Array<Locale?>? by lazy { Locale.getAvailableLocales() ?: null }

    /**
     * 获取当前手机系统版本号
     */
    val systemVersion: String by lazy { Build.VERSION.RELEASE ?: "" }

    /**
     * 获取手机型号
     *
     */
    val systemModel: String by lazy { Build.MODEL ?: "" }


    /**
     * 获取主板型号
     */
    val board: String by lazy { Build.BOARD ?: "" }


    /**
     * 获取硬件CPU型号
     */
    val hardware: String by lazy { Build.HARDWARE ?: "" }


    /**
     * 获取手机厂商
     *
     */
    val deviceBrand: String by lazy { Build.BRAND ?: "" }


    /**
     * 获取手机制造商
     *
     */
    val deviceManufacturer: String by lazy { Build.MANUFACTURER ?: "" }


    /**
     * 指纹ID
     */
    val fingerprint: String by lazy { Build.FINGERPRINT ?: "" }

    /**
     * 系列号
     */
    val serial: String by lazy { Build.SERIAL ?: "" }

    /**
     * 设备版本号
     */
    val buildId: String by lazy { Build.ID ?: "" }


    /**
     * AndroidID
     */
    val androidID: String by lazy {
        Settings.Secure.getString(
            AppConfig.application.contentResolver, Settings.Secure.ANDROID_ID
        ) ?: ""
    }

}
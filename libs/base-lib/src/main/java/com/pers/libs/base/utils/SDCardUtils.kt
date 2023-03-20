package com.pers.libs.base.utils

import android.content.Context
import android.os.Environment
import com.pers.libs.base.app.AppConfig
import java.io.File

object SDCardUtils {

    private val context: Context by lazy {
        AppConfig.application
    }

    /**
     * 获取 APP 的 files 路径
     *
     * @return /storage/emulated/0/Android/data/packageName/files
     */
    fun getSDCardFilesPath(): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            context.getExternalFilesDir(null)?.path
        } else {
            //外部存储不可用
            context.filesDir?.path
        }
    }


    /**
     * 获取 APP 的 cache 路径
     *
     * @return /storage/emulated/0/Android/data/packageName/cache
     */
    fun getSDCardCachePath(): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            // /storage/emulated/0/Android/data/packageName/cache   不会在空间少时被自动清除
            context.externalCacheDir?.path
        } else {
            // /data/data/<应用包名>/cache   用来存储临时数据。因此在系统空间较少时有可能会被自动清除。
            context.cacheDir?.path
        }
    }


    /**
     * 文件下载目录
     *
     * @return /storage/emulated/0/Android/data/packageName/cache/download/
     *
     * @return
     */
    fun getDownloadCreatePath(): String? {
        val downloadFile = File(getSDCardCachePath(), "download/")
        if (!downloadFile.exists()) {
            val isMkdirs = downloadFile.mkdirs() //创建文件夹
            if (isMkdirs) {
                return downloadFile.toString()
            }
        } else {
            return downloadFile.toString()
        }
        return ""
    }


}
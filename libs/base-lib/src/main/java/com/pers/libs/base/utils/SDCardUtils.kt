package com.pers.libs.base.utils

import android.app.Application
import android.content.Context
import android.os.Environment
import com.pers.libs.base.app.AppConfig
import java.io.File

object SDCardUtils {


    val SDCardFilesPath: String? by lazy { Holder.getSDCardFilesPath() }
    val SDCardCachePath: String? by lazy { Holder.getSDCardCachePath() }
    val SDCardDownloadFilesPath: String? by lazy { Holder.getDownloadFilesPath() }
    val SDCardDownloadCachePath: String? by lazy { Holder.getDownloadCreatePath() }

    private object Holder {
        /**
         * 获取 APP 的 files 路径
         *
         * @return /storage/emulated/0/Android/data/packageName/files
         */
        fun getSDCardFilesPath(): String? {
            return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                //外部存储可用
                AppConfig.application.getExternalFilesDir(null)?.path
            } else {
                //外部存储不可用
                AppConfig.application.filesDir?.path
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
                AppConfig.application.externalCacheDir?.path
            } else {
                // /data/data/<应用包名>/cache   用来存储临时数据。因此在系统空间较少时有可能会被自动清除。
                AppConfig.application.cacheDir?.path
            }
        }


        /**
         * 文件下载目录，需要长期保存的文件
         */
        fun getDownloadFilesPath(): String? {
            val downloadFile = File(SDCardFilesPath, "download/")
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

        /**
         * 文件下载缓存目录,临时保存的文件
         *
         * @return /storage/emulated/0/Android/data/packageName/cache/download/
         *
         * @return
         */
        fun getDownloadCreatePath(): String? {
            val downloadFile = File(SDCardCachePath, "download/")
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


}
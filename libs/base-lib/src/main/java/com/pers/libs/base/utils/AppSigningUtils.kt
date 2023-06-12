package com.pers.libs.base.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import com.pers.libs.base.app.AppConfig
import java.security.MessageDigest
import java.util.*


object AppSigningUtils {

    private const val MD5 = "MD5"
    private const val SHA1 = "SHA1"
    private const val SHA256 = "SHA256"
    private val SIGN_MAP = HashMap<String, MutableList<String>>()

    /**
     * 获取签名MD5值
     */
    fun getMD5(): String {
        var res = ""
        val list = getSignInfo(MD5)
        if (list != null && list.size > 0) {
            res = list[0]
        }
        return res
    }

    /**
     * 获取签名SHA1值
     */
    fun getSHA1(): String {
        var res = ""
        val list = getSignInfo(SHA1)
        if (list != null && list.size > 0) {
            res = list[0]
        }
        return res
    }

    /**
     * 获取签名SHA256值
     */
    fun getSHA256(context: Context?): String {
        var res = ""
        val list = getSignInfo(SHA256)
        if (list != null && list.size > 0) {
            res = list[0]
        }
        return res
    }


    /**
     * 返回一个签名的对应类型的字符串
     *
     * @return 因为一个安装包可以被多个签名文件签名，所以返回一个签名信息的list
     */
    private fun getSignInfo(type: String): MutableList<String>? {
        val packageName = AppConfig.application.packageName ?: return null
        if (SIGN_MAP[type] != null) {
            return SIGN_MAP[type]
        }
        val list = ArrayList<String>()
        try {
            val signs = getSignatures(packageName)
            for (sig in signs) {
                var tmp = "error!"
                if (MD5 == type) {
                    tmp = getSignatureByteString(sig, MD5)
                } else if (SHA1 == type) {
                    tmp = getSignatureByteString(sig, SHA1)
                } else if (SHA256 == type) {
                    tmp = getSignatureByteString(sig, SHA256)
                }
                list.add(tmp)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        SIGN_MAP[type] = list
        return list
    }

    /**
     * 返回对应包的签名信息
     */
    private fun getSignatures(packageName: String): Array<Signature> {
        val packageManager: PackageManager = AppConfig.application.packageManager
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val signingInfo = packageInfo.signingInfo
            signingInfo.apkContentsSigners
        } else {
            packageInfo.signatures
        }
    }


    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成 95:F4:D4:FG 这样的字符串形式）
     */
    private fun getSignatureByteString(sig: Signature, type: String): String {
        val hexBytes = sig.toByteArray()
        var fingerprint = "error!"
        try {
            val digest = MessageDigest.getInstance(type)
            val digestBytes = digest.digest(hexBytes)
            val sb = StringBuilder()
            for (digestByte in digestBytes) {
                sb.append(Integer.toHexString(digestByte.toInt() and 0xFF or 0x100).substring(1, 3).uppercase(Locale.getDefault()))
                sb.append(":")
            }
            fingerprint = sb.substring(0, sb.length - 1).toString()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return fingerprint
    }

}
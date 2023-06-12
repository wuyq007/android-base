package com.pers.libs.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.Html
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

object CodedUtils {

    fun md5LowerCase(str: String): String {
        return md5(str).lowercase(Locale.getDefault())
    }

    fun md5LowerCase(input: ByteArray): String {
        return md5(input).lowercase(Locale.getDefault())
    }

    fun md5UpperCase(str: String): String {
        return md5(str).uppercase(Locale.getDefault())
    }

    fun md5UpperCase(input: ByteArray): String {
        return md5(input).uppercase(Locale.getDefault())
    }

    fun md5(input: ByteArray, salt: String = ""): String {
        return md5(input.toString(Charset.defaultCharset()), salt)
    }

    fun md5(content: String, salt: String = ""): String {
        val hash = MessageDigest.getInstance("MD5").digest((content + salt).toByteArray())
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            var str = Integer.toHexString(b.toInt())
            if (b < 0x10) {
                str = "0$str"
            }
            hex.append(str.substring(str.length - 2))
        }
        return hex.toString()
    }

    /**
     * 获取 JKS 的 MD5
     */
    fun getSignatureCharsStringMD5(context: Context): String? {
        try {
            @SuppressLint("PackageManagerGetSignatures") val packageInfo =
                context.packageManager.getPackageInfo(
                    context.packageName, PackageManager.GET_CONFIGURATIONS
                )
//            val signs =

            val signString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo.toString()
            } else {
                packageInfo.signatures[0].toCharsString()
            }
            return md5LowerCase(signString)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }


    /**
     * URL编码
     */
    fun urlEncode(input: String?): String? {
        return urlEncode(input, "UTF-8")
    }

    fun urlEncode(input: String?, charsetName: String?): String? {
        return try {
            URLEncoder.encode(input, charsetName)
        } catch (e: UnsupportedEncodingException) {
            throw java.lang.AssertionError(e)
        }
    }

    fun urlDecode(input: String?): String? {
        return urlDecode(input, "UTF-8")
    }

    fun urlDecode(input: String?, charsetName: String?): String? {
        return try {
            URLDecoder.decode(input, charsetName)
        } catch (e: UnsupportedEncodingException) {
            throw AssertionError(e)
        }
    }


    /**
     * HTML编码
     */
    fun htmlEncode(input: CharSequence): String? {
        val sb = java.lang.StringBuilder()
        var c: Char
        var i = 0
        val len = input.length
        while (i < len) {
            c = input[i]
            when (c) {
                '<' -> sb.append("&lt;") //$NON-NLS-1$
                '>' -> sb.append("&gt;") //$NON-NLS-1$
                '&' -> sb.append("&amp;") //$NON-NLS-1$
                '\'' -> sb.append("&#39;") //$NON-NLS-1$
                '"' -> sb.append("&quot;") //$NON-NLS-1$
                else -> sb.append(c)
            }
            i++
        }
        return sb.toString()
    }

    fun htmlDecode(input: String): CharSequence? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(input)
        }
    }


}
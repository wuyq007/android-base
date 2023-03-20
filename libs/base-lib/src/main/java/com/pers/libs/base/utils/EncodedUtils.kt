package com.pers.libs.base.utils

import java.security.MessageDigest

object EncodedUtils {

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

}
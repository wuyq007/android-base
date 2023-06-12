package com.pers.libs.base.utils

import java.security.SecureRandom

object KeyUtils {

    // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private const val SHA1PRNG = "SHA1PRNG"
    private const val HEX = "0123456789ABCDEF"


    /**
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    fun generateKey(): String? {
        try {
            val localSecureRandom = SecureRandom.getInstance(SHA1PRNG)
            val bytesKey = ByteArray(20)
            localSecureRandom.nextBytes(bytesKey)
            return toHex(bytesKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    //二进制转字符
    private fun toHex(buf: ByteArray?): String? {
        if (buf == null) {
            return ""
        }
        val result = StringBuffer(2 * buf.size)
        for (i in buf.indices) {
            appendHex(result, buf[i])
        }
        return result.toString()
    }

    private fun appendHex(sb: StringBuffer, b: Byte) {
        sb.append(HEX[b.toInt() shr 4 and 0x0f]).append(HEX[b.toInt() and 0x0f])
    }

}
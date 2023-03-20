package com.pers.libs.base.utils

import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    private val random: SecureRandom by lazy {
        SecureRandom()
    }

    /**
     * 生成日期+时间字符串，去除符号
     */
    fun getStrDate(): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val ret =
            sdf.format(Date()).replace("-", "").replace(" ", "").replace(":", "").trim { it <= ' ' }
        return ret.trim { it <= ' ' }
    }

    /**
     * 生成随机数
     *
     * @param length 随机数长度
     * @return 随机数
     */
    fun getStringRandom(length: Int): String? {
        val stringBuilder = StringBuilder()
        //参数length，表示生成几位随机数
        for (i in 0 until length) {
            val charOrNum = if (random.nextInt(2) % 2 == 0) "char" else "num"
            //输出字母还是数字
            if ("char".equals(charOrNum, ignoreCase = true)) {
                //输出是大写字母还是小写字母
                val temp = if (random.nextInt(2) % 2 == 0) 65 else 97
                stringBuilder.append((random.nextInt(26) + temp).toChar())
            } else {
                stringBuilder.append(random.nextInt(10))
            }
        }
        return stringBuilder.toString()
    }
}
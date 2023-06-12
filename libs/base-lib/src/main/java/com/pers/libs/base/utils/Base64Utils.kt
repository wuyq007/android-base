package com.pers.libs.base.utils

import android.os.Build
import android.util.Base64
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import kotlin.text.Charsets.UTF_8

object Base64Utils {

    fun encodeAny(objectInfo: Any): String {
        val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        val json = gson.toJson(objectInfo)
        return encodeString(json)
    }

    fun <T> decodeAny(string: String, cls: Class<T>): T {
        val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        return gson.fromJson<T>(
            decode(string).toString(), cls
        )
    }


    fun encodeString(data: String): String {
        return encode(data.toByteArray(UTF_8))
    }

    fun decodeString(base64String: String): String {
        return decode(base64String).toString(UTF_8)
    }

    fun encode(data: ByteArray): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.util.Base64.getEncoder().encodeToString(data)
        } else {
            Base64.encodeToString(data, FLAGS)
        }
    }

    fun decode(base64String: String): ByteArray {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.util.Base64.getDecoder().decode(base64String)
        } else {
            Base64.decode(base64String, FLAGS)
        }
    }


    /**
     * 编码还是解码都会有一个参数Flags，Android提供了以下几种
     *
     *
     * DEFAULT 这个参数是默认，使用默认的方法来加密
     *
     *
     * NO_PADDING 这个参数是略去加密字符串最后的”=”
     *
     *
     * NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
     *
     *
     * CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
     *
     *
     * URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
     */
    private const val FLAGS = Base64.NO_WRAP

    /**
     * 对文件进行Base64编码
     *
     * @param file
     * @return
     */
    @Throws(IOException::class)
    fun base64FileEncode(file: File): String {
        var inputFile: FileInputStream? = null
        inputFile = FileInputStream(file)
        val buffer = ByteArray(file.length().toInt())
        inputFile.read(buffer)
        inputFile.close()
        return encode(buffer)
    }

    /**
     * 对文件内容进行Base64解码
     *
     * @param file          文件路径
     * @param encodedString 需要解码的内容
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun base64FileDecode(file: File, encodedString: String) {
        var outputFile: FileOutputStream? = null
        val decodeBytes: ByteArray = decode(encodedString)
        outputFile = FileOutputStream(file)
        outputFile.write(decodeBytes)
        outputFile.close()
    }


}
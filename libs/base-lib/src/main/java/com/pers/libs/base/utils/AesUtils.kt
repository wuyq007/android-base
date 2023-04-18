package com.pers.libs.base.utils

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesUtils {

    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private const val AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding"
    private const val AES = "AES" //AES 加密

    private const val AES_KEY_SIZE = 128
    private const val AES_BLOCK_SIZE = 16


    /**
     * AES加密
     */
    fun encrypt(input: String, password: String): String {
        val result = encrypt(input.toByteArray(), password.toByteArray())
        return String(result)
    }

    fun encrypt(data: ByteArray, key: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key, AES)
        val cipher = Cipher.getInstance(AES_CBC_PKCS5)
        val iv = ByteArray(AES_BLOCK_SIZE)
        val random = SecureRandom()
        random.nextBytes(iv)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }


    //AES解密
    fun decrypt(input: String, password: String): String {
        val result = decrypt(input.toByteArray(), password.toByteArray())
        return String(result)
    }

    fun decrypt(data: ByteArray, key: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key, AES)
        val cipher = Cipher.getInstance(AES_CBC_PKCS5)
        val iv = ByteArray(AES_BLOCK_SIZE)
        System.arraycopy(data, 0, iv, 0, iv.size)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        val encryptedData = ByteArray(data.size - iv.size)
        System.arraycopy(data, iv.size, encryptedData, 0, encryptedData.size)
        return cipher.doFinal(encryptedData)
    }


    fun generateKey(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(AES_KEY_SIZE)
        val secretKey = keyGenerator.generateKey()
        return secretKey.encoded
    }

}
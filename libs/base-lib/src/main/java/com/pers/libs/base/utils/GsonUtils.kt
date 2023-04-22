package com.pers.libs.base.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object GsonUtils {

    private val GSON: Gson by lazy { GsonBuilder().create() }

    @Throws(JsonSyntaxException::class)
    fun toJson(json: Any?): String? {
        return GSON.toJson(json)
    }

    /**
     * Json 转对象 T
     */
    @Throws(Exception::class)
    fun <T> fromJson(jsonElement: String?, clazz: Type?): T {
        return GSON.fromJson(jsonElement, clazz)
    }

    @Throws(Exception::class)
    fun <T> fromJson(jsonElement: JsonElement?, tClass: Class<T>?): T {
        return GSON.fromJson(jsonElement, tClass)
    }

    @Throws(Exception::class)
    fun <T> fromJson(jsonElement: String?, tClass: Class<T>?): T {
        return GSON.fromJson(jsonElement, tClass)
    }

    /**
     * Json 转 List<T>
     */
    @Throws(Exception::class)
    fun <T> fromJsonList(jsonElement: JsonElement?, tClass: Class<T>): MutableList<T>? {
        val objectType: Type = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(tClass)
            }

            override fun getRawType(): Type {
                return MutableList::class.java
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
        return GSON.fromJson(jsonElement, objectType)
    }


    /**
     * List<T> 转 List<Object>
     */
    @Throws(Exception::class)
    fun <T> fromJsonList(listT: List<T>?, tClass: Class<T>): List<T>? {
        val objectType: Type = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(tClass)
            }

            override fun getRawType(): Type {
                return MutableList::class.java
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
        return GSON.fromJson(GSON.toJson(listT), objectType)
    }

    /**
     * json 转对象 K，K 对象中有 List<T>
     *
     * @param kClass      父对象
     * @param tClass      子对象
     * @param jsonElement json 串
    </T> */
    @Throws(Exception::class)
    fun <K, T> getJsonResult(kClass: Class<K>, tClass: Class<T>, jsonElement: JsonElement?): K {
        val objectType: Type = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(tClass)
            }

            override fun getRawType(): Type {
                return kClass
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
        return GSON.fromJson(jsonElement, objectType)
    }

}
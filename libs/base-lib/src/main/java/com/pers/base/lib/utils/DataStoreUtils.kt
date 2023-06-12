package com.pers.base.lib.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.pers.base.lib.AppConfig
import kotlinx.coroutines.flow.*
import java.io.IOException
import kotlin.properties.Delegates

// 文件名称: /data/data/项目包名/files/下创建名为 dataStore_data 的文件
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store_data")

object DataStoreUtils {

    private val context: () -> Context = { AppConfig.getContext() }
    private val dataStore = context().dataStore

    suspend fun saveInt(key: String, value: Int) {
        dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    suspend fun getInt(key: String): Int? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[intPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveFloat(key: String, value: Float) {
        dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }
    }

    suspend fun getFloat(key: String): Float? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[floatPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveDouble(key: String, value: Double) {
        dataStore.edit {
            it[doublePreferencesKey(key)] = value
        }
    }

    suspend fun getDouble(key: String): Double? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[doublePreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveLong(key: String, value: Long) {
        dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    suspend fun getLong(key: String): Long? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[longPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[booleanPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveString(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[stringPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveStringSet(key: String, value: Set<String>) {
        dataStore.edit {
            it[stringSetPreferencesKey(key)] = value
        }
    }

    suspend fun getStringSet(key: String): Set<String>? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[stringSetPreferencesKey(key)]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun removeInt(key: String) {
        dataStore.edit {
            it.remove(intPreferencesKey(key))
        }
    }

    suspend fun removeFloat(key: String) {
        dataStore.edit {
            it.remove(floatPreferencesKey(key))
        }
    }

    suspend fun removeDouble(key: String) {
        dataStore.edit {
            it.remove(doublePreferencesKey(key))
        }
    }

    suspend fun removeLong(key: String) {
        dataStore.edit {
            it.remove(longPreferencesKey(key))
        }
    }

    suspend fun removeBoolean(key: String) {
        dataStore.edit {
            it.remove(booleanPreferencesKey(key))
        }
    }

    suspend fun removeString(key: String) {
        dataStore.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

    suspend fun removeStringSet(key: String) {
        dataStore.edit {
            it.remove(stringSetPreferencesKey(key))
        }
    }

    suspend fun containsInt(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(intPreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsFloat(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(floatPreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsDouble(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(doublePreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsLong(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(longPreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsBoolean(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(booleanPreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsString(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(stringPreferencesKey(key))
        }
        return isContains
    }

    suspend fun containsStringSet(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(stringSetPreferencesKey(key))
        }
        return isContains
    }

    suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> get(key: Preferences.Key<T>): T? {
        return try {
            dataStore.data.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { it ->
                it[key]
            }.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun <T> contains(key: Preferences.Key<T>): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(key)
        }
        return isContains
    }

    suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key)
        }
    }

    /**
     * contains 时不用管存的什么类型
     */
    suspend fun contains(key: String): Boolean {
        var isContains by Delegates.notNull<Boolean>()
        dataStore.edit {
            isContains = it.contains(stringPreferencesKey(key))
        }
        return isContains
    }


    /**
     * remove 时不用管存的什么类型，都会删除掉
     */
    suspend fun remove(key: String) {
        dataStore.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

    /**
     * 清除全部数据
     */
    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}
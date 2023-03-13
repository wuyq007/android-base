package com.pers.base.lib.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.pers.base.lib.AppConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// 文件名称: /data/data/项目包名/files/下创建名为 dataStore_data 的文件
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore_data")

object DataStoreUtils {

    private val context: () -> Context = { AppConfig.getContext() }
    private val dataStore = context().dataStore

    suspend fun saveInt(key: String, value: Int) {
        dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    suspend fun getInt(key: String): Int {
        return context().dataStore.data.map {
            it[intPreferencesKey(key)] ?: 0
        }.first()
    }


    suspend fun saveFloat(key: String, value: Float) {
        dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }
    }

    suspend fun getFloat(key: String): Float {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: 0F
        }.first()
    }

    suspend fun saveDouble(key: String, value: Double) {
        dataStore.edit {
            it[doublePreferencesKey(key)] = value
        }
    }

    suspend fun getDouble(key: String): Double {
        return dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: 0.0
        }.first()
    }


    suspend fun saveLong(key: String, value: Long) {
        dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    suspend fun getLong(key: String): Long {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: 0L
        }.first()
    }


    suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }.first()
    }


    suspend fun saveString(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: ""
        }.first()
    }


    suspend fun saveStringSet(key: String, value: Set<String>) {
        dataStore.edit {
            it[stringSetPreferencesKey(key)] = value
        }
    }

    suspend fun getStringSet(key: String): Set<String> {
        val flow: Flow<Set<String>> = dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: HashSet<String>()
        }
        return flow.first()
    }

}
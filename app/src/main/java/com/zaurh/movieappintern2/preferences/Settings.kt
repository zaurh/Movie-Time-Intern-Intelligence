package com.zaurh.movieappintern2.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")


class DataStoreSettings(context: Context) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    private val dataStore = context.dataStore

    suspend fun saveDarkMode(switched: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = switched
        }
    }

    val getDarkMode: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: false
        }

}
package com.example.coffeehouse.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "authToken")

@Singleton
class TokenDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val ACCESS_TOKEN = stringPreferencesKey("accessToken")

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
    }

    suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }

    }
}
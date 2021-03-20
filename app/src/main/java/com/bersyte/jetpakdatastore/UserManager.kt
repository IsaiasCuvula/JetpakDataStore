package com.bersyte.jetpakdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(content: Context) {


    // Create the dataStore and give it a name same as shared preferences
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    private val mDataStore: DataStore<Preferences> = content.dataStore

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys

    suspend fun storeUser(age: Int, name: String) {
        mDataStore.edit { preferences ->

            preferences[USER_NAME_KEY] = name
            preferences[USER_AGE_KEY] = age

            // here it refers to the preferences we are editing

        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine

    val userNameFlow: Flow<String> = mDataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    // Create a name flow to retrieve name from the preferences
    val userAgeFlow: Flow<Int> = mDataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

}
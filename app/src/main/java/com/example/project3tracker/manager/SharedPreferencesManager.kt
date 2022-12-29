package com.example.project3tracker.manager

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val SHARED_PREFERENCES_NAME = "ThreeTrackerSharedPreferences"
    private var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "SHARED_PREFERENCES_KEY_TOKEN"
        const val KEY_TOKEN_DEADLINE = "SHARED_PREFERENCES_KEY_TOKEN_DEADLINE"
    }

    fun putStringValue(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun putLongValue(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun getStringValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }
}
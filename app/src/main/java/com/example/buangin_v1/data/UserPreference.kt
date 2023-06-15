package com.example.buangin_v1.data

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    private val keyPref = "loginData"
    private val pref: SharedPreferences =
        context.getSharedPreferences(keyPref, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()

    fun input(name: String, email: String,token: String) {
        editor.apply {
            putString(DataExtra.NAME, name)
            putString(DataExtra.EMAIL, email)
            putString(DataExtra.TOKEN, token)
        }
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        editor.putBoolean("is_user_logged", isLoggedIn)
        editor.apply()
    }

    fun getLoginStatus(): Boolean {
        return pref.getBoolean("is_user_logged", false)
    }


    fun getString(key: String, defaultValue: String = ""): String {
        return pref.getString(key, defaultValue) ?: defaultValue
    }

    fun inputLogin(key: String, isLogin: Boolean) {
        editor.putBoolean(key, isLogin)
            .apply()
    }

    fun getLogin(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun getToken(key: String): String? {
        return pref.getString(key, null)
    }

    fun clear() {
        editor.clear().apply()
    }
}

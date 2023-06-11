package com.ardianhilmip.catcares.data

import android.content.Context
import com.ardianhilmip.catcares.data.remote.response.DataLogin

class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setToken(value: DataLogin) {
        val editor = preference.edit()
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getToken(): DataLogin {
        return DataLogin(
            token = preference.getString(TOKEN, "") ?: ""
        )
    }

    fun logOut() {
        val editor = preference.edit()
        editor.clear().apply()
    }

    val isLoggedIn: Boolean
        get() = preference.contains(TOKEN)

    companion object {
        val PREF_NAME = "user_pref"
        val TOKEN = "token"
    }
}
package com.zexceed.tokoku.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.zexceed.tokoku.util.Constants.AUTH_TOKEN
import com.zexceed.tokoku.util.Constants.AUTH_USERID
import com.zexceed.tokoku.util.Constants.PREF_NAME

class AuthPreferences(private val context: Context) {

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE)
    }

    fun setToken(userId: Int, token: String) {
        getSharedPreferences().edit().apply {
            putString(AUTH_TOKEN, token)
            putString(AUTH_USERID, userId.toString())
            apply()
        }
    }

    fun getToken(): String {
        return getSharedPreferences().getString(AUTH_TOKEN, "").toString()
    }

    fun getBearerToken(): String {
        return "Bearer ${getToken()}"
    }

    fun isTokenExist(): Boolean {
        return getToken().isNotEmpty()
    }

    fun getUserId(): String {
        return getSharedPreferences().getString(AUTH_USERID, "").toString()
    }

    fun isUserIdExist(): Boolean {
        return getUserId().isNotEmpty()
    }


    fun removeAuth() {
        getSharedPreferences().edit().apply {
            clear()
            apply()
        }
    }
}
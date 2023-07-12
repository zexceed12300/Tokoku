package com.zexceed.tokoku.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

object Constants {
    const val API_BASE_URL = "https://dummyjson.com/"

    const val PREF_NAME = "userPreferences"
    const val AUTH_TOKEN = "Token"
    const val AUTH_USERID = "UserId"


    fun Context.createImageProgress(): CircularProgressDrawable {
        return CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }
}
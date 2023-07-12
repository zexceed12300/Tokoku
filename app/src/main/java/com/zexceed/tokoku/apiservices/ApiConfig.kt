package com.zexceed.tokoku.apiservices

import com.zexceed.tokoku.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig(private val url: String) {
    private val logginInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logginInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }

}
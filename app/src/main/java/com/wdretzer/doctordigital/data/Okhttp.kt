package com.wdretzer.doctordigital.data

import com.wdretzer.doctordigital.BuildConfig
import com.wdretzer.doctordigital.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Okhttp {
    fun build(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(TokenInterceptor())
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)

        }

        readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

    }.build()

    private const val DEFAULT_TIMEOUT = 60L
}

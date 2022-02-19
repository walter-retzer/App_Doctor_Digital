package com.wdretzer.doctordigital.factory

import com.wdretzer.doctordigital.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    fun build(): OkHttpClient = OkHttpClient.Builder().apply {
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

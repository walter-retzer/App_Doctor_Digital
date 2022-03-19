package com.wdretzer.doctordigital.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun build(api: String?, client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(
            if (api == "ProfileAPI") "https://randomuser.me/"
            else "https://dh-digital-doctor-api.herokuapp.com/"
        )
        .build()
}

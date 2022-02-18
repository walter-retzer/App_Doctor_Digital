package com.wdretzer.doctordigital.network

import com.wdretzer.doctordigital.factory.GsonFactory
import com.wdretzer.doctordigital.factory.OkHttpFactory
import com.wdretzer.doctordigital.factory.RetrofitFactory
import com.wdretzer.doctordigital.model.ProfileResponse
import retrofit2.http.GET

interface Api {

    @GET("api")
    suspend fun getProfile(): ProfileResponse

    companion object{

        val instance: Api by lazy {
            RetrofitFactory.build(
                OkHttpFactory.build(),
                GsonFactory.build()
            ).create(Api::class.java)
        }

    }
}
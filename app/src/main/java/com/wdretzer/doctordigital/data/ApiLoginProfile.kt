package com.wdretzer.doctordigital.data


import com.wdretzer.doctordigital.model.ProfileResponseLogin
import retrofit2.http.GET

interface ApiLoginProfile {

    @GET("api")
    suspend fun getProfile(): ProfileResponseLogin

    companion object {
        val instance: ApiLoginProfile by lazy {
            RetrofitFactory.build(
                "ProfileAPI",
                Okhttp.build(),
                GsonFactory.build()
            ).create(ApiLoginProfile::class.java)
        }
    }
}

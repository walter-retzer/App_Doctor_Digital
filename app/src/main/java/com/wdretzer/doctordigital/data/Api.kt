package com.wdretzer.doctordigital.data

import com.wdretzer.doctordigital.model.*
import retrofit2.http.*

interface Api {
    @POST("login")
    suspend fun login(@Body login: LoginRequest): LoginResponseUser

    @GET("user/profile")
    suspend fun profile(
    ): LoginUser

    @GET("doctor")
    suspend fun doctors(
        @Query("page") pageNumber: Int = 1
    ): DoctorsResponse

    companion object {
        val api: Api by lazy {
            RetrofitFactory
                .build(
                    "Doctor",
                    Okhttp.build(),
                    GsonFactory.build()
                )
                .create(Api::class.java)
        }
    }
}

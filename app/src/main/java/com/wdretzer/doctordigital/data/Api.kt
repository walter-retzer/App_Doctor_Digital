package com.wdretzer.doctordigital.data

import retrofit2.http.*

interface Api {
    @POST("login")
    suspend fun login(@Body login: LoginRequest): LoginResponse

    @GET("doctor")
    suspend fun doctors(
        @Header("Content-Type") content: String = "application/json",
        @Header("Authorization") authorization: String,
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

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val token: String)

data class DoctorsResponse(val doctors: List<DoctorsList>)

data class DoctorsList(
    val id: String,
    val photo: String,
    val name: String,
    val specialization: String,
    val classification: Double,
    val views: Int
)

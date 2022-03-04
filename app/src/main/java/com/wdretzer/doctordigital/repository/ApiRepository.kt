package com.wdretzer.doctordigital.repository


import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.data.DoctorsList
import com.wdretzer.doctordigital.data.LoginRequest
import com.wdretzer.doctordigital.data.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ApiRepository(private val api: Api = Api.api) {
    fun login(email: String, password: String): Flow<LoginResponse> = flow {
        emit(Api.api.login(LoginRequest(email, password)))
        kotlinx.coroutines.delay(5000L)
    }.flowOn(Dispatchers.IO)

    companion object{
        val instance: ApiRepository by lazy { ApiRepository() }
    }
}

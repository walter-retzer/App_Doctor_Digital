package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.data.DoctorsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiDoctorRepository(private val api: Api = Api.api) {
    fun doctors(token: String): Flow<DoctorsResponse> = flow {
        emit(Api.api.doctors("application/json", token))
        delay(5000L)
    }.flowOn(Dispatchers.IO)

    companion object {
        val instance: ApiDoctorRepository by lazy { ApiDoctorRepository() }
    }
}

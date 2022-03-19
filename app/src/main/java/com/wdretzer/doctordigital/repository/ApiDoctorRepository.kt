package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiDoctorRepository(
    private val api: Api = Api.api,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getDoctors(pageNumber: Int = 1) = flow {
        emit(api.doctors(pageNumber))
    }.flowOn(dispatcher)

    companion object {
        val instance by lazy { ApiDoctorRepository() }
    }
}

package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.data.DataBaseFactory
import com.wdretzer.doctordigital.model.DoctorsList
import com.wdretzer.doctordigital.model.DoctorsResponse
import com.wdretzer.doctordigital.util.NetworkConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException

class ApiDoctorRepository(
    private val api: Api = Api.api,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getDoctors(pageNumber: Int = 1) = flow {
        emit(api.doctors(pageNumber))

    }.catch { error ->
        if (error is IOException && NetworkConnectionUtils.isConnected().not()){
            val dados = DataBaseFactory.getDataBAse()
            emit(
                DoctorsResponse(
                    limitPage = 1,
                    doctors = dados.doctorDao().listAll().map(::DoctorsList)
                )
            )
        }
    }.flowOn(dispatcher)

    companion object {
        val instance by lazy { ApiDoctorRepository() }
    }
}

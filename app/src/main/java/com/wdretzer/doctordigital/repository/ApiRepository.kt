package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.data.extension.updateStatus
import com.wdretzer.doctordigital.model.LoginRequest
import com.wdretzer.doctordigital.model.LoginResponseUser
import com.wdretzer.doctordigital.network.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class ApiRepository(
    private val api: Api = Api.api,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun login(email: String, password: String): Flow<DataResult<LoginResponseUser>> =
        flow<DataResult<LoginResponseUser>> {
            emit(DataResult.Success(api.login(LoginRequest(email, password))))
        }
            .updateStatus()
            .flowOn(defaultDispatcher)

    companion object {
        val instance: ApiRepository by lazy { ApiRepository() }
    }
}

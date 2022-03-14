package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.extension.SessionManager
import com.wdretzer.doctordigital.model.LoginRequest
import com.wdretzer.doctordigital.model.LoginResponseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach


class ApiRepository(
    private val api: Api = Api.api,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun login(email: String, password: String): Flow<LoginResponseUser> = flow {
        emit(Api.api.login(LoginRequest(email, password)))

    }.onEach {
        SessionManager.saveSession(it.token)
        SessionManager.saveProfile(it.user)

    }.flowOn(defaultDispatcher)

    companion object {
        val instance: ApiRepository by lazy { ApiRepository() }
    }
}

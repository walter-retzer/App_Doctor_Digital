package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.model.LoginUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UserRepository(private val api: Api = Api.api) {

    fun fetchProfile(): Flow<LoginUser> = flow {
        emit(api.profile())
    }.flowOn(Dispatchers.IO)

    companion object {
        val instance by lazy { UserRepository() }
    }
}

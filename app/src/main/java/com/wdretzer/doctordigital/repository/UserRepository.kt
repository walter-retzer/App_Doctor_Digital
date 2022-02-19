package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.model.ProfileResponse
import com.wdretzer.doctordigital.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val api: Api = Api.instance) {

    fun fetchProfile(): Flow<ProfileResponse> = flow {
        emit(api.getProfile())
    }.flowOn(Dispatchers.IO)

    companion object {
        val instance by lazy { UserRepository() }
    }

}

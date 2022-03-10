package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.ApiLoginProfile
import com.wdretzer.doctordigital.model.ProfileResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UserRepository(private val api: ApiLoginProfile = ApiLoginProfile.instance) {

    fun fetchProfile(): Flow<ProfileResponseLogin> = flow {
        emit(api.getProfile())
    }.flowOn(Dispatchers.IO)

    companion object {
        val instance by lazy { UserRepository() }
    }
}

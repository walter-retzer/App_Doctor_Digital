package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.*
import com.wdretzer.doctordigital.model.LoginResponseUser
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.repository.ApiRepository


class ApiViewModel(
    private val repository: ApiRepository = ApiRepository.instance
) : ViewModel() {

    fun login(email: String, password: String): LiveData<DataResult<LoginResponseUser>> =
        repository
            .login(email, password).asLiveData()

}

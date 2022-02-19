package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.network.Repository

class LoginViewModel(private val repository: Repository = Repository.instance): ViewModel() {

    fun login(email: String, password: String): LiveData<DataResult<Boolean>> =
        repository.login(email, password)

}

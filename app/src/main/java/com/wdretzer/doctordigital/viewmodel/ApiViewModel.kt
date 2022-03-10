package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdretzer.doctordigital.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class ApiViewModel(private val repository: ApiRepository = ApiRepository.instance) : ViewModel() {

    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String>
        get() = _success

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.Main){
        repository
            .login(email, password)
            .onStart { _loading.postValue(true) }
            .catch { _error.value = true
                    _loading.postValue(false)}
            .collect {
                _success.value = it.token
                _loading.postValue(false)
            }
    }
}

package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdretzer.doctordigital.data.DoctorsResponse
import com.wdretzer.doctordigital.repository.ApiDoctorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DoctorsViewModel (private val repository: ApiDoctorRepository = ApiDoctorRepository.instance) : ViewModel() {

    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _success: MutableLiveData<DoctorsResponse> = MutableLiveData()
    val success: LiveData<DoctorsResponse>
        get() = _success

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading


    fun listDoctors(token: String) = viewModelScope.launch(Dispatchers.Main){
        repository
            .doctors(token)
            .onStart { _loading.postValue(true) }
            .catch { _error.value = true
                _loading.postValue(false)}
            .collect {
                _success.value = it
                _loading.postValue(false)
            }
    }
}

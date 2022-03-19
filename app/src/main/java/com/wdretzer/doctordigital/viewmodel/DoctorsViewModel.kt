package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdretzer.doctordigital.model.DoctorsResponse
import com.wdretzer.doctordigital.repository.ApiDoctorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class DoctorsViewModel(
    private val repository: ApiDoctorRepository = ApiDoctorRepository.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    private val _success: MutableLiveData<DoctorsResponse> = MutableLiveData()
    val success: LiveData<DoctorsResponse>
        get() = _success

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    var limitPage = 0

    fun listDoctors(pageNumber: Int = 1) = viewModelScope.launch(dispatcher) {
        repository
            .getDoctors(pageNumber)
            .onStart { _loading.postValue(true) }
            .catch { _error.value = true }
            .onCompletion { _loading.postValue(false) }
            .collect{
                _success.postValue(it)
                limitPage = it.limitPage
            }
    }
}

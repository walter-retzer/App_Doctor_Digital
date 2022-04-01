package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.*
import com.wdretzer.doctordigital.model.DoctorsList
import com.wdretzer.doctordigital.repository.ApiDoctorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class DoctorsViewModel(
    private val repository: ApiDoctorRepository = ApiDoctorRepository.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun listDoctors(pageNumber: Int = 1) = repository.getDoctors(pageNumber).flowOn(dispatcher).asLiveData()
    fun addOrRemoveFavourite(item: DoctorsList) = repository.addOrRemoveFavourite(item).flowOn(dispatcher).asLiveData()
    fun getFavourite() = repository.getFavourite().flowOn(dispatcher).asLiveData()
}

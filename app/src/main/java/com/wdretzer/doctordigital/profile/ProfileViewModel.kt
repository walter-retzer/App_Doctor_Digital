package com.wdretzer.doctordigital.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdretzer.doctordigital.model.ProfileItem
import com.wdretzer.doctordigital.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class ProfileViewModel(private val repository: UserRepository = UserRepository.instance) :
    ViewModel() {

    private val _profile = MutableLiveData<ProfileItem>()
    val profile: LiveData<ProfileItem>
        get() = _profile

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    fun loadProfile() {
        viewModelScope.launch {
            repository.fetchProfile()
                .onStart { _loading.value = true }
                .catch { _error.value = true }
                .onCompletion { _loading.value = false }
                .collect {
                    _profile.value = it.results.first()
                }
        }
    }

}



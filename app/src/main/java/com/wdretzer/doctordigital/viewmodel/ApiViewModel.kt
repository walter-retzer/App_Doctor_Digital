package com.wdretzer.doctordigital.viewmodel

import androidx.lifecycle.*
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.wdretzer.doctordigital.extension.SessionManager
import com.wdretzer.doctordigital.model.LoginResponseUser
import com.wdretzer.doctordigital.model.SocialLogin
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.repository.ApiRepository
import com.wdretzer.doctordigital.util.FacebookCustomCallback
import com.wdretzer.doctordigital.util.GoogleLogInActivityContract
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ApiViewModel(
    private val repository: ApiRepository = ApiRepository.instance
) : ViewModel() {

    private val _socialLoginError: MutableLiveData<String> = MutableLiveData()
    val socialLoginError: LiveData<String>
        get() = _socialLoginError

    private val _apiLoginResult: MutableLiveData<DataResult<LoginResponseUser>> = MutableLiveData()
    val apiLoginResult: LiveData<DataResult<LoginResponseUser>>
        get() = _apiLoginResult

    val loginManager: LoginManager = LoginManager.getInstance()
    val callbackManager = CallbackManager.Factory.create()

    init {
        loginManager.registerCallback(callbackManager, FacebookCustomCallback {
            when (it) {
                is FacebookCustomCallback.Result.Success -> {
                    socialLogin(FACEBOOK_ARGS, it.token)
                }
                else -> {
                    _socialLoginError.value = FACEBOOK_ARGS
                }
            }
        })
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        repository.login(email, password).collect {
            _apiLoginResult.value = it }
    }


    fun socialLogin(type: String, token: String) = viewModelScope.launch {
        repository.socialLogin(SocialLogin(type, token))
            .collect {
                if(it is DataResult.Success){
                    SessionManager.saveSession(it.data.token)
                    SessionManager.saveProfile(it.data.user)
                }
                _apiLoginResult.value = it }
    }


    fun loginGoogle(result: GoogleLogInActivityContract.Result) {
        when (result) {
            is GoogleLogInActivityContract.Result.Success -> {
                val token = result.googleSignInAccount.idToken.toString()
                socialLogin(GOOGLE_ARGS, token)
            }
            is GoogleLogInActivityContract.Result.Error -> {
                _socialLoginError.value = GOOGLE_ARGS
            }
        }
    }

    companion object {
        const val FACEBOOK_ARGS = "FACEBOOK"
        const val GOOGLE_ARGS = "GOOGLE"
    }

}

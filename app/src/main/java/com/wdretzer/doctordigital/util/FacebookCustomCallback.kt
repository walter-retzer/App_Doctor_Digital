package com.wdretzer.doctordigital.util

import android.util.Log
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult

class FacebookCustomCallback(private val callback: (Result) -> Unit) : FacebookCallback<LoginResult> {

    override fun onCancel() {}

    override fun onError(error: FacebookException) {
        Log.e(TAG, error.message ?: "")
        callback.invoke(Result.Error(error))
    }

    override fun onSuccess(result: LoginResult) {
        callback.invoke(Result.Success(result.accessToken.token))
    }

    sealed class Result{
        data class  Success(val token: String) : Result()
        data class  Error(val exception: Exception) : Result()
    }

    companion object {
        const val TAG = "FACEBOOK_CALLBACK"
    }

}

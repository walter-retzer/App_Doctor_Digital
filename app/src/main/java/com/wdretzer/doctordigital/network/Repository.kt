package com.wdretzer.doctordigital.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData


class Repository {

    fun login(email: String, password: String): MutableLiveData<DataResult<Boolean>> {

        val result = MutableLiveData<DataResult<Boolean>>()
        result.value = DataResult.Loading

        try {

            Handler(Looper.getMainLooper()).postDelayed({
                result.value = DataResult.Success(true)
            }, 2000)

//            throw InterruptedException()

        } catch (e: InterruptedException) {
            Log.e(LOG_TAG, e.message ?: "")
            result.value = DataResult.Error(error = e)
        }

        return result
    }

    companion object {
        val instance: Repository by lazy { Repository() }
        private const val LOG_TAG = "SubmitAPI"
    }

}

sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val error: Throwable?) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()
}
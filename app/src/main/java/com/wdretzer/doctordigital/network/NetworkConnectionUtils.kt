package com.wdretzer.doctordigital.network

import android.content.Context
import android.net.ConnectivityManager
import com.wdretzer.doctordigital.util.AppUtil

object NetworkConnectionUtils {

    fun isConnected(): Boolean {
        val connectivityManager =
            AppUtil.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

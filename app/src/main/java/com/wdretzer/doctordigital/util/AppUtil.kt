package com.wdretzer.doctordigital.util

import android.app.Application
import android.content.Context
import com.wdretzer.doctordigital.data.DataBaseFactory

class AppUtil: Application() {

    override fun onCreate() {
        super.onCreate()
        DataBaseFactory.build(this)
        appContext = applicationContext
    }

    companion object{
        var appContext: Context? = null
    }
}

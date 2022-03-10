package com.wdretzer.doctordigital.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {
    fun build(): Gson = GsonBuilder().create()
}

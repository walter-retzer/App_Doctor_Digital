package com.wdretzer.doctordigital.data

import android.content.Context
import androidx.room.Room
import com.wdretzer.doctordigital.data.local.AppDataBase

object DataBaseFactory {

    private var instance: AppDataBase? = null

    fun getDataBAse() = instance ?: throw IllegalStateException("Database is not initialized")

    fun build(context: Context): AppDataBase {

        val currentInstance = instance
        if (currentInstance != null) return currentInstance

        val dataBase = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "doctor-room"
        )

        dataBase.allowMainThreadQueries()
        val dbInstance = dataBase.build()
        instance = dbInstance

        return dbInstance
    }

    fun removeInstance(){
        instance = null
    }
}

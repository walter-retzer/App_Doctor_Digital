package com.wdretzer.doctordigital.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wdretzer.doctordigital.data.dao.DoctorDao
import com.wdretzer.doctordigital.data.entity.DoctorEntity

@Database(
    entities = [DoctorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao

}
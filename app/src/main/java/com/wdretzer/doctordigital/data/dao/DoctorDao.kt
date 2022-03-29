package com.wdretzer.doctordigital.data.dao

import androidx.room.*
import com.wdretzer.doctordigital.data.entity.DoctorEntity

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insert(vararg doctorEntity: DoctorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertAll(doctorEntity: List<DoctorEntity>)

    @Delete
    fun delete(vararg doctorEntity: DoctorEntity)

    @Update
    fun update(vararg doctorEntity: DoctorEntity)

    @Query("SELECT * FROM doctor ORDER BY id DESC")
    fun listAll(): List<DoctorEntity>

//    @Query("SELECT * FROM doctor ORDER BY id DESC LIMIT 10 OFFSET (:page-1)*10")
//    fun listAll(page: Int): List<DoctorEntity>

    @Query("SELECT * FROM doctor WHERE id = :id")
    fun retrieveById(id: Int): DoctorEntity

    @Query("SELECT * FROM doctor WHERE api_id = :apiId")
    fun retrieveByApiId(apiId: String) : DoctorEntity
}
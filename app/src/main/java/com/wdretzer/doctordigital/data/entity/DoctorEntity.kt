package com.wdretzer.doctordigital.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "doctor")
data class DoctorEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val photo: String,
    @ColumnInfo
    val specialization: String = "",
    @ColumnInfo
    val classification: Int,
    @ColumnInfo
    val experience: Int = 1,
    @ColumnInfo(name = "patient_stories" )
    val patientStories: Int = 5,
    @ColumnInfo
    val views: Int = 100,
    @ColumnInfo(name = "api_id")
    val apiId: String = "",

    )
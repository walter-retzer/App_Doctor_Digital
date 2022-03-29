package com.wdretzer.doctordigital.model

import com.google.gson.annotations.SerializedName
import com.wdretzer.doctordigital.data.entity.DoctorEntity

data class DoctorsResponse(
    @SerializedName("limit_page")
    val limitPage: Int,
    val doctors: List<DoctorsList>)

data class DoctorsList(
    val id: String,
    val photo: String,
    val name: String,
    val specialization: String,
    val classification: Double,
    val experience: Int,
    val patientStories: Int,
    val views: Int
){
    constructor(doctorEntity: DoctorEntity) : this(
        doctorEntity.apiId,
        doctorEntity.photo,
        doctorEntity.name,
        doctorEntity.specialization,
        doctorEntity.classification.toDouble(),
        doctorEntity.experience,
        doctorEntity.patientStories,
        doctorEntity.views,

    )
}
package com.wdretzer.doctordigital.model

data class DoctorsResponse(
    val limit_page: Int,
    val doctors: List<DoctorsList>)

data class DoctorsList(
    val id: String,
    val photo: String,
    val name: String,
    val specialization: String,
    val classification: Double,
    val views: Int
)

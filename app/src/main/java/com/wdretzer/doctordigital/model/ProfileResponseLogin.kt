package com.wdretzer.doctordigital.model

data class ProfileResponseLogin(val results: List<ProfileItemLoginApi>)
data class ProfileItemLoginApi(
    val name: ProfileName,
    val phone: String,
    val dob: ProfileRegister,
    val location: ProfileLocation,
    val picture: ProfilePictures
)
data class ProfileName(val first: String, val last: String)
data class ProfileRegister(val date: String)
data class ProfileLocation(val city: String)
data class ProfilePictures(val large: String)

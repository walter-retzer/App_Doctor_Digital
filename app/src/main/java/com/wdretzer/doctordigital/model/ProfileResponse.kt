package com.wdretzer.doctordigital.model


data class ProfileResponse(val results: List<ProfileItem>)
data class ProfileItem(val name: ProfileName, val phone:String, val dob: ProfileRegister, val location: ProfileLocation, val picture: ProfilePictures)
data class ProfileName(val first:String, val last:String)
data class ProfileRegister(val date: String)
data class ProfileLocation(val city: String)
data class ProfilePictures(val large: String)

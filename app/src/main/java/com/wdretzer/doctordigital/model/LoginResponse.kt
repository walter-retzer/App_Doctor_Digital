package com.wdretzer.doctordigital.model

data class LoginResponse(val user: List<LoginUser>)
data class LoginUser(
    val name: String,
    val email: String,
    val photo: String,
    val phone: String,
    val bday: String,
    val location: String
)

data class LoginToken(val token: String)

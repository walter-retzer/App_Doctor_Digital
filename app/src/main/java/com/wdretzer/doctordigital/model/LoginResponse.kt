package com.wdretzer.doctordigital.model

data class LoginResponseUser(val user: LoginUser, val token: String)

data class LoginUser(
    val name: String,
    val email: String,
    val photo: String,
    val phone: String,
    val bday: String,
    val location: String
)

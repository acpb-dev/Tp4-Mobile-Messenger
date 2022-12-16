package com.example.messenger.api.data

data class CreateAccount(
    val email: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val password: String = "",
    val profileImgUrl: String = ""
)
package com.example.messenger.viewmodel.data

class Users : ArrayList<UsersItem>()

data class UsersItem(
    val firstname: String = "",
    val friends: List<String> = listOf(),
    val id: String = "",
    val lastname: String = "",
    val profileImgUrl: String = "",
    val isCurrentUser: Boolean
)
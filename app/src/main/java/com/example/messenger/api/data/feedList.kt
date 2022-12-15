package com.example.messenger.api.data

class feedList : ArrayList<feedItem>()

data class feedItem(
    val createdAt: String = "",
    val id: String = "",
    val images: List<Any> = listOf(),
    val text: String = "",
    val user: String = "",
    val isFromCurrentUser: Boolean = false
)
package com.example.messenger.api.data

class FeedList : ArrayList<FeedItem>()

data class FeedItem(
    val createdAt: String = "",
    val id: String = "",
    val images: List<Any> = listOf(),
    val likes: List<Any> = listOf(),
    val text: String = "",
    val user: String = "",
    val isFromCurrentUser: Boolean = false
)
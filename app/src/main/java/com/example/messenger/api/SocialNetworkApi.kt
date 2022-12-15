package com.example.messenger.api

import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.feedList
import retrofit2.Response
import retrofit2.http.Url

interface SocialNetworkApi {
    suspend fun signIn(email: String, password: String): Boolean// Resource<Unit>
    suspend fun signUp(email: String, password: String, firstname: String, lastname: String)
    suspend fun feed(): feedList?
    suspend fun getUsers(search: String): Users?
    suspend fun postToFeed(body: PostInfo): Boolean
    suspend fun addFriend(url: String): Boolean
}
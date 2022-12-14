package com.example.messenger.data.api

import com.example.messenger.viewmodel.data.PostInfo
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedList
import retrofit2.Response
import retrofit2.http.Url

interface SocialNetworkApi {
    suspend fun signIn(email: String, password: String): Boolean// Resource<Unit>
    suspend fun signUp(email: String, password: String, firstname: String, lastname: String)
    suspend fun feed(email: String, password: String): feedList?
    suspend fun getUsers(email: String, password: String, search: String): Users?
    suspend fun postToFeed(email: String, password: String, body: PostInfo): Boolean
    suspend fun addFriend(email: String, password: String, url: String): Boolean
}
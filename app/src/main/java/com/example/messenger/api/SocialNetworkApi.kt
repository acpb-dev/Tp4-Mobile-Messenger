package com.example.messenger.api

import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.UpdateProfile
import com.example.messenger.api.data.Users
import retrofit2.Response

interface SocialNetworkApi {
    suspend fun ping() : Boolean
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String, firstname: String, lastname: String): Boolean
    suspend fun getFeed(): FeedList?
    suspend fun getUserPosts(userId: String): FeedList?
    suspend fun getUsers(search: String): Users?
    suspend fun postToFeed(body: PostInfo): Boolean
    suspend fun addFriend(url: String): Boolean
    suspend fun updateProfile(update: UpdateProfile): Boolean
    suspend fun likePost(postId: String) : Boolean
    fun getStoredEmail(): String
    fun getStoredPassword(): String
    fun clearStoredCredentials()
}
package com.example.messenger.api

import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.feedList
import retrofit2.Response
import retrofit2.http.*


interface RetrofitSocialNetworkApi {
    @GET("/signin")
    suspend fun signIn():Response<Unit>
    @GET("/signup")
    suspend fun signUp(email: String, password: String, firstname: String, lastname: String):Response<Unit>
    @GET("/feed")
    suspend fun feed():Response<feedList>
    @GET("/user")
    suspend fun getUsers(@Query("search") name: String):Response<Users>
    @PUT("/post")
    suspend fun postToFeed(@Body body: PostInfo): Response<Unit>
    @PUT("/user/friend/{Id}")
    suspend fun addFriend(@Path("Id") userId: String): Response<Unit>
}
package com.example.messenger.api

import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.UpdateProfile
import com.example.messenger.api.data.Users
import retrofit2.Response
import retrofit2.http.*


interface RetrofitSocialNetworkApi {
    @GET("/signin")
    suspend fun signIn(): Response<Unit>

    @GET("/signup")
    suspend fun signUp(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Response<Unit>

    @GET("/feed")
    suspend fun getFeed(): Response<FeedList>

    @GET("/user/{Id}/posts")
    suspend fun getUserPosts(@Path("Id") userId: String): Response<FeedList>

    @GET("/user")
    suspend fun getUsers(@Query("search") name: String): Response<Users>

    @PUT("/post")
    suspend fun postToFeed(@Body body: PostInfo): Response<Unit>

    @PUT("/user/friend/{Id}")
    suspend fun addFriend(@Path("Id") userId: String): Response<Unit>

    @PUT("/user/")
    suspend fun updateProfile(@Body update: UpdateProfile): Response<Unit>
}
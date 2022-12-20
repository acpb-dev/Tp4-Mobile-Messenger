package com.example.messenger.api

import com.example.messenger.api.data.*
import retrofit2.Response
import retrofit2.http.*


interface RetrofitSocialNetworkApi {
    @GET("/")
    suspend fun ping(): Response<Boolean>

    @GET("/signin")
    suspend fun signIn(): Response<Unit>

    @POST("/signup")
    suspend fun signUp(@Body body: CreateAccount): Response<Unit>

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

    @POST("/post/{Id}/like")
    suspend fun likePost(@Path("Id") postId: String): Response<Unit>
}
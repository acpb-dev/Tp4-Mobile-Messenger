package com.example.messenger.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSocialNetworkApi {
    @GET("/signin")
    suspend fun signIn():Response<Unit>
    @GET("/signup")
    suspend fun signUp(email: String, password: String, firstname: String, lastname: String):Response<Unit>
}
package com.example.messenger.data.api

import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedList

interface SocialNetworkApi {
    suspend fun signIn(email: String, password: String): Boolean// Resource<Unit>

    suspend fun signUp(email: String, password: String, firstname: String, lastname: String)

    suspend fun feed(email: String, password: String): feedList?

    suspend fun getUsers(email: String, password: String, search: String): Users?
}
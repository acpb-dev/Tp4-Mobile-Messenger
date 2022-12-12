package com.example.messenger.data.api

import com.example.social_network.utils.contants.Resource

interface SocialNetworkApi {
    suspend fun signIn(email: String, password: String): Boolean// Resource<Unit>

    suspend fun signUp(email: String, password: String, firstname: String, lastname: String)
}
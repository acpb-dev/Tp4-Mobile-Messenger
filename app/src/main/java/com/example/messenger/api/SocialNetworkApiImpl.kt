package com.example.messenger.api

import android.content.SharedPreferences
import com.example.messenger.api.auth.BasicAuthInterceptor
import com.example.messenger.api.data.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ServiceConfigurationError
import java.util.concurrent.TimeUnit


class SocialNetworkApiImpl(private val sharedPreferences: SharedPreferences) : SocialNetworkApi {

    private val client: RetrofitSocialNetworkApi = Retrofit.Builder()
        .baseUrl("https://wiveb.webredirect.org:8888")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitSocialNetworkApi::class.java)


    private var authenticatedClient: RetrofitSocialNetworkApi = createAuthClient()

    override suspend fun ping() {
       val response = authenticatedClient.ping()

    }

    override suspend fun signIn(email: String, password: String): Boolean {
        saveAuthInformation(email, password)
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.signIn()
        return if (response.isSuccessful) {
            return true
        } else {
            clearStoredCredentials()
            return false
        }
    }

    override fun getStoredEmail(): String{
        return sharedPreferences.getString("email", "") ?: ""
    }

    override fun getStoredPassword(): String {
        return sharedPreferences.getString("password", "") ?: ""
    }

    override fun clearStoredCredentials(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()
    }

    override suspend fun signUp(email: String, password: String, firstname: String, lastname: String): Boolean {
        val body = CreateAccount(email = email, firstname = firstname, lastname = lastname, password = password)
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.signUp(body)
        if (response.isSuccessful){
            saveAuthInformation(email, password)
        }
        return response.isSuccessful
    }

    override suspend fun updateProfile(updated: UpdateProfile): Boolean {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.updateProfile(updated)
        return response.isSuccessful
    }

    override suspend fun postToFeed(body: PostInfo): Boolean {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.postToFeed(body)
        return if (response.isSuccessful) {
            println("Success")
            return true
        } else {
            return false
        }
    }

    override suspend fun addFriend(friendId: String): Boolean {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.addFriend(friendId)
        return if (response.isSuccessful) {
            println("Success")
            return true
        } else {
            return false
        }
    }

    override suspend fun getFeed(): FeedList? {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.getFeed()
        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    override suspend fun getUserPosts(userId: String): FeedList? {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.getUserPosts(userId)
        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    override suspend fun getUsers(search: String): Users? {
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.getUsers(search)
        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    private fun saveAuthInformation(email: String, password: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun createAuthClient(): RetrofitSocialNetworkApi {
        val okHttpClient = UnsafeOkHttpClient
            .getUnsafeOkHttpClient()
            .connectTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(
                BasicAuthInterceptor(
                    sharedPreferences.getString("email", "") ?: "",
                    sharedPreferences.getString("password", "") ?: ""
                )
            )
            .build()
        return Retrofit.Builder()
            .baseUrl("https://wiveb.webredirect.org:8888")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitSocialNetworkApi::class.java)
    }
}
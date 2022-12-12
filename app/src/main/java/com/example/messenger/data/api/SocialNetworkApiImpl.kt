package com.example.messenger.data.api

import UnsafeOkHttpClient
import android.content.SharedPreferences
import com.example.messenger.data.api.auth.BasicAuthInterceptor
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedList
import com.google.gson.Gson
import okhttp3.internal.wait
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SocialNetworkApiImpl(private val sharedPreferences: SharedPreferences): SocialNetworkApi {

    private val client: RetrofitSocialNetworkApi = Retrofit.Builder()
        .baseUrl("https://wiveb.webredirect.org:8888")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitSocialNetworkApi::class.java);


    private var authenticatedClient: RetrofitSocialNetworkApi = createAuthClient()

    override suspend fun signIn(email: String, password: String): Boolean {
        saveAuthInformation(email, password)
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.signIn()
        return if(response.isSuccessful){
            println("Success")
            return true
        }else {
            return false
        }
    }

    override suspend fun feed(email: String, password: String): feedList? {
        saveAuthInformation(email, password)
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.feed()
        return if(response.isSuccessful){
            return response.body();
        }else {
            return null
        }
    }

    override suspend fun getUsers(email: String, password: String): Users?{
        saveAuthInformation(email, password)
        authenticatedClient = createAuthClient()
        val response = authenticatedClient.getUsers("")
        return if(response.isSuccessful){
            return response.body();
        }else {
            return null
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ) {
        //client.signUp(email, password, firstname, lastname)
        TODO("Not yet implemented")
    }

    private fun saveAuthInformation(email: String, password: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun createAuthClient(): RetrofitSocialNetworkApi {
        // sharedPreferences = sauvegarder des informations sur l'appareil. Utile si on veut
        // implementer une logique pour contouner le login si deja signin
        val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            .addInterceptor(
                BasicAuthInterceptor(
                sharedPreferences.getString("email","")?: "",
                sharedPreferences.getString("password","")?: ""
            )
            )
            .build()
        return Retrofit.Builder()
            .baseUrl("https://wiveb.webredirect.org:8888")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitSocialNetworkApi::class.java);
    }
}
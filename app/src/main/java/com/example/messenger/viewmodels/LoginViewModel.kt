package com.example.messenger.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import kotlinx.coroutines.launch

class LoginViewModel(private val api: SocialNetworkApi) : ViewModel() {

    var isAuthenticated = mutableStateOf(false)
    val pingValid = mutableStateOf(true)
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)

    init{
        println("Started")
        signInAuto()
//        viewModelScope.launch {
//            while (true){
//                try {
//                    api.ping()
//                    pingValid.value = true
//                }catch (e: SocketTimeoutException){
//                    pingValid.value = false
//                    println(e)
//                }
//                delay(5000)
//            }
//        }
    }

    fun signIn(email: String, password: String) {
        if (pingValid.value) {
            viewModelScope.launch {
                val response = api.signIn(email, password)
                isAuthenticated.value = response
                // usernameStored.value = api.getStoredEmail()
            }
        }
    }

    fun signInAuto() {
        val email = api.getStoredEmail()
        val password = api.getStoredPassword()
        if (pingValid.value) {
            if (password.isNotEmpty() && email.isNotEmpty()) {
                viewModelScope.launch {
                    val response = api.signIn(email, password)
                    isAuthenticated.value = response
                }
            }
        }
    }


}
package com.example.messenger.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import kotlinx.coroutines.launch

class LoginViewModel(private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {

    val sharedViewModel = sharedViewModel

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)

    init{
        signInAuto()
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val response = api.signIn(email, password)
            sharedViewModel.isAuthenticated.value = response
        }

    }

    private fun signInAuto() {
        val email = api.getStoredEmail()
        val password = api.getStoredPassword()
        if (password.isNotEmpty() && email.isNotEmpty()) {
            viewModelScope.launch {
                val response = api.signIn(email, password)
                sharedViewModel.isAuthenticated.value = response
            }
        }
    }


}
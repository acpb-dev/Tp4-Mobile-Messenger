package com.example.messenger.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import kotlinx.coroutines.launch

class RegisterViewModel(private val api: SocialNetworkApi) : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var password2 = mutableStateOf("")
    var firstname = mutableStateOf("")
    var lastname = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)



    var isAuthenticated = mutableStateOf(false)

    fun signUp(email: String, password: String, firstname: String, lastname: String){
            viewModelScope.launch {
                val response = api.signUp(email = email, password = password, firstname = firstname, lastname = lastname)
                isAuthenticated.value = response
            }
    }

    fun verifyInfo(email: String, password: String, password2: String, fName: String, lName: String): String{
        val emailT = email.trim()
        val passwordT = password.trim()
        val fNameT = fName.trim()
        val lNameT = lName.trim()
        var error = ""
        if (fNameT == ""){
            error = "Firstname cannot be empty "
        }
        if (lNameT == ""){
            error = "Lastname cannot be empty "
        }
        if (passwordT == ""){
            error = "Password cannot be empty"
        }

        if (password2 != password){
            error = "Passwords must match"
        }
        if (!emailT.contains('@')){
            error = "Invalid Email address"
        }
        return error
    }
}
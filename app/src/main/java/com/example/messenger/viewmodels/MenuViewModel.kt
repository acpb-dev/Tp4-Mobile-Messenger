package com.example.messenger.viewmodels

import androidx.lifecycle.ViewModel
import com.example.messenger.api.SocialNetworkApi

class MenuViewModel(private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {

    val sharedViewModel = sharedViewModel

    fun clearSavedCredentials(){
        api.clearStoredCredentials()
    }



}
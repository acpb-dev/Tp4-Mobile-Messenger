package com.example.messenger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.UpdateProfile
import kotlinx.coroutines.launch

class UpdateProfileViewModel (private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {

    val sharedViewModel = sharedViewModel

    fun updateProfile(fName: String, lName: String, imgLink: String) {
        val updated = UpdateProfile(
            firstname = fName.trim(),
            lastname = lName.trim(),
            profileImageUrl = imgLink.trim()
        )
        viewModelScope.launch {
            val response = api.updateProfile(updated)
            if (response) {
                sharedViewModel.getAllUsers(true)
            }
        }

    }
}
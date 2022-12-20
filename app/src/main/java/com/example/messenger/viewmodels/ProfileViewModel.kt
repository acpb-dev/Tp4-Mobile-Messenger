package com.example.messenger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.UsersItem
import kotlinx.coroutines.launch

class ProfileViewModel (private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {
    val sharedViewModel = sharedViewModel

    fun getUserPosts(userId: String) {
        viewModelScope.launch {
            val response = api.getUserPosts(userId)
            if (response != null) {
                sharedViewModel.userFeed.value = response
            }
        }
    }

    fun getCurrentUser(user: UsersItem): UsersItem {
        sharedViewModel.userList.value.forEach {
            if (it.id == user.id) {
                return it
            }
        }
        return UsersItem()
    }

    val getEmail: String
        get() = api.getStoredEmail()

    fun addFriend(id: String) {
        viewModelScope.launch {
            val response = api.addFriend(id)
        }
    }


}
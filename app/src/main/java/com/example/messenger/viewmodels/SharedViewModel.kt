package com.example.messenger.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.UsersItem
import kotlinx.coroutines.launch

class SharedViewModel(private val api: SocialNetworkApi) : ViewModel() {
    var isAuthenticated = mutableStateOf(false)
    val currentUser = mutableStateOf(UsersItem())
    val userList = mutableStateOf(Users())
    val userFeed = mutableStateOf(FeedList())
    private var recentUsers = mutableStateOf(mutableListOf<UsersItem>())
    val myUser = mutableStateOf(UsersItem())

    fun clearRecent() {
        recentUsers.value.clear()
    }

    fun addRecent(uid: UsersItem) {
        if (recentUsers.value.isNotEmpty()) {
            if (recentUsers.value.last().id != uid.id) {
                recentUsers.value.add(uid)
            }
        } else {
            recentUsers.value.add(uid)
        }
    }

    fun removeRecent() {
        recentUsers.value.removeLast()
        if (recentUsers.value.isNotEmpty()) {
            currentUser.value = recentUsers.value.last()
        }
    }

    fun getAllUsers(setCurrentUser: Boolean) {
        viewModelScope.launch {
            val response = api.getUsers("")
            if (response != null) {
                userList.value = response
                userList.value.forEach {
                    if (it.isCurrentUser) {
                        if (setCurrentUser) {
                            currentUser.value = it
                        }
                        myUser.value = it
                    }
                }
            }
        }
    }







}
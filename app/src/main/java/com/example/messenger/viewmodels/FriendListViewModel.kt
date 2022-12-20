package com.example.messenger.viewmodels

import androidx.lifecycle.ViewModel
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.UsersItem

class FriendListViewModel (private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel(){

    val sharedViewModel = sharedViewModel

    fun getFriendsList(friendList: List<String>): MutableList<String> {
        val list = mutableListOf<String>()
        sharedViewModel.userList.value.forEach { user ->
            friendList.forEach { friend ->
                if (user.id == friend) {
                    list.add(friend)
                }
            }
        }
        return list
    }

    fun getUserById(uid: String): UsersItem {
        sharedViewModel.userList.value.forEach { user ->
            if (user.id == uid) {
                return user
            }
        }
        return UsersItem()
    }
}
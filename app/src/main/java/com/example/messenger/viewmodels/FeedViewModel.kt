package com.example.messenger.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.UsersItem
import kotlinx.coroutines.launch

class FeedViewModel (private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {
    val sharedViewModel = sharedViewModel
    val feed = mutableStateOf(FeedList())


    fun getFeed() {
        viewModelScope.launch {
            val response = api.getFeed()
            if (response != null) {
                feed.value = response
            }
        }
    }

    fun postFeed(body: PostInfo) {
        viewModelScope.launch {
            api.postToFeed(body)
        }
    }

    fun getUserById(uid: String): UsersItem {
        sharedViewModel.userList.value.forEach { user ->
            if (user.id == uid) {
                return user
            }
        }
        return UsersItem()
    }

    fun likePost(postId: String){
        viewModelScope.launch {
            val response = api.likePost(postId)
            if (response){
                getFeed()
            }
        }
    }
}
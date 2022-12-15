package com.example.messenger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.UsersItem
import com.example.messenger.api.data.feedList
import com.example.messenger.utils.SearchWidget
import kotlinx.coroutines.launch

class MessengerViewModel(private val api: SocialNetworkApi): ViewModel() {
    val searchedUser = mutableStateOf(Users())
    val currentUser = mutableStateOf(UsersItem())
    val userList = mutableStateOf(Users())
    val myFriends = mutableStateOf(mutableListOf<String>())
    val feed = mutableStateOf(feedList())
    val isAuthenticated = mutableStateOf(false)
    private val usernameStored = mutableStateOf("")
    val getEmail: String
        get() = usernameStored.value

    fun signIn(email: String, password: String){
        usernameStored.value = email
        viewModelScope.launch {
            isAuthenticated.value = api.signIn(email, password)

        }
        println(isAuthenticated)
    }

    fun feed(){
        viewModelScope.launch {
            val response = api.feed()
            println("Get Feed : " + response.toString())
            if (response != null){
                feed.value = response
            }
        }
    }

    fun addFriend(id: String){
        viewModelScope.launch {
            val response = api.addFriend(id)
            println("Added Friend : $id $response")
        }
    }

    fun postFeed(body: PostInfo){
        viewModelScope.launch {
            api.postToFeed(body)
        }
    }

    fun getAllUsers(){
        viewModelScope.launch {
            val response = api.getUsers("")
            if (response != null){
                userList.value = response
                userList.value.forEach{
                    if (it.isCurrentUser){
                        currentUser.value = it
                    }
                }
            }
        }
    }

    fun getUserByName(search: String) {
        viewModelScope.launch {
            val response = api.getUsers(search)
            if (response != null){
                searchedUser.value = response
            }
        }
    }

    fun getFriendsList(){
        var list = mutableListOf<String>()
        userList.value.forEach{ user ->
            currentUser.value.friends.forEach { friend ->
                if (user.id == friend){
                    list.add(friend)
                }
            }
        }
        myFriends.value = list;
    }

    fun getUserById(uid: String): UsersItem {
        userList.value.forEach{ user ->
            if (user.id == uid){
                return user;
            }
        }
        return UsersItem()
    }

    private val searchWidget = SearchWidget();
    val searchWidgetState: State<SearchWidget.SearchWidgetState>
        get() = searchWidget.searchWidgetState

    val searchTextState: State<String>
        get() = searchWidget.searchTextState

    fun updateSearchWidgetState(newValue: SearchWidget.SearchWidgetState){
        searchWidget.updateSearchWidgetState(newValue);
    }

    fun updateSearchTextState(newValue: String){
        val trimmedValue = newValue.trim()
        if (trimmedValue != ""){
            getUserByName(trimmedValue)
        }else{
            searchedUser.value = Users()
        }
        searchWidget.updateSearchTextState(newValue);
    }
}
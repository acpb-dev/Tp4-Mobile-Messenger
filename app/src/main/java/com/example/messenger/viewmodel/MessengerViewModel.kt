package com.example.messenger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.api.SocialNetworkApi
import com.example.messenger.viewmodel.data.*
import kotlinx.coroutines.launch

class MessengerViewModel(private val api: SocialNetworkApi): ViewModel() {


    var myFriends = mutableStateOf(mutableListOf<String>())

    var searchedUser = mutableStateOf(Users())

    var currentUser = mutableStateOf(UsersItem())
    var userSelected = mutableStateOf("")
    val isAuthenticated = mutableStateOf(false)
    private val usernameStored = mutableStateOf("")
    val getEmail: String
        get() = usernameStored.value

    private val passwordStored = mutableStateOf("")
    val feed = mutableStateOf(feedList())
    val userList = mutableStateOf(Users())

    fun signIn(email: String, password: String){
        usernameStored.value = email
        passwordStored.value = password
        viewModelScope.launch {
            isAuthenticated.value = api.signIn(email, password)
        }
    }

    fun feed(){
        viewModelScope.launch {
            val tempfeed = api.feed(usernameStored.value, passwordStored.value)
            if (tempfeed != null){
                feed.value = tempfeed
            }
        }
    }

    fun getAllUsers(){
        viewModelScope.launch {
            val tempfeed = api.getUsers(usernameStored.value, passwordStored.value, "")
            if (tempfeed != null){
                userList.value = tempfeed
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
            val tempfeed = api.getUsers(usernameStored.value, passwordStored.value, search)
            if (tempfeed != null){
                searchedUser.value = tempfeed
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












    //TODO: Search Widget
    private val searchWidget = SearchWidget();
    val searchWidgetState: State<SearchWidget.SearchWidgetState>
        get() = searchWidget.searchWidgetState

    val searchTextState: State<String>
        get() = searchWidget.searchTextState

    fun updateSearchWidgetState(newValue: SearchWidget.SearchWidgetState){
        searchWidget.updateSearchWidgetState(newValue);
    }

    fun updateSearchTextState(newValue: String){
        searchWidget.updateSearchTextState(newValue);
    }




}
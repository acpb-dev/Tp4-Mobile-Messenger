package com.example.messenger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.*
import com.example.messenger.utils.SearchWidget
import kotlinx.coroutines.launch

class ViewModel(private val api: SocialNetworkApi) : ViewModel() {
    val searchedUser = mutableStateOf(Users())
    val currentUser = mutableStateOf(UsersItem())
    val myUser = mutableStateOf(UsersItem())
    val userList = mutableStateOf(Users())
    val feed = mutableStateOf(FeedList())
    val userFeed = mutableStateOf(FeedList())
    var isAuthenticated = mutableStateOf(false)
    var usernameStored = mutableStateOf("")
    val getEmail: String
        get() = usernameStored.value
    private var recentUsers = mutableStateOf(mutableListOf<UsersItem>())

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

    fun clearRecent() {
        recentUsers.value.clear()
    }

    fun signUp(email: String, password: String, firstname: String, lastname: String){
        viewModelScope.launch {
            isAuthenticated.value = api.signUp(email = email, password = password, firstname = firstname, lastname = lastname)
        }
    }

    fun signIn(email: String, password: String) {
        println("$email $password \n\n\n\n\n")
        viewModelScope.launch {
            isAuthenticated.value = api.signIn(email, password)
            //usernameStored.value = api.getStoredEmail()
        }
    }

    fun signInAuto(){
        val email = api.getStoredEmail()
        val password = api.getStoredPassword()
        if (password.isNotEmpty() && email.isNotEmpty()){
            viewModelScope.launch {
                val response = api.signIn(email, password)
                isAuthenticated.value = response
                println("$response\n\n\n\n\n")
            }
        }

    }

    fun clearSavedCredentials(){
        api.clearStoredCredentials()
    }

    fun getFeed() {
        viewModelScope.launch {
            val response = api.getFeed()
            if (response != null) {
                feed.value = response
            }
        }
    }

    fun getUserPosts(userId: String) {
        println(userId)
        viewModelScope.launch {
            val response = api.getUserPosts(userId)
            if (response != null) {
                userFeed.value = response
            }
        }
    }

    fun updateProfile(fName: String, lName: String, imgLink: String) {
        val updated = UpdateProfile(
            firstname = fName.trim(),
            lastname = lName.trim(),
            profileImageUrl = imgLink.trim()
        )
        viewModelScope.launch {
            val response = api.updateProfile(updated)
            if (response) {
                getAllUsers(true)
            }
            println("WAS SUCCESSFUL $response")
        }

    }

    fun addFriend(id: String) {
        viewModelScope.launch {
            val response = api.addFriend(id)
        }
    }

    fun postFeed(body: PostInfo) {
        viewModelScope.launch {
            api.postToFeed(body)
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

    private fun getUserByName(search: String) {
        viewModelScope.launch {
            val response = api.getUsers(search)
            if (response != null) {
                searchedUser.value = response
            }
        }
    }

    fun getFriendsList(friendList: List<String>): MutableList<String> {
        val list = mutableListOf<String>()
        userList.value.forEach { user ->
            friendList.forEach { friend ->
                if (user.id == friend) {
                    list.add(friend)
                }
            }
        }
        return list
    }

    fun getUserById(uid: String): UsersItem {
        userList.value.forEach { user ->
            if (user.id == uid) {
                return user
            }
        }
        return UsersItem()
    }

    private val searchWidget = SearchWidget()
    val searchWidgetState: State<SearchWidget.SearchWidgetState>
        get() = searchWidget.searchWidgetState

    val searchTextState: State<String>
        get() = searchWidget.searchTextState

    fun updateSearchWidgetState(newValue: SearchWidget.SearchWidgetState) {
        searchWidget.updateSearchWidgetState(newValue)
    }

    fun updateSearchTextState(newValue: String) {
        val trimmedValue = newValue.trim()
        if (trimmedValue != "") {
            getUserByName(trimmedValue)
        } else {
            searchedUser.value = Users()
        }
        searchWidget.updateSearchTextState(newValue)
    }
}
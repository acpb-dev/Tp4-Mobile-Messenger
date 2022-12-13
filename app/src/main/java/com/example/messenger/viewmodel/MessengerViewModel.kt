package com.example.messenger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.api.SocialNetworkApi
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.Messages
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedList
import kotlinx.coroutines.launch

class MessengerViewModel(private val api: SocialNetworkApi): ViewModel() {
    private var currentUserSelected: String = "";
    private val login = Login();
    private val convoListClass = ConvoList();
    private val convoClass = Convo();



    var currentUser = mutableStateOf("")
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

    fun getUsers(){
        viewModelScope.launch {
            val tempfeed = api.getUsers(usernameStored.value, passwordStored.value)
            if (tempfeed != null){
                userList.value = tempfeed
                userList.value.forEach{
                    if (it.isCurrentUser){
                        currentUser.value = it.id
                    }
                }
            }
        }
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
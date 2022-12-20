package com.example.messenger.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.api.SocialNetworkApi
import com.example.messenger.api.data.Users
import com.example.messenger.utils.SearchWidget
import kotlinx.coroutines.launch

class SearchViewModel (private val api: SocialNetworkApi, sharedViewModel: SharedViewModel) : ViewModel() {

    val sharedViewModel = sharedViewModel

    val searchedUser = mutableStateOf(Users())

    private val searchWidget = SearchWidget()
    val searchWidgetState: State<SearchWidget.SearchWidgetState>
        get() = searchWidget.searchWidgetState

    val searchTextState: State<String>
        get() = searchWidget.searchTextState

    fun updateSearchWidgetState(newValue: SearchWidget.SearchWidgetState) {
        searchWidget.updateSearchWidgetState(newValue)
    }

    private fun getUserByName(search: String) {
        viewModelScope.launch {
            val response = api.getUsers(search)
            if (response != null) {
                //print("$response\n\n\n\n")
                searchedUser.value = response
                println(searchedUser.value)
            }
        }
    }

    fun updateSearchTextState(newValue: String) {
        val trimmedValue = newValue.trim()
        if (trimmedValue != "") {
            println("$trimmedValue\n\n\n")
            getUserByName(trimmedValue)
        } else {
            searchedUser.value = Users()
        }
        searchWidget.updateSearchTextState(newValue)
    }
}
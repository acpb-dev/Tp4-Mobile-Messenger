package com.example.messenger.composable.searchUsers

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.messenger.viewmodel.MessengerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(navController: NavController, messengerViewModel: MessengerViewModel) {
    val usersFound by remember { messengerViewModel.searchedUser }

    androidx.compose.material3.Scaffold(topBar = {
        searchTopBar(messengerViewModel = messengerViewModel, navController)
    },
        content = { content ->
            searchResults(navController = navController, messengerViewModel = messengerViewModel, usersFound = usersFound)
        })
}
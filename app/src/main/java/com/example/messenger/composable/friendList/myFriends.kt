package com.example.messenger.composable.friendList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.MessengerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myFriends(navController: NavController, messengerViewModel: MessengerViewModel) {

    val friends by remember { messengerViewModel.myFriends }

    Scaffold(topBar = {
        DefaultAppBar(navController, "Friends List")
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                myFriendsComponent(navController = navController, messengerViewModel = messengerViewModel, friends)
            }
        })
}





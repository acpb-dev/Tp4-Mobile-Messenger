package com.example.messenger.composable.friendList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodels.FriendListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController, friendListViewModel: FriendListViewModel) {
    val myF = friendListViewModel.getFriendsList(friendListViewModel.sharedViewModel.myUser.value.friends)

    Scaffold(topBar = {
        DefaultAppBar("Friends List", Icons.Filled.ArrowBack) { navController.popBackStack() }
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                FriendsComponent(navController = navController, friendListViewModel = friendListViewModel, myF)
            }
        })
}





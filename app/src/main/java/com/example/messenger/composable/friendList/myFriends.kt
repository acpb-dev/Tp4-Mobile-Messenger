package com.example.messenger.composable.friendList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myFriends(navController: NavController, viewModel: ViewModel) {
    val myF = viewModel.getFriendsList(viewModel.myUser.value.friends)

    Scaffold(topBar = {
        DefaultAppBar("Friends List") { navController.popBackStack() }
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                myFriendsComponent(navController = navController, viewModel = viewModel, myF)
            }
        })
}





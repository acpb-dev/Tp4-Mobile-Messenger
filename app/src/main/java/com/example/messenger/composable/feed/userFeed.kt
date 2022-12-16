package com.example.messenger.composable.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
fun userFeed(navController: NavController, messengerViewModel: MessengerViewModel) {

    val userFeed by remember { messengerViewModel.userFeed }
    val users by remember { messengerViewModel.userList }
    Column(Modifier.fillMaxSize().background(Color.Black)) {
    Scaffold(topBar = {
        DefaultAppBar("Feed") { navController.popBackStack() }
    },
        content = { padding ->
            feedComponent(
                feedList = userFeed,
                user = users,
                messengerViewModel = messengerViewModel,
                canType = false
            )
        })
    }
}
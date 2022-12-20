package com.example.messenger.composable.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodels.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFeedScreen(navController: NavController, feedViewModel: FeedViewModel) {

    val userFeed by remember { feedViewModel.sharedViewModel.userFeed }
    val users by remember { feedViewModel.sharedViewModel.userList }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        Scaffold(topBar = {
            DefaultAppBar("Feed", Icons.Filled.ArrowBack) { navController.popBackStack() }
        },
            content = { padding ->
                FeedComponent(
                    navController = navController,
                    feedList = userFeed,
                    user = users,
                    feedViewModel = feedViewModel,
                    canType = false
                )
            })
    }
}
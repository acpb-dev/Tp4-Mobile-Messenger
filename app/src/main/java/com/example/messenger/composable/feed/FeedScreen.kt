package com.example.messenger.composable.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodels.FeedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController, feedViewModel: FeedViewModel) {
    LaunchedEffect(true) {
        var i = 0;
        while (i < 73) {
            feedViewModel.getFeed()
            delay(2500)
            i++
        }
        navController.navigate(Routes.FeedScreen.route) { popUpTo(Routes.FeedScreen.route) { inclusive = true }}
    }

    val feed by remember { feedViewModel.feed }
    val users by remember { feedViewModel.sharedViewModel.userList }

    Scaffold(topBar = {
        DefaultAppBar("Feed", Icons.Filled.ArrowBack) { navController.popBackStack() }
    },
        content = {
            FeedComponent(
                navController = navController,
                feedList = feed,
                user = users,
                feedViewModel = feedViewModel,
                canType = true
            )
        })
}

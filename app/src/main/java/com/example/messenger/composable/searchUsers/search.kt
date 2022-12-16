package com.example.messenger.composable.searchUsers

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.messenger.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(navController: NavController, viewModel: ViewModel) {
    val usersFound by remember { viewModel.searchedUser }

    androidx.compose.material3.Scaffold(topBar = {
        searchTopBar(viewModel = viewModel, navController)
    },
        content = { content ->
            searchResults(navController = navController, viewModel = viewModel, usersFound = usersFound)
        })
}
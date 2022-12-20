package com.example.messenger.composable.searchUsers

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.messenger.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel) {

    val usersFound by searchViewModel.searchedUser // IDK why it doesn't require the "remember { xxx }" but it's works...

    println("\n\n\n\n\n\n\n\n\n\n\nn$usersFound")
    androidx.compose.material3.Scaffold(topBar = {
        searchTopBar(searchViewModel = searchViewModel, navController)
    },
        content = { content ->
            searchResults(
                navController = navController,
                searchViewModel = searchViewModel,
                usersFound = usersFound
            )
        })
}
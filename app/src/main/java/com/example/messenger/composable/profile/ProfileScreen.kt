package com.example.messenger.composable.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
import com.example.messenger.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profileScreen(navController: NavController, profileViewModel: ProfileViewModel) {

    val userViewing by remember { profileViewModel.sharedViewModel.currentUser }

    val currentUser = profileViewModel.getCurrentUser(userViewing)
    profileViewModel.getUserPosts(currentUser.id)
    profileViewModel.sharedViewModel.addRecent(currentUser)

    fun back() {
        profileViewModel.sharedViewModel.removeRecent()
        navController.popBackStack()
    }

    Scaffold(topBar = {
        DefaultAppBar("Profile", Icons.Filled.ArrowBack) { back() }
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                ProfileComponent(navController, profileViewModel, currentUser)
            }
        })
}






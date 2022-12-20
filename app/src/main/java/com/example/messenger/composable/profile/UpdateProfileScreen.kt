package com.example.messenger.composable.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodels.UpdateProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavController, updateProfileViewModel: UpdateProfileViewModel) {
    Scaffold(topBar = {
        DefaultAppBar("Update Profile", Icons.Filled.ArrowBack) { navController.popBackStack() }
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                UpdateProfileComponent(navController, updateProfileViewModel)
            }
        })
}
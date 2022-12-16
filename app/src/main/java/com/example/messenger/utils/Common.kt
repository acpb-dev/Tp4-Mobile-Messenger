package com.example.messenger.utils

import androidx.navigation.NavController
import com.example.messenger.api.data.UsersItem
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.ViewModel


fun navToProfile(navController: NavController, viewModel: ViewModel, user: UsersItem) {
    viewModel.currentUser.value = user
    navController.navigate(Routes.ProfileScreen.route)
}

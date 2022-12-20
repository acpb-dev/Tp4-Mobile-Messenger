package com.example.messenger.utils

import androidx.navigation.NavController
import com.example.messenger.api.data.UsersItem
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodels.SharedViewModel


fun navToProfile(navController: NavController, sharedViewModel: SharedViewModel, user :UsersItem) {
    sharedViewModel.currentUser.value = user
    sharedViewModel.addRecent(user)
    navController.navigate(Routes.ProfileScreen.route)
}

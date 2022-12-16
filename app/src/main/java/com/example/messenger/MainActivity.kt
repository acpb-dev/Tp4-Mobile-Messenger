package com.example.messenger

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messenger.api.SocialNetworkApiImpl
import com.example.messenger.composable.feed.FeedScreen
import com.example.messenger.composable.feed.UserFeedScreen
import com.example.messenger.composable.friendList.FriendsScreen
import com.example.messenger.composable.login.LoginScreen
import com.example.messenger.composable.menu.MenuScreen
import com.example.messenger.composable.profile.profileScreen
import com.example.messenger.composable.profile.UpdateProfileScreen
import com.example.messenger.composable.searchUsers.SearchScreen
import com.example.messenger.composable.register.SignUp
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.ViewModel

class MainActivity : ComponentActivity() {
    private var sharedPreferences: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("login_information", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        val socialNetworkApi = SocialNetworkApiImpl(sharedPreferences!!)
        val viewModel = ViewModel(socialNetworkApi)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen(viewModel: ViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LoginScreen.route) {
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(navController, viewModel)
        }

        composable(route = Routes.RegisterScreen.route) {
            SignUp(navController, viewModel)
        }

        composable(route = Routes.Menu.route) {
            viewModel.getAllUsers(true)
            MenuScreen(navController, viewModel)
        }

        composable(route = Routes.Feed.route) {
            FeedScreen(navController, viewModel)
        }

        composable(route = Routes.UserFeed.route) {
            UserFeedScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.FriendList.route) {
            viewModel.clearRecent()
            FriendsScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.MyProfile.route) {
            profileScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.SearchFriend.route) {
            SearchScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.UpdateProfile.route) {
            UpdateProfileScreen(navController = navController, viewModel = viewModel)
        }

    }
}


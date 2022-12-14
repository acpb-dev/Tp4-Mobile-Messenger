package com.example.messenger.utils.const

sealed class Routes(val route: String) {
    object LoginScreen: Routes("login")
    object Menu: Routes("menu")
    object Feed: Routes("feed")
    object FriendList: Routes("friendList")
    object MyProfile: Routes("myProfile")
    object SearchFriend: Routes("searchFriends")
}

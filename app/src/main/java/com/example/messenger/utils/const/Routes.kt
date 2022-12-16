package com.example.messenger.utils.const

sealed class Routes(val route: String) {
    object LoginScreen : Routes("login")
    object RegisterScreen : Routes("register")
    object Menu : Routes("menu")
    object Feed : Routes("feed")
    object UserFeed : Routes("userFeed")
    object FriendList : Routes("friendList")
    object MyProfile : Routes("myProfile")
    object SearchFriend : Routes("searchFriends")
    object UpdateProfile : Routes("updateProfile")
}

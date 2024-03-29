package com.example.messenger.composable.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.api.data.Users
import com.example.messenger.api.data.UsersItem
import com.example.messenger.utils.const.Routes
import com.example.messenger.utils.navToProfile
import com.example.messenger.viewmodels.ProfileViewModel

@Composable
fun ProfileComponent(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    currentUser: UsersItem
) {

    val myUser by remember { profileViewModel.sharedViewModel.myUser }

    val friends: MutableList<String> = mutableListOf()
    currentUser.friends.forEach {
        friends.add(it)
    }

    val friendList = getFriendsProfile(profileViewModel.sharedViewModel.userList.value, friends)


    fun getImage(img: String): String {
        if (img.trim() == "") {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/1200px-Default_pfp.svg.png"
        }
        return img
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(Modifier.clickable(onClick = { navController.navigate(Routes.UpdateProfileScreen.route) })) {
                        Image(
                            painter = rememberAsyncImagePainter(getImage(currentUser.profileImgUrl)),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                    }
                    Row {
                        Row(
                            Modifier
                                .padding(top = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Transparent)
                                    .padding(10.dp)
                            ) {
                                Row(Modifier.align(Alignment.Center)) {
                                    Column(Modifier.padding(5.dp)) {
                                        if (currentUser.isCurrentUser) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(Color.DarkGray)
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    text = checkNull(profileViewModel.getEmail),
                                                    fontSize = 18.sp,
                                                    color = Color.White,
                                                    textAlign = TextAlign.Right
                                                )
                                            }
                                        } else {
                                            if (myUser.friends.contains(currentUser.id)) {
                                                Button(
                                                    onClick = { },
                                                    shape = CutCornerShape(10),
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Color.Red
                                                    )
                                                ) {
                                                    Text(
                                                        text = "Remove Friend",
                                                        color = Color.White
                                                    )
                                                }
                                            } else {
                                                Button(
                                                    onClick = {
                                                        profileViewModel.addFriend(currentUser.id)
                                                        profileViewModel.sharedViewModel.getAllUsers(false)
                                                        navController.navigate(Routes.ProfileScreen.route) { popUpTo(Routes.ProfileScreen.route) { inclusive = true } }
                                                    },
                                                    shape = CutCornerShape(10),
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Color.Green
                                                    )
                                                ) {
                                                    Text(text = "Add Friend", color = Color.White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        Modifier
                            .padding(top = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.DarkGray)
                                .padding(1.dp)
                        ) {
                            Row(Modifier.align(Alignment.Center)) {
                                Column(Modifier.padding(5.dp)) {
                                    Text(
                                        text = checkNull(currentUser.firstname) + " " + checkNull(
                                            currentUser.lastname
                                        ),
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Right
                                    )
                                }
                            }
                        }
                    }


                    Row(
                        Modifier
                            .padding(top = 20.dp)
                            .clickable(onClick = {
                                seePosts(
                                    navController,
                                    profileViewModel,
                                    currentUser.id
                                )
                            })
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colors.primaryVariant)
                                .padding(1.dp)
                        ) {
                            Row(Modifier.align(Alignment.Center)) {
                                Column(Modifier.padding(5.dp)) {
                                    Text(
                                        text = "See Post History",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Right
                                    )
                                }
                            }
                        }
                    }


                }
            }
            Divider(color = Color.White, thickness = 1.dp, startIndent = 0.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier
                            .padding(top = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.DarkGray)
                                .padding(1.dp)
                        ) {
                            Row(Modifier.align(Alignment.Center)) {
                                Column(Modifier.padding(5.dp)) {
                                    Text(
                                        text = "Friend List: ",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn(
                        reverseLayout = false
                    ) {
                        itemsIndexed(friendList) { index, user ->
                            Row(
                                Modifier
                                    .padding(20.dp)
                                    .clickable(onClick = {
                                        navToProfile(
                                            navController,
                                            profileViewModel.sharedViewModel,
                                            user
                                        )
                                    }),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(Modifier.padding(end = 5.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(getImage(user.profileImgUrl)),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(end = 5.dp)
                                            .clip(CircleShape)
                                    )
                                }
                                Column {
                                    Text(
                                        text = user.firstname + " " + user.lastname,
                                        color = Color.White
                                    )
                                }
                            }

                            if (index < friendList.lastIndex) {
                                Divider(color = Color.White, thickness = 1.dp, startIndent = 20.dp)
                            }
                        }
                    }

                }
            }
        }
    }
}

fun seePosts(navController: NavController, profileViewModel: ProfileViewModel, userId: String) {
    profileViewModel.getUserPosts(userId)
    navController.navigate(Routes.UserFeedScreen.route)
}

fun getFriendsProfile(users: Users, uids: MutableList<String>): List<UsersItem> {
    val list = mutableListOf<UsersItem>()
    users.forEach { user ->
        uids.forEach { friend ->
            if (user.id == friend) {
                list.add(user)
            }
        }
    }
    return list
}

fun checkNull(entry: String?): String {
    if (entry != null) {
        return entry
    }
    return ""
}


package com.example.messenger.composable.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.composable.topBar
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.UsersItem

@Composable
fun profileScreen(navController: NavController, messengerViewModel: MessengerViewModel) {

    val userViewing by remember { messengerViewModel.currentUser }

    var currentUser = getCurrentUser(messengerViewModel.userList.value, userViewing)
    var friends: MutableList<String> = mutableListOf()
    currentUser?.friends?.forEach{
        friends.add(it)
    }

    var tt = getFriendsProfile(messengerViewModel.userList.value, friends)

    var image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/1200px-Default_pfp.svg.png";
    if(currentUser?.profileImgUrl != ""){
        image = currentUser!!.profileImgUrl
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Column(modifier =Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Row(Modifier.padding(top = 10.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(image),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                    Row() {
                        Row(
                            Modifier
                                .clickable(onClick = {})
                                .padding(top = 3.dp)){
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Transparent)
                                    .padding(1.dp)
                            ){
                                Row(Modifier.align(Alignment.Center)) {
                                    Column(Modifier.padding(5.dp)) {
                                        if (currentUser.isCurrentUser) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(DarkGray)
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    text = checkNull(messengerViewModel.getEmail),
                                                    fontSize = 18.sp,
                                                    color = White,
                                                    textAlign = TextAlign.Right
                                                )
                                            }
                                        } else {
                                            if (messengerViewModel.myFriends.value.contains(currentUser.id)) {
                                                Button(
                                                    onClick = {  },
                                                    shape = CutCornerShape(10),
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Red
                                                    )
                                                ) {
                                                    Text(text = "Remove Friend", color = White)
                                                }
                                            } else {
                                                Button(
                                                    onClick = { messengerViewModel.addFriend(currentUser.id) },
                                                    shape = CutCornerShape(10),
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Green
                                                    )
                                                ) {
                                                    Text(text = "Add Friend", color = White)
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
                            .padding(top = 3.dp)){
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.DarkGray)
                                .padding(1.dp)
                        ){
                            Row(Modifier.align(Alignment.Center)) {
                                Column(Modifier.padding(5.dp)) {
                                    Text(text = checkNull(currentUser?.firstname) + " " + checkNull(currentUser?.lastname), fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Right)
                                }
                            }
                        }
                    }


                }
            }
            Divider(color = White, thickness = 1.dp, startIndent = 0.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.TopCenter
            ){
                Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier
                            .padding(top = 3.dp)){
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkGray)
                                .padding(1.dp)
                        ){
                            Row(Modifier.align(Alignment.Center)) {
                                Column(Modifier.padding(5.dp)) {
                                    Text(text = "Friend List: ", fontSize = 18.sp, color = White, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                    LazyColumn(
                        reverseLayout = false
                    ) {
                        itemsIndexed(tt) { index, user ->
                            // here
                            Row(
                                Modifier
                                    .padding(20.dp)
                                    .clickable(onClick = {
                                        goTo(
                                            navController,
                                            messengerViewModel,
                                            user
                                        )
                                    }),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.padding(end = 5.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(image),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(end = 5.dp)
                                    )
                                }
                                Column() {
                                    Text(text = user.firstname + " " + user.lastname, color = White)
                                }
                            }

                            if (index < tt.lastIndex){
                                Divider(color = White, thickness = 1.dp, startIndent = 20.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun goTo(navController: NavController, messengerViewModel: MessengerViewModel, user: UsersItem){
    // messengerViewModel.userSelected.value = messengerViewModel.currentUser.value
    messengerViewModel.currentUser.value = user
    navController.navigate(Routes.MyProfile.route)
}

fun getFriendsProfile(users: Users, uids: MutableList<String>): List<UsersItem>{
    var list = mutableListOf<UsersItem>()
    users.forEach{ user ->
        uids.forEach{ friend ->
            if(user.id == friend){
                list.add(user)
            }
        }
    }
    return list
}

fun checkNull(entry: String?): String{
    if (entry != null){
        return entry;
    }
    return ""
}

fun getCurrentUser(userList: Users, user: UsersItem): UsersItem? {
    userList.forEach{
        if (it.id == user.id){
            return it
        }
    }
    return null
}
package com.example.messenger.composable.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.UsersItem

@Composable
fun Menu(navController: NavController, messengerViewModel: MessengerViewModel) {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row() {
            Image(
                painter = rememberAsyncImagePainter("https://icons.iconarchive.com/icons/igh0zt/ios7-style-metro-ui/512/MetroUI-Apps-Messaging-Alt-icon.png"),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }
        Row(Modifier.padding(top = 50.dp).clickable(onClick = { goTo(navController, messengerViewModel, messengerViewModel.currentUser.value)})){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .padding(10.dp)

            ){
                Row(Modifier.align(Alignment.Center)) {
                    Column(Modifier.padding(5.dp)) {
                        Text(text = "My Profile", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Right)
                    }

                }
            }
        }
        Row(Modifier.clickable(onClick = {navController.navigate(Routes.Feed.route)})){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .padding(10.dp)

            ){
                Row(Modifier.align(Alignment.Center)) {
                    Column(Modifier.padding(5.dp)) {
                        Text(text = "My Feed", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Right)
                    }

                }
            }
        }
        Row(Modifier.clickable(onClick = {navController.navigate(Routes.FriendList.route)})){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .padding(10.dp)
            ){
                Row(Modifier.align(Alignment.Center)) {
                    Column(Modifier.padding(5.dp)) {
                        Text(text = "My Friends", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Right)
                    }

                }
            }
        }
    }
}

fun goTo(navController: NavController, messengerViewModel: MessengerViewModel, user: UsersItem){
    messengerViewModel.currentUser.value = user
    navController.navigate(Routes.MyProfile.route)
}
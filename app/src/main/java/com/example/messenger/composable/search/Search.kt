package com.example.messenger.composable.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.UsersItem

@Composable
fun Search(navController: NavController, messengerViewModel: MessengerViewModel) {
    val usersFound by remember { messengerViewModel.searchedUser }
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ){
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                        .padding(1.dp)
                        .fillMaxWidth()
                ){
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                        TextField(
                            value = search,
                            onValueChange = { search = it; search(search, messengerViewModel) },
                            label = { Text("") },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent, //hide the indicator
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    }
                }


                LazyColumn(
                    reverseLayout = false
                ) {
                    itemsIndexed(usersFound) { index, user ->
                        var image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/1200px-Default_pfp.svg.png"
                        if (!user.isCurrentUser){
                            if(user.profileImgUrl != ""){
                                image = user.profileImgUrl
                            }
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
                                Column {
                                    Text(text = user.firstname + " " + user.lastname, color = Color.White)
                                }
                            }

                            if (index < usersFound.lastIndex){
                                Divider(color = Color.White, thickness = 1.dp, startIndent = 20.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun search(searchString: String, messengerViewModel: MessengerViewModel){
    if (searchString.trim() != ""){
        messengerViewModel.getUserByName(searchString)
    }
}

fun goTo(navController: NavController, messengerViewModel: MessengerViewModel, user: UsersItem){
    messengerViewModel.currentUser.value = user
    navController.navigate(Routes.MyProfile.route)
}
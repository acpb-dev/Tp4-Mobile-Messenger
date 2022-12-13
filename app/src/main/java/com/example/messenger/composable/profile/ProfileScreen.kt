package com.example.messenger.composable.profile

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.UsersItem

@Composable
fun profileScreen(messengerViewModel: MessengerViewModel) {
    val userViewing by remember { messengerViewModel.userSelected }

    var currentUser = getCurrentUser(messengerViewModel.userList.value, userViewing)
//    LaunchedEffect(userViewing) {
//
//    }


    var image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/1200px-Default_pfp.svg.png";
    if(currentUser?.profileImgUrl != ""){
        image = currentUser!!.profileImgUrl
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(Modifier.padding(top = 10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
        
        if (currentUser.isCurrentUser){
            Row(
                Modifier
                    .clickable(onClick = {})
                    .padding(top = 3.dp)){
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                        .padding(1.dp)
                ){
                    Row(Modifier.align(Alignment.Center)) {
                        Column(Modifier.padding(5.dp)) {
                            Text(text = checkNull(messengerViewModel.getEmail), fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Right)
                        }
                    }
                }
            }
        }else{
            Button(onClick = {}, shape = CutCornerShape(10), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                Text(text = "Add Friend")
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



        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
                            Text(text = "Friend List: ", fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Start)
                        }
                    }
                }
            }
            var tt = getFriendsProfile(messengerViewModel.userList.value)
            LazyColumn(
                reverseLayout = false
            ) {
                itemsIndexed(tt) { index, user ->
                    // here
                    Row(Modifier.padding(20.dp).clickable(onClick = {}), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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

fun getFriendsProfile(users: Users): List<UsersItem>{
    var list = mutableListOf<UsersItem>()
    users.forEach{
        if (!it.isCurrentUser){
            print(it.firstname + "\n")
            list.add(it);
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

fun getCurrentUser(userList: Users, uid: String): UsersItem? {
    userList.forEach{
        println(uid)
        println(it.id + "\n\n\n")
        if (it.id == uid){
            return it
        }
    }
    return null
}
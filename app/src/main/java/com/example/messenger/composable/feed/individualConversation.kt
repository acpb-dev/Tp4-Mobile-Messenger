package com.example.messenger.composable.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.composable.menu.isOnline
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.SearchWidget
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedItem
import com.example.messenger.viewmodel.data.feedList
import java.time.LocalDateTime
import androidx.compose.material3.Button as Button1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun individualConversation(navController: NavController, messengerViewModel: MessengerViewModel) {
    // messengerViewModel.getMessages();
    messengerViewModel.feed()
    messengerViewModel.getUsers();

    val feed by remember { messengerViewModel.feed }
    val users by remember { messengerViewModel.userList }
    print(messengerViewModel.feed);

//    val convo = remember { messengerViewModel.convosMessages }


    Scaffold(topBar = {
        MainAppBar(
            onSearchTriggered = {
                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.OPENED)
            }
        )
    },
        content = { padding ->
            Row(Modifier.background(Black)) {
                MessageList(
                    feedList = feed,
                    user = users,
                    messengerViewModel = messengerViewModel
                )
            }
        })
}

fun getName(uid: String, users: Users): String{
    users.forEach{
        print(uid + "\n" + it.id + "\n\n")
        if (uid == it.id){
            return it.firstname + " " + it.lastname
        }
    }
    return "Deleted User"
}


@Composable
fun MessageList(feedList: feedList, user: Users, messengerViewModel: MessengerViewModel) {
    val reverse: List<feedItem> = feedList.reversed();
    Box(
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            reverseLayout = true
        ) {
            itemsIndexed(feedList) { index, message ->
                if(index == 0){
                    MessageInput(messengerViewModel)
                }
                MessageCard1(message, user)
            }
        }
    }
}

@Composable
fun MessageCard1(feedPost: feedItem, user: Users) {
    val localDateTime = LocalDateTime.parse(feedPost.createdAt)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when { // 2
            false -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor1(feedPost), // 3
            backgroundColor = when {
                false -> MaterialTheme.colors.primary
                else -> DarkGray
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = feedPost.text,
                color = when {
                    false -> MaterialTheme.colors.onPrimary
                    else -> White
                },
            )
        }
        Text( // 4
            text = when {
                false -> "Me"
                else -> getName(feedPost.user, user)
            },
            color = White,
            fontSize = 12.sp,
        )
        Text( // 4
            text = formatTime(localDateTime),
            color = White,
            fontSize = 8.sp,
        )
    }
}

fun formatTime(time: LocalDateTime): String{
    return time.hour.toString() + ":" + time.minute.toString().padStart(2, '0') + " " + time.dayOfMonth.toString() + " " + time.month.toString() + " " + time.year
}

@Composable
fun cardShapeFor1(messageItem: feedItem): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        false -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageInput(messengerViewModel: MessengerViewModel) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() {
        messengerViewModel.pushMessage(inputValue);
        inputValue = ""
    }
    Row {
        TextField( // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = DarkGray,
                focusedIndicatorColor =  Color.Transparent,
                unfocusedIndicatorColor = DarkGray),
            onValueChange = { inputValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        Button1( // 5
            modifier = Modifier.height(56.dp).background(MaterialTheme.colors.primary),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon( // 6
                imageVector = Icons.Default.Send,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun MainAppBar(
    onSearchTriggered: () -> Unit
) {
    DefaultAppBar1(
        onSearchClicked = onSearchTriggered,
    )
}

@Composable
fun DefaultAppBar1(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Feed", color = Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.discordapp.com/avatars/305770707239829504/a_dab45062e55791a8d483a9f7d7373c66.gif?size=256"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, isOnline(true), CircleShape)
                )
            }
        }
    )
}

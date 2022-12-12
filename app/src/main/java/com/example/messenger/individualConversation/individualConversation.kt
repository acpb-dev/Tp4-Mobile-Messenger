package com.example.messenger.individualConversation

import android.util.Log
import android.widget.ImageButton
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.conversationsScreen.ConversationListComponent
import com.example.messenger.conversationsScreen.isOnline
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.SearchWidget
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.Messages
import androidx.compose.material3.Button as Button1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun individualConversation(navController: NavController, messengerViewModel: MessengerViewModel) {
    messengerViewModel.getMessages();

    val convo = remember { mutableStateOf(messengerViewModel.convosMessages) }

    Scaffold(topBar = {
        MainAppBar(
            messengerViewModel = messengerViewModel,
            onSearchTriggered = {
                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.OPENED)
            }
        )
    },
        content = { padding ->
            Row(Modifier.background(Black)) {
                MessageList(
                    messages = convo.value,
                    user = messengerViewModel.getCurrentUser(),
                    messengerViewModel = messengerViewModel
                )
            }
        })
}


@Composable
fun MessageList(messages: MutableList<Messages>, user: ConvoListData, messengerViewModel: MessengerViewModel) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            reverseLayout = true
        ) {
            itemsIndexed(messages) { index, message ->
                if(index == 0){
                    MessageInput(messengerViewModel, user)
                }
                MessageCard1(message, user)
            }
        }
    }
}

@Composable
fun MessageCard1(messageItem: Messages, user: ConvoListData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when { // 2
            messageItem.isMine -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor1(messageItem), // 3
            backgroundColor = when {
                messageItem.isMine -> MaterialTheme.colors.primary
                else -> DarkGray
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.messages,
                color = when {
                    messageItem.isMine -> MaterialTheme.colors.onPrimary
                    else -> White
                },
            )
        }
        Text( // 4
            text = when {
                messageItem.isMine -> "Me"
                else -> user.user.firstName},
            color = White,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun cardShapeFor1(messageItem: Messages): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        messageItem.isMine -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageInput(messengerViewModel: MessengerViewModel, user: ConvoListData) {
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
    messengerViewModel: MessengerViewModel,
    onSearchTriggered: () -> Unit
) {
    val user = messengerViewModel.getCurrentUser();
    DefaultAppBar1(
        messengerViewModel = messengerViewModel,
        onSearchClicked = onSearchTriggered,
        user = user,
    )
}

@Composable
fun DefaultAppBar1(messengerViewModel: MessengerViewModel, onSearchClicked: () -> Unit, user : ConvoListData) {
    TopAppBar(
        title = {
            Text(
                text = user.user.firstName + " " + user.user.lastName, color = Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.user.imgUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, isOnline(user.user.online), CircleShape)
                )
            }
        }
    )
}

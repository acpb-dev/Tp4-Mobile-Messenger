package com.example.messenger.composable.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.SearchWidget
import com.example.messenger.viewmodel.data.UsersItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsScreen(navController: NavController, messengerViewModel: MessengerViewModel) {
    val searchWidgetState by messengerViewModel.searchWidgetState
    val searchTextState by messengerViewModel.searchTextState

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




//    Scaffold(topBar = {
//        MainAppBar(
//            searchWidgetState = searchWidgetState,
//            searchTextState = searchTextState,
//            onTextChange = {
//                messengerViewModel.updateSearchTextState(newValue = it)
//
//            },
//            onCloseClicked = {
//                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.CLOSED)
//            },
//            onSearchClicked = {
//                Log.d("Searched Text", it)
//            },
//            onSearchTriggered = {
//                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.OPENED)
//            }
//        )
//    },
//        content = { padding ->
//            Column(modifier = Modifier.padding(padding)) {
//                ConversationListComponent(
//                    convoList = convosList.value,
//                    itemOnClick = navigateToConvoSelected
//                )
//            }
//        })
}

fun goTo(navController: NavController, messengerViewModel: MessengerViewModel, uid: UsersItem){
    // messengerViewModel.userSelected.value = messengerViewModel.currentUser.value
    messengerViewModel.userSelected.value = uid.id
    navController.navigate(Routes.MyProfile.route)
}

@Composable
fun MainAppBar(
    searchWidgetState: SearchWidget.SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidget.SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidget.SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Messenger", color = Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.DarkGray
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.LightGray
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                textColor = Color.White,
            ))
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}
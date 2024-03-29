package com.example.messenger.composable.searchUsers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.api.data.Users
import com.example.messenger.utils.navToProfile
import com.example.messenger.viewmodels.SearchViewModel

@Composable
fun SearchComponent(
    navController: NavController,
    searchViewModel: SearchViewModel,
    usersFound: Users
) {
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
        ) {
            LazyColumn(
                reverseLayout = false
            ) {
                itemsIndexed(usersFound) { index, user ->
                    var image =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/1200px-Default_pfp.svg.png"
                    if (!user.isCurrentUser) {
                        if (user.profileImgUrl != "") {
                            image = user.profileImgUrl
                        }
                        Row(
                            Modifier
                                .padding(20.dp)
                                .clickable(onClick = {
                                    navToProfile(
                                        navController,
                                        searchViewModel.sharedViewModel,
                                        user
                                    )
                                    //TODO HERE
                                }),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.padding(end = 5.dp)) {
                                Image(
                                    painter = rememberAsyncImagePainter(image),
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

                        if (index < usersFound.lastIndex) {
                            Divider(color = Color.White, thickness = 1.dp, startIndent = 20.dp)
                        }
                    }
                }
            }
        }
    }
}



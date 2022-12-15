package com.example.messenger.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun DefaultAppBar(navController: NavController, tabName: String) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { navController.popBackStack() })) {
                Icon(Icons.Filled.ArrowBack, "", tint = Color.White)
            }
        },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        actions = {
            Column(Modifier.padding(5.dp)) {
                androidx.compose.material3.Text(text = tabName, fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}

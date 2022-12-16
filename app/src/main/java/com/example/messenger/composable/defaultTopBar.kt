package com.example.messenger.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun DefaultAppBar(tabName: String, icon: ImageVector, onClick: ()-> Unit) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { onClick() })) {
                Icon(icon, "", tint = Color.White)
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            Column(Modifier.padding(5.dp)) {
                Text(text = tabName, fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}

package com.example.scoreg.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scoreg.R

@Composable
fun Navbar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF25F396)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NavButton(
            text = "Jogos Completados",
            icon = R.drawable.navbar_icon_1,
            onClick = { navController.navigate("completedGamesPage") }
        )

        NavButton(
            text = "Jogando Agora",
            icon = R.drawable.navbar_icon_2,
            onClick = { navController.navigate("playingNowPage") }
        )

        NavButton(
            text = "Lista de Compras",
            icon = R.drawable.navbar_icon_3,
            onClick = { navController.navigate("wishListPage") }
        )
    }
}

@Composable
fun NavButton(text: String, icon: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$icon",
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

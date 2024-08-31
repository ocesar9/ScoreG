package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.GameButton
import com.example.scoreg.database.entities.Game

@Composable
fun CompleteGamesPage(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Jogos Completados")
        Spacer(modifier = Modifier.height(16.dp))

        // Exemplo de utilização do GameButton
        val exampleGame = Game(
            urlImage = "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg",
            title = "O Witcher lá",
            score = 92
        )

        GameButton(game = exampleGame, onClick = {
            // Ação ao clicar no botão do jogo
        })

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Voltar para Home")
        }
    }
}
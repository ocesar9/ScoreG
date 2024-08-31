package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.GameButton
import com.example.scoreg.database.dbmanipulation.ManipulateGame
import com.example.scoreg.database.entities.Game

@Composable
fun CompleteGamesPage(navController: NavController) {
    val manipulateGame = ManipulateGame()

    // Estado para armazenar a lista de jogos
    var gamesList by remember { mutableStateOf<List<Game>>(emptyList()) }

    // Chama fetchGames e atualiza gamesList
    LaunchedEffect(Unit) {
        manipulateGame.fetchGames { games ->
            gamesList = games
        }
    }

    // Layout da página
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Jogos Completados")
        Spacer(modifier = Modifier.height(16.dp))

        // Itera sobre a lista de jogos e cria um GameButton para cada um
        gamesList.forEach { game ->
            GameButton(game = game, onClick = {
                // Ação ao clicar no botão do jogo
            })
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Voltar para Home")
        }
    }
}

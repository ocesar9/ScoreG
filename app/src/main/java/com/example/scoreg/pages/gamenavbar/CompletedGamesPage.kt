package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.GameButton
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel

@Composable
fun CompletedGamesPage(navController: NavController, mainViewModel: MainViewModel, userListName: String) {
    // Estado para armazenar a lista de jogos
    var userGamesList by remember { mutableStateOf<List<Game>>(emptyList()) }

    // Chama fetchCurrentUserGamesList e atualiza gamesList
    LaunchedEffect(Unit) {
        mainViewModel.fetchCurrentUserGamesList( userListName) { games ->
            if (games != null) {
                userGamesList = games
            }
        }
    }

    // Layout da página com rolagem
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Jogos Completados")
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn para a lista de jogos com rolagem

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)  // Isso faz a coluna ocupar o máximo de altura disponível
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento entre os itens
        ) {
            items(userGamesList) { game ->
                GameButton(game = game, onClick = {
                    // Ação ao clicar no botão do jogo

                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Voltar para Home")
        }
    }
}
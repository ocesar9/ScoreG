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
import com.example.scoreg.components.GameGrid
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel

@Composable
fun CompletedGamesPage(
    navController: NavController,
    mainViewModel: MainViewModel,
    userListName: String,

) {
    // Estado para armazenar a lista de jogos
    mainViewModel.fetchCurrentUserGameList(userListName)

    // Layout da página com rolagem
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Jogos Completados")
        Spacer(modifier = Modifier.height(16.dp))

        GameGrid(
            games = mainViewModel.currentUserCompletedGamesList.value.toList(),
            mainViewModel = mainViewModel,
            navController = navController
        )
    }
}
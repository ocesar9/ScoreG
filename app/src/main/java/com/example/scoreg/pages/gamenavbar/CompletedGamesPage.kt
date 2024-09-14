package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.NavController
import com.example.scoreg.components.CustomTopAppBar
import com.example.scoreg.components.GameGrid
import com.example.scoreg.models.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedGamesPage(
    navController: NavController,
    mainViewModel: MainViewModel,
    userListName: String,
    modifier: Modifier = Modifier

) {
    // Estado para armazenar a lista de jogos
    mainViewModel.fetchCurrentUserGameList(userListName)
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CustomTopAppBar(
            showBackButton = true,
            onBackClick = {
                navController.popBackStack()
            }
        )

        GameGrid(
            title = "Jogos Completados",
            games = mainViewModel.currentUserCompletedGamesList.value.toList(),
            mainViewModel = mainViewModel,
            navController = navController
        )
    }
}
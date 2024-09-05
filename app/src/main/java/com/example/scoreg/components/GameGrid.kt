package com.example.scoreg.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel


@Composable
fun GameGrid(
    games: List<Game>,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
    ) {
        items(games) { game ->
            GameButton(
                game = game,
                onClick = {
                    mainViewModel.setCurrentGame(game)
                    navController.navigate("gameInfoPage")
                }
            )
        }
    }
}
package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.scoreg.components.ActionButtons
import com.example.scoreg.components.CustomTopAppBar
import com.example.scoreg.components.GameListSection
import com.example.scoreg.components.GameView
import com.example.scoreg.models.MainViewModel


@Composable
fun GameInfoPage(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    mainViewModel.fetchAndSortGames()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTopAppBar(
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )

            GameView(game = mainViewModel.currentGame,)

            // Adicionando a Row com os botões abaixo do GameView
            ActionButtons(
                onAddToCompleted = {  },
                onAddToPlaying = {  },
                onAddToWishlist = {  },
            )
        }

        Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()

            ) {
                GameListSection(
                    title = "Mais Populares",
                    games = mainViewModel.gamesSortedByScore.value.toList(),
                    mainViewModel = mainViewModel,
                    navController = navController
                )
            }
        }
}

package com.example.scoreg.pages.gamenavbar

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val activity = LocalContext.current as? Activity
    var resultMessage by remember { mutableStateOf("") }

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

            GameView(game = mainViewModel.currentGame)

            // Adicionando a Row com os botões abaixo do GameView
            ActionButtons(
                onAddToCompleted = {
                    if (activity != null) {
                        mainViewModel.addGameToCurrentUserListWithCheck("completedGames") { result ->
                            resultMessage = result
                            Toast.makeText(activity, resultMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onAddToPlaying = {
                    if (activity != null) {
                        mainViewModel.addGameToCurrentUserListWithCheck("playingNow") { result ->
                            resultMessage = result
                            Toast.makeText(activity, resultMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onAddToWishlist = {
                    if (activity != null) {
                        mainViewModel.addGameToCurrentUserListWithCheck("wishList") { result ->
                            resultMessage = result
                            Toast.makeText(activity, resultMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
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

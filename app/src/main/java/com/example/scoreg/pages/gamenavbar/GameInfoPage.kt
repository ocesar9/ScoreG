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
                    navController.navigate("home")
                }
            )

            GameView(game = mainViewModel.currentGame)

            // Adicionando a Row com os botões abaixo do GameView
            ActionButtons(
                onAddToCompleted = {
                    mainViewModel.addGameToCurrentUserList("completedGames")
                    when (mainViewModel.currentGameList.value) {
                        "completedGames" -> {
                            mainViewModel.removeGameToCurrentUserList("completedGames")
                            Toast.makeText(activity, "Jogo removido da lista 'Jogos Completados'.", Toast.LENGTH_SHORT).show()
                        }
                        "playingNow" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Jogando Agora' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "wishList" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Lista de Desejos' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "" -> {
                            mainViewModel.addGameToCurrentUserList("completedGames")
                            Toast.makeText(activity, "Jogo adicionado à lista 'Jogos Completados'.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Caso não tratado, pode adicionar lógica extra se necessário
                        }
                    }
                },
                onAddToPlaying = {
                    when (mainViewModel.currentGameList.value) {
                        "completedGames" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Jogos Completados' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "playingNow" -> {
                            mainViewModel.removeGameToCurrentUserList("playingNow")
                            Toast.makeText(activity, "Jogo removido da lista 'Jogando Agora'.", Toast.LENGTH_SHORT).show()
                        }
                        "wishList" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Jogando Agora' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "" -> {
                            mainViewModel.addGameToCurrentUserList("playingNow")
                            Toast.makeText(activity, "Jogo adiconado à lista 'Jogando Agora'.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Caso não tratado, pode adicionar lógica extra se necessário
                        }
                    }
                },
                onAddToWishlist = {
                    when (mainViewModel.currentGameList.value) {
                        "completedGames" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Jogos Completados' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "playingNow" -> {
                            Toast.makeText(activity, "Remova o jogo da lista 'Jogando Agora' antes.", Toast.LENGTH_SHORT).show()
                        }
                        "wishList" -> {
                            mainViewModel.removeGameToCurrentUserList("wishList")
                            Toast.makeText(activity, "Jogo removido da lista 'Lista de Compras'.", Toast.LENGTH_SHORT).show()
                        }
                        "" -> {
                            mainViewModel.addGameToCurrentUserList("wishList")
                            Toast.makeText(activity, "Jogo adicionado à lista 'Lista de Compras'.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Caso não tratado, pode adicionar lógica extra se necessário
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

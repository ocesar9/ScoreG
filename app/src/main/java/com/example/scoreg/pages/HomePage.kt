package com.example.scoreg.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.Navbar
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.scoreg.utils.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, mainViewModel: MainViewModel, modifier: Modifier = Modifier) {

    mainViewModel.fetchAndSortGames()

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { /*TODO*/ },
            actions = {
                IconButton(
                    onClick = {
                        Firebase.auth.signOut()
                        val intent = IntentUtils.createLoginActivityIntent(context)
                        context.startActivity(intent)
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Localized description"
                    )
                }
            }
        )
        Navbar(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bem-vindo à HomePage!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exibir lista de jogos ordenados por score
        Text(
            text = "Jogos Ordenados por Pontuação",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(mainViewModel.gamesSortedByScore.value.toList()) { game ->
                GameCard(game = game)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibir lista de jogos ordenados por ano de lançamento
        Text(
            text = "Jogos Ordenados por Ano de Lançamento",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(mainViewModel.gamesSortedByReleaseYear.value.toList()) { game ->
                GameCard(game = game)
            }
        }
    }
}

@Composable
fun GameCard(game: Game, modifier: Modifier = Modifier) {
    Text(text = "${game.title} / ", style = MaterialTheme.typography.bodyMedium)
    /* TODO
            crie um botão que leve para a tela unificada de informação de um jogo em questão e use as funções abaixo:
                mainViewModel.setCurrentGame(game)
                navController.navigate(("gameInfoPage"))
     */
}
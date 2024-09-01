package com.example.scoreg.pages.gamenavbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.GameButton
import com.example.scoreg.database.dbmanipulation.ManipulateUser
import com.example.scoreg.database.entities.Game
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PlayingNowPage(navController: NavController) {
    val manipulateUser = ManipulateUser()

    // Estado para armazenar a lista de jogos
    var userGamesList by remember { mutableStateOf<List<Game>>(emptyList()) }

    // Identificar usuário logado
    val firebaseAuth = FirebaseAuth.getInstance()

    val currentUser = firebaseAuth.currentUser

    // Salvando o UID do usuário logado em uma string
    val userId: String = currentUser!!.uid

    // Chama fetchGames e atualiza gamesList
    LaunchedEffect(Unit) {
        manipulateUser.fetchUserGamesList(userId, "playingNow") { games ->
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
        Text(text = "Jogando Agora")
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
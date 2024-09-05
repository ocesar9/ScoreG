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
import com.example.scoreg.components.GameGrid
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel

@Composable
fun WishListPage(navController: NavController, mainViewModel: MainViewModel, userListName: String) {
    // Estado para armazenar a lista de jogos
    mainViewModel.fetchCurrentUserGameList(userListName)

    // Layout da página com rolagem
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Lista de Desejos")
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn para a lista de jogos com rolagem

        GameGrid(
            games = mainViewModel.currentUserWishList.value.toList(),
            mainViewModel = mainViewModel,
            navController = navController
        )
    }
}
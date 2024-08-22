package com.example.scoreg.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scoreg.models.MainViewModel

@Composable
fun HomePage(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = viewModel<MainViewModel>();
    val searchText by viewModel.searchText.collectAsState();
    val games by viewModel.games.collectAsState();
    val isSearching by viewModel.isSearching.collectAsState();

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Botão 1 - Jogos Completos
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("complete_games") },
                modifier = Modifier
                    .size(100.dp)  // Define o botão quadrado
            ) {
                // Aqui você pode usar um ícone no lugar do Text, se preferir
                Text(text = "🎮")  // Placeholder para ícone
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Jogos Completos")
        }

        // Botão 2 - Jogando Agora
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("playing_now") },
                modifier = Modifier
                    .size(100.dp)  // Define o botão quadrado
            ) {
                Text(text = "▶️")  // Placeholder para ícone
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Jogando Agora")
        }

        // Botão 3 - Lista de Compras
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("wish_list") },
                modifier = Modifier
                    .size(100.dp)  // Define o botão quadrado
            ) {
                Text(text = "🛒")  // Placeholder para ícone
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Lista de Compras")
        }
    }

//    Column(modifier = Modifier
//        .fillMaxSize(),
//    ){
//        TextField(
//            value = searchText,
//            onValueChange = viewModel::onSearchTextChange,
//            placeholder = { Text(text = "Procure...") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        if(isSearching){
//            Box(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//            this.items(games){ game ->
//                Text(
//                    text = game.title,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp)
//                )
//            }
//        }
//    }
}
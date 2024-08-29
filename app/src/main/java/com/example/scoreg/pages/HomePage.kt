package com.example.scoreg.pages


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scoreg.components.Navbar
import com.example.scoreg.models.MainViewModel

@Composable
fun HomePage(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = viewModel<MainViewModel>();
    val searchText by viewModel.searchText.collectAsState();
    val games by viewModel.games.collectAsState();
    val isSearching by viewModel.isSearching.collectAsState();

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Navbar(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bem-vindo à HomePage!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

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
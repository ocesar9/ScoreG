package com.example.scoreg.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.scoreg.models.MainViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val viewModel = viewModel<MainViewModel>();
    val searchText by viewModel.searchText.collectAsState();
    val games by viewModel.games.collectAsState();
    val isSearching by viewModel.isSearching.collectAsState();

    // ID do usuário logado (você pode obter isso via FirebaseAuth ou mockar para teste)
    val loggedInUserId = "K8kxtmXOhyMuJHGIc2pJ5EtiVZm1"
    val friendId = "a1b2c3d4"

    // Chame os métodos de teste ao iniciar a página
    LaunchedEffect(Unit) {
        viewModel.testAddFriend(loggedInUserId, friendId)
        viewModel.testFetchAllUsersExceptLoggedIn(loggedInUserId)
        viewModel.testFetchFriendsOfLoggedInUser(loggedInUserId)
    }

    Column(modifier = Modifier
        .fillMaxSize(),
    ){
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            placeholder = { Text(text = "Procure...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(isSearching){
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            this.items(games){ game ->
                Text(
                    text = game.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}
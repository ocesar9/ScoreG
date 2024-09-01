package com.example.scoreg.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.scoreg.models.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.scoreg.utils.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    //val viewModel = viewModel<MainViewModel>();
    //val searchText by viewModel.searchText.collectAsState();
    //val games by viewModel.games.collectAsState();
    //val isSearching by viewModel.isSearching.collectAsState();

    val context = LocalContext.current // Obtenha o contexto


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
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.GameListSection
import com.example.scoreg.components.Navbar
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
            modifier = Modifier.height(56.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF25F396)
            ),
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

        Column {
            GameListSection(
                title = "Mais Populares",
                games = mainViewModel.gamesSortedByScore.value.toList()
            )

            GameListSection(
                title = "Lançamentos",
                games = mainViewModel.gamesSortedByReleaseYear.value.toList()
            )
        }



    }
}
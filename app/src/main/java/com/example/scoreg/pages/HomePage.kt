package com.example.scoreg.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.components.CustomTopAppBar
import com.example.scoreg.components.GameListSection
import com.example.scoreg.components.Navbar
import com.example.scoreg.components.SearchGameBar
import com.example.scoreg.models.MainViewModel
import com.example.scoreg.utils.IntentUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    mainViewModel.fetchAndSortGames()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CustomTopAppBar(
            showLogoutButton = true,
            onLogoutClick = {
                Firebase.auth.signOut()
                val intent = IntentUtils.createLoginActivityIntent(context)
                context.startActivity(intent)
            }
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF25F396))
                .padding(bottom = 12.dp)
                .padding(horizontal = 8.dp),
        ) {
            SearchGameBar(
                mainViewModel = mainViewModel,
                navController = navController
            )

            Spacer(modifier = Modifier.height(12.dp))

            Navbar(
                navController = navController,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
        ) {
            GameListSection(
                title = "Mais Populares",
                games = mainViewModel.gamesSortedByScore.value.toList(),
                mainViewModel = mainViewModel,
                navController = navController
            )

            GameListSection(
                title = "Lançamentos",
                games = mainViewModel.gamesSortedByReleaseYear.value.toList(),
                mainViewModel = mainViewModel,
                navController = navController
            )
        }



    }
}
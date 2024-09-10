package com.example.scoreg.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scoreg.database.entities.Game
import com.example.scoreg.models.MainViewModel

@Composable
fun GameListSection(
    title: String,
    games: List<Game>,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(games) { game ->
                GameCard(
                    game = game,
                    modifier = modifier,
                    onClick = {
                        mainViewModel.setCurrentGame(game)
                        navController.navigate("gameInfoPage")
                    },
                    showScore = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
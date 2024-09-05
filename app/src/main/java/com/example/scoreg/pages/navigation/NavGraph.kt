package com.example.scoreg.pages.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scoreg.models.MainViewModel
import com.example.scoreg.pages.*
import com.example.scoreg.pages.gamenavbar.CompletedGamesPage
import com.example.scoreg.pages.gamenavbar.GameInfoPage
import com.example.scoreg.pages.gamenavbar.PlayingNowPage
import com.example.scoreg.pages.gamenavbar.WishListPage

@Composable
fun SetupNavGraph(navController: NavHostController, mainViewModel: MainViewModel) {

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePage(navController = navController, mainViewModel = mainViewModel) }
        composable("completedGamesPage") { CompletedGamesPage(navController = navController, mainViewModel = mainViewModel, userListName = "completedGames") }
        composable("playingNowPage") { PlayingNowPage(navController = navController, mainViewModel = mainViewModel, userListName = "playingNow") }
        composable("wishListPage") { WishListPage(navController = navController, mainViewModel = mainViewModel, userListName = "wishList") }
        composable("gameInfoPage") {GameInfoPage(navController = navController, mainViewModel = mainViewModel) }
    }
}
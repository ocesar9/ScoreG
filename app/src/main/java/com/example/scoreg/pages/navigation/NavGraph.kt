package com.example.scoreg.pages.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scoreg.pages.*
import com.example.scoreg.pages.gamenavbar.CompleteGamesPage
import com.example.scoreg.pages.gamenavbar.PlayingNowPage
import com.example.scoreg.pages.gamenavbar.WishListPage

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePage(navController = navController) }
        composable("completedgamesPage") { CompleteGamesPage(navController = navController) }
        composable("playingNowPage") { PlayingNowPage(navController = navController) }
        composable("wishListPage") { WishListPage(navController = navController) }
    }
}
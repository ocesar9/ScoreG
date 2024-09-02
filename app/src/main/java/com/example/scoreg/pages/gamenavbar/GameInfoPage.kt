package com.example.scoreg.pages.gamenavbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.scoreg.models.MainViewModel

@Composable
fun GameInfoPage(navController: NavController, mainViewModel: MainViewModel) {

    // Página que detalha todos os dados do jogo clicado independente da tela anterior

    Text(text = mainViewModel.currentGame.id)
    Text(text = mainViewModel.currentGame.title)
    Text(text = mainViewModel.currentGame.description)

    /* TODO:
        - criar botão que envie o currentGame.id como parâmetro para a função que adicone ou remova o jogo de alguma lista
            mainViewModel.addGameToCurrentUserList(mainViewModel.currentGame.id, PasseAStringDeNomeDaListaAqui)
        - criar alguma validação validação para saber se o jogo já está em:
        - Listas:
            mainViewModel.currentUserCompletedGamesList
            mainViewModel.currentUserPlayingNowList
            mainViewModel.currentUserWishList
    */

}
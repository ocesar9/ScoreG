package com.example.scoreg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreg.database.dbmanipulation.ManipulateGame
import com.example.scoreg.database.dbmanipulation.ManipulateUser
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val manipulateGame = ManipulateGame()
    private val manipulateUser = ManipulateUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Chama a função fetchGames
        /*
        manipulateGame.fetchGames { gamesList ->
            for (game in gamesList) {
                println(game.title)
            }
        }
        */

        // Chama a função searchGameByTitle
        /*
        val titleToSearch = "The Witcher 3: Wild Hunt"
        manipulateGame.searchGameByTitle(titleToSearch) { game ->
            if (game != null) {
                println("Game found: ${game.title}")
            } else {
                println("Game not found")
            }
        }
         */

        // Chama a função fetchGamesSortedByYear
        /*
        manipulateGame.fetchGamesSortedByYear { gamesList ->
            for (game in gamesList) {
                println("Game: ${game.title}, Year: ${game.releaseYear}")
            }
        }
         */

        // ID do usuário a ser buscado

        val userId = "001" // Exemplo de ID

        // Chama a função fetchUserGamesList passando o usuário e a lista desejada do mesmo
        manipulateUser.fetchUserGamesList(userId, "completed_games") { games ->
            println("Completed Games: ${games?.map { it.title }}")
        }

        manipulateUser.fetchUserGamesList(userId, "playing_now") { games ->
            println("Playing Now Games: ${games?.map { it.title }}")
        }

        manipulateUser.fetchUserGamesList(userId, "wish_list") { games ->
            println("Wish List Games: ${games?.map { it.title }}")
        }

    }
}
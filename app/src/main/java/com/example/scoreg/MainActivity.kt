package com.example.scoreg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreg.DB.Game
import com.example.scoreg.DB.ManipulateGame
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val gameList = mutableListOf<Game>()
    private val manipulateGame = ManipulateGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Chama a função fetchGames
        manipulateGame.fetchGames { gamesList ->
            // Utilize a lista de jogos aqui
            for (game in gamesList) {
                println(game.title)
            }
        }

        // Chama a função de busca
        val titleToSearch = "The Witcher 3: Wild Hunt"
        manipulateGame.searchGameByTitle(titleToSearch) { game ->
            if (game != null) {
                println("Game found: ${game.title}")
            } else {
                println("Game not found")
            }
        }
    }
}
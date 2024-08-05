package com.example.scoreg.DB

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManipulateGame {

    // Retorna todos os items da tabela/nó games
    fun fetchGames(callback: (List<Game>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val gamesRef = database.getReference("games")

        gamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gamesList = mutableListOf<Game>()
                for (gameSnapshot in snapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java)
                    game?.let { gamesList.add(it) }
                }
                callback(gamesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Trate o erro, se necessário
            }
        })
    }

    // Função para buscar game por title
    fun searchGameByTitle(title: String, callback: (Game?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val gamesRef = database.getReference("games")

        val query = gamesRef.orderByChild("title").equalTo(title)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (gameSnapshot in snapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java)
                    if (game != null) {
                        callback(game)
                        return
                    }
                }
                callback(null)
            }

            override fun onCancelled(error: DatabaseError) {
                // Trate o erro, se necessário
                callback(null)
            }
        })
    }

}
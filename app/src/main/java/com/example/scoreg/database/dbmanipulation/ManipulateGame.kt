package com.example.scoreg.database.dbmanipulation

import com.example.scoreg.database.entities.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManipulateGame {

    // Retorna List com todos os Games
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
                callback(emptyList())
            }
        })
    }

    // Retorna List com todos os Games e os ordena em ordem de mais recente
    fun fetchGamesSortedByYear(callback: (List<Game>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val gamesRef = database.getReference("games")

        gamesRef.orderByChild("releaseYear").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gamesList = mutableListOf<Game>()
                for (gameSnapshot in snapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java)
                    game?.let { gamesList.add(it) }
                }
                // Ordena a lista de jogos por ano de lançamento mais recente
                val sortedList = gamesList.sortedByDescending { it.releaseYear }
                callback(sortedList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Trate o erro, se necessário
                callback(emptyList())
            }
        })
    }

    // Retorna Objeto Game passando Title
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
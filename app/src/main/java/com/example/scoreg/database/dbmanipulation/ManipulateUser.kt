package com.example.scoreg.database.dbmanipulation

import com.example.scoreg.database.entities.Game
import com.example.scoreg.database.entities.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManipulateUser {

    // Função que retorna List de jogos especificada de um User
    fun fetchUserGamesList(
        userId: String,
        listType: String,
        callback: (List<Game>?) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId).child(listType)
        val gamesRef = database.getReference("games")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userSnapshot: DataSnapshot) {
                val gameIds = userSnapshot.children.map { it.key }
                if (gameIds.isNullOrEmpty()) {
                    callback(emptyList())
                    return
                }

                gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(gameSnapshot: DataSnapshot) {
                        val gamesList = mutableListOf<Game>()
                        gameIds.forEach { gameId ->
                            val game = gameSnapshot.child(gameId!!).getValue(Game::class.java)
                            if (game != null) {
                                gamesList.add(game)
                            }
                        }
                        callback(gamesList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(null)
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

}
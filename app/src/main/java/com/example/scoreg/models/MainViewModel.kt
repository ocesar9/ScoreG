package com.example.scoreg.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.scoreg.database.entities.Game
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {
    private var _loggedIn = mutableStateOf(false)
    val loggedIn: Boolean get() = _loggedIn.value
    private val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _loggedIn.value = firebaseAuth.currentUser != null
    }

    // Instância do FirebaseDatabase
    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    // Retorna a referência para um caminho específico no Realtime Database
    fun getDatabaseReference(path: String): DatabaseReference {
        return database.getReference(path)
    }

    // Outras funções que manipulam o banco de dados
    fun updateGameScore(gameId: String, newScore: Int) {
        val gameRef = getDatabaseReference("games/$gameId")
        gameRef.child("score").setValue(newScore)
    }

    fun getCurrentUserId(): String? {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid
    }

    // Função que retorna Lista de jogos especificada do usuário logado
    fun fetchCurrentUserGamesList(
        listType: String,
        callback: (List<Game>?) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance()
        val userRef = getCurrentUserId()?.let { database.getReference("users").child(it).child(listType) }
        val gamesRef = database.getReference("games")

        if (userRef != null) {
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




}

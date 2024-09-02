package com.example.scoreg.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.scoreg.database.entities.Game
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {

    // V Auth State  V ---------------------------------------------------------------------------
    private var _loggedIn = mutableStateOf(false)
    val loggedIn: Boolean get() = _loggedIn.value
    private val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _loggedIn.value = firebaseAuth.currentUser != null
    }

    init {
        listener.onAuthStateChanged(Firebase.auth)
    }

    // V FB Utils V ---------------------------------------------------------------------------

    // Instância do FirebaseDatabase
    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    // Retorna a referência para um caminho específico no Realtime Database
    fun getDatabaseReference(path: String): DatabaseReference {
        return database.getReference(path)
    }

    fun getCurrentUserId(): String? {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid
    }

    // V Games Table V ---------------------------------------------------------------------------

    fun updateGameScore(gameId: String, newScore: Int) {
        val gameRef = getDatabaseReference("games/$gameId")
        gameRef.child("score").setValue(newScore)
    }

    // Listas observáveis para armazenar os jogos ordenados por score e releaseYear
    var gamesSortedByScore = mutableStateOf<List<Game>>(listOf())
        private set

    var gamesSortedByReleaseYear = mutableStateOf<List<Game>>(listOf())
        private set

    fun fetchAndSortGames() {
        val gamesRef = getDatabaseReference("games")

        // Listener para escutar mudanças no nó "games"
        gamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gamesList = mutableListOf<Game>()

                // Itera sobre todos os jogos no banco de dados
                for (gameSnapshot in dataSnapshot.children) {
                    val gameId = gameSnapshot.key // Obtém o ID único do jogo
                    val game = gameSnapshot.getValue(Game::class.java)

                    if (game != null && gameId != null) {
                        // Define o ID do jogo
                        val gameWithId = game.copy(id = gameId)
                        gamesList.add(gameWithId)
                    }
                }

                // Ordena as listas
                val sortedByScore = gamesList.sortedByDescending { it.score }
                val sortedByReleaseYear = gamesList.sortedByDescending { it.releaseYear }

                // Atualiza as listas observáveis
                gamesSortedByScore.value = sortedByScore
                gamesSortedByReleaseYear.value = sortedByReleaseYear
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Lida com erros, se necessário
            }
        })
    }

    // V Users Table V ---------------------------------------------------------------------------

    // Função que retorna Lista de jogos especificada do usuário logado
    fun fetchCurrentUserGamesList(
        userListName: String,
        callback: (List<Game>?) -> Unit
    ) {
        val userRef = getCurrentUserId()?.let { getDatabaseReference("users").child(it).child(userListName) }
        val gamesRef = getDatabaseReference("games")

        if (userRef != null) {
            // Adiciona um ValueEventListener contínuo
            userRef.addValueEventListener(object : ValueEventListener {
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

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
    private fun getDatabaseReference(path: String): DatabaseReference {
        return database.getReference(path)
    }

    private fun getCurrentUserId(): String? {
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

    lateinit var currentGame: Game
        private set

    fun setCurrentGame(currentGameTemp: Game) {
        currentGame = currentGameTemp
    }

    // V Users Table V ---------------------------------------------------------------------------

    var currentUserCompletedGamesList = mutableStateOf<List<Game>>(listOf())
        private set

    var currentUserPlayingNowList = mutableStateOf<List<Game>>(listOf())
        private set

    var currentUserWishList = mutableStateOf<List<Game>>(listOf())
        private set

    // Função para atualizar a Game list de jogos de acordo com o nome da lista do usuário logado
    fun fetchCurrentUserGameList(listName: String) {
        val currentUserId = getCurrentUserId()
        val userRef = getDatabaseReference("users/$currentUserId/$listName")
        val gamesRef = getDatabaseReference("games")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userSnapshot: DataSnapshot) {
                val completedGameIds = userSnapshot.children.mapNotNull { it.key }

                val completedGamesListTemp = mutableListOf<Game>()

                gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(gamesSnapshot: DataSnapshot) {
                        for (gameSnapshot in gamesSnapshot.children) {
                            val gameId = gameSnapshot.key
                            val game = gameSnapshot.getValue(Game::class.java)

                            if (gameId != null && gameId in completedGameIds && game != null) {
                                val gameWithId = game.copy(id = gameId)
                                completedGamesListTemp.add(gameWithId)
                            }
                        }

                        if (listName == "completedGames") {
                            currentUserCompletedGamesList.value = completedGamesListTemp
                        }
                        else if (listName == "playingNow") {
                            currentUserPlayingNowList.value = completedGamesListTemp
                        }
                        else if (listName == "wishList") {
                            currentUserWishList.value = completedGamesListTemp
                        }

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Lida com erros, se necessário
                    }
                })
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Lida com erros, se necessário
            }
        })
    }

    private fun convertListNameToFBRefName(listName: String): String {
        val listNamePretty = when (listName) {
            "completedGames" -> "lista Jogos Completados"
            "playingNow" -> "lista Jogando Agora"
            "wishList" -> "Lista de Compras"
            else -> "Tem isso não!"
        }

        return listNamePretty

    }

    // Função conferir se o jogo faz parte de alguma lista e adiciona o ID do jogo na lista de jogos do usuário logado
    fun addGameToCurrentUserListWithCheck(listName: String, callback: (String) -> Unit) {
        val userId = getCurrentUserId()
        val userRef = getDatabaseReference("users/$userId")
        val listNamePretty = convertListNameToFBRefName(listName)

        // Listener para pegar todas as listas de jogos do usuário
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val completedGames = dataSnapshot.child("completedGames").children.mapNotNull { it.key }
                val playingNow = dataSnapshot.child("playingNow").children.mapNotNull { it.key }
                val wishList = dataSnapshot.child("wishList").children.mapNotNull { it.key }

                // Verifica se o jogo já pertence a alguma lista
                when (currentGame.id) {
                    in completedGames -> {
                        callback("O jogo já está na lista 'Jogos Completados'")
                    }
                    in playingNow -> {
                        callback("O jogo já está na lista 'Jogando Agora'")
                    }
                    in wishList -> {
                        callback("O jogo já está na 'Lista de Compras'")
                    }
                    else -> {
                        // Adiciona o jogo à lista especificada
                        val userGameListRef: DatabaseReference = userRef.child(listName).child(currentGame.id)
                        userGameListRef.setValue(true).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                callback("Jogo adicionado com sucesso à $listNamePretty")
                            } else {
                                callback("Erro ao adicionar jogo à $listNamePretty")
                            }
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Retorna erro
                callback("Erro ao acessar o banco de dados")
            }
        })
    }

}

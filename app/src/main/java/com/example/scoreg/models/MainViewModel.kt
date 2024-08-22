package com.example.scoreg.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreg.database.dbmanipulation.ManipulateGame
import com.example.scoreg.database.dbmanipulation.ManipulateUser
import com.example.scoreg.database.entities.Game
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _loggedIn = mutableStateOf(false)
    val loggedIn: Boolean get() = _loggedIn.value
    private val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _loggedIn.value = firebaseAuth.currentUser != null
    }

//  __________________________________________________________

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_games) { text, games ->
            if (text.isBlank()) {
                games
            } else {
                games.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val manipulateGame = ManipulateGame()

    init {
        listener.onAuthStateChanged(Firebase.auth)
        fetchGames()
    }

    // Função para buscar os jogos do Firebase
    private fun fetchGames() {
        manipulateGame.fetchGames { fetchedGames ->
            // Atualiza o StateFlow _games com a lista de jogos buscada
            _games.update { fetchedGames }
        }
    }

    // Função para atualizar o texto de busca
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private val manipulateUser = ManipulateUser()

    // Testa o método para adicionar um amigo
    fun testAddFriend(loggedInUserId: String, friendId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            manipulateUser.addFriendToUser(loggedInUserId, friendId,
                onSuccess = {
                    println("Amigo adicionado com sucesso.")
                },
                onFailure = { exception ->
                    println("Falha ao adicionar amigo: ${exception.message}")
                }
            )
        }
    }

    // Testa o método para buscar todos os usuários, exceto o logado
    fun testFetchAllUsersExceptLoggedIn(loggedInUserId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            manipulateUser.fetchAllUsersExceptLoggedInUser(loggedInUserId) { users ->
                if (users != null) {
                    println("Usuários encontrados: ${users.size}")
                    users.forEach { user ->
                        println("Usuário: ${user.name}, Email: ${user.email}")
                    }
                } else {
                    println("Falha ao buscar usuários.")
                }
            }
        }
    }

    // Testa o método para buscar todos os amigos do usuário logado
    fun testFetchFriendsOfLoggedInUser(loggedInUserId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            manipulateUser.fetchFriendsOfLoggedInUser(loggedInUserId) { friends ->
                if (friends != null) {
                    println("Amigos encontrados: ${friends.size}")
                    friends.forEach { friend ->
                        println("Amigo: ${friend.name}, Email: ${friend.email}")
                    }
                } else {
                    println("Falha ao buscar amigos.")
                }
            }
        }
    }

}

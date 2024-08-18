package com.example.scoreg.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreg.database.dbmanipulation.ManipulateGame
import com.example.scoreg.database.entities.Game
import kotlinx.coroutines.delay
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
        fetchGames()
    }

    // Função para buscar os jogos do Firebase
    private fun fetchGames() {
        viewModelScope.launch {
            manipulateGame.fetchGames { fetchedGames ->
                _games.update { fetchedGames }
            }
        }
    }

    // Função para atualizar o texto de busca
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}
package com.example.scoreg.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private val _searchText = MutableStateFlow("");
    val searchText = _searchText.asStateFlow();

    private val _isSearching = MutableStateFlow(false);
    val isSearching = _isSearching.asStateFlow();

    private val _games = MutableStateFlow(allGames);
    val games = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_games){
            text, games ->
                if(text.isBlank()){
                    games
                }else {
                    delay(2000)
                    games.filter {
                        it.doesMatchSearchQuery(text)
                    }
                }
        }
        .onEach { _isSearching.update {false} }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _games.value
        )

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }
}

data class Game(
    val name: String,
    val score: Int
) {
    fun doesMatchSearchQuery(query:String): Boolean {
        val matchingCombinations = listOf(
            "${name}",
            "${name.first()}",
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allGames = listOf(
    Game(name = "Black Myth Wukong", score = 5),
    Game(name = "Cyberpunk 2077", score = 4),
    Game(name = "The Witcher 3: Wild Hunt", score = 5),
    Game(name = "Grand Theft Auto V", score = 4),
    Game(name = "Red Dead Redemption 2", score = 5),
    Game(name = "The Legend of Zelda: Breath of the Wild", score = 5),
    Game(name = "God of War (2018)", score = 5),
    Game(name = "Super Mario Odyssey", score = 4),
    Game(name = "Dark Souls III", score = 5),
    Game(name = "Persona 5", score = 4)
)
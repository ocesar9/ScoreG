package com.example.scoreg.database.entities

data class User(
    val email: String? = null,
    val name: String? = null,
    val completedGames: Map<String, Boolean>? = null,
    val friends: Map<String, Boolean>? = null,
    val playingNow: Map<String, Boolean>? = null,
    val wishList: Map<String, Boolean>? = null
)

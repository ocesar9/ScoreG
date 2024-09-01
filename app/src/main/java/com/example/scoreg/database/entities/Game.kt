package com.example.scoreg.database.entities

data class Game(
    val description: String = "",
    val releaseYear: Int = 0,
    val score: Int = 0,
    val title: String = "",
    val urlImage: String = "",
    val urlTrailer: String = ""
)
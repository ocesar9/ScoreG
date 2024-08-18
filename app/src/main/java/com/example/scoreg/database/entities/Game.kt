package com.example.scoreg.database.entities

data class Game(
    val description: String = "",
    val releaseYear: Int = 0,
    val score: Int = 0,
    val title: String = "",
    val urlImage: String = "",
    val urlTrailer: String = ""
){
    fun doesMatchSearchQuery(query:String): Boolean {
        val matchingCombinations = listOf(
            title,
            "${title.first()}",
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
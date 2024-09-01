package com.example.scoreg.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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

}

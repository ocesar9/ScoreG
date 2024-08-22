package com.example.scoreg.database.dbmanipulation

import com.example.scoreg.database.entities.Game
import com.example.scoreg.database.entities.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManipulateUser {

    // Função que retorna List de jogos especificada de um User
    fun fetchUserGamesList(
        userId: String,
        listType: String,
        callback: (List<Game>?) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId).child(listType)
        val gamesRef = database.getReference("games")

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

    // Função que adiciona um user à lista de amigos (friends) de outro user no Realtime Database
    fun addFriendToUser(userId: String, friendId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val userFriendsRef = database.getReference("users").child(userId).child("friends")

        // Adiciona o friendId à lista de amigos do userId com o valor true
        userFriendsRef.child(friendId).setValue(true)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Função que retorna uma lista de usuários, exceto o usuário logado
    fun fetchAllUsersExceptLoggedInUser(loggedInUserId: String, callback: (List<User>?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList = mutableListOf<User>()

                for (userSnapshot in snapshot.children) {
                    val userId = userSnapshot.key
                    val user = userSnapshot.getValue(User::class.java)

                    // Adiciona à lista apenas os usuários que não são o logado
                    if (userId != null && userId != loggedInUserId && user != null) {
                        usersList.add(user)
                    }
                }

                // Retorna a lista de usuários, exceto o logado
                callback(usersList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Em caso de erro, retorna null
                callback(null)
            }
        })
    }

    // Função que retorna a lista de amigos do usuário logado
    fun fetchFriendsOfLoggedInUser(loggedInUserId: String, callback: (List<User>?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val userFriendsRef = database.getReference("users").child(loggedInUserId).child("friends")
        val usersRef = database.getReference("users")

        userFriendsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val friendIds = snapshot.children.map { it.key }

                if (friendIds.isNullOrEmpty()) {
                    // Se o usuário não tiver amigos, retorna uma lista vazia
                    callback(emptyList())
                    return
                }

                usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(usersSnapshot: DataSnapshot) {
                        val friendsList = mutableListOf<User>()

                        for (friendId in friendIds) {
                            val friend = usersSnapshot.child(friendId!!).getValue(User::class.java)
                            if (friend != null) {
                                friendsList.add(friend)
                            }
                        }

                        // Retorna a lista de amigos do usuário logado
                        callback(friendsList)
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
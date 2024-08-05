package com.example.scoreg

import GameAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreg.DB.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private val gameList = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(gameList)
        recyclerView.adapter = gameAdapter

        // Inicializa o Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Objeto database guarda a tabela games
        database = FirebaseDatabase.getInstance().getReference("games")
        // Database vai fazer ações toda vez que identificar alterações no Firebase
        database.addValueEventListener(object : ValueEventListener {
            // Quando a tabela games for alterada...
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Limpe a lista gameList...
                gameList.clear()
                // Cada cada filho de games...
                for (gameSnapshot in dataSnapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java)
                    // Adicione cada filho de games à lsita gameList
                    game?.let { gameList.add(it) }
                }
                // Atualiza a interface para apresentar as alterações em games
                gameAdapter.notifyDataSetChanged()
            }

            // Não foi possível coletar dados do Firebase
            override fun onCancelled(error: DatabaseError) {
                Log.w("MainActivity", "Failed to read value.", error.toException())
            }
        })
    }
}
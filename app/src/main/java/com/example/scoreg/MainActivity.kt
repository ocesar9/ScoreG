package com.example.scoreg

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.scoreg.models.MainViewModel
import com.example.scoreg.pages.navigation.SetupNavGraph
import com.example.scoreg.ui.theme.ScoreGTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instância do MainViewModel
        val mainViewModel: MainViewModel by viewModels()

        setContent {
            ScoreGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Passar o viewModel para o SetupNavGraph
                    SetupNavGraph(navController = navController, mainViewModel = mainViewModel)
                }
            }
        }
    }
}
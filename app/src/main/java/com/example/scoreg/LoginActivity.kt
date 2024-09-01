package com.example.scoreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.scoreg.models.MainViewModel
import com.example.scoreg.pages.LoginPage
import com.example.scoreg.ui.theme.ScoreGTheme
import com.example.scoreg.utils.IntentUtils

class LoginActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.loggedIn){
            val intent = IntentUtils.createMainActivityIntent(this)
            startActivity(intent);
        }else {
            setContent {
                ScoreGTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LoginPage()
                    }
                }

            }

        }
    }
}
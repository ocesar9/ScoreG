package com.example.scoreg

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreg.models.MainViewModel
import com.weatherapp.utils.IntentUtils

class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val ivNote: ImageView = findViewById(R.id.iv_note)
        ivNote.alpha = 0f;
        ivNote.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent = IntentUtils.createLoginActivityIntent(this)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}
package com.weatherapp.utils

import android.content.Context
import android.content.Intent
import com.example.scoreg.LoginActivity
import com.example.scoreg.MainActivity


object IntentUtils {
    fun createMainActivityIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

    fun createLoginActivityIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

}
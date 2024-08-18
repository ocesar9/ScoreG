package com.example.scoreg.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreg.MainActivity
import com.example.scoreg.R
import com.example.scoreg.RegisterActivity
import com.example.scoreg.components.ClickableText
import com.example.scoreg.components.CustomButton
import com.example.scoreg.components.FormField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Column(modifier = Modifier
            .width(368.dp)
            .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(31.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"

            )
            Text(
                text = "Faça login com sua conta",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            FormField(value = email, onValueChange = {email = it}, placeholder = "E-mail", type = "email")

            FormField(value = password, onValueChange = {password = it}, placeholder = "Senha", type = "password")

            CustomButton(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) {
                            task ->
                        if (task.isSuccessful) {
                            activity.startActivity( Intent(activity, MainActivity::class.java).setFlags(
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                            ));
                            Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            password = ""; email = "" ;
                        }else{
                            Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                        }
                    }
            }, buttonText = "Entrar")
            
            ClickableText(
                text = "Você não possui conta? Cadastre-se",
                onClick = {
                    activity?.startActivity(
                        Intent(activity, RegisterActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                 }
            )

        }
    }
}
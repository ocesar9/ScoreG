package com.example.scoreg.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.scoreg.LoginActivity
import com.example.scoreg.MainActivity
import com.example.scoreg.R
import com.example.scoreg.RegisterActivity
import com.example.scoreg.components.ClickableText
import com.example.scoreg.components.CustomButton
import com.example.scoreg.components.FormField

@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
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
                text = "Crie sua conta",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            FormField(value = name, onValueChange = {name = it}, placeholder = "Digite o seu nome", type = "text")

            FormField(value = email, onValueChange = {email = it}, placeholder = "E-mail", type = "email")

            FormField(value = password, onValueChange = {password = it}, placeholder = "Senha", type = "password")

            FormField(value = confirmPassword, onValueChange = {confirmPassword = it}, placeholder = "Confirme sua senha", type = "password")

            CustomButton(
                onClick = {
                    if (password != confirmPassword) {
                        Toast.makeText(activity, "As senhas não coincidem!", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(activity, "Cadastro realizado com Sucesso!", Toast.LENGTH_LONG).show()
                        activity?.startActivity(
                            Intent(activity, LoginActivity::class.java).setFlags(
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                            )
                        )
                    }
                    name = "";
                    email = "";
                    password = "";
                    confirmPassword = "";
            }, buttonText = "Cadastrar")

            ClickableText(
                text = "Você já possui conta? Entre",
                onClick = {
                    activity?.startActivity(
                        Intent(activity, LoginActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                }
            )

        }
    }
}
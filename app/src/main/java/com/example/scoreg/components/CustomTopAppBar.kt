package com.example.scoreg.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = {},
    showLogoutButton: Boolean = false,
    onLogoutClick: (() -> Unit)? = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { /* Título ou conteúdo do TopAppBar */ },
        modifier = modifier
            .wrapContentSize()
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF25F396)
        ),
        navigationIcon = {
            if (showBackButton) {

                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (showLogoutButton) {
                IconButton(
                    onClick = { onLogoutClick?.invoke() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Sair",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

package com.example.scoreg.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.helper.widget.MotionPlaceholder

@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    type: String
) {
    val keyboardType = when (type) {
        "email" -> KeyboardType.Email
        "number" -> KeyboardType.Number
        "password" -> KeyboardType.Password
        "text" -> KeyboardType.Text
        else -> KeyboardType.Text
    }

    val visualTransformation: VisualTransformation = if (type == "password") {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }


    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = visualTransformation

    )
}
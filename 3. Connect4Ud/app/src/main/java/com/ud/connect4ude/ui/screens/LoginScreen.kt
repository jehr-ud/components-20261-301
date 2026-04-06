package com.ud.connect4ude.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(){
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column() {
        Text("Username")

        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Etiqueta") }
        )

        Text("password")

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Etiqueta") }
        )

        Button(onClick = {}) {
            Text("Log in")
        }
    }
}

@Preview
@Composable
fun LoginScreenPrev(){
    LoginScreen()
}
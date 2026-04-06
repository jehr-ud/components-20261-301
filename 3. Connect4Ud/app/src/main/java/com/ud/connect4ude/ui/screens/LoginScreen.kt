package com.ud.connect4ude.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column() {
        Text("Email")

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Etiqueta") }
        )

        Text("password")

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Etiqueta") }
        )

        Button(
            onClick = { onLoginSuccess(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}

@Preview
@Composable
fun LoginScreenPrev(){
    LoginScreen(onLoginSuccess =  { email, password -> {}})
}
package com.ud.connect4ude.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ud.connect4ude.repositories.GameRepository

@Composable
fun GameScreen() {
    val repository = remember { GameRepository() }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Game")

        Button(onClick = {
            val hostUid = "uid_temporal"
            val gameCode = repository.createGame(hostUid)
            Toast.makeText(context, "Sala creada: $gameCode", Toast.LENGTH_SHORT).show()
        }) {
            Text("Crear partida")
        }
    }
}
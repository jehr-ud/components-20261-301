package com.ud.connect4ude.ui.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ud.connect4ude.models.AuthUiState
import com.ud.connect4ude.models.GameStatus
import com.ud.connect4ude.viewmodels.ConfViewModel
import com.ud.connect4ude.viewmodels.GameViewModel

@Composable
fun GameScreen() {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: GameViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )

    val confViewModel: ConfViewModel = viewModel()
    val config by confViewModel.config.collectAsState()
    val loadingConfig by confViewModel.loading.collectAsState()
    val errorConfig by confViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        confViewModel.getConfig()
    }

    val game by viewModel.gameState.collectAsState()
    var codeInput by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (loadingConfig) {
            CircularProgressIndicator()
        }

        if (errorConfig != null) {
            Text(text = errorConfig!!, color = Color.Red)
        }

        if (uiState is AuthUiState.Error) {
            Text(
                text = (uiState as AuthUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (game == null) {
            TextField(
                value = codeInput,
                onValueChange = { codeInput = it },
                label = { Text("Introduce código de juego") }
            )

            Button(onClick = { viewModel.joinGameByCode(codeInput) }) {
                Text("Unirse a partida")
            }

            Text("ó")

            Button(onClick = { viewModel.startNewGame() }) {
                Text("Crear partida nueva")
            }
        } else {

            Text("Código: ${game?.code}", style = MaterialTheme.typography.headlineSmall)
            Text("Jugador 1: ${game?.player1}")
            Text("Estado: ${game?.status}")

            if (game?.status == GameStatus.WAITING) {

                Text("Esperando a que se conecte el Jugador 2...")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Cyan,
                        strokeWidth = 4.dp
                    )
                }
            } else if (game?.status == GameStatus.PLAYING) {
                Text("¡Partida Iniciada!")
            }
        }
    }
}
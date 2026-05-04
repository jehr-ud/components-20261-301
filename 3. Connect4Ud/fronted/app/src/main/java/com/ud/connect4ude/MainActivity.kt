package com.ud.connect4ude

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ud.connect4ude.models.AuthUiState
import com.ud.connect4ude.ui.menu.MainNavigation
import com.ud.connect4ude.ui.screens.LoginScreen
import com.ud.connect4ude.ui.theme.Connect4UdeTheme
import com.ud.riddle.viewmodels.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val authViewModel: AuthViewModel = AuthViewModel(this.application)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Connect4UdeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val uiState by authViewModel.uiState.collectAsState()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState) {
                            is AuthUiState.Success -> {
                                MainNavigation()
                            }
                            else -> {
                                LoginScreen(authViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
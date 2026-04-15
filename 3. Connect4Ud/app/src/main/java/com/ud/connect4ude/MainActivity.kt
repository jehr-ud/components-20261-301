package com.ud.connect4ude

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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


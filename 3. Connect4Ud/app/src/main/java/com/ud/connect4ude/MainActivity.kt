package com.ud.connect4ude

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ud.connect4ude.ui.menu.MainNavigation
import com.ud.connect4ude.ui.screens.LoginScreen
import com.ud.connect4ude.ui.theme.Connect4UdeTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()

        setContent {
            Connect4UdeTheme {
                var userLogged by remember { mutableStateOf(auth.currentUser != null) }

                if (!userLogged) {
                    LoginScreen(onLoginSuccess = { email, password ->
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    userLogged = true
                                } else {
                                    Toast.makeText(baseContext, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    })
                } else {
                    MainNavigation()
                }
            }
        }
    }
}

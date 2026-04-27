package com.ud.connect4ude.ui.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar en Material 3
@Composable
fun Connect4TobBar(onOpenDrawer: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Connect4 UD")
        },
        navigationIcon = {
            IconButton(onClick = { onOpenDrawer() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú lateral"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
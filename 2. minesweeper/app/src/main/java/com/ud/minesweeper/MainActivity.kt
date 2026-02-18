package com.ud.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ud.minesweeper.models.GameStateEnum
import com.ud.minesweeper.models.LevelEnum
import com.ud.minesweeper.ui.theme.MinesweeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinesweeperTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    var gameState by remember { mutableStateOf(GameStateEnum.LEVEL) }
    var level by remember { mutableStateOf(LevelEnum.LOW) }

    when(gameState){
        GameStateEnum.LEVEL ->  {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Levels:",
                    modifier = modifier
                )

                Button(
                    onClick = {
                        gameState = GameStateEnum.IN_GAME
                        level = LevelEnum.LOW
                              },
                    modifier = modifier,
                    content = {Text("Low")},
                )

                Button(
                    onClick = { gameState = GameStateEnum.IN_GAME
                        level = LevelEnum.MEDIUM      },
                    modifier = modifier,
                    content = {Text("Medium")},
                )


                Button(
                    onClick = { gameState = GameStateEnum.IN_GAME
                        level = LevelEnum.HIGH      },
                    modifier = modifier,
                    content = {Text("High")},
                )
            }
        }
        GameStateEnum.IN_GAME  ->  {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Welcome to Game $level",
                    modifier = modifier
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(8)
                ) {
                    items(8*8 ) {
                        index -> Cell(index)
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(index: Int){
    Box(
        modifier = Modifier.size(30.dp)
            .background(Color.Red),
    ){
        Text(index.toString())

    }
}

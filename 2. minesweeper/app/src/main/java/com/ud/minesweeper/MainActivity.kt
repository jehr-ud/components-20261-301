package com.ud.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameScreen()
        }
    }
}

enum class GameState { LEVEL, GAME, WIN, LOSE }
enum class Level { LOW, MEDIUM, HIGH }

@Composable
fun GameScreen() {

    var gameState by remember { mutableStateOf(GameState.LEVEL) }
    var level by remember { mutableStateOf(Level.LOW) }

    var firstClick by remember { mutableStateOf(true) }

    val size = when(level){
        Level.LOW -> 8
        Level.MEDIUM -> 10
        Level.HIGH -> 12
    }

    val bombs = when(level){
        Level.LOW -> 8
        Level.MEDIUM -> 15
        Level.HIGH -> 25
    }

    var board by remember { mutableStateOf(Array(size*size){0}) }
    var revealed by remember { mutableStateOf(mutableSetOf<Int>()) }

    when(gameState){

        GameState.LEVEL -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text("Select Level")

                Button(onClick={
                    level = Level.LOW
                    gameState = GameState.GAME
                    firstClick = true
                    board = Array(8*8){0}
                    revealed.clear()
                }){ Text("Low") }

                Button(onClick={
                    level = Level.MEDIUM
                    gameState = GameState.GAME
                    firstClick = true
                    board = Array(10*10){0}
                    revealed.clear()
                }){ Text("Medium") }

                Button(onClick={
                    level = Level.HIGH
                    gameState = GameState.GAME
                    firstClick = true
                    board = Array(12*12){0}
                    revealed.clear()
                }){ Text("High") }

            }
        }

        GameState.GAME -> {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){

                Text("Minesweeper - $level")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(size),
                    modifier = Modifier.padding(16.dp)
                ){

                    items(board.size){ index ->

                        Cell(
                            index,
                            board,
                            revealed,
                            size
                        ){

                            if(firstClick){
                                board = generateBoard(size,bombs,index)
                                firstClick = false
                            }

                            if(board[index] == -1){
                                gameState = GameState.LOSE
                            }else{
                                reveal(board,size,index,revealed)
                            }

                            if(revealed.size == size*size - bombs){
                                gameState = GameState.WIN
                            }

                        }

                    }

                }

            }

        }

        GameState.WIN -> {
            ResultScreen("YOU WIN "){ gameState = GameState.LEVEL }
        }

        GameState.LOSE -> {
            ResultScreen("GAME OVER "){ gameState = GameState.LEVEL }
        }

    }

}

@Composable
fun ResultScreen(text:String, restart:()->Unit){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(text)

        Button(onClick = restart){
            Text("Play Again")
        }

    }

}

@Composable
fun Cell(
    index:Int,
    board:Array<Int>,
    revealed:Set<Int>,
    size:Int,
    onClick:()->Unit
){

    val revealedCell = revealed.contains(index)

    Box(
        modifier = Modifier
            .size(40.dp)
            .border(1.dp,Color.Black)
            .background(if(revealedCell) Color.White else Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){

        if(revealedCell){

            when{
                board[index] == -1 -> Text("💣")
                board[index] == 0 -> Text("")
                else -> Text(board[index].toString())
            }

        }

    }

}

fun generateBoard(size:Int,bombs:Int,firstClick:Int):Array<Int>{

    val board = Array(size*size){0}

    val safeCells = getNeighbors(firstClick,size).toMutableSet()
    safeCells.add(firstClick)

    var placed = 0

    while(placed < bombs){

        val pos = Random.nextInt(size*size)

        if(pos !in safeCells && board[pos] != -1){
            board[pos] = -1
            placed++
        }

    }

    for(i in board.indices){

        if(board[i] == -1) continue

        val neighbors = getNeighbors(i,size)

        var count = 0

        neighbors.forEach{
            if(board[it] == -1) count++
        }

        board[i] = count

    }

    return board
}

fun reveal(board:Array<Int>,size:Int,index:Int,revealed:MutableSet<Int>){

    if(index < 0 || index >= board.size) return
    if(revealed.contains(index)) return

    revealed.add(index)

    if(board[index] == 0){

        val neighbors = getNeighbors(index,size)

        neighbors.forEach{
            reveal(board,size,it,revealed)
        }

    }

}

fun getNeighbors(index:Int,size:Int):List<Int>{

    val neighbors = mutableListOf<Int>()

    val row = index / size
    val col = index % size

    for(r in row-1..row+1){
        for(c in col-1..col+1){

            if(r==row && c==col) continue

            if(r>=0 && r<size && c>=0 && c<size){
                neighbors.add(r*size + c)
            }

        }
    }

    return neighbors
}
package com.ud.connect4ude.models


data class Game(
     val code: String = "",
     val status: GameStatus = GameStatus.WAITING,
     val player1: String = "",
     val player2: String = "",
     val turnPlayerId: String = "",
     val winnerPlayerId: String = ""
)
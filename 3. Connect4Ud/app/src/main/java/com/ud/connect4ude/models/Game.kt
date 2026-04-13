package com.ud.connect4ude.models


data class Game(
     val code: String,
     val status: GameStatus,
     val player1: String,
     val player2: String?=null,
     val turnPlayerId: String,
     val winnerPlayerId: String?=null,


)
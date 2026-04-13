package com.ud.connect4ude.repositories

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.ud.connect4ude.models.Game
import com.ud.connect4ude.models.GameStatus

class GameRepository {
    val database = Firebase.database
    val gameRef = database.getReference("games")

    fun createGame(hostUid: String): String {
        val code = gameRef.push().key!!
        val game = Game(
            code = code,
            player1 = hostUid,
            player2 = null,
            status = GameStatus.WAITING,
            turnPlayerId = hostUid,
            winnerPlayerId = null,
        )
        gameRef.child(code).setValue(game)
        return code
    }

}

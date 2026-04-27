package com.ud.connect4ude.repositories

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.ud.connect4ude.models.Game
import com.ud.connect4ude.models.GameStatus

class GameRepository {
    private val database = Firebase.database
    private val gameRef = database.getReference("games")

    fun createGame(hostUid: String, onComplete: (String) -> Unit) {
        val code = gameRef.push().key ?: return
        val game = Game(
            code = code,
            player1 = hostUid,
            status = GameStatus.WAITING,
            turnPlayerId = hostUid
        )
        gameRef.child(code).setValue(game).addOnSuccessListener {
            onComplete(code)
        }
    }

    fun getGameListener(code: String, onUpdate: (Game?) -> Unit): ValueEventListener {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val game = snapshot.getValue(Game::class.java)
                onUpdate(game)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        gameRef.child(code).addValueEventListener(listener)
        return listener
    }

    fun removeListener(code: String, listener: ValueEventListener) {
        gameRef.child(code).removeEventListener(listener)
    }

    fun joinGame(code: String, userId: String, onComplete: () -> Unit) {
        val updates = mapOf(
            "player2" to userId,
            "status" to GameStatus.PLAYING
        )

        gameRef.child(code).updateChildren(updates).addOnSuccessListener {
            onComplete()
        }
    }
}

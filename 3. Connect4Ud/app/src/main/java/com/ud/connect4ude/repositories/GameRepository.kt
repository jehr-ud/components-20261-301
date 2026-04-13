package com.ud.connect4ude.repositories

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.ud.connect4ude.models.Game

class GameRepository {
    val database = Firebase.database
    val gameRef = database.getReference("games")

    fun initGame(game: Game){
        gameRef.setValue(game)
    }
}
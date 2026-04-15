package com.ud.connect4ude.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ValueEventListener
import com.ud.connect4ude.models.AuthUiState
import com.ud.connect4ude.models.Game
import com.ud.connect4ude.models.GameStatus
import com.ud.connect4ude.repositories.GameRepository
import com.ud.connect4ude.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GameViewModel(application: Application) : AndroidViewModel(application){
    private val repository = GameRepository()
    private val userPrefs = UserPreferences(application)

    private val _gameState = MutableStateFlow<Game?>(null)
    val gameState: StateFlow<Game?> = _gameState.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private var currentListener: ValueEventListener? = null
    private var currentCode: String? = null

    fun startNewGame() {
        val userId = userPrefs.getUser()?.uid

        if (userId  == null || userId.isBlank()) {
            _uiState.value = AuthUiState.Error("No existe usuario")
            return
        }

        repository.createGame(userId, ) { code ->
            currentCode = code
            listenToGame(code)
        }
    }

    fun joinGameByCode(code: String) {
        val userId = userPrefs.getUser()?.uid ?: return

        repository.joinGame(code, userId) {
            currentCode = code
            listenToGame(code)
        }
    }

    private fun listenToGame(code: String) {
        currentListener?.let { repository.removeListener(currentCode ?: "", it) }

        currentListener = repository.getGameListener(code) { updatedGame ->
            _gameState.value = updatedGame
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentListener?.let {
            currentCode?.let { code -> repository.removeListener(code, it) }
        }
    }
}
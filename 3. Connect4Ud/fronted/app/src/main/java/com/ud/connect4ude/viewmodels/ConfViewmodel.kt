
package com.ud.connect4ude.viewmodels


import androidx.lifecycle.ViewModel
import com.ud.connect4ude.models.Colors
import com.ud.connect4ude.models.GameConfig
import com.ud.connect4ude.models.Size
import com.ud.connect4ude.repositories.ConfRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConfViewModel : ViewModel() {

    private val repository = ConfRepository()

    private val _config = MutableStateFlow<GameConfig>(GameConfig(size= Size(0, 0), colors= Colors()))
    val config: StateFlow<GameConfig> = _config.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getConfig(game: String = "connect4") {
        _loading.value = true
        _error.value = null

        repository.getConfig(game) { result ->
            _loading.value = false

            if (result != null) {
                _config.value = result
            } else {
                _error.value = "Error al consumir la API"
            }
        }
    }
}
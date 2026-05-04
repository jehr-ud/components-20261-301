package com.ud.connect4ude.models

data class ConfigResponse(
    val status: Boolean,
    val data: GameConfig
)

data class GameConfig(
    val type: String="",
    val size: Size,
    val colors: Colors
)

data class Size(
    val rows: Int=0,
    val cols: Int=0
)

data class Colors(
    val main: String="",
    val second: String=""
)
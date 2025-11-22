package com.instrumentdex.instrumentdex_backend.dto.song

data class CreatePracticeSongRequest(
    val id: String,               // "guitar-001" 같은 곡 ID
    val title: String,
    val artist: String,
    val instrumentId: String,
    val level: String,
    val bpm: Int?,
    val key: String?,
    val description: String?,
    val youtubeUrl: String?,
    val sheetUrl: String?,
    val tags: List<String> = emptyList()
)

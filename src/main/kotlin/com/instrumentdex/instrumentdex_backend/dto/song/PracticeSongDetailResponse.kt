package com.instrumentdex.instrumentdex_backend.dto.song

data class PracticeSongDetailResponse(
    val id: String,
    val title: String,
    val artist: String,
    val instrumentId: String,
    val level: String,
    val bpm: Int?,
    val key: String?,
    val description: String?,
    val youtubeUrl: String?,
    val sheetUrl: String?,
    val tags: List<String>
)

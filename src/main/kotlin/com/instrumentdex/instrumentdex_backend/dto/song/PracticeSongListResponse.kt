package com.instrumentdex.instrumentdex_backend.dto.song

data class PracticeSongListResponse(
    val id: String,
    val title: String,
    val artist: String,
    val instrumentId : String,
    val level: String,
    val bpm: Int?,
    val key: String?,
    val tags: List<String>
)
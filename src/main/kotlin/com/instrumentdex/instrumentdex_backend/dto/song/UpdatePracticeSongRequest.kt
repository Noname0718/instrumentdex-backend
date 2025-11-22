package com.instrumentdex.instrumentdex_backend.dto.song

data class UpdatePracticeSongRequest(
    val title: String? = null,
    val artist: String? = null,
    val instrumentId: String? = null,
    val level: String? = null,
    val bpm: Int? = null,
    val key: String? = null,
    val description: String? = null,
    val youtubeUrl: String? = null,
    val sheetUrl: String? = null,
    val tags: List<String>? = null
)

package com.instrumentdex.instrumentdex_backend.domain.song

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "practice_songs")
data class PracticeSong(
    @Id
    val id: String,     //ex. "guitar-001"

    val title: String,
    val artist: String,

    val instrumentId: String,   //Instrument.id 참조

    val level: String,          //BEGINNER / NORMAL / HARD....
    val bpm: Int? = null,
    val key: String? = null,

    val description: String? = null,
    val youtubeUrl: String? = null,
    val sheetUrl: String? = null,

    val tags: List<String> = emptyList()
)

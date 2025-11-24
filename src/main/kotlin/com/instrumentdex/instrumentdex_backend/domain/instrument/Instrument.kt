package com.instrumentdex.instrumentdex_backend.domain.instrument

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "instruments")
data class Instrument(
    @Id
    val id: String,                     //ex: "guitar"

    val nameKo: String,
    val nameEn: String? = null,

    val family: String,                 //STRING / KEYBOARD / WOODWIND ...
    val difficultyLevel: String,        // BEGINNER / EASY / NORMAL / HARD 등

    val description: String? = null,
    val imageUrl: String? = null,

    val tags: List<String> = emptyList()
)

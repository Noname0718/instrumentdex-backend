package com.instrumentdex.instrumentdex_backend.dto.instrument

/**
 * DTO for creating new instruments.
 */
data class CreateInstrumentRequest(
    val id: String,
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val tags: List<String> = emptyList()
)

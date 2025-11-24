package com.instrumentdex.instrumentdex_backend.dto.instrument

/**
 * DTO for partial updates of an existing instrument.
 */
data class UpdateInstrumentRequest(
    val nameKo: String? = null,
    val nameEn: String? = null,
    val family: String? = null,
    val difficultyLevel: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val tags: List<String>? = null
)

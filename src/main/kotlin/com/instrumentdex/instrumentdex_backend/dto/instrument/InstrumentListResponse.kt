package com.instrumentdex.instrumentdex_backend.dto.instrument

/**
 * DTO for representing an instrument entry in list views.
 */
data class InstrumentListResponse(
    val id: String,
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val tags: List<String>
)

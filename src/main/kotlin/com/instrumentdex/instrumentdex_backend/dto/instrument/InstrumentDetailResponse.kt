package com.instrumentdex.instrumentdex_backend.dto.instrument

import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongListResponse

/**
 * DTO for detailed instrument retrieval.
 */
data class InstrumentDetailResponse(
    val id: String,
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val description: String?,
    val imageUrl: String?,
    val tags: List<String>,
    val songs: List<PracticeSongListResponse>
)

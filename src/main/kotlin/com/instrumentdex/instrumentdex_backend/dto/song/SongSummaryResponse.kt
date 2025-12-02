package com.instrumentdex.instrumentdex_backend.dto.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel

data class SongSummaryResponse(
    val id: String,
    val title: String,
    val artist: String?,
    val difficulty: DifficultyLevel?,
    val tags: List<String>,
    val sections: List<SongSectionDto> = emptyList()
)

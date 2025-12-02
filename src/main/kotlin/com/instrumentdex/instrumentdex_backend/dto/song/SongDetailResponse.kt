package com.instrumentdex.instrumentdex_backend.dto.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel

data class SongDetailResponse(
    val id: String,
    val title: String,
    val artist: String?,
    val genre: String?,
    val difficulty: DifficultyLevel?,
    val tags: List<String>,
    val sections: List<SongSectionDto>
)

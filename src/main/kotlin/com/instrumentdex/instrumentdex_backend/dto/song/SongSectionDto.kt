package com.instrumentdex.instrumentdex_backend.dto.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import com.instrumentdex.instrumentdex_backend.domain.song.SongSectionRole

data class SongSectionDto(
    val instrumentId: String,
    val level: DifficultyLevel?,
    val role: SongSectionRole,
    val youtubeUrl: String?,
    val sheetUrl: String?,
    val note: String?
)

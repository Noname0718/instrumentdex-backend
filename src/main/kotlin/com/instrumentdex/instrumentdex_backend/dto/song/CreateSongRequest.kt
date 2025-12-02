package com.instrumentdex.instrumentdex_backend.dto.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import com.instrumentdex.instrumentdex_backend.domain.song.SongSectionRole

data class CreateSongRequest(
    val id: String,
    val title: String,
    val artist: String?,
    val genre: String?,
    val difficulty: DifficultyLevel?,
    val tags: List<String> = emptyList(),
    val sections: List<CreateSongSectionRequest> = emptyList()
)

data class CreateSongSectionRequest(
    val instrumentId: String,
    val level: DifficultyLevel?,
    val role: SongSectionRole,
    val youtubeUrl: String?,
    val sheetUrl: String?,
    val note: String?
)

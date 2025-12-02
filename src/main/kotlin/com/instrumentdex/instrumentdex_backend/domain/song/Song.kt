package com.instrumentdex.instrumentdex_backend.domain.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "songs")
data class Song(
    @Id
    val id: String,
    val title: String,
    val artist: String?,
    val genre: String?,
    val difficulty: DifficultyLevel?,
    val tags: List<String> = emptyList(),
    val sections: List<SongSection> = emptyList()
)

data class SongSection(
    val instrumentId: String,
    val level: DifficultyLevel?,
    val role: SongSectionRole,
    val youtubeUrl: String?,
    val sheetUrl: String?,
    val note: String?
)

enum class SongSectionRole {
    MAIN,
    MELODY,
    ACCOMPANIMENT,
    ARRANGEMENT
}

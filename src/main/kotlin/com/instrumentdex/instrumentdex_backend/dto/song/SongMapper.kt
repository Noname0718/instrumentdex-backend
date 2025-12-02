package com.instrumentdex.instrumentdex_backend.dto.song

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import com.instrumentdex.instrumentdex_backend.domain.song.Song
import com.instrumentdex.instrumentdex_backend.domain.song.SongSection

fun Song.toDetailResponse(): SongDetailResponse =
    SongDetailResponse(
        id = id,
        title = title,
        artist = artist,
        genre = genre,
        difficulty = difficulty,
        tags = tags,
        sections = sections.map { it.toDto() }
    )

fun Song.toSummaryResponseFilteredBy(
    instrumentId: String?,
    difficulty: DifficultyLevel?
): SongSummaryResponse {
    // 1단계: 악기 기준 필터
    val byInstrument = if (instrumentId.isNullOrBlank()) {
        sections
    } else {
        sections.filter { it.instrumentId == instrumentId }
    }

    // 2단계: 난이도 기준 필터
    val byDifficulty = if (difficulty == null) {
        byInstrument
    } else {
        byInstrument.filter { it.level == difficulty }
    }

    return SongSummaryResponse(
        id = id,
        title = title,
        artist = artist,
        difficulty = difficulty,
        tags = tags,
        sections = byDifficulty.map { it.toDto() }
    )
}

fun SongSection.toDto(): SongSectionDto =
    SongSectionDto(
        instrumentId = instrumentId,
        level = level,
        role = role,
        youtubeUrl = youtubeUrl,
        sheetUrl = sheetUrl,
        note = note
    )

fun CreateSongRequest.toEntity(): Song =
    Song(
        id = id,
        title = title,
        artist = artist,
        genre = genre,
        difficulty = difficulty,
        tags = tags,
        sections = sections.map { it.toEntity() }
    )

fun CreateSongSectionRequest.toEntity(): SongSection =
    SongSection(
        instrumentId = instrumentId,
        level = level,
        role = role,
        youtubeUrl = youtubeUrl,
        sheetUrl = sheetUrl,
        note = note
    )

package com.instrumentdex.instrumentdex_backend.service

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import com.instrumentdex.instrumentdex_backend.domain.song.Song
import com.instrumentdex.instrumentdex_backend.dto.song.CreateSongRequest
import com.instrumentdex.instrumentdex_backend.dto.song.SongDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.SongSummaryResponse
import com.instrumentdex.instrumentdex_backend.dto.song.toDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.toEntity
import com.instrumentdex.instrumentdex_backend.dto.song.toSummaryResponseFilteredBy
import com.instrumentdex.instrumentdex_backend.repository.SongRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SongService(
    private val songRepository: SongRepository
) {

    @Transactional(readOnly = true)
    fun getSongs(
        instrumentId: String?,
        difficulty: DifficultyLevel?,
        tag: String?,
        keyword: String?
    ): List<SongSummaryResponse> {
        val baseSongs: List<Song> = when {
            !keyword.isNullOrBlank() ->
                songRepository.findByTitleContainingIgnoreCase(keyword)

            !instrumentId.isNullOrBlank() ->
                songRepository.findByInstrumentId(instrumentId)

            else ->
                songRepository.findAll()
        }

        return baseSongs
            .asSequence()
            .filter { song ->
                if (difficulty == null) {
                    true
                } else {
                    (song.difficulty == difficulty) || song.sections.any { it.level == difficulty }
                }
            }
            .filter { song ->
                if (tag.isNullOrBlank()) {
                    true
                } else {
                    song.tags.contains(tag)
                }
            }
            .map { song ->
                song.toSummaryResponseFilteredBy(instrumentId, difficulty)
            }
            .toList()
    }

    @Transactional(readOnly = true)
    fun getSongDetail(id: String): SongDetailResponse {
        val song = songRepository.findById(id)
            .orElseThrow { NoSuchElementException("곡을 찾을 수 없습니다. id=$id") }

        return song.toDetailResponse()
    }

    fun createSong(request: CreateSongRequest): SongDetailResponse {
        if (songRepository.existsById(request.id)) {
            throw IllegalArgumentException("이미 존재하는 곡 ID 입니다. id=${request.id}")
        }

        val saved = songRepository.save(request.toEntity())
        return saved.toDetailResponse()
    }

    fun deleteSong(id: String) {
        if (!songRepository.existsById(id)) {
            throw NoSuchElementException("삭제할 곡을 찾을 수 없습니다. id=$id")
        }
        songRepository.deleteById(id)
    }
}

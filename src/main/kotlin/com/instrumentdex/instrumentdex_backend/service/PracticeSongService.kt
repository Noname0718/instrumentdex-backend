package com.instrumentdex.instrumentdex_backend.service

import com.instrumentdex.instrumentdex_backend.domain.song.PracticeSong
import com.instrumentdex.instrumentdex_backend.dto.song.CreatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongListResponse
import com.instrumentdex.instrumentdex_backend.dto.song.UpdatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.repository.PracticeSongRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.collections.filter

@Service
class PracticeSongService (
    private val practiceSongRepository: PracticeSongRepository
){
    @Transactional(readOnly = true)
    fun getSongs(
        instrumentId: String?,
        level: String?,
        q: String?,
        tag: String?
    ): List<PracticeSongListResponse> {

        // 1차 필터: instrumentId / level / tag 를 DB 레벨에서 최대한 활용
        val baseList: List<PracticeSong> = when {
            instrumentId != null && level != null ->
                practiceSongRepository.findByInstrumentIdAndLevel(instrumentId, level)

            instrumentId != null ->
                practiceSongRepository.findByInstrumentId(instrumentId)

            level != null ->
                practiceSongRepository.findByLevel(level)

            tag != null ->
                practiceSongRepository.findByTagsContaining(tag)

            else ->
                practiceSongRepository.findAll()
        }

        // 2차 필터: q (제목/아티스트 검색은 메모리에서 간단히)
        val filtered = baseList.filter { song ->
            if (q.isNullOrBlank()) {
                true
            } else {
                val lower = q.lowercase()
                song.title.lowercase().contains(lower) ||
                        song.artist.lowercase().contains(lower)
            }
        }.filter { song ->
            // 태그 필터가 있으면서, 위에서 못 걸렀을 때 대비 (안전장치)
            if (tag.isNullOrBlank()) true
            else song.tags.contains(tag)
        }

        return filtered.map { toListDto(it) }
    }

    @Transactional(readOnly = true)
    fun getSong(id: String): PracticeSongDetailResponse {
        val song = practiceSongRepository.findById(id)
            .orElseThrow { IllegalArgumentException("연습곡을 찾을 수 없습니다. id=$id") }

        return toDetailDto(song)
    }

    @Transactional
    fun createSong(request: CreatePracticeSongRequest): PracticeSongDetailResponse {
        // 같은 ID 중복 방지
        if (practiceSongRepository.existsById(request.id)) {
            throw IllegalArgumentException("이미 존재하는 연습곡 ID입니다. id=${request.id}")
        }

        val now = Instant.now()

        val song = PracticeSong(
            id = request.id,
            title = request.title,
            artist = request.artist,
            instrumentId = request.instrumentId,
            level = request.level,
            bpm = request.bpm,
            key = request.key,
            description = request.description,
            youtubeUrl = request.youtubeUrl,
            sheetUrl = request.sheetUrl,
            tags = request.tags,
            createdAt = now,
            updatedAt = now
        )

        val saved = practiceSongRepository.save(song)
        return toDetailDto(saved)
    }

    @Transactional
    fun updateSong(id: String, request: UpdatePracticeSongRequest): PracticeSongDetailResponse {
        val song = practiceSongRepository.findById(id)
            .orElseThrow { IllegalArgumentException("연습곡을 찾을 수 없습니다. id=$id") }

        val updated = song.copy(
            title = request.title ?: song.title,
            artist = request.artist ?: song.artist,
            instrumentId = request.instrumentId ?: song.instrumentId,
            level = request.level ?: song.level,
            bpm = request.bpm ?: song.bpm,
            key = request.key ?: song.key,
            description = request.description ?: song.description,
            youtubeUrl = request.youtubeUrl ?: song.youtubeUrl,
            sheetUrl = request.sheetUrl ?: song.sheetUrl,
            tags = request.tags ?: song.tags,
            updatedAt = Instant.now()
        )

        val saved = practiceSongRepository.save(updated)
        return toDetailDto(saved)
    }

    @Transactional
    fun deleteSong(id: String) {
        if (!practiceSongRepository.existsById(id)) {
            throw IllegalArgumentException("연습곡을 찾을 수 없습니다. id=$id")
        }
        practiceSongRepository.deleteById(id)
    }

    // ================== 내부 변환 함수 ==================

    private fun toListDto(song: PracticeSong) =
        PracticeSongListResponse(
            id = song.id,
            title = song.title,
            artist = song.artist,
            instrumentId = song.instrumentId,
            level = song.level,
            bpm = song.bpm,
            key = song.key,
            tags = song.tags
        )

    private fun toDetailDto(song: PracticeSong) =
        PracticeSongDetailResponse(
            id = song.id,
            title = song.title,
            artist = song.artist,
            instrumentId = song.instrumentId,
            level = song.level,
            bpm = song.bpm,
            key = song.key,
            description = song.description,
            youtubeUrl = song.youtubeUrl,
            sheetUrl = song.sheetUrl,
            tags = song.tags
        )
}
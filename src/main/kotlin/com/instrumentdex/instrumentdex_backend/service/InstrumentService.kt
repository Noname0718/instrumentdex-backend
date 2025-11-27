package com.instrumentdex.instrumentdex_backend.service

import com.instrumentdex.instrumentdex_backend.domain.instrument.Instrument
import com.instrumentdex.instrumentdex_backend.dto.instrument.CreateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentListResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.UpdateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongListResponse
import com.instrumentdex.instrumentdex_backend.repository.InstrumentRepository
import com.instrumentdex.instrumentdex_backend.repository.PracticeSongRepository
import org.springframework.stereotype.Service

@Service
class InstrumentService(
    private val instrumentRepository: InstrumentRepository,
    private val practiceSongRepository: PracticeSongRepository
) {

    // 리스트 조회
    fun getAllInstruments(): List<InstrumentListResponse> =
        instrumentRepository.findAll()
            .map { it.toListDto() }

    // 단일 조회
    fun getInstrument(id: String): InstrumentDetailResponse {
        val instrument = instrumentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("악기를 찾을 수 없습니다. id=$id") }

        val songs = practiceSongRepository.findByInstrumentId(id)
            .map { song ->
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
            }

        return instrument.toDetailDto(songs)
    }

    // 생성
    fun createInstrument(request: CreateInstrumentRequest): InstrumentDetailResponse {
        // 같은 id 가 이미 있으면 예외
        if (instrumentRepository.existsById(request.id)) {
            throw IllegalArgumentException("Instrument id already exists: ${request.id}")
        }

        val saved = instrumentRepository.save(
            Instrument(
                id = request.id,
                nameKo = request.nameKo,
                nameEn = request.nameEn,
                family = request.family,
                difficultyLevel = request.difficultyLevel,
                description = request.description,
                imageUrl = request.imageUrl,
                tags = request.tags
            )
        )
        return saved.toDetailDto()
    }

    // 부분 수정 (PATCH)
    fun updateInstrument(id: String, request: UpdateInstrumentRequest): InstrumentDetailResponse {
        val current = instrumentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Instrument not found: $id") }

        val updated = current.copy(
            nameKo = request.nameKo ?: current.nameKo,
            nameEn = request.nameEn ?: current.nameEn,
            family = request.family ?: current.family,
            difficultyLevel = request.difficultyLevel ?: current.difficultyLevel,
            description = request.description ?: current.description,
            imageUrl = request.imageUrl ?: current.imageUrl,
            tags = request.tags ?: current.tags
        )

        val saved = instrumentRepository.save(updated)
        return saved.toDetailDto()
    }

    // 삭제
    fun deleteInstrument(id: String) {
        if (!instrumentRepository.existsById(id)) {
            throw IllegalArgumentException("Instrument not found: $id")
        }
        instrumentRepository.deleteById(id)
    }

    // ---------- 내부 변환 함수 ----------

    private fun Instrument.toListDto(): InstrumentListResponse =
        InstrumentListResponse(
            id = id,
            nameKo = nameKo,
            nameEn = nameEn,
            family = family,
            difficultyLevel = difficultyLevel,
            imageUrl = imageUrl,
            tags = tags
        )

    private fun Instrument.toDetailDto(
        songs: List<PracticeSongListResponse> = emptyList()
    ): InstrumentDetailResponse =
        InstrumentDetailResponse(
            id = id,
            nameKo = nameKo,
            nameEn = nameEn,
            family = family,
            difficultyLevel = difficultyLevel,
            description = description,
            imageUrl = imageUrl,
            tags = tags,
            songs = songs
        )
}

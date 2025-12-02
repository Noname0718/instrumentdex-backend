package com.instrumentdex.instrumentdex_backend.controller

import com.instrumentdex.instrumentdex_backend.domain.common.DifficultyLevel
import com.instrumentdex.instrumentdex_backend.dto.song.CreateSongRequest
import com.instrumentdex.instrumentdex_backend.dto.song.SongDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.SongSummaryResponse
import com.instrumentdex.instrumentdex_backend.service.SongService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/songs")
class SongController(
    private val songService: SongService
) {

    @GetMapping
    fun getSongs(
        @RequestParam(required = false) instrumentId: String?,
        @RequestParam(required = false) difficulty: DifficultyLevel?,
        @RequestParam(required = false) tag: String?,
        @RequestParam(name = "q", required = false) keyword: String?
    ): ResponseEntity<List<SongSummaryResponse>> {
        val songs = songService.getSongs(
            instrumentId = instrumentId,
            difficulty = difficulty,
            tag = tag,
            keyword = keyword
        )
        return ResponseEntity.ok(songs)
    }

    @GetMapping("/{id}")
    fun getSongDetail(
        @PathVariable id: String
    ): ResponseEntity<SongDetailResponse> = ResponseEntity.ok(songService.getSongDetail(id))

    @PostMapping
    fun createSong(
        @RequestBody request: CreateSongRequest
    ): ResponseEntity<SongDetailResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(songService.createSong(request))

    @DeleteMapping("/{id}")
    fun deleteSong(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        songService.deleteSong(id)
        return ResponseEntity.noContent().build()
    }
}

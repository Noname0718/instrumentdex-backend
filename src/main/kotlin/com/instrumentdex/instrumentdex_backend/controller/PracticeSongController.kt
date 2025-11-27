package com.instrumentdex.instrumentdex_backend.controller

import com.instrumentdex.instrumentdex_backend.dto.song.CreatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongListResponse
import com.instrumentdex.instrumentdex_backend.dto.song.UpdatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.service.PracticeSongService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/practice-songs")
@CrossOrigin(origins = ["*"])
class PracticeSongController(
    private val practiceSongService: PracticeSongService
) {
    // 연습곡 리스트 + 필터
    @GetMapping
    fun getSongs(
        @RequestParam(required = false) instrumentId: String?,
        @RequestParam(required = false) level: String?,
        @RequestParam(required = false) q: String?,
        @RequestParam(required = false) tag: String?
    ): ResponseEntity<List<PracticeSongListResponse>> {
        val songs = practiceSongService.getSongs(
            instrumentId = instrumentId,
            level = level,
            q = q,
            tag = tag
        )
        return ResponseEntity.ok(songs)
    }

    // 연습곡 상세
    @GetMapping("/{id}")
    fun getSong(@PathVariable id: String): ResponseEntity<PracticeSongDetailResponse> {
        return ResponseEntity.ok(practiceSongService.getSong(id))
    }

    // 연습곡 생성 (관리자)
    @PostMapping
    fun createSong(
        @RequestBody request: CreatePracticeSongRequest
    ): ResponseEntity<PracticeSongDetailResponse> {
        val created = practiceSongService.createSong(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    // 연습곡 부분 수정 (PATCH)
    @PatchMapping("/{id}")
    fun updateSong(
        @PathVariable id: String,
        @RequestBody request: UpdatePracticeSongRequest
    ): ResponseEntity<PracticeSongDetailResponse> {
        return ResponseEntity.ok(practiceSongService.updateSong(id, request))
    }

    // 연습곡 삭제
    @DeleteMapping("/{id}")
    fun deleteSong(@PathVariable id: String): ResponseEntity<Void> {
        practiceSongService.deleteSong(id)
        return ResponseEntity.noContent().build()
    }
}

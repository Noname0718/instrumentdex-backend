package com.instrumentdex.instrumentdex_backend.controller

import com.instrumentdex.instrumentdex_backend.dto.song.CreatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.song.PracticeSongListResponse
import com.instrumentdex.instrumentdex_backend.dto.song.UpdatePracticeSongRequest
import com.instrumentdex.instrumentdex_backend.service.PracticeSongService
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
@RequestMapping("/api")
@CrossOrigin(origins = ["*"])
class PracticeSongController(
    private val practiceSongService: PracticeSongService
) {
    // 연습곡 리스트 + 필터
    @GetMapping(value = ["/songs", "/practice-songs"])
    fun getSongs(
        @RequestParam(required = false) instrumentId: String?,
        @RequestParam(required = false) level: String?,
        @RequestParam(required = false) q: String?,
        @RequestParam(required = false) tag: String?
    ): List<PracticeSongListResponse> {
        return practiceSongService.getSongs(
            instrumentId = instrumentId,
            level = level,
            q = q,
            tag = tag
        )
    }

    // 연습곡 상세
    @GetMapping(value = ["/songs/{id}", "/practice-songs/{id}"])
    fun getSong(@PathVariable id: String): PracticeSongDetailResponse {
        return practiceSongService.getSong(id)
    }

    // 연습곡 생성 (관리자)
    @PostMapping(value = ["/songs", "/practice-songs"])
    fun createSong(
        @RequestBody request: CreatePracticeSongRequest
    ): PracticeSongDetailResponse {
        return practiceSongService.createSong(request)
    }

    // 연습곡 부분 수정 (PATCH)
    @PatchMapping(value = ["/songs/{id}", "/practice-songs/{id}"])
    fun updateSong(
        @PathVariable id: String,
        @RequestBody request: UpdatePracticeSongRequest
    ): PracticeSongDetailResponse {
        return practiceSongService.updateSong(id, request)
    }

    // 연습곡 삭제
    @DeleteMapping(value = ["/songs/{id}", "/practice-songs/{id}"])
    fun deleteSong(@PathVariable id: String) {
        practiceSongService.deleteSong(id)
    }
}

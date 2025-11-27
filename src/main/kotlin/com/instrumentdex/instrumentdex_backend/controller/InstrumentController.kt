package com.instrumentdex.instrumentdex_backend.controller

import com.instrumentdex.instrumentdex_backend.dto.instrument.CreateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentListResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.UpdateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.service.InstrumentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/instruments")
class InstrumentController(
    private val instrumentService: InstrumentService
) {

    // 리스트 조회
    @GetMapping
    fun getInstruments(): ResponseEntity<List<InstrumentListResponse>> =
        ResponseEntity.ok(
            instrumentService.getAllInstruments()
                .map { it.withAbsoluteImageUrl() }
        )

    // 단일 조회
    @GetMapping("/{id}")
    fun getInstrument(@PathVariable id: String): ResponseEntity<InstrumentDetailResponse> =
        ResponseEntity.ok(
            instrumentService.getInstrument(id).withAbsoluteImageUrl()
        )

    // 생성 (관리자)
    @PostMapping
    fun create(@RequestBody request: CreateInstrumentRequest): ResponseEntity<InstrumentDetailResponse> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(instrumentService.createInstrument(request).withAbsoluteImageUrl())

    // 부분 수정 (관리자)
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateInstrumentRequest
    ): ResponseEntity<InstrumentDetailResponse> =
        ResponseEntity.ok(
            instrumentService.updateInstrument(id, request).withAbsoluteImageUrl()
        )

    // 삭제 (관리자)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        instrumentService.deleteInstrument(id)
        return ResponseEntity.noContent().build()
    }

    private fun InstrumentListResponse.withAbsoluteImageUrl(): InstrumentListResponse =
        copy(imageUrl = resolveImageUrl(imageUrl))

    private fun InstrumentDetailResponse.withAbsoluteImageUrl(): InstrumentDetailResponse =
        copy(imageUrl = resolveImageUrl(imageUrl))

    private fun resolveImageUrl(path: String?): String? {
        if (path.isNullOrBlank()) return path
        if (path.startsWith("http", ignoreCase = true)) return path
        return ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path(path)
            .toUriString()
    }
}

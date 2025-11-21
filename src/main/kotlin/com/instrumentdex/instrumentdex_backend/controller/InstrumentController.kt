package com.instrumentdex.instrumentdex_backend.controller

import com.instrumentdex.instrumentdex_backend.dto.instrument.CreateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentDetailResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.InstrumentListResponse
import com.instrumentdex.instrumentdex_backend.dto.instrument.UpdateInstrumentRequest
import com.instrumentdex.instrumentdex_backend.service.InstrumentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/instruments")
class InstrumentController(
    private val instrumentService: InstrumentService
) {

    // 리스트 조회
    @GetMapping
    fun getInstruments(): List<InstrumentListResponse> =
        instrumentService.getAllInstruments()

    // 단일 조회
    @GetMapping("/{id}")
    fun getInstrument(@PathVariable id: String): InstrumentDetailResponse =
        instrumentService.getInstrument(id)

    // 생성 (관리자)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateInstrumentRequest): InstrumentDetailResponse =
        instrumentService.createInstrument(request)

    // 부분 수정 (관리자)
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateInstrumentRequest
    ): InstrumentDetailResponse =
        instrumentService.updateInstrument(id, request)

    // 삭제 (관리자)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        instrumentService.deleteInstrument(id)
    }
}

package com.instrumentdex.instrumentdex_backend.dto.instrument

// 리스트 조회용
data class InstrumentListResponse(
    val id: String,
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val tags: List<String>
)

// 상세 조회용
data class InstrumentDetailResponse(
    val id: String,
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val description: String?,
    val imageUrl: String?,
    val tags: List<String>
)

// 생성용 요청 DTO
data class CreateInstrumentRequest(
    val id: String,              // 클라이언트에서 지정 (예: "guitar")
    val nameKo: String,
    val nameEn: String?,
    val family: String,
    val difficultyLevel: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val tags: List<String> = emptyList()
)

// 수정용 요청 DTO (PATCH → 부분 수정 위해 전부 nullable)
data class UpdateInstrumentRequest(
    val nameKo: String? = null,
    val nameEn: String? = null,
    val family: String? = null,
    val difficultyLevel: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val tags: List<String>? = null
)

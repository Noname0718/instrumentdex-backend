package com.instrumentdex.instrumentdex_backend.repository

import com.instrumentdex.instrumentdex_backend.domain.song.PracticeSong
import org.springframework.data.mongodb.repository.MongoRepository

interface PracticeSongRepository : MongoRepository<PracticeSong, String> {
    fun findByInstrumentId(instrumentId: String): List<PracticeSong>
}

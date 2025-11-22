package com.instrumentdex.instrumentdex_backend.repository

import com.instrumentdex.instrumentdex_backend.domain.song.PracticeSong
import org.springframework.data.mongodb.repository.MongoRepository

interface PracticeSongRepository : MongoRepository<PracticeSong, String> {
    fun findByInstrumentId(instrumentId: String): List<PracticeSong>

    fun findByInstrumentIdAndLevel(instrumentId: String, level: String):List<PracticeSong>

    fun findByLevel(level: String): List<PracticeSong>

    fun findByTagsContaining(tag: String): List<PracticeSong>
}

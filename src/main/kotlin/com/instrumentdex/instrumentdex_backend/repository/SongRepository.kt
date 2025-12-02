package com.instrumentdex.instrumentdex_backend.repository

import com.instrumentdex.instrumentdex_backend.domain.song.Song
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SongRepository : MongoRepository<Song, String> {

    @Query("{ 'sections.instrumentId': ?0 }")
    fun findByInstrumentId(instrumentId: String): List<Song>

    fun findByTitleContainingIgnoreCase(keyword: String): List<Song>
}

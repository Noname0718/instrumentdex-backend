package com.instrumentdex.instrumentdex_backend.config

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.instrumentdex.instrumentdex_backend.domain.instrument.Instrument
import com.instrumentdex.instrumentdex_backend.domain.song.PracticeSong
import com.instrumentdex.instrumentdex_backend.domain.song.Song
import com.instrumentdex.instrumentdex_backend.repository.InstrumentRepository
import com.instrumentdex.instrumentdex_backend.repository.PracticeSongRepository
import com.instrumentdex.instrumentdex_backend.repository.SongRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val instrumentRepository: InstrumentRepository,
    private val practiceSongRepository: PracticeSongRepository,
    private val songRepository: SongRepository
) : CommandLineRunner{
    private val log = LoggerFactory.getLogger(javaClass)
    private val objectMapper = jacksonObjectMapper()

    override fun run(vararg args: String?) {
        if (instrumentRepository.count() > 0L) {
            log.info("Instruments already exist. Skip seeding.")
            return
        }

        val resource = ClassPathResource("data/sample-data.json")
        if (!resource.exists()) {
            log.warn("sample-data.json not found. Skip seeding.")
            return
        }

        resource.inputStream.use { input ->
            val root: JsonNode = objectMapper.readTree(input)

            val instrumentsNode = root["instruments"]
            val practiceSongsNode = root["practice_songs"]
            val songsNode = root["songs"]

            if (instrumentsNode != null && instrumentsNode.isArray) {
                val instruments: List<Instrument> =
                    objectMapper.readValue(instrumentsNode.toString())
                instrumentRepository.saveAll(instruments)
                log.info("Inserted ${instruments.size} instruments.")
            }

            if (practiceSongsNode != null && practiceSongsNode.isArray) {
                val songs: List<PracticeSong> =
                    objectMapper.readValue(practiceSongsNode.toString())
                practiceSongRepository.saveAll(songs)
                log.info("Inserted ${songs.size} practice songs.")
            }

            if (songsNode != null && songsNode.isArray) {
                val songs: List<Song> =
                    objectMapper.readValue(songsNode.toString())
                songRepository.saveAll(songs)
                log.info("Inserted ${songs.size} songs.")
            }
        }
    }
}

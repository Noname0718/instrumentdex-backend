package com.instrumentdex.instrumentdex_backend.repository

import com.instrumentdex.instrumentdex_backend.domain.instrument.Instrument
import org.springframework.data.mongodb.repository.MongoRepository

interface InstrumentRepository : MongoRepository<Instrument, String> {
}

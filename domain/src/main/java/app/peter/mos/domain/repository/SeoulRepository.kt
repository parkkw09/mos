package app.peter.mos.domain.repository

import app.peter.mos.domain.model.seoul.CulturalEventPage

interface SeoulRepository {
    suspend fun getCulturalEvents(start: Int, end: Int): CulturalEventPage
}

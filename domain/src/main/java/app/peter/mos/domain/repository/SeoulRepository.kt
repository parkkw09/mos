package app.peter.mos.domain.repository

import app.peter.mos.domain.model.CulturalEvent

interface SeoulRepository {
    suspend fun getCulturalEvents(forceRefresh: Boolean = false): List<CulturalEvent>
}

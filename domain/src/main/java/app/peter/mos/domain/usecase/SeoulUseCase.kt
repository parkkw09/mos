package app.peter.mos.domain.usecase

import app.peter.mos.domain.model.seoul.CulturalEvent
import app.peter.mos.domain.repository.SeoulRepository
import javax.inject.Inject

class SeoulUseCase @Inject constructor(private val repository: SeoulRepository) {
    suspend operator fun invoke(): List<CulturalEvent> = repository.getCulturalEvents()
}

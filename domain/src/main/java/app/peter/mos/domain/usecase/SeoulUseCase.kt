package app.peter.mos.domain.usecase

import app.peter.mos.domain.model.CulturalEvent
import app.peter.mos.domain.repository.SeoulRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeoulUseCase @Inject constructor(private val repository: SeoulRepository) {
    suspend operator fun invoke(): List<CulturalEvent> =
            withContext(Dispatchers.IO) { repository.getCulturalEvents() }
}

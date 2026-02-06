package app.peter.mos.data.repositories

import app.peter.mos.data.source.local.dao.CulturalEventDao
import app.peter.mos.data.source.local.entity.CulturalEventEntity
import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfo
import app.peter.mos.data.source.remote.SeoulApi
import app.peter.mos.domain.model.CulturalEvent
import app.peter.mos.domain.repository.SeoulRepository
import javax.inject.Inject

class SeoulRepositoryImpl
@Inject
constructor(private val api: SeoulApi, private val culturalEventDao: CulturalEventDao) :
        SeoulRepository {

    // Condition 1: Check if this session has fetched remote data at least once
    private var isSessionCacheValid = false

    suspend fun getCulturalEventFromRemote(
            pageStart: Int = 1,
            pageEnd: Int = 5
    ): CulturalEventInfo {
        return api.getCulturalEvent(pageStart, pageEnd).info
    }

    override suspend fun getCulturalEvents(forceRefresh: Boolean): List<CulturalEvent> {
        val localData = culturalEventDao.getAll()

        // Logical combination of the conditions:
        // Condition 2: localData.isEmpty()
        // Condition 1: !isSessionCacheValid
        // Condition 3 & 4: forceRefresh
        val shouldFetchRemote = forceRefresh || localData.isEmpty() || !isSessionCacheValid

        return if (!shouldFetchRemote) {
            localData.map { it.toDomain() }
        } else {
            try {
                val remoteData = getCulturalEventFromRemote(pageEnd = 100)
                val entities =
                        remoteData.list.map {
                            CulturalEventEntity(
                                    title = it.title,
                                    date = it.date,
                                    place = it.place,
                                    mainImage = it.mainImage,
                                    orgName = it.orgName,
                                    useFee = it.useFee
                            )
                        }

                // Update local storage
                culturalEventDao.deleteAll()
                culturalEventDao.insertAll(entities)

                // Set session flag to true after successful remote fetch (Condition 1)
                isSessionCacheValid = true

                entities.map { it.toDomain() }
            } catch (e: Exception) {
                // Return local data as fallback if remote fails
                if (localData.isNotEmpty()) {
                    localData.map { it.toDomain() }
                } else {
                    throw e
                }
            }
        }
    }

    // ... remainder of the helper methods
    private fun CulturalEventEntity.toDomain() =
            CulturalEvent(
                    title = title,
                    date = date,
                    place = place,
                    mainImage = mainImage,
                    orgName = orgName,
                    useFee = useFee
            )
}

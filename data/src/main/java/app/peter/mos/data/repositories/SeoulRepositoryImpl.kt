package app.peter.mos.data.repositories

import app.peter.mos.data.di.IoDispatcher
import app.peter.mos.data.source.local.dao.CulturalEventDao
import app.peter.mos.data.source.local.entity.CulturalEventEntity
import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfo
import app.peter.mos.data.source.remote.SeoulApi
import app.peter.mos.domain.model.seoul.CulturalEvent
import app.peter.mos.domain.repository.SeoulRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SeoulRepositoryImpl
@Inject
constructor(
        private val api: SeoulApi,
        private val culturalEventDao: CulturalEventDao,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SeoulRepository {

    // Condition 1: Check if this session has fetched remote data at least once
    private var isSessionCacheValid = false

    suspend fun getCulturalEventFromRemote(
            pageStart: Int = 1,
            pageEnd: Int = 5
    ): CulturalEventInfo {
        return api.getCulturalEvent(pageStart, pageEnd).info
    }

    override suspend fun getCulturalEvents(forceRefresh: Boolean): List<CulturalEvent> =
            withContext(ioDispatcher) {
                val localData = culturalEventDao.getAll()

                // Logical combination of the conditions:
                // Condition 2: localData.isEmpty()
                // Condition 1: !isSessionCacheValid
                // Condition 3 & 4: forceRefresh
                val shouldFetchRemote = forceRefresh || localData.isEmpty() || !isSessionCacheValid

                return@withContext if (!shouldFetchRemote) {
                    localData.map { it.toDomain() }
                } else {
                    try {
                        val remoteData = getCulturalEventFromRemote(pageEnd = 100)
                        val entities =
                                remoteData.list.map {
                                    CulturalEventEntity(
                                            title = it.title,
                                            codeName = it.codeName,
                                            guName = it.guName,
                                            date = it.date,
                                            place = it.place,
                                            orgName = it.orgName,
                                            useTarget = it.useTarget,
                                            useFee = it.useFee,
                                            inquiry = it.inquiry,
                                            player = it.player,
                                            program = it.program,
                                            etcDesc = it.etcDesc,
                                            orgLink = it.orgLink,
                                            mainImage = it.mainImage,
                                            registrationDate = it.registrationDate,
                                            ticket = it.ticket,
                                            startDate = it.startDate,
                                            endDate = it.endDate,
                                            themeCode = it.themeCode,
                                            lot = it.lot,
                                            lat = it.lat,
                                            isFree = it.isFree,
                                            homepageAddr = it.homepageAddr,
                                            proTime = it.proTime
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
                    codeName = codeName,
                    guName = guName,
                    date = date,
                    place = place,
                    orgName = orgName,
                    useTarget = useTarget,
                    useFee = useFee,
                    inquiry = inquiry,
                    player = player,
                    program = program,
                    etcDesc = etcDesc,
                    orgLink = orgLink,
                    mainImage = mainImage,
                    registrationDate = registrationDate,
                    ticket = ticket,
                    startDate = startDate,
                    endDate = endDate,
                    themeCode = themeCode,
                    lot = lot,
                    lat = lat,
                    isFree = isFree,
                    homepageAddr = homepageAddr,
                    proTime = proTime
            )
}

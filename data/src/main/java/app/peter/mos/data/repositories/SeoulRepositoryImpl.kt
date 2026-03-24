package app.peter.mos.data.repositories

import app.peter.mos.data.di.IoDispatcher
import app.peter.mos.data.source.local.dao.CulturalEventDao
import app.peter.mos.data.source.local.entity.CulturalEventEntity
import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfo
import app.peter.mos.data.source.remote.SeoulApi
import app.peter.mos.domain.model.seoul.CulturalEvent
import app.peter.mos.domain.model.seoul.CulturalEventPage
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

    private suspend fun fetchFromRemote(start: Int, end: Int): CulturalEventInfo {
        return api.getCulturalEvent(start, end).info
    }

    override suspend fun getCulturalEvents(start: Int, end: Int): CulturalEventPage =
            withContext(ioDispatcher) {
                try {
                    val remoteInfo = fetchFromRemote(start, end)
                    val entities =
                            remoteInfo.list.map {
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

                    if (start == 1) culturalEventDao.deleteAll()
                    culturalEventDao.insertAll(entities)

                    CulturalEventPage(
                            events = entities.map { it.toDomain() },
                            totalCount = remoteInfo.count
                    )
                } catch (e: Exception) {
                    if (start == 1) {
                        val localData = culturalEventDao.getAll()
                        if (localData.isNotEmpty()) {
                            CulturalEventPage(
                                    events = localData.map { it.toDomain() },
                                    totalCount = localData.size
                            )
                        } else {
                            throw e
                        }
                    } else {
                        throw e
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

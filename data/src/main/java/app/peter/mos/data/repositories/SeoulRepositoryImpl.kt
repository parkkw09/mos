package app.peter.mos.data.repositories

import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfo
import app.peter.mos.data.source.remote.SeoulApi
import app.peter.mos.data.tool.network.Network
import app.peter.mos.domain.model.CulturalEvent
import app.peter.mos.domain.repository.SeoulRepository
import javax.inject.Inject

class SeoulRepositoryImpl @Inject constructor(private val key: String) : SeoulRepository {

    private val api = SeoulApi(Network.getClient(), key)

    suspend fun getCulturalEvent(pageStart: Int = 1, pageEnd: Int = 5): CulturalEventInfo {
        return api.getCulturalEvent(pageStart, pageEnd).info
    }

    override suspend fun getCulturalEvents(): List<CulturalEvent> {
        val result = getCulturalEvent(pageEnd = 100)
        return result.list.map {
            CulturalEvent(
                    title = it.title,
                    date = it.date,
                    place = it.place,
                    mainImage = it.mainImage,
                    orgName = it.orgName,
                    useFee = it.useFee
            )
        }
    }
}

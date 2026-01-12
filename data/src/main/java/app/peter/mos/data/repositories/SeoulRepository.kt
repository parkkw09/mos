package app.peter.mos.data.repositories

import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfo
import app.peter.mos.data.source.remote.SeoulApi
import app.peter.mos.data.tool.network.Network

class SeoulRepository(private val key: String) {

    private val api = SeoulApi(Network.getClient(), key)

    suspend fun getCulturalEvent(pageStart: Int = 1, pageEnd: Int = 5): CulturalEventInfo {
        return api.getCulturalEvent(pageStart, pageEnd).info
    }
}
package app.peter.mos.data.source.remote

import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeoulApi(private val client: HttpClient,
               private val key: String) {

    private val baseUrl = "http://openapi.seoul.go.kr:8088"

    suspend fun getCulturalEvent(pageStart: Int = 1, pageEnd: Int = 5): CulturalEventInfoResponse {
        return client.get("${baseUrl}/${key}/json/culturalEventInfo/${pageStart}/${pageEnd}").body()
    }
}
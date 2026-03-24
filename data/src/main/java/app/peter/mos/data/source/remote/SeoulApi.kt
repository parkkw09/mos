package app.peter.mos.data.source.remote

import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Named

class SeoulApi
@Inject
constructor(private val client: HttpClient, @param:Named("seoul_key") private val key: String) {

    private val baseUrl = "http://openapi.seoul.go.kr:8088"

    suspend fun getCulturalEvent(start: Int, end: Int): CulturalEventInfoResponse {
        return client.get("${baseUrl}/${key}/json/culturalEventInfo/${start}/${end}").body()
    }
}

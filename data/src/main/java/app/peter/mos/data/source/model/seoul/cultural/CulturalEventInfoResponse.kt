package app.peter.mos.data.source.model.seoul.cultural

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CulturalEventInfoResponse(@SerialName("culturalEventInfo") val info: CulturalEventInfo)

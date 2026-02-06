package app.peter.mos.data.source.model.seoul.cultural

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CulturalEventInfo(
        @SerialName("list_total_count") val count: Int,
        @SerialName("RESULT") val result: CulturalEventInfoResult,
        @SerialName("row") val list: List<CulturalEventInfoData>
)

@Serializable
data class CulturalEventInfoResult(
        @SerialName("CODE") val code: String,
        @SerialName("MESSAGE") val message: String
)

@Serializable
data class CulturalEventInfoData(
        @SerialName("CODENAME") val codeName: String,
        @SerialName("GUNAME") val guName: String,
        @SerialName("TITLE") val title: String,
        @SerialName("DATE") val date: String,
        @SerialName("PLACE") val place: String,
        @SerialName("ORG_NAME") val orgName: String,
        @SerialName("USE_TRGT") val useTarget: String,
        @SerialName("USE_FEE") val useFee: String,
        @SerialName("PLAYER") val player: String,
        @SerialName("PROGRAM") val program: String,
        @SerialName("ETC_DESC") val etcDesc: String,
        @SerialName("ORG_LINK") val orgLink: String,
        @SerialName("MAIN_IMG") val mainImage: String,
        @SerialName("RGSTDATE") val registrationDate: String,
        @SerialName("TICKET") val ticket: String,
        @SerialName("STRTDATE") val startDate: String,
        @SerialName("END_DATE") val endDate: String,
        @SerialName("THEMECODE") val themeCode: String
)

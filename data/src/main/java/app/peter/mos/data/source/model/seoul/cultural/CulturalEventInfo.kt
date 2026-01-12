package app.peter.mos.data.source.model.seoul.cultural

import com.google.gson.annotations.SerializedName

data class CulturalEventInfo(
    @SerializedName("list_total_count") val count: Int,
    @SerializedName("RESULT") val result: CulturalEventInfoResult,
    @SerializedName("row") val list: List<CulturalEventInfoData>
)

data class CulturalEventInfoResult(
    @SerializedName("CODE") val code: String,
    @SerializedName("MESSAGE") val message: String
)

data class CulturalEventInfoData(
    @SerializedName("CODENAME") val codeName: String,
    @SerializedName("GUNAME") val guName: String,
    @SerializedName("TITLE") val title: String,
    @SerializedName("DATE") val date: String,
    @SerializedName("PLACE") val place: String,
    @SerializedName("ORG_NAME") val orgName: String,
    @SerializedName("USE_TRGT") val useTarget: String,
    @SerializedName("USE_FEE") val useFee: String,
    @SerializedName("PLAYER") val player: String,
    @SerializedName("PROGRAM") val program: String,
    @SerializedName("ETC_DESC") val etcDesc: String,
    @SerializedName("ORG_LINK") val orgLink: String,
    @SerializedName("MAIN_IMG") val mainImage: String,
    @SerializedName("RGSTDATE") val registrationDate: String,
    @SerializedName("TICKET") val ticket: String,
    @SerializedName("STRTDATE") val startDate: String,
    @SerializedName("END_DATE") val endDate: String,
    @SerializedName("THEMECODE") val themeCode: String
)
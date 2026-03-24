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
        @SerialName("CODE") val code: String = "",
        @SerialName("MESSAGE") val message: String = ""
)

@Serializable
data class CulturalEventInfoData(
        @SerialName("CODENAME") val codeName: String = "",         // 1. 분류
        @SerialName("GUNAME") val guName: String = "",             // 2. 자치구
        @SerialName("TITLE") val title: String = "",               // 3. 공연/행사명
        @SerialName("DATE") val date: String = "",                 // 4. 날짜
        @SerialName("PLACE") val place: String = "",               // 5. 장소
        @SerialName("ORG_NAME") val orgName: String = "",          // 6. 기관명
        @SerialName("USE_TRGT") val useTarget: String = "",        // 7. 이용대상
        @SerialName("USE_FEE") val useFee: String = "",            // 8. 이용요금
        @SerialName("INQUIRY") val inquiry: String = "",           // 9. 문의
        @SerialName("PLAYER") val player: String = "",             // 10. 출연자정보
        @SerialName("PROGRAM") val program: String = "",           // 11. 프로그램소개
        @SerialName("ETC_DESC") val etcDesc: String = "",          // 12. 기타내용
        @SerialName("ORG_LINK") val orgLink: String = "",          // 13. 홈페이지 주소
        @SerialName("MAIN_IMG") val mainImage: String = "",        // 14. 대표이미지
        @SerialName("RGSTDATE") val registrationDate: String = "", // 15. 신청일
        @SerialName("TICKET") val ticket: String = "",             // 16. 시민/기관
        @SerialName("STRTDATE") val startDate: String = "",        // 17. 시작일
        @SerialName("END_DATE") val endDate: String = "",          // 18. 종료일
        @SerialName("THEMECODE") val themeCode: String = "",       // 19. 테마분류
        @SerialName("LOT") val lot: String = "",                   // 20. 경도(Y좌표)
        @SerialName("LAT") val lat: String = "",                   // 21. 위도(X좌표)
        @SerialName("IS_FREE") val isFree: String = "",            // 22. 유무료
        @SerialName("HMPG_ADDR") val homepageAddr: String = "",    // 23. 문화포털상세URL
        @SerialName("PRO_TIME") val proTime: String = ""           // 24. 행사시간
)

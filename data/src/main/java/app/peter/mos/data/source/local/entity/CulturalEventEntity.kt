package app.peter.mos.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultural_events")
data class CulturalEventEntity(
        @PrimaryKey val title: String,         // 3. 공연/행사명 (PK)
        val codeName: String,                 // 1. 분류
        val guName: String,                   // 2. 자치구
        val date: String,                     // 4. 날짜
        val place: String,                    // 5. 장소
        val orgName: String,                  // 6. 기관명
        val useTarget: String,                // 7. 이용대상
        val useFee: String,                   // 8. 이용요금
        val inquiry: String,                  // 9. 문의
        val player: String,                   // 10. 출연자정보
        val program: String,                  // 11. 프로그램소개
        val etcDesc: String,                  // 12. 기타내용
        val orgLink: String,                  // 13. 홈페이지 주소
        val mainImage: String,                // 14. 대표이미지
        val registrationDate: String,         // 15. 신청일
        val ticket: String,                   // 16. 시민/기관
        val startDate: String,                // 17. 시작일
        val endDate: String,                  // 18. 종료일
        val themeCode: String,                // 19. 테마분류
        val lot: String,                      // 20. 경도(Y좌표)
        val lat: String,                      // 21. 위도(X좌표)
        val isFree: String,                   // 22. 유무료
        val homepageAddr: String,             // 23. 문화포털상세URL
        val proTime: String,                  // 24. 행사시간
        val createdAt: Long = System.currentTimeMillis()
)

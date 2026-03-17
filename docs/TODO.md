# 📋 MOS 프로젝트 할 일 목록 (TODO)

이 문서는 프로젝트의 향후 계획, 개선 사항 및 액션 아이템을 통합 관리하는 유일한 소스입니다.

---

## ✅ 완료된 작업 이력

| 날짜 | 작업 내용 |
|---|---|
| 2026-02-13 | Google/YouTube API 연동 (`GoogleRepositoryImpl`, `GoogleAuthInterceptor`, DataStore OAuth 토큰 관리) |
| 2026-03-17 | Data 모듈 전용 코루틴 Qualifier 분리 (`CoroutineQualifiers`, `DispatchersModule`) |
| 2026-03-17 | `SeoulRepositoryImpl` — `@IoDispatcher` Hilt 주입 방식으로 `withContext` 리팩토링 |
| 2026-03-17 | `SeoulUseCase` — `withContext` 제거, IO 전환 책임을 Repository 계층으로 이관 |
| 2026-03-17 | `Preference` — `@param:ApplicationContext` 어노테이션 설정으로 Kotlin 어노테이션 경고 해소 |

---

## 🎯 우선순위 로드맵

### 🔴 1순위: 핵심 기능 완성 (High Priority)

- [ ] **Google UseCase 구현**
  - `GoogleRepository`를 활용하는 UseCase 클래스 작성
  - ViewModel과 연동하여 구독/재생목록 데이터 노출

- [ ] **Google OAuth 로그인 화면 구현**
  - 사용자 로그인 플로우 UI (로그인 버튼 → 토큰 발급 → DataStore 저장)
  - `Preference.saveGoogleAccessToken()` 활용

- [ ] **Navigation 적용**
  - Jetpack Navigation Compose 도입
  - 화면 전환 및 백스택 관리 체계 수립

- [ ] **상세 화면(Detail Screen) 구현**
  - 문화행사 아이템 클릭 시 상세 정보(이미지, 지도, 연락처, 상세 설명 등) 표시
  - Navigation과 연계하여 라우팅

- [ ] **메인 화면 UI/UX 개선**
  - 리스트 레이아웃 개선 (현재 텍스트 Only → 카드 뷰, 이미지 포함)
  - Pull-to-Refresh 기능 (`forceRefresh = true` 파라미터 활용)
  - 무한 스크롤 (Paging) 지원 및 Repository 전략 보완

---

### 🟡 2순위: 코드 품질 및 안정성 (Medium Priority)

- [ ] **단위 테스트 (Unit Test) 작성**
  - `SeoulUseCase` — Mock Repository 활용
  - `MainViewModel` — UI 상태 변화(Loading → Complete/Error) 및 중복 호출 방지 검증
  - `SeoulRepositoryImpl` — Cache-then-Network 전략 분기 케이스별 테스트

- [ ] **에러 핸들링 UI 강화**
  - 네트워크 단절, API 에러 시 사용자 친화적 안내 (SnackBar, ErrorScreen 컴포저블)
  - 현재 `loadState` 문자열 기반 → sealed class/enum 기반 상태 모델로 개선

- [ ] **오프라인 완전 지원**
  - 앱 첫 진입 시 오프라인 상태일 경우 Room 캐시 우선 노출 시나리오 보완
  - `CulturalEventEntity.createdAt` 필드를 활용한 캐시 만료 전략 추가

- [ ] **`GoogleAuthInterceptor` 코루틴 개선**
  - 현재 `runBlocking` 사용 → OkHttp Interceptor의 스레드 제약 내 안전한 비동기 처리 방안 검토

---

### 🟢 3순위: 운영 및 고도화 (Low Priority)

- [ ] **태그 기반 검색 및 필터링**
  - 장르별, 지역별, 유/무료 필터링 기능 추가

- [ ] **다국어 지원 (i18n)**
  - 시스템 리소스 다국어화 (`strings.xml` 기반)

- [ ] **코드 스타일 및 정적 분석**
  - `ktlint` 및 `detekt` 적용으로 코드 일관성 유지

- [ ] **CI/CD 환경 구축**
  - GitHub Actions를 통한 자동 빌드 및 테스트 (PR 시 체크)

---

## 🛠️ 백로그 (Backlog)

- [ ] 디자인 시스템(Design System) 정립 및 테마 고도화 (`MosTheme` 확장)
- [ ] 홈 화면 위젯 구현
- [ ] 푸시 알림 (행사 시작 전 알림 등)
- [ ] 상세뷰 데이터 모델 전략 정의
- [ ] 성능 테스트 전략 수립
- [ ] `GoogleRepository` 관련 UseCase 구현 시 `DefaultDispatcher` / `IoDispatcher` 활용 전략 정의
- [ ] `Local.kt` 클래스 활용 계획 정립 (현재 빈 클래스)

---

*마지막 업데이트: 2026-03-17*
*관련 문서: [ARCHITECTURE.md](./ARCHITECTURE.md)*

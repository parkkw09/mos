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
| 2026-03-17 | `loadState` 타입을 String → `LoadState` sealed interface로 개선 |
| 2026-03-17 | `GoogleAuthState` sealed interface 구현 (`Unauthenticated`, `Authenticating`, `Authenticated`, `Error`) |
| 2026-03-17 | Google UseCase 구현 (`GetSubscriptionsUseCase`, `GetPlaylistUseCase`, `GetContentDetailUseCase`, `SaveGoogleTokenUseCase`, `ClearGoogleTokenUseCase`) |
| 2026-03-17 | `GoogleRepository`에 `saveAccessToken`, `clearAccessToken` 규약 추가 및 `GoogleRepositoryImpl` 구현 |
| 2026-03-31 | 페이지네이션 구현 (`CulturalEventPage` 도메인 모델, `SeoulUseCase(start, end)`, `MainViewModel.loadNextPage()`) |
| 2026-03-31 | `MainScreen` 무한스크롤 구현 (리스트 하단 3개 항목 진입 시 자동 로드) |
| 2026-03-31 | `MainViewModel.refresh()` 구현 (전체 상태 초기화 후 첫 페이지 재로드) |
| 2026-03-31 | `GoogleSignInManager` 구현 (Google Identity SDK OAuth, `suspendCancellableCoroutine` 코루틴 래핑) |
| 2026-03-31 | `MainActivity`에서 앱 시작 시 Google OAuth 자동 실행 및 토큰 DataStore 저장 연동 |
| 2026-03-31 | `SeoulRepositoryImpl` — 세션 캐시 플래그 방식 제거, Remote-First + Fallback 전략으로 단순화 |
| 2026-04-09 | `GoogleAuthInterceptor` — `runBlocking` 제거, `Preference`에 `StateFlow` 캐시 도입으로 OkHttp 스레드 블로킹 해소 |

---

## 🎯 우선순위 로드맵

### 🔴 1순위: 핵심 기능 완성 (High Priority)

- [ ] **Navigation 적용**
  - Jetpack Navigation Compose 도입
  - 화면 전환 및 백스택 관리 체계 수립
  - 현재 화면이 `MainScreen` 단일 컴포저블이므로 라우팅 구조 설계 선행 필요

- [ ] **상세 화면(Detail Screen) 구현**
  - `CulturalEvent`의 모든 필드(이미지, 지도 좌표, 행사시간, 문의처 등) 표시
  - Navigation과 연계하여 리스트 아이템 클릭 → 상세 화면 라우팅

- [ ] **메인 화면 UI/UX 개선**
  - 리스트 레이아웃 개선 (현재 텍스트 + 구분선만 → 카드뷰, `mainImage` 이미지 로딩 포함)
  - Pull-to-Refresh 기능 연결 (`MainViewModel.refresh()`는 이미 구현됨 — UI 제스처만 연결 필요)

- [ ] **Google 로그인 상태에 따른 화면 분기**
  - 현재는 `Toast`만 표시 — `googleAuthState`를 활용한 로그인/로그아웃 UI 구현
  - 인증 전/후 사용 가능한 기능 분기

- [ ] **📍 위치 기반 주변 행사 탐색 — 핵심 목표 기능**
  - `CulturalEvent`의 `lot`(경도), `lat`(위도) 필드를 활용하여 사용자 현재 위치 기반으로 주변 행사를 찾아준다.
  - 이 기능이 이 앱의 존재 이유 중 하나다. 단순 행사 목록 나열이 아니라, **지금 내 주변에서 무슨 일이 벌어지는지**를 즉각 알려주는 것이 핵심이다.
  - 캐싱 전략과 강한 시너지: 데이터가 로컬에 캐싱되어 있으면 서버 없이 **즉시** 거리 계산 가능 → 위치 기반 탐색이 곧 캐싱의 킬러 유스케이스.

  **사전 요건:**
  - Navigation 도입 완료 후 진행 (위치 기반 전용 화면 필요)
  - `lot` / `lat` 필드가 현재 `String` 타입 → `Double?`로 타입 정제 또는 파싱 레이어 추가 필요 (빈 문자열, 좌표 누락 행사 처리)

  **구현 단계:**
  1. Android 위치 권한 요청 흐름 구현 (`ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION`)
  2. 사용자 현재 위치 취득 (Fused Location Provider)
  3. 거리 계산 로직 — `android.location.Location.distanceBetween()` 활용, `@DefaultDispatcher`에서 실행 (현재 미사용 Dispatcher 활용 기회)
  4. 거리 기준 정렬 및 반경 필터링 (예: 3km / 5km / 10km)
  5. 리스트 UI에 거리 표시 (예: "도보 15분", "2.3km")

  **확장 방향 (선택):**
  - 지도 뷰 (Google Maps 또는 Naver Maps) 위에 행사 핀 표시
  - 현재 위치 자동 갱신 + 이동 감지 시 주변 행사 리스트 재계산

---

### 🟡 2순위: 코드 품질 및 안정성 (Medium Priority)

- [ ] **단위 테스트 (Unit Test) 작성**
  - `SeoulUseCase` — Mock Repository 활용
  - `MainViewModel` — UI 상태 변화(Idle → Loading → Success/Error) 및 페이지네이션, 중복 호출 방지 검증
  - `SeoulRepositoryImpl` — Remote-First 성공/실패(Fallback) 케이스별 테스트

- [ ] **에러 핸들링 UI 강화**
  - 네트워크 단절, API 에러 시 재시도 버튼 포함 에러 화면 (SnackBar 또는 ErrorScreen 컴포저블)
  - 현재 에러 시 텍스트만 표시되고 재시도 수단 없음

- [ ] **로컬 캐싱 전략 재설계 — 네트워크 호출 최적화**
  - 로컬 캐싱의 목적은 오프라인 대응이 아니라 **불필요한 네트워크 호출을 줄여 사용자 스트레스를 낮추는 것**이다.
  - 요즘 앱들이 당연하게 여기는 "매번 서버에서 긁어오기" 관행의 뒤통수를 치는 전략이 필요하다.
  - 서버가 캐시 힌트(`ETag`, `Cache-Control` 등)를 제공하지 않더라도, **클라이언트 스스로 최적화**할 수 있음을 보여줘야 한다.
  - Pull-to-Refresh는 어느 전략이든 사용자의 **명시적 강제 갱신** 수단으로만 사용한다.

  **후보 전략 (하나를 선택하거나 조합):**

  > **전략 A — TTL 기반 Cache-First**
  > - `createdAt` 기준으로 유효 시간(예: 1시간) 내면 API 호출 없이 Room 즉시 반환.
  > - 만료된 경우에만 API 호출 후 캐시 갱신.
  > - 구현 단순. 데이터 신선도는 TTL 주기에 종속됨.

  > **전략 B — Stale-While-Revalidate**
  > - 캐시가 있으면 **즉시** 화면에 표시 (체감 로딩 없음).
  > - 동시에 백그라운드에서 API 호출 → 새 데이터 도착 시 UI 조용히 갱신.
  > - 항상 최신 데이터를 유지하면서도 사용자는 로딩을 기다리지 않음.
  > - 구현 복잡도 중간. 가장 공격적인 UX 개선 효과.

  > **전략 C — TTL + Stale-While-Revalidate 혼합**
  > - TTL 이내: 캐시만 사용 (백그라운드 갱신 없음, 서버 부하 최소).
  > - TTL 초과: 전략 B처럼 캐시 즉시 표시 + 백그라운드 갱신.
  > - 신선도와 서버 부하 사이의 균형점. 가장 세밀한 제어 가능.

- [x] **`GoogleAuthInterceptor` 코루틴 개선**
  - 현재 `runBlocking` 사용 → OkHttp Interceptor의 스레드 제약 내 안전한 비동기 처리 방안 검토

---

### 🟢 3순위: 운영 및 고도화 (Low Priority)

- [ ] **태그 기반 검색 및 필터링**
  - 장르별(`codeName`), 지역별(`guName`), 유/무료(`isFree`) 필터링 기능 추가

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
- [ ] `GoogleRepository` 관련 UseCase를 ViewModel에 연동하여 구독/재생목록 화면 구현
- [ ] `DefaultDispatcher` 활용 전략 정의 (현재 `@IoDispatcher`만 실제 사용 중)
- [ ] `Local.kt` 클래스 활용 계획 정립 (현재 빈 클래스)
- [ ] 성능 테스트 전략 수립

---

*마지막 업데이트: 2026-04-09 (`GoogleAuthInterceptor` 코루틴 개선 완료)*
*관련 문서: [ARCHITECTURE.md](./ARCHITECTURE.md)*

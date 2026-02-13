# 📋 MOS 프로젝트 변경 이력 (CHANGELOG)

이 문서는 프로젝트의 모든 작업 이력을 시간순으로 기록합니다.  
**새로운 챗이 시작될 때 반드시 이 문서를 먼저 읽어주세요.**

---

## [2026-02-13] - Google/YouTube API 연동 구현 및 인증 체계 구축

### 완료한 작업
- **Google/YouTube API 연동 구현체 완성**:
  - `GoogleRepositoryImpl`에 `getSubscriptions()`, `getPlaylist()`, `getContentDetail()` 실구현
  - Data Layer 모델(`Subscription`, `Playlist`, `PlaylistItem`)에서 Domain 모델로의 매핑 함수 구성
- **도메인 모델 신규 생성**:
  - `Subscription`: YouTube 구독 정보 (채널 ID, 썸네일, 제목 등)
  - `PlayList`: 재생목록 정보 (채널 ID, 제목, 로컬라이즈 등)
  - `PlayItem`: 재생목록 아이템 정보 (영상 ID, 플레이리스트 ID 등)
- **GoogleRepository 인터페이스 구체화**:
  - 기존 placeholder(`test()`)를 실제 비즈니스 메서드 3개로 교체
- **GoogleApi 엔드포인트 정의**:
  - `/youtube/v3/subscriptions` - 구독 목록 조회
  - `/youtube/v3/playlists` - 재생목록 조회
  - `/youtube/v3/playlistItems` - 재생목록 아이템 상세 조회
- **Google OAuth 인증 체계 구축**:
  - `GoogleAuthInterceptor` (OkHttp Interceptor) 생성 - Bearer 토큰 자동 첨부
  - `Preference` 클래스에 DataStore 기반 액세스 토큰 관리 (저장/조회/삭제) 구현
- **DI 아키텍처 개선**:
  - `DataModule`의 Retrofit 생성 방식을 `Retrofit.Builder` 패턴으로 리팩토링
  - Google API 전용 OkHttpClient에 `GoogleAuthInterceptor` 주입
- **의존성 추가**:
  - `androidx.datastore:datastore-preferences:1.1.0` 추가

### 변경된 파일
- `data/src/main/java/app/peter/mos/data/repositories/GoogleRepositoryImpl.kt` (전면 구현)
- `data/src/main/java/app/peter/mos/data/source/remote/GoogleApi.kt` (엔드포인트 정의)
- `data/src/main/java/app/peter/mos/data/di/DataModule.kt` (Retrofit Builder 패턴, Interceptor 주입)
- `data/src/main/java/app/peter/mos/data/tool/network/GoogleAuthInterceptor.kt` **(신규)**
- `data/src/main/java/app/peter/mos/data/tool/preference/Preference.kt` (DataStore 구현)
- `domain/src/main/java/app/peter/mos/domain/model/PlayItem.kt` **(신규)**
- `domain/src/main/java/app/peter/mos/domain/model/PlayList.kt` **(신규)**
- `domain/src/main/java/app/peter/mos/domain/model/Subscription.kt` **(신규)**
- `domain/src/main/java/app/peter/mos/domain/repository/GoogleRepository.kt` (인터페이스 구체화)
- `data/build.gradle.kts`, `gradle/libs.versions.toml` (DataStore 의존성)

---

## [2026-02-06] - 로컬 캐싱(Room) 도입 및 네트워크 스택 최적화

### 완료한 작업
- **Room 데이터베이스 구축**:
  - `CulturalEventEntity`, `CulturalEventDao`, `AppDatabase` 정의 및 로컬 영속성 계층 구현
  - `DatabaseModule`을 통한 Room DB 및 DAO 의존성 주입 설정
- **네트워크 스택 개선 (Gson → Kotlinx Serialization)**:
  - Ktor `ContentNegotiation`에 `kotlinx-serialization-json` 적용
  - `SeoulApi` 및 데이터 모델들(`CulturalEventInfo` 등)을 `@Serializable`로 전환하여 런타임 성능 및 안정성 개선
- **Repository 캐싱 전략 구현**:
  - `SeoulRepositoryImpl`에 로컬 우선(Cache-then-Network) 전략 적용
  - 로컬 데이터 부재 시 혹은 강제 새로고침 시에만 리모트 데이터 요청 및 로컬 DB 갱신
- **의존성 주입(DI) 아키텍처 개선**:
  - `SeoulApi`를 Hilt 생성자 주입 방식으로 변경하고 `@Named("seoul_key")`를 통해 API 키 분리 주입
  - `DataModule` 정리 및 `@Inject`, `@Binds`를 통한 자동 의존성 해결 적용
- **빌드 및 런타임 오류 수정**:
  - `SerializationException` (네트워크 데이터 파싱 오류) 해결
  - KSP 및 Gradle 버전 호환성 문제 해결을 통한 빌드 안정화

### 변경된 파일
- `data/src/main/java/app/peter/mos/data/source/local/` (Room 관련 엔티티 및 DAO 추가)
- `data/src/main/java/app/peter/mos/data/tool/db/AppDatabase.kt`
- `data/src/main/java/app/peter/mos/data/tool/network/Network.kt`
- `data/src/main/java/app/peter/mos/data/repositories/SeoulRepositoryImpl.kt`
- `data/src/main/java/app/peter/mos/data/di/DataModule.kt`, `AppModule.kt`
- `data/build.gradle.kts` (Dependencies 업데이트)

---

## [2026-02-03] - 스플래시 화면 구현 및 클린 아키텍처 강화

### 완료한 작업
- **Splash Screen 구현**: Android 12 대응 `core-splashscreen` 도입 및 데이터 로딩 동기화
- **도메인 레이어 강화**: 
  - `SeoulUseCase` 구현 및 `Dispatchers.IO` 적용
  - `SeoulRepository` 인터페이스 및 `SeoulRepositoryImpl` 분리
- **DI 정교화**: Hilt를 사용하여 UseCase 및 Repository 바인딩 최적화
- **ViewModel 도입**: `MainViewModel`을 통한 상태 관리 및 UseCase 호출

### 변경된 파일
- `app/src/main/java/app/peter/mos/MainActivity.kt`
- `app/src/main/java/app/peter/mos/MainViewModel.kt`
- `domain/src/main/java/app/peter/mos/domain/usecase/SeoulUseCase.kt`
- `data/src/main/java/app/peter/mos/data/repositories/SeoulRepositoryImpl.kt`

---

## [2026-02-02] - 모듈 구조 간소화 (Consolidation)

### 완료한 작업
- **모듈 통합**: 4개 모듈(app, presentation, domain, data)에서 3개 모듈(app, domain, data)로 통합
- **Presentation 통합**: `presentation` 모듈의 UI 및 테마 코드를 `app` 모듈로 이관
- **의존성 단순화**: 모듈 간 불필요한 의존성 제거 및 빌드 속도 개선

### 변경된 파일
- `settings.gradle.kts`, `build.gradle.kts`
- `app/src/main/java/app/peter/mos/ui/` (Presentation 코드 이관)

---

## [2026-01-24] - 프로젝트 규칙 및 문서화 체계 정립

---

## [2026-01-12] - 모듈화 및 Hilt DI 적용

### 완료한 작업
- 프로젝트를 4개 모듈로 분리 (app, presentation, domain, data)
- Hilt DI 적용 (AppModule, NetworkModule, RepositoryModule)
- Kotlin 1.9.0으로 업데이트
- JDK 17로 업데이트
- Clean Architecture 구조 적용
- README.md 업데이트

### 변경된 파일
- 전체 프로젝트 구조 변경 (상세: `docs/MODULARIZATION.md`)

### 다음 할 일
- GoogleRepository 구현
- Translator 구현
- ViewModel 추가

---

## [2026-01-12] - 초기 코드 품질 개선

### 완료한 작업
- README.md 정확성 개선
- Deprecated API 수정 (Theme.kt - `window.statusBarColor`)
- 중복 의존성 제거 (`app/build.gradle`)

### 변경된 파일
- `README.md`
- `app/build.gradle`
- `presentation/src/.../theme/Theme.kt`

### 다음 할 일
- 모듈화 진행
- Hilt DI 적용

---

# 📌 빠른 참조

## 현재 프로젝트 상태 (2026-02-13 기준)

### ✅ 완료된 기능
| 기능 | 상태 | 설명 |
|------|------|------|
| 모듈화 | ✅ 완료 | app, domain, data 3개 모듈 (Consolidated) |
| Architecture | ✅ 완료 | Clean Architecture (MVVM + UseCase) |
| Hilt DI | ✅ 완료 | Interface-Implementation 바인딩 및 생성자 주입 최적화 |
| Splash Screen | ✅ 완료 | core-splashscreen 적용 및 데이터 로딩 동기화 |
| Seoul 문화행사 API | ✅ 완료 | SeoulRepository, SeoulApi 구현됨 |
| Google/YouTube API | ✅ 완료 | GoogleRepository 실구현, 구독/재생목록/아이템 조회 |
| Google OAuth 인증 | ✅ 완료 | GoogleAuthInterceptor + DataStore 기반 토큰 관리 |
| ViewModel | ✅ 완료 | MainViewModel을 통한 UI 상태 관리 |
| MainScreen UI | ✅ 완료 | 문화행사 목록 표시 및 상태 표시 |

### ⏳ 미구현 (Placeholder)
| 항목 | 파일 위치 | 비고 |
|------|-----------|------|
| Translator | `domain/.../tool/Translator.kt` | (파일 없음) 목적 정의 필요 |
| Google UseCase | `domain/.../usecase/` | Google 데이터 활용 UseCase 미구현 |

---

## 기술 스택
- **Kotlin**: 1.9.0
- **JDK**: 17
- **Jetpack Compose**: BOM 2025.03.00
- **Hilt**: 2.48
- **splashscreen**: 1.0.1
- **Ktor**: 2.3.2
- **Retrofit**: OkHttp + Gson Converter (Google API 용)
- **DataStore**: Preferences 1.1.0 (토큰 관리)
- **Room**: 2.7.0 (로컬 캐싱)

---

## 모듈 구조
```
┌─────────────┐
│     app     │ ← MainActivity, MainViewModel, Splash, UI
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    data     │ ← SeoulRepositoryImpl, GoogleRepositoryImpl, SeoulApi, GoogleApi
│             │   GoogleAuthInterceptor, Preference (DataStore), Network
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   domain    │ ← SeoulUseCase, SeoulRepository, GoogleRepository (Interface)
│             │   CulturalEvent, Subscription, PlayList, PlayItem
└─────────────┘
```

---

---

## 🎯 향후 계획
- 모든 할 일 및 액션 아이템은 [TODO.md](./TODO.md)에서 통합 관리됩니다.

---

---

*마지막 업데이트: 2026-02-13*

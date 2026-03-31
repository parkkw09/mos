# Midst of Soul

## 프로젝트 개요

서울 공공 API를 활용한 문화행사 정보 제공 Android 앱입니다.  
**Jetpack Compose** 기반의 Modern Android 앱으로, **Clean Architecture**와 **Hilt DI**를 적용하여 3개의 모듈로 구성되어 있습니다.

## 현재 구현 상태

- ✅ **서울 문화행사 API 연동 완료**
- ✅ **로컬 캐싱(Room) 도입 완료** (Remote-First + Fallback 전략)
- ✅ **모듈 구조 최적화** (app, domain, data)
- ✅ **Clean Architecture 적용 완료** (ViewModel → UseCase → Repository)
- ✅ **Hilt DI 적용 완료**
- ✅ **Splash Screen 구현 완료**
- ✅ **페이지네이션 구현 완료** (PAGE_SIZE=50, 무한스크롤)
- ✅ **LoadState sealed interface 적용** (Idle/Loading/LoadingMore/Success/Error)
- ✅ **Google OAuth 인증 구현 완료** (`GoogleSignInManager`, DataStore 토큰 저장)
- ✅ **Google UseCase 구현 완료** (구독/재생목록/상세 조회, 토큰 저장/삭제)
- ⏳ **Navigation + 상세 화면** (미구현)
- ⏳ **메인 화면 UI 개선** (현재 텍스트 Only — 카드뷰, 이미지 미적용)
- ⏳ **Pull-to-Refresh** (`refresh()` 구현됨, UI 제스처 연결 미완)
- ⏳ **Google 데이터 화면 연동** (UseCase 구현됨, ViewModel·UI 연결 미완)

## 빠른 시작

### 1. 요구사항

- **JDK 17**
- **Android SDK**
  - compileSdk: 36
  - minSdk: 27 (Android 8.1 Oreo)
  - targetSdk: 36
- **Gradle Wrapper** 사용 (프로젝트 내 `gradlew`)
- **인터넷 연결** (INTERNET 퍼미션 필요)

### 2. 비밀 키 설정

로컬 키를 다음 경로에 파일 형태로 추가합니다:  
`~/Documents/private/key/app_props.properties`

예시 내용:
```properties
SEOUL_KEY=your_seoul_api_key
GOOGLE_AUTH_KEY=your_google_client_id
```

### 3. 빌드

전체 빌드:
```bash
./gradlew clean build
```

디바이스에 설치 (디버그):
```bash
./gradlew :app:installDebug
```

## 프로젝트 구조

```
mos/
├── app/                    # 앱 모듈 (Presentation Layer)
│   ├── MainActivity.kt     # 앱 진입점, Splash Screen, Google 로그인 트리거
│   ├── MainViewModel.kt    # 페이지네이션 + Google 인증 상태 관리
│   ├── MosApplication.kt   # Hilt Application
│   ├── LoadState.kt        # sealed interface (UI 로딩 상태)
│   ├── GoogleAuthState.kt  # sealed interface (Google 인증 상태)
│   ├── auth/               # GoogleSignInManager (OAuth 래퍼)
│   └── ui/                 # UI 레이어 (Compose, 무한스크롤)
│
├── domain/                 # 도메인 모듈 (Business Logic, Pure Kotlin)
│   ├── model/              # CulturalEvent(24필드), CulturalEventPage, Google 모델
│   ├── repository/         # Repository 인터페이스
│   └── usecase/            # SeoulUseCase, Google UseCases (5개)
│
└── data/                   # 데이터 모듈 (Data Layer)
    ├── di/                 # Hilt DI 모듈 (Network, DB, Repository, Dispatchers)
    ├── repositories/       # SeoulRepositoryImpl, GoogleRepositoryImpl
    ├── source/             # Remote(Ktor/Retrofit) + Local(Room/DataStore)
    └── tool/               # Network, AppDatabase, Preference
```

### 모듈 의존성 관계

```
app → data → domain
```

- **app**: UI 및 사용자 인터랙션 담당. `domain`의 UseCase를 통해 데이터를 가져오며, `data` 모듈의 의존성을 주입받습니다.
- **domain**: 순수 비즈니스 로직 및 엔티티. 외부 의존성이 없는 Pure Kotlin 모듈입니다.
- **data**: 데이터 소스(API/DB) 연동 및 Repository 구현. `domain`의 인터페이스를 구현합니다.

## 기술 스택

### 언어 & 빌드
- **Kotlin**: 1.9.0
- **Gradle**: 8.11.1
- **JVM Target**: 17

### 아키텍처 & DI
- **Architecture**: Clean Architecture + MVVM
- **DI**: Hilt 2.48

### UI
- **Jetpack Compose**: BOM 2025.03.00
- **Material3**: 적용
- **Splash Screen**: core-splashscreen 1.0.1

### 네트워킹 & 데이터
- **Ktor**: 2.3.2 (Client Core, CIO, Logging, Content Negotiation) — 서울 API
- **Retrofit + OkHttp**: Gson 기반 — Google YouTube API
- **Serialization**: Kotlinx Serialization 1.6.0
- **Database**: Room 2.6.1
- **DataStore**: Preferences (Google Access Token 영속 저장)
- **Concurrency**: Coroutines 1.7.3

### 인증
- **Google Identity SDK**: OAuth 2.0 (YouTube 읽기 전용 Scope)

## 테스트

단위테스트 실행:
```bash
./gradlew test
```

계측테스트 실행:
```bash
./gradlew connectedAndroidTest
```

## 주요 문서 (docs/)

- [ARCHITECTURE.md](./docs/ARCHITECTURE.md): 상세 아키텍처 및 데이터 흐름
- [TODO.md](./docs/TODO.md): **(중요)** 향후 계획 및 모든 액션 아이템 통합 관리

## 🎯 향후 로드맵 및 할 일
- 모든 기능 확장 계획 및 액션 아이템은 [TODO.md](./docs/TODO.md)에서 통합 관리됩니다.

## 라이선스

프로젝트에 라이선스 파일이 없으므로 필요하면 추가하세요.

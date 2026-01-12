# Midst of Soul

## 프로젝트 개요

서울 공공 API를 활용한 문화행사 정보 제공 Android 앱입니다.  
**Jetpack Compose** 기반의 Modern Android 앱으로, **Clean Architecture**와 **Hilt DI**를 적용하여 모듈화된 구조를 가지고 있습니다.

## 현재 구현 상태

- ✅ **Seoul 문화행사 API 연동 완료**
- ✅ **모듈화 완료** (App, Domain, Data, Presentation)
- ✅ **Hilt DI 적용 완료**
- ⏳ Google/YouTube 기능 (모델만 정의, 미구현)
- ⏳ Translator 기능 (클래스만 생성, 미구현)

## 빠른 시작

### 1. 요구사항

- **JDK 17 이상** (현재 프로젝트는 JDK 17 사용)
- **Android SDK**
  - compileSdk: 35
  - minSdk: 26 (Android 8.0 Oreo)
  - targetSdk: 35
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

## 프로젝트 구조 (모듈화)

```
mos/
├── app/                    # 앱 모듈 (Application Entry Point)
│   ├── MosApplication.kt   # Hilt Application
│   └── MainActivity.kt     # 메인 액티비티
│
├── presentation/           # 프레젠테이션 모듈 (외부 채널용 UI)
│   └── ui/
│       ├── MainScreen.kt   # 메인 화면 Composable
│       └── theme/          # Material3 테마
│
├── domain/                 # 도메인 모듈 (Pure Kotlin/Java)
│   ├── model/              # 도메인 모델
│   ├── usecase/            # Use Cases
│   └── tool/               # Translator (미구현)
│
└── data/                   # 데이터 모듈 (Android Library)
    ├── di/                 # Hilt DI 모듈
    │   ├── AppModule.kt    # Seoul Key 제공
    │   └── DataModule.kt   # Network, Repository 제공
    ├── repositories/       # Repository 구현
    │   ├── SeoulRepository.kt (구현됨)
    │   └── GoogleRepository.kt (빈 클래스)
    ├── source/
    │   ├── model/          # Seoul, Google/YouTube 데이터 모델
    │   ├── remote/         # SeoulApi (Ktor 사용)
    │   └── local/          # Local 데이터 소스
    └── tool/
        ├── network/        # Network (Ktor Client)
        ├── db/             # Database
        └── preference/     # Preference
```

### 모듈 의존성 관계

```
app → presentation → data → domain
```

- **app**: 앱 자체의 기능에 집중 (Application, MainActivity)
- **presentation**: 앱과 연계되지 않은 외부 채널을 위한 UI 모듈
- **data**: 데이터 소스 및 Repository 구현 (Android 의존성 포함)
- **domain**: 순수 비즈니스 로직 (Android 의존성 없음)

## 기술 스택

### 언어 & 빌드
- **Kotlin**: 1.9.0
- **Gradle**: 8.11.1
- **Android Gradle Plugin**: 8.9.0
- **JVM Target**: 17

### 아키텍처 & DI
- **아키텍처**: Clean Architecture (Presentation - Domain - Data)
- **DI**: Hilt 2.48 (Dagger)
- **모듈화**: Multi-module project

### UI
- **Jetpack Compose**: BOM 2025.03.00
- **Material3**: Latest
- **Compose Compiler**: 1.5.2

### 네트워킹 & 데이터
- **Ktor**: 2.3.2 (Client Core, CIO, Logging, Content Negotiation)
- **JSON 파싱**: Gson 2.10.1
- **비동기**: Kotlin Coroutines 1.7.3

### 테스트
- **JUnit**: 4.13.2
- **Espresso**: 3.6.1
- **Compose UI Test**: Latest

## 테스트

단위테스트 실행:
```bash
./gradlew test
```

계측테스트 실행 (디바이스 연결 필요):
```bash
./gradlew connectedAndroidTest
```

## Hilt DI 구조

### AppModule (data 모듈)
```kotlin
@Provides
@Singleton
@Named("seoul_key")
fun provideSeoulKey(@ApplicationContext context: Context): String
```

### NetworkModule (data 모듈)
```kotlin
@Provides
@Singleton
fun provideHttpClient(): HttpClient
```

### RepositoryModule (data 모듈)
```kotlin
@Provides
@Singleton
fun provideSeoulRepository(@Named("seoul_key") seoulKey: String): SeoulRepository
```

## 유의사항

- `app/build.gradle`에서 로컬 키를 `~/Documents/private/key/app_props.properties`에서 불러옵니다.
- 해당 파일을 준비하지 않으면 리소스에 `"NOT_FOUND"` 값이 채워집니다.
- 모든 모듈은 JDK 17을 타겟으로 합니다.
- Hilt를 사용하므로 `@AndroidEntryPoint`, `@HiltAndroidApp` 어노테이션이 필요합니다.

## 다음 권장 작업

### 1. 코드 품질 개선
- `Translator.kt`의 목적(예: 다국어 처리, 텍스트 변환 등)을 정하고 구현 추가
- `GoogleRepository.kt` 구현 (YouTube API 연동)
- ViewModel 추가 (AAC ViewModel + Hilt)

### 2. 기능 확장
- Google/YouTube API 연동 구현
- 로컬 데이터베이스 구현 (Room + Hilt)
- 사용자 설정 기능 (Preference 활용)
- Navigation Compose 적용

### 3. 개발 프로세스 개선
- CI/CD 구축 (예: GitHub Actions로 `./gradlew build` 자동화)
- 단위 테스트 및 UI 테스트 추가
- ProGuard/R8 난독화 설정 (release 빌드)
- Detekt/ktlint 적용 (코드 스타일 검사)

### 4. 문서화
- 라이선스 파일 추가
- API 문서화 (KDoc)
- 기여 가이드라인 작성
- 아키텍처 다이어그램 추가

## 참고 파일

- `app/src/main/java/app/peter/mos/MosApplication.kt` — Hilt Application 클래스
- `app/src/main/java/app/peter/mos/MainActivity.kt` — 메인 액티비티
- `presentation/src/main/java/app/peter/mos/presentation/ui/MainScreen.kt` — 메인 화면
- `data/src/main/java/app/peter/mos/data/di/` — Hilt DI 모듈
- `domain/src/main/java/app/peter/mos/domain/tool/Translator.kt` — 빈 클래스 (미구현)
- `data/src/main/java/app/peter/mos/data/repositories/GoogleRepository.kt` — 빈 클래스 (미구현)
- 린트 리포트: `app/build/reports/lint-results-debug.html`

## 라이선스

프로젝트에 라이선스 파일이 없으므로 필요하면 추가하세요.

## 변경 이력

### 2026-01-12 - 모듈화 및 Hilt 적용
- ✅ 프로젝트를 4개 모듈로 분리 (app, presentation, domain, data)
- ✅ Hilt DI 적용
- ✅ Kotlin 1.9.0으로 업데이트
- ✅ JDK 17로 업데이트
- ✅ Clean Architecture 구조 적용
- ✅ 빌드 및 검증 성공

### 2026-01-12 - 초기 개선
- ✅ README 정확성 개선
- ✅ Deprecated API 수정 (Theme.kt)
- ✅ 중복 의존성 제거

# Midst of Soul

간단한 설명
- Android 앱 프로젝트입니다. 서울 공공 API를 사용하여 문화행사 정보를 가져와 표시합니다.
- Jetpack Compose 기반의 Modern Android 앱입니다.

현재 구현 상태
- ✅ Seoul 문화행사 API 연동 완료
- ⏳ Google/YouTube 기능 (모델만 정의, 미구현)
- ⏳ Translator 기능 (클래스만 생성, 미구현)

빠른 시작

1. 요구사항
   - **JDK 17 이상 권장** (현재 프로젝트는 JDK 17 사용)
   - Android SDK (compileSdk 35, minSdk 26 - Android 8.0 Oreo)
   - Gradle Wrapper 사용 (프로젝트 내 `gradlew`)
   - 인터넷 연결 (INTERNET 퍼미션 필요)

2. 비밀 키 설정
   - 로컬 키를 다음 경로에 파일 형태로 추가합니다: `~/Documents/private/key/app_props.properties`
   - 예시 내용:

```
SEOUL_KEY=your_seoul_api_key
GOOGLE_AUTH_KEY=your_google_client_id
```

3. 빌드
   - 전체 빌드:

```bash
./gradlew clean build
```

   - 디바이스에 설치 (디버그):

```bash
./gradlew :app:installDebug
```

프로젝트 구조 (간략)
- `app/` - Android 애플리케이션 모듈
  - `src/main/java/app/peter/mos/`
    - `application/` - Application 클래스 (App.kt - TAG 상수 정의)
    - `presentation/` - UI/Activity (Jetpack Compose)
      - `ui/` - MainActivity, Theme, Color, Type
    - `domain/` - 도메인 계층
      - `model/` - 도메인 모델
      - `tool/` - Translator.kt (빈 클래스)
      - `usecase/` - UseCase
    - `data/` - 데이터 소스 및 리포지토리
      - `repositories/` - SeoulRepository (구현됨), GoogleRepository (빈 클래스)
      - `source/model/` - Seoul, Google/YouTube 데이터 모델
      - `source/remote/` - SeoulApi (Ktor 사용)
      - `source/local/` - Local 데이터 소스
      - `tool/` - Network, Database, Preference

기술 스택
- **언어**: Kotlin 1.7.20
- **빌드 도구**: Gradle 8.11.1, Android Gradle Plugin 8.9.0
- **UI**: Jetpack Compose (Material3)
- **네트워킹**: Ktor 2.3.2 (Client Core, CIO, Logging, Content Negotiation)
- **JSON 파싱**: Gson 2.10.1
- **비동기**: Kotlin Coroutines
- **아키텍처**: Clean Architecture (Presentation - Domain - Data)

참고 파일
- `app/src/main/java/app/peter/mos/domain/tool/Translator.kt` — 현재 빈 클래스입니다.
- `app/src/main/java/app/peter/mos/data/repositories/GoogleRepository.kt` — 현재 빈 클래스입니다 (미구현).
- 린트 리포트: `app/build/reports/lint-results-debug.html`

테스트
- 단위테스트 실행:

```bash
./gradlew test
```

- 계측테스트 실행 (디바이스 연결 필요):

```bash
./gradlew connectedAndroidTest
```

유의사항
- `app/build.gradle`에서 로컬 키를 `~/Documents/private/key/app_props.properties`에서 불러옵니다. 해당 파일을 준비하지 않으면 리소스에 `"NOT_FOUND"` 값이 채워집니다.
- Gradle과 Kotlin 플러그인 버전은 루트 `build.gradle`과 `app/build.gradle`에 정의되어 있으니, 환경에 맞게 JDK와 Android SDK 버전을 맞춰주세요.

다음 권장 작업
1. **코드 품질 개선**
   - `Translator.kt`의 목적(예: 다국어 처리, 텍스트 변환 등)을 정하고 구현 추가
   - `GoogleRepository.kt` 구현 (YouTube API 연동)

2. **기능 확장**
   - Google/YouTube API 연동 구현
   - 로컬 데이터베이스 구현 (오프라인 지원)
   - 사용자 설정 기능 (Preference 활용)

3. **개발 프로세스 개선**
   - CI/CD 구축 (예: GitHub Actions로 `./gradlew build` 자동화)
   - 단위 테스트 및 UI 테스트 추가
   - ProGuard/R8 난독화 설정 (release 빌드)

4. **문서화**
   - 라이선스 파일 추가
   - API 문서화 (KDoc)
   - 기여 가이드라인 작성

라이선스
- 프로젝트에 라이선스 파일이 없으므로 필요하면 추가하세요.

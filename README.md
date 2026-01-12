# Midst of Soul

간단한 설명
- Android 앱 프로젝트입니다. 서울 공공 API와 Google/YouTube 관련 데이터를 가져와 표시하는 구조로 보입니다.

빠른 시작

1. 요구사항
   - JDK 11 이상 권장
   - Android SDK (compileSdk 35)
   - Gradle Wrapper 사용 (프로젝트 내 `gradlew`)

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
  - `src/main/java/app/peter/mos/presentation` - UI/Activity
  - `src/main/java/app/peter/mos/domain` - 도메인 계층 (`Translator.kt` 포함)
  - `src/main/java/app/peter/mos/data` - 데이터 소스 및 리포지토리 (Seoul API, Google/YouTube 모델 등)

참고 파일
- `app/src/main/java/app/peter/mos/domain/tool/Translator.kt` — 현재 빈 클래스입니다.
- `app/src/main/java/app/peter/mos/presentation/ui/theme/Theme.kt` — deprecated 경고가 존재합니다 (statusBarColor setter).
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
- `Translator.kt`의 목적(예: 다국어 처리, 텍스트 변환 등)을 정하고 구현 추가
- `Theme.kt`의 deprecated API 수정
- CI(예: GitHub Actions)로 `./gradlew build` 자동화

라이선스
- 프로젝트에 라이선스 파일이 없으므로 필요하면 추가하세요.

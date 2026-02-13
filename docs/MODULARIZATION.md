# 프로젝트 모듈화 보고서

**최종 수정일**: 2026-02-13  
**작업 유형**: 모듈 구조 간소화

---

## 📋 작업 개요

기존 4개 모듈 구조에서 **3개 모듈**로 간소화했습니다.  
Presentation 모듈을 App 모듈로 통합하여 더 단순한 구조를 갖추었습니다.

---

## 🏗️ 모듈 구조

### 현재 모듈 구조

```
mos/
├── app/                    # Android Application 모듈 (UI 포함)
├── domain/                 # Pure Kotlin/Java Library 모듈
└── data/                   # Android Library 모듈 (Data Layer)
```

### 모듈별 상세 정보

#### 1. **app 모듈** (Android Application)
- **역할**: 앱의 진입점, 사용자 인터랙션, UI
- **주요 구성**:
  ```
  app/src/main/java/app/peter/mos/
  ├── MainActivity.kt           # 앱 진입점, Splash Screen 제어
  ├── MainViewModel.kt          # UI 상태 관리, UseCase 호출
  ├── MosApplication.kt         # @HiltAndroidApp
  └── ui/                       # UI 레이어
      ├── MainScreen.kt         # 메인 화면
      └── theme/                # 테마/스타일
          ├── Color.kt
          ├── Theme.kt
          └── Type.kt
  ```
- **의존성**: data, domain
- **특징**: Hilt DI, Jetpack Compose, Splash Screen API

#### 2. **domain 모듈** (Pure Kotlin/Java Library)
- **역할**: 비즈니스 로직 (Android 의존성 없음)
- **주요 파일**:
  - `model/CulturalEvent.kt`
  - `model/Subscription.kt`, `model/PlayList.kt`, `model/PlayItem.kt`
  - `repository/SeoulRepository.kt` (Interface)
  - `repository/GoogleRepository.kt` (Interface)
  - `usecase/SeoulUseCase.kt`
- **의존성**: 없음 (순수 Kotlin)
- **특징**: Coroutines, Javax Inject

#### 3. **data 모듈** (Android Library)
- **역할**: 데이터 소스 및 Repository 구현
- **주요 파일**:
  - `di/` (Hilt Modules: `DataModule`, `NetworkModule`, `DatabaseModule`, `AppModule`)
  - `repositories/SeoulRepositoryImpl.kt`, `repositories/GoogleRepositoryImpl.kt`
  - `source/remote/SeoulApi.kt`, `source/remote/GoogleApi.kt`
  - `source/local/` (Room DAO, Entity)
  - `tool/network/Network.kt`, `tool/network/GoogleAuthInterceptor.kt`
  - `tool/preference/Preference.kt` (DataStore)
  - `tool/db/AppDatabase.kt`
- **의존성**: domain
- **특징**: Ktor, Retrofit, Gson, Room, DataStore, Hilt

---

## 📊 모듈 의존성 그래프

```
┌─────────────┐
│     app     │ (Android Application)
│  - UI       │
│  - ViewModel│
└──────┬──────┘
       │
       ├──────────────┐
       │              │
       ▼              ▼
┌──────────┐   ┌──────────┐
│   data   │   │  domain  │
│(Android) │   │  (Pure)  │
└────┬─────┘   └──────────┘
     │
     └─────────────▶ domain
```

---

## 🎯 설계 원칙

### App 모듈의 역할
1. **사용자 인터랙션 처리**: 모든 UI 이벤트는 App 모듈에서 시작
2. **ViewModel = Adapter**: ViewModel이 Domain과 UI 사이의 어댑터 역할 수행
3. **단방향 데이터 흐름**: 
   ```
   User Action → ViewModel → UseCase → Repository → API
                    ↑                                  │
                    └──────────── Result ──────────────┘
   ```

### 클린 아키텍처 준수
- **Domain 레이어**: Android 의존성 없음 (Pure Kotlin)
- **Data 레이어**: Repository 패턴으로 데이터 소스 추상화
- **Presentation 레이어**: App 모듈 내 UI 패키지

---

## � 기술 스택

| 카테고리 | 기술 |
|----------|------|
| **언어** | Kotlin 1.9.0 |
| **UI** | Jetpack Compose |
| **DI** | Hilt 2.48 |
| **네트워크** | Ktor |
| **빌드** | Gradle 8.11.1 |
| **JDK** | 17 |

---

## ✅ 빌드 검증

```bash
./gradlew clean build
```

```
BUILD SUCCESSFUL in 20s
226 actionable tasks: 216 executed, 10 up-to-date
```

### 생성된 산출물
- ✅ `app-debug.apk`
- ✅ `app-release-unsigned.apk`
- ✅ `data.aar`
- ✅ `domain.jar`

---

## � 변경 이력

### 2026-02-02: 모듈 간소화
- **변경 사항**: Presentation 모듈을 App 모듈로 통합
- **이유**: 
  - 단일 프레젠테이션 레이어로 충분
  - ViewModel이 이미 Adapter 역할 수행
  - 불필요한 모듈 복잡성 제거
- **이전 구조**: app, presentation, domain, data (4개)
- **현재 구조**: app, domain, data (3개)

### 2026-01-12: 초기 모듈화
- 단일 모듈 → 4개 모듈 분리
- Hilt DI 적용

---

## 🚀 확장 계획

### Feature 모듈화 (필요 시)
프로젝트가 성장하면 Feature 기반 모듈화 고려:

```
mos/
├── app/
├── feature/
│   ├── home/
│   ├── festival/
│   └── map/
├── core/
│   ├── ui/          # 공통 컴포넌트
│   ├── domain/
│   └── data/
```

### 추가 개선
1. ViewModel 추가 (Hilt + AAC ViewModel)
2. UseCase 구현 (domain 모듈)
3. Navigation Compose 적용
4. 단위 테스트 작성

---

## 📝 참고 자료

- [Android 모듈화 가이드](https://developer.android.com/topic/modularization)
- [Now in Android 샘플](https://github.com/android/nowinandroid)
- [Hilt 공식 문서](https://dagger.dev/hilt/)

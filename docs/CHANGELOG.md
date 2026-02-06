# 📋 MOS 프로젝트 변경 이력 (CHANGELOG)

이 문서는 프로젝트의 모든 작업 이력을 시간순으로 기록합니다.  
**새로운 챗이 시작될 때 반드시 이 문서를 먼저 읽어주세요.**

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

## 현재 프로젝트 상태 (2026-02-06 기준)

### ✅ 완료된 기능
| 기능 | 상태 | 설명 |
|------|------|------|
| 모듈화 | ✅ 완료 | app, domain, data 3개 모듈 (Consolidated) |
| Architecture | ✅ 완료 | Clean Architecture (MVVM + UseCase) |
| Hilt DI | ✅ 완료 | Interface-Implementation 바인딩 포함 |
| Splash Screen | ✅ 완료 | core-splashscreen 적용 및 데이터 로딩 동기화 |
| Seoul 문화행사 API | ✅ 완료 | SeoulRepository, SeoulApi 구현됨 |
| ViewModel | ✅ 완료 | MainViewModel을 통한 UI 상태 관리 |
| MainScreen UI | ✅ 완료 | 문화행사 목록 표시 및 상태 표시 |

### ⏳ 미구현 (Placeholder)
| 항목 | 파일 위치 | 비고 |
|------|-----------|------|
| GoogleRepository | `data/.../repositories/GoogleRepository.kt` | 빈 클래스 |
| Translator | `domain/.../tool/Translator.kt` | (파일 없음) 목적 정의 필요 |

---

## 기술 스택
- **Kotlin**: 1.9.0
- **JDK**: 17
- **Jetpack Compose**: BOM 2025.03.00
- **Hilt**: 2.48
- **splashscreen**: 1.0.1
- **Ktor**: 2.3.2
- **Gson**: 2.10.1

---

## 모듈 구조
```
┌─────────────┐
│     app     │ ← MainActivity, MainViewModel, Splash, UI
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    data     │ ← SeoulRepositoryImpl, SeoulApi, Network
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   domain    │ ← SeoulUseCase, SeoulRepository (Interface), CulturalEvent
└─────────────┘
```

---

## 🎯 다음 우선순위 작업

### 1순위 (기능 확장)
- [ ] GoogleRepository 구현 (YouTube API 연동)
- [ ] 상세 화면(Detail Screen) 구현 및 네비게이션 적용

### 2순위 (품질 개선)
- [ ] UseCase 및 ViewModel 단위 테스트 작성
- [ ] Room Database를 이용한 로컬 캐싱 도입

### 3순위 (고도화)
- [ ] CI (GitHub Actions) 연동
- [ ] Translator 구현 (다국어 지원 등)

---

*마지막 업데이트: 2026-02-06*

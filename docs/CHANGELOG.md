# 📋 MOS 프로젝트 변경 이력 (CHANGELOG)

이 문서는 프로젝트의 모든 작업 이력을 시간순으로 기록합니다.  
**새로운 챗이 시작될 때 반드시 이 문서를 먼저 읽어주세요.**

---

## [2026-01-24] - 프로젝트 규칙 및 문서화 체계 정립

### 완료한 작업
- `.gemini/rules.md` 업데이트
  - 새 챗 시작 시 필수 문서 읽기 규칙 추가
  - 작업 완료 시 문서화 규칙 추가
  - 코드 작업 규칙 추가
- `docs/CHANGELOG.md` 생성 (이 문서)

### 변경된 파일
- `.gemini/rules.md`
- `docs/CHANGELOG.md` (신규)

### 다음 할 일
- GoogleRepository 구현 (YouTube API 연동)
- Translator 클래스 구현
- ViewModel 추가 (Hilt + AAC ViewModel)

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

## 현재 프로젝트 상태 (2026-01-24 기준)

### ✅ 완료된 기능
| 기능 | 상태 | 설명 |
|------|------|------|
| 모듈화 | ✅ 완료 | app, presentation, domain, data 4개 모듈 |
| Hilt DI | ✅ 완료 | AppModule, NetworkModule, RepositoryModule |
| Seoul 문화행사 API | ✅ 완료 | SeoulRepository, SeoulApi 구현됨 |
| MainScreen UI | ✅ 완료 | 문화행사 목록 표시 (LazyColumn) |
| Theme | ✅ 완료 | Material3 테마 적용 |

### ⏳ 미구현 (빈 클래스)
| 항목 | 파일 위치 | 비고 |
|------|-----------|------|
| GoogleRepository | `data/.../repositories/GoogleRepository.kt` | 빈 클래스 |
| Translator | `domain/.../tool/Translator.kt` | 빈 클래스 |
| UseCase | `domain/.../usecase/UseCase.kt` | 빈 클래스 |
| DomainModel | `domain/.../model/DomainModel.kt` | 빈 클래스 |

### 📁 Google/YouTube 모델 (정의만 됨)
- `data/.../source/model/google/GoogleResponse.kt`
- `data/.../source/model/google/youtube/` (4개 파일)

---

## 기술 스택
- **Kotlin**: 1.9.0
- **JDK**: 17
- **Jetpack Compose**: BOM 2025.03.00
- **Hilt**: 2.48
- **Ktor**: 2.3.2
- **Gson**: 2.10.1

---

## 모듈 구조
```
┌─────────────┐
│     app     │ ← MainActivity, MosApplication
└──────┬──────┘
       │
       ▼
┌─────────────┐
│presentation │ ← MainScreen, Theme
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    data     │ ← SeoulRepository, SeoulApi, Network
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   domain    │ ← UseCase, DomainModel, Translator (미구현)
└─────────────┘
```

---

## 🎯 다음 우선순위 작업

### 1순위 (구조 개선)
- [ ] ViewModel 추가 (Hilt + AAC ViewModel)
- [ ] MainScreen에서 Repository 직접 사용 → ViewModel로 분리

### 2순위 (기능 구현)
- [ ] UseCase 구현 (GetCulturalEventsUseCase)
- [ ] GoogleRepository 구현 (YouTube API)
- [ ] Translator 구현 (목적 정의 필요)

### 3순위 (품질 개선)
- [ ] 단위 테스트 추가
- [ ] Navigation Compose 적용
- [ ] Room Database 추가

---

*마지막 업데이트: 2026-01-24*

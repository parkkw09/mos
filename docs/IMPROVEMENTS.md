# 프로젝트 개선 사항 및 기술 부채

**최종 업데이트**: 2026-02-06  
**작업자**: AI Assistant

## 최근 개선 완료 항목 (2026-02-03 ~ 2026-02-06) ✅

### 1. 모듈 구조 최적화 (Consolidation)
- **변경 사항**: `presentation` 모듈을 `app` 모듈로 통합하여 3개 모듈 구조로 개편.
- **이유**: 단순한 앱 구조에서 불필요한 모듈 분리로 인한 빌드 오버헤드와 복잡성을 줄이고, Presentation 로직과 App 진입점 간의 결합을 효율적으로 관리하기 위함.

### 2. Clean Architecture 강화
- **변경 사항**: 
  - `SeoulUseCase`를 통한 비즈니스 로직 캡슐화.
  - Repository 인터페이스(`domain`)와 구현체(`data`)의 명확한 분리.
  - Hilt `@Binds`를 이용한 의존성 역전 원칙(DIP) 적용.
- **이유**: 테스트 가능성(Testability)을 높이고 각 레이어 간의 결합도를 낮추기 위함.

### 3. Splash Screen 및 초기화 로직 개선
- **변경 사항**: `androidx.core:core-splashscreen` 도입 및 `MainViewModel`의 데이터 로딩 상태(`isReady`)와 연동.
- **이유**: Android 12 이상에서의 일관된 스플래시 경험 제공 및 데이터 로딩 완료 전 빈 화면 노출 방지.

### 4. 문서화 최신화
- **변경 사항**: `README.md`, `CHANGELOG.md`, `ARCHITECTURE.md` 등을 현재 코드 베이스에 맞춰 전면 업데이트.
- **이유**: 프로젝트 현행화를 통해 협업 및 유지보수 효율성 증대.

---

## 남은 개선 과제 (우선순위순) 📋

### 1순위: 기능 보완
- [ ] **GoogleRepository 구현**: 정의만 되어 있는 YouTube API 연동 기능을 실제로 구현하여 영상 정보 제공.
- [ ] **상세 화면 구현**: 목록 클릭 시 문화행사 상세 정보를 보여주는 화면 추가.
- [ ] **Navigation 적용**: Jetpack Navigation Compose를 도입하여 화면 전환 관리.

### 2순위: 품질 개선
- [ ] **단위 테스트 작성**: 
  - `SeoulUseCase` 단위 테스트 (Mock Repository 사용).
  - `MainViewModel` 상태 변화 테스트.
- [ ] **로컬 캐싱 도입**: Room Database를 사용하여 오프라인에서도 이전에 불러온 데이터를 확인할 수 있도록 개선.

### 3순위: 운영 및 고도화
- [ ] **CI/CD 구축**: GitHub Actions를 통한 자동 빌드 및 테스트 환경 구축.
- [ ] **Translator 구현**: 도큐먼트에는 언급되었으나 현재 파일이 없는 `Translator`의 목적을 정의하고 구현.
- [ ] **코드 스타일 관리**: Detekt 및 ktlint 적용.

---

## 기술 부채 현황

### 해결됨 ✅
- **모듈 구조 불일치**: 4개 -> 3개 통합 완료.
- **Domain 레이어 부재**: UseCase 및 Interface 분리 완료.
- **문서 최신화**: 완료.

### 남아있음 ⏳
- **테스트 코드 부재**: 현재 비즈니스 로직에 대한 테스트 코드가 전혀 없음.
- **비정상 종료 대응**: API 호출 실패 시 에러 핸들링 UI(Error Screen/SnackBar) 보완 필요.
- **오프라인 미지원**: 네트워크 연결 없이는 앱 사용 불가.

---

*작업 관련 문의나 추가 개선 사항이 있다면 CHANGELOG를 참고해 주세요.*

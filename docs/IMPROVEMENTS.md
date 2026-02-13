# 프로젝트 개선 사항 및 기술 부채

**최종 업데이트**: 2026-02-13  
**작업자**: AI Assistant

## 최근 개선 완료 항목 (2026-02-13) ✅

### 7. Google/YouTube API 연동 구현체 완성
- **변경 사항**: `GoogleRepositoryImpl`에서 YouTube Data API v3와 연동하여 구독 목록, 재생목록, 아이템 상세 조회 기능을 구현. 기존 placeholder(`test()`)를 실제 비즈니스 메서드로 교체.
- **이유**: 문화행사 관련 YouTube 영상 정보를 제공하기 위한 핵심 데이터 소스 구축.

### 8. Google OAuth 인증 체계 구축
- **변경 사항**: `GoogleAuthInterceptor` (OkHttp Interceptor)를 통해 API 요청에 Bearer 토큰을 자동 첨부. `Preference` 클래스에 DataStore Preferences 기반 토큰 영속 관리 구현.
- **이유**: Google API 호출 시 인증을 투명하게 처리하여 Repository 코드의 단순성 유지. DataStore를 통해 SharedPreferences보다 안전한 비동기 데이터 저장 제공.

### 9. 도메인 모델 확장
- **변경 사항**: `Subscription`, `PlayList`, `PlayItem` 도메인 모델 신규 생성. Data → Domain 매핑 함수 구현.
- **이유**: Clean Architecture 원칙에 따라 Data Layer의 DTO와 분리된 순수 도메인 모델을 통해 비즈니스 로직의 독립성 보장.

### 10. DI 아키텍처 고도화
- **변경 사항**: Retrofit 생성을 `Retrofit.Builder` 패턴으로 리팩토링하여 여러 API별 커스텀 설정 가능. Google API 전용 OkHttpClient에 인터셉터 주입.
- **이유**: 서로 다른 base URL과 인증 방식을 가진 API들을 유연하게 지원하기 위함.

---

## 이전 개선 완료 항목 (2026-02-03 ~ 2026-02-06) ✅

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

### 4. 의존성 주입(DI) 최적화
- **변경 사항**: `SeoulApi` 및 `SeoulRepositoryImpl`에 Hilt 생성자 주입을 적용하여 객체 생성을 자동화하고 결합도를 낮춤.
- **이유**: 수동 객체 생성을 제거하여 코드 유지보수성을 높이고 유닛 테스트 시 Mock 주입을 용이하게 하기 위함.

### 5. 로컬 캐싱(Room) 도입 및 데이터 직렬화 개선
- **변경 사항**: Room Database를 이용한 오프라인 대응 로직 구현 및 Gson에서 Kotlinx Serialization으로 전환.
- **이유**: 데이터 유지력 강화 및 런타임 성능 최적화를 위함.

### 6. 문서화 최신화
- **변경 사항**: `README.md`, `CHANGELOG.md`, `ARCHITECTURE.md` 등을 현재 코드 베이스에 맞춰 전면 업데이트.
- **이유**: 프로젝트 현행화를 통해 협업 및 유지보수 효율성 증대.

---

## 🎯 향후 개선 과제
- 구체적인 액션 아이템은 [TODO.md](./TODO.md)에서 확인 및 관리 바랍니다.

---

## 기술 부채 현황

### 해결됨 ✅
- **모듈 구조 불일치**: 4개 -> 3개 통합 완료.
- **Domain 레이어 부재**: UseCase 및 Interface 분리 완료.
- **문서 최신화**: 완료.

### 남아있음 ⏳
- **테스트 코드 부재**: 현재 비즈니스 로직에 대한 테스트 코드가 전혀 없음.
- **비정상 종료 대응**: API 호출 실패 시 에러 핸들링 UI(Error Screen/SnackBar) 보완 필요.
- **오프라인 완전 지원**: 캐싱은 도입되었으나, 첫 진입 시 네트워크 필수 등 보완 필요.

---

*작업 관련 문의나 추가 개선 사항이 있다면 CHANGELOG를 참고해 주세요.*

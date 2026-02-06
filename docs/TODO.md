# 📋 MOS 프로젝트 할 일 목록 (TODO)

이 문서는 프로젝트의 향후 계획, 개선 사항 및 액션 아이템을 통합 관리하는 유일한 소스입니다.

---

## 🎯 우선순위 로드맵

### 🔴 1순위: 핵심 기능 완성 (High Priority)
- [ ] **상세 화면(Detail Screen) 구현**
  - 문화행사 클릭 시 상세 정보(지도, 연락처, 상세 설명 등) 표시
- [ ] **Navigation 적용**
  - Jetpack Navigation Compose를 도입하여 화면 전환 및 백스택 관리
- [ ] **Google/YouTube API 연동**
  - `GoogleRepository` 실구현을 통해 해당 문화행사 관련 영상 정보 제공

### 🟡 2순위: 사용자 코드 품질 및 안정성 (Medium Priority)
- [ ] **단위 테스트 (Unit Test) 작성**
  - `SeoulUseCase` (Mock Repository 활용)
  - `MainViewModel` (UI 상태 변화 및 에러 핸들링)
- [ ] **에러 핸들링 UI 강화**
  - 네트워크 단절, API 에러 발생 시 사용자에게 친절한 안내(SnackBar, Error Screen) 제공
- [ ] **오프라인 완전 지원**
  - 앱 첫 진입 시 오프라인 상태일 경우의 시나리오 보완 (캐시된 데이터 우선 노출)

### 🟢 3순위: 운영 및 고도화 (Low Priority)
- [ ] **태그 기반 검색 및 필터링**
  - 장르별, 지역별, 유/무료 필터링 기능 추가
- [ ] **다국어 지원 (Translator)**
  - `Translator` 도구 구현 및 시스템 리소스 다국어화
- [ ] **코드 스타일 및 정적 분석**
  - `ktlint` 및 `detekt` 적용으로 코드 일관성 유지
- [ ] **CI/CD 환경 구축**
  - GitHub Actions를 통한 자동 빌드 및 테스트(PR 시 체크)

---

## 🛠️ 백로그 (Backlog)
- [ ] 디자인 시스템(Design System) 정립 및 테마 고도화
- [ ] 홈 화면 위젯 구현
- [ ] 푸시 알림 (행사 시작 전 알림 등)
- 메인ui 리프레시 가능하게 구성. 레이아웃 개선.
- 무한 스크롤이 가능하게 구성. 이에 맞는 리포지터리 전략
- 상세뷰 데이터 모델 전략 정의
- 테스트 케이스/ 성능 테스트 전략 수립

---

*마지막 업데이트: 2026-02-06*
*관련 문서: [CHANGELOG.md](./CHANGELOG.md), [IMPROVEMENTS.md](./IMPROVEMENTS.md)*

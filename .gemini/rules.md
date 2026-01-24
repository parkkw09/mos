# Project Rules for MOS

## 1. 새 챗 시작 시 필수 문서 읽기 (최우선)

**새로운 대화가 시작될 때, 작업을 수행하기 전에 반드시 아래 문서들을 모두 읽을 것.**

### 필수 문서 (순서대로 읽기):
1. `README.md` - 프로젝트 개요 및 현재 상태
2. `docs/CHANGELOG.md` - 최근 작업 이력 (가장 중요!)
3. `docs/MODULARIZATION.md` - 모듈 구조 및 아키텍처
4. `docs/IMPROVEMENTS.md` - 개선 사항 및 TODO
5. `docs/GITIGNORE_GUIDE.md` - Git 관련 가이드

### 이해해야 할 내용:
- 프로젝트의 목적과 기능
- 모듈 구조 (app, domain, data, presentation)
- 사용 중인 기술 스택 (Kotlin, Jetpack Compose, Hilt 등)
- 아키텍처 패턴 (Clean Architecture, MVVM)
- **최근 작업 이력 및 다음 할 일**

---

## 2. 작업 완료 시 문서화 규칙

**모든 작업이 완료되면 반드시 `docs/` 폴더에 기록할 것.**

### 문서화 절차:
1. **CHANGELOG.md 업데이트** (필수)
   - 날짜, 작업 제목, 변경 사항 요약
   - 다음에 해야 할 일 명시

2. **관련 문서 업데이트** (해당 시)
   - 새로운 기능 추가 → `README.md` 업데이트
   - 아키텍처 변경 → `MODULARIZATION.md` 업데이트
   - TODO 완료 → `IMPROVEMENTS.md` 업데이트

3. **새 문서 생성** (필요 시)
   - 대규모 작업은 별도 문서로 작성
   - 파일명: `docs/YYYY-MM-DD_작업명.md` 형식

### CHANGELOG.md 형식:
```markdown
## [날짜] - 작업 제목

### 완료한 작업
- 작업 내용 1
- 작업 내용 2

### 변경된 파일
- `path/to/file.kt`

### 다음 할 일
- 다음 작업 1
- 다음 작업 2
```

---

## 3. 코드 작업 규칙

### 빌드 검증:
- 코드 변경 후 반드시 `./gradlew build` 실행
- 린트 오류가 없어야 함

### 커밋 메시지:
- 한글 또는 영어로 명확하게 작성
- 변경 내용을 간결하게 요약

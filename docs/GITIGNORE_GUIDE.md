# .gitignore 구조 가이드

프로젝트의 각 모듈별로 적절한 `.gitignore` 파일이 설정되어 있습니다.

## 📁 .gitignore 파일 위치

```
mos/
├── .gitignore              # 루트 (전체 프로젝트)
├── app/.gitignore          # app 모듈
├── data/.gitignore         # data 모듈
└── domain/.gitignore       # domain 모듈
```

---

## 🔍 각 .gitignore 파일의 역할

### 1. **루트 .gitignore** (전체 프로젝트)

**대상**: 프로젝트 전체에 공통으로 적용되는 파일

**주요 무시 항목**:
- ✅ Gradle 빌드 파일 (`.gradle/`, `build/`)
- ✅ IntelliJ IDEA 설정 (`.idea/`)
- ✅ Android Studio 생성 파일
- ✅ macOS 시스템 파일 (`.DS_Store`)
- ✅ 로컬 설정 (`local.properties`)
- ✅ **비밀 키** (`**/app_props.properties`, `secrets.properties`)
- ✅ APK/AAB 파일
- ✅ Keystore 파일 (`.jks`, `.keystore`)
- ✅ Google Services (`google-services.json`)

**특징**: 
- 가장 포괄적인 설정
- 보안 관련 파일 철저히 제외
- 모든 빌드 산출물 제외

---

### 2. **app/.gitignore** (앱 모듈)

**대상**: Android Application 모듈 특화

**주요 무시 항목**:
- ✅ 빌드 산출물 (`/build/`)
- ✅ APK/AAB 파일
- ✅ Lint 리포트
- ✅ 테스트 결과
- ✅ 코드 커버리지 리포트
- ✅ ProGuard 매핑 파일

**특징**:
- APK 생성 관련 파일 제외
- Release/Debug 빌드 산출물 제외

---

### 3. **data/.gitignore** (데이터 모듈)

**대상**: Android Library 모듈 (Data Layer)

**주요 무시 항목**:
- ✅ 빌드 산출물 (`/build/`)
- ✅ AAR 파일
- ✅ Lint 리포트
- ✅ 테스트 결과
- ✅ **Database 파일** (`.db`, `.db-shm`, `.db-wal`)
- ✅ **Kapt 생성 파일** (`/kapt/`, `/kaptKotlin/`)
- ✅ API 캐시 (`/cache/`)

**특징**:
- Hilt/Kapt 생성 파일 제외
- Room Database 파일 제외
- 네트워크 캐시 제외

---

### 4. **domain/.gitignore** (도메인 모듈)

**대상**: Pure Kotlin/Java Library 모듈

**주요 무시 항목**:
- ✅ 빌드 산출물 (`/build/`)
- ✅ JAR 파일
- ✅ Class 파일
- ✅ 테스트 결과
- ✅ Kotlin 컴파일 파일

**특징**:
- 가장 단순한 설정 (Pure Kotlin)
- JAR 산출물 제외
- Android 관련 파일 없음

---

## 🔐 보안 관련 중요 사항

### 절대 커밋하면 안 되는 파일

1. **API 키 파일**
   ```
   **/app_props.properties
   secrets.properties
   ```

2. **Keystore 파일**
   ```
   *.jks
   *.keystore
   ```

3. **Google Services**
   ```
   google-services.json
   ```

4. **로컬 설정**
   ```
   local.properties
   ```

### 이미 커밋된 경우 제거 방법

```bash
# Git 캐시에서 제거 (파일은 유지)
git rm --cached path/to/file

# 커밋
git commit -m "Remove sensitive file from git"

# 푸시
git push
```

---

## 📊 .gitignore 계층 구조

```
루트 .gitignore (전체 프로젝트)
    ↓
    ├── app/.gitignore (APK, Release 빌드)
    ├── data/.gitignore (AAR, Kapt, DB)
    └── domain/.gitignore (JAR, Kotlin)
```

**우선순위**: 하위 모듈의 `.gitignore`가 루트보다 우선 적용됩니다.

---

## ✅ 검증 방법

### 1. 무시된 파일 확인
```bash
git status --ignored
```

### 2. 특정 파일이 무시되는지 확인
```bash
git check-ignore -v path/to/file
```

### 3. 무시되지 않아야 할 파일 확인
```bash
git ls-files --others --exclude-standard
```

---

## 🔧 유지보수

### .gitignore 업데이트 시 고려사항

1. **새로운 빌드 도구 추가 시**
   - 해당 도구의 생성 파일 확인
   - 적절한 모듈의 `.gitignore`에 추가

2. **새로운 라이브러리 추가 시**
   - 라이브러리가 생성하는 파일 확인
   - 필요시 `.gitignore` 업데이트

3. **CI/CD 설정 시**
   - CI 산출물 제외 여부 결정
   - 필요시 루트 `.gitignore`에 추가

---

## 📝 참고 자료

- [GitHub .gitignore 템플릿](https://github.com/github/gitignore)
- [Android .gitignore 공식 가이드](https://developer.android.com/studio/build)
- [Git 공식 문서](https://git-scm.com/docs/gitignore)

---

## 🎯 요약

각 모듈별로 역할에 맞는 `.gitignore`가 설정되어 있습니다:

- ✅ **루트**: 전체 프로젝트 공통 + 보안 파일
- ✅ **app**: APK, Release 빌드 산출물
- ✅ **data**: AAR, Kapt, Database 파일
- ✅ **domain**: JAR, Kotlin 컴파일 파일

**중요**: 비밀 키와 Keystore는 절대 커밋하지 마세요! 🔐

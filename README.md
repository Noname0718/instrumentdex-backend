# 🎼 InstrumentDex Backend

악기 도감 서비스를 위한 Kotlin + Spring Boot REST API입니다. 악기/연습곡 데이터를 MongoDB에 저장하고 정적 이미지까지 한 서버에서 서빙하여 프론트엔드(Vite + React)가 바로 사용할 수 있도록 설계했습니다.

---

## 1. 시스템 개요
- **언어 / 런타임**: Kotlin 1.9, JDK 21
- **프레임워크**: Spring Boot 3.5 (Web + Data MongoDB)
- **빌드 도구**: Gradle Kotlin DSL + Wrapper
- **데이터베이스**: MongoDB (Atlas 또는 로컬 인스턴스)
- **구성 방식**: RESTful API + 정적 리소스(`/images/**`)

초기 실행 시 `sample-data.json`을 사용해 MongoDB에 악기·연습곡 샘플을 자동으로 입력하므로, 백엔드만 띄워도 프론트엔드 데모를 바로 확인할 수 있습니다.

---

## 2. 빠른 시작
```bash
cd instrumentdex-backend

# (선택) 도커로 MongoDB 실행
docker run -d --name instrumentdex-mongo -p 27017:27017 mongo:7

# 애플리케이션 실행
export MONGODB_URI="mongodb://localhost:27017/instrumentdex"
./gradlew bootRun

# 테스트
./gradlew test
```
- 기본 포트: `8080`
- REST API 엔드포인트: `http://localhost:8080/api`
- 이미지 경로: `http://localhost:8080/images/<파일명>`

---

## 3. 환경 변수 & 설정
| 변수 | 용도 | 예시 |
| --- | --- | --- |
| `MONGODB_URI` (필수) | MongoDB 연결 문자열 | `mongodb://localhost:27017/instrumentdex?directConnection=true` |
| `SPRING_PROFILES_ACTIVE` (선택) | `local`, `prod` 등 프로파일 구분 | `prod` |

> IDE에서 실행 시 Run/Debug Configurations에 `MONGODB_URI`를 넣어두면 매번 커맨드를 수정할 필요가 없습니다.

---

## 4. 디렉터리 구조
```
src/main/kotlin/com/instrumentdex/instrumentdex_backend
├── controller/    # 악기·연습곡 API
├── service/       # 도메인 로직 및 필터링
├── repository/    # Spring Data Mongo 인터페이스
├── domain/        # 문서/임베디드/열거 타입
└── dto/           # 요청·응답 모델

src/main/resources
├── application.yaml       # MongoDB URI 환경 변수 사용
├── data/sample-data.json  # 최초 실행 시 데이터 시드
└── static/images          # /images/** 로 노출되는 썸네일
```

---

## 5. 데이터 시드 & 정적 자원
1. **시드 로직**: 첫 기동 시 `sample-data.json`을 읽어 `instruments`, `practice_songs` 컬렉션을 채웁니다. 이미 데이터가 있다면 기존 값을 유지합니다.
2. **이미지 서빙**: `static/images` 폴더에 파일을 추가하면 `/images/<파일명>`으로 접근 가능합니다. InstrumentController는 상대 경로를 절대 URL로 변환해 프론트에서 즉시 사용 가능합니다.

---

## 6. API 한눈에 보기
| 구분 | 메서드 & 경로 | 설명 |
| --- | --- | --- |
| 악기 목록 | `GET /api/instruments` | 전체 악기 + 태그/난이도 정보 |
| 악기 상세 | `GET /api/instruments/{id}` | 단일 악기 세부 정보 |
| 악기 생성 | `POST /api/instruments` | 관리자용 생성 (`CreateInstrumentRequest`) |
| 악기 수정 | `PATCH /api/instruments/{id}` | 부분 수정 (필요 필드만) |
| 악기 삭제 | `DELETE /api/instruments/{id}` | 관리자용 삭제 |
| 연습곡 목록 | `GET /api/songs` | `instrumentId`, `difficulty`, `tag`, `q` 필터 지원 |
| 연습곡 상세 | `GET /api/songs/{id}` | 유튜브 URL 포함한 상세 |
| 연습곡 생성 | `POST /api/songs` | 단순 CRUD용 생성 |
| 연습곡 삭제 | `DELETE /api/songs/{id}` | 단순 CRUD용 삭제 |
| 고급 연습곡 API | `GET/POST/PATCH/DELETE /api/practice-songs` | 관리자 화면과 1:1 매핑. `level`, `bpm`, `sheetUrl` 등 확장 필드 관리. `@CrossOrigin("*")`으로 프론트 개발 서버에서도 바로 호출 가능. |

요청/응답 스키마는 `src/main/kotlin/.../dto` 디렉터리를 참고하세요. 필드 변경 시 프론트엔드 타입 정의 및 샘플 데이터도 함께 수정하는 것을 권장합니다.

---

## 7. 문제 해결 팁
1. **MongoDB 연결 실패**: `MONGODB_URI`에 `directConnection=true`를 붙여보거나 Atlas에서는 IP 화이트리스트를 확인하세요.
2. **이미지 404**: 파일이 `static/images`에 있는지 확인하고, DTO의 `imageUrl`이 `/images/...` 형식을 따르는지 점검하세요.
3. **CORS 오류**: `/api/practice-songs`는 이미 `@CrossOrigin("*")`이지만 다른 컨트롤러에서 필요하다면 `WebMvcConfigurer`로 글로벌 CORS 설정을 추가하세요.

---

## 8. 배포 가이드 (요약)
1. MongoDB Atlas 클러스터 또는 자체 호스팅 MongoDB 준비.
2. `MONGODB_URI`가 포함된 환경 변수 혹은 `application-prod.yaml` 작성.
3. `./gradlew bootJar`로 JAR 생성 후, `java -jar build/libs/...jar` 형태로 실행.
4. Reverse Proxy(Nginx 등)에서 `/api`와 `/images`를 같은 백엔드로 연결하면 프론트엔드가 추가 설정 없이 동작합니다.

행복한 코딩 되세요! 🧑‍🎤

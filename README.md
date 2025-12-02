# 🥁 InstrumentDex 백엔드

Kotlin + Spring Boot 3 기반의 REST API 서버로, 악기/연습곡 데이터를 MongoDB에 저장하고 정적 이미지 파일을 함께 제공해 프론트엔드(InstrumentDex SPA)가 바로 소비할 수 있도록 구성되어 있습니다.

## 🔧 기술 스택
- Kotlin 1.9, Spring Boot 3.5
- Spring Web, Spring Data MongoDB, Jackson Kotlin module
- Gradle Kotlin DSL + Wrapper
- MongoDB Atlas 혹은 로컬 MongoDB (Connection String 필요)

## 📋 사전 준비
| 항목 | 내용 |
| --- | --- |
| JDK | Java 21 이상 (Gradle Toolchain에서 자동으로 21을 사용) |
| MongoDB | `MONGODB_URI` 환경 변수에 입력할 수 있는 연결 문자열 필요 |
| 포트 | 애플리케이션 기본 포트 `8080` (프론트엔드 `client.js`의 `http://localhost:8080/api`와 동일) |

> `MONGODB_URI` 예시: `mongodb://localhost:27017/instrumentdex?directConnection=true`

## 🚀 실행 · 테스트
```bash
# 0. 저장소 루트 → 백엔드 디렉터리
cd instrumentdex-backend

# 1. (최초 1회) 의존성 다운로드
./gradlew dependencies

# 2. 애플리케이션 실행
MONGODB_URI="mongodb://localhost:27017/instrumentdex" ./gradlew bootRun

# 3. 테스트
./gradlew test
```

실행 후 `http://localhost:8080/api` 경로에서 REST API가 열리고, 정적 파일은 `http://localhost:8080/images/<파일명>`으로 접근할 수 있습니다.

## 🗂️ 폴더 구조
```
src/main/kotlin/com/instrumentdex/instrumentdex_backend
├── controller/    # 악기·연습곡 REST 컨트롤러
├── service/       # 비즈니스 로직과 필터링
├── repository/    # Spring Data Mongo Repository
├── domain/        # 도메인/임베디드/열거 타입 정의
└── dto/           # 요청·응답 모델

src/main/resources
├── application.yaml   # MongoDB 연결 설정 (MONGODB_URI 변수 사용)
├── data/sample-data.json
└── static/images      # 프론트가 참조할 썸네일
```

## 🧷 정적 자원 & 이미지
- `src/main/resources/static/images` 아래에 악기/연습곡 썸네일을 배치하면 `/images/<파일명>` 경로로 자동 서빙됩니다.
- `InstrumentController`는 상대 경로(`/images/...`)를 절대 URL로 변환하므로, 응답을 그대로 프론트에서 `<img />`에 바인딩할 수 있습니다.
- Git에 포함된 샘플 이미지는 UI 데모용으로 제공되며, 실제 배포 시에는 같은 위치에 고해상도 이미지를 교체하세요.

## 🌱 초기 데이터 시드
- 애플리케이션 최초 기동 시 `src/main/resources/data/sample-data.json`을 읽어 MongoDB에 악기(`instruments`), 연습곡(`practice_songs`) 문서를 넣습니다.
- 시드에는 `/images/*.png` 정적 경로와 유튜브 링크가 포함되어 있어, 프론트엔드가 즉시 동작하는지 확인할 수 있습니다.
- 이미 데이터가 들어 있는 컬렉션은 덮어쓰지 않으므로, 필요 시 컬렉션을 비우고 애플리케이션을 재실행하세요.

## 📡 API 요약
| 기능 | 메서드 & 경로 | 설명 |
| --- | --- | --- |
| 악기 목록 | `GET /api/instruments` | 전체 악기 및 태그/난이도 정보 조회 (이미지 URL 자동 보정) |
| 악기 상세 | `GET /api/instruments/{id}` | 단일 악기 세부 설명, 태그, 이미지 |
| 악기 생성 | `POST /api/instruments` | 관리자용 생성 (요청 본문: `CreateInstrumentRequest`) |
| 악기 수정 | `PATCH /api/instruments/{id}` | 부분 수정 |
| 악기 삭제 | `DELETE /api/instruments/{id}` | 데이터 삭제 |
| 연습곡 목록 | `GET /api/songs` | `instrumentId`, `difficulty`, `tag`, `q` 파라미터로 필터링 |
| 연습곡 상세 | `GET /api/songs/{id}` | 유튜브 URL·악기 정보 포함 |
| 연습곡 생성 | `POST /api/songs` | 관리자용 생성 (간단 버전) |
| 연습곡 삭제 | `DELETE /api/songs/{id}` | 관리자용 삭제 |
| 고급 연습곡 API | `GET/POST/PATCH/DELETE /api/practice-songs` | 관리자 화면과 1:1로 매핑되어 level, bpm, sheetUrl 등 확장 필드를 다룹니다. |

`/api/practice-songs` 컨트롤러에는 `@CrossOrigin("*")`이 적용되어 있어, 프론트 개발 서버(포트 5173)에서도 추가 설정 없이 접근할 수 있습니다.

## 🔐 환경 변수 & 구성 팁
- `MONGODB_URI` 외에도 테스트/운영 환경에서 `SPRING_PROFILES_ACTIVE`를 지정해 MongoDB를 분리할 수 있습니다.
- 도커로 MongoDB를 띄우는 경우:
  ```bash
  docker run -d --name instrumentdex-mongo -p 27017:27017 mongo:7
  export MONGODB_URI="mongodb://localhost:27017/instrumentdex"
  ```
- 이미지 업로드가 필요한 경우 S3 등 외부 스토리지를 사용하고, `imageUrl` 필드에 절대 URL을 저장하면 Controller가 그대로 전달합니다.

## 📌 참고
- `gradle.properties`나 OS 환경 변수에 `MONGODB_URI`를 등록해두면 매번 커맨드를 입력할 필요가 없습니다.
- 프론트엔드 README에 명시된 API 스키마가 바뀌면 DTO/컨트롤러/시드를 함께 업데이트해 두 레포가 일치하도록 유지하세요.

# InstrumentDex Backend

Spring Boot + MongoDB 기반 백엔드로, InstrumentDex 웹 서비스에 필요한 REST API와 정적 이미지 리소스를 제공합니다.

## Static image hosting

- 악기 및 연습곡 썸네일 이미지는 `src/main/resources/static/images` 디렉터리에 저장합니다.
- 애플리케이션 실행 후 이미지는 다음과 같은 URL로 접근할 수 있습니다.  
  예: [`http://localhost:8080/images/guitar.png`](http://localhost:8080/images/guitar.png)
- 백엔드가 반환하는 `imageUrl` 값과 프론트엔드 `<img />` 태그에서는  
  `/images/guitar.png` 처럼 **상대 경로**를 그대로 사용합니다.

---

## Practice song endpoints

### 1. 목록 조회 / 필터링

- **GET `/api/songs`**
- **GET `/api/practice-songs`**

지원하는 쿼리 파라미터:

- `instrumentId`
- `level`
- `tag`
- `q` (검색어)

---

### 2. 상세 조회

- **GET `/api/songs/{id}`**
- **GET `/api/practice-songs/{id}`**

응답에는 연습곡의 **YouTube URL**이 포함되며,  
프론트엔드에서 링크/임베드에 사용할 수 있습니다.

---

### 3. 생성 / 수정 / 삭제 (관리자용)

- **POST** `/api/songs`
- **PATCH** `/api/songs/{id}`
- **DELETE** `/api/songs/{id}`

동일한 동작을 하는 alias 경로:

- **POST** `/api/practice-songs`
- **PATCH** `/api/practice-songs/{id}`
- **DELETE** `/api/practice-songs/{id}`

두 base path는 동일하게 동작합니다.

---

## Initial data seeding

앱 최초 실행 시 다음 파일의 데이터가 MongoDB에 자동 삽입됩니다.

```text
src/main/resources/data/sample-data.json

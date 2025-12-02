InstrumentDex Backend

Spring Boot + MongoDB 기반 백엔드로, InstrumentDex 웹 서비스에 필요한 REST API와 정적 이미지 리소스를 제공합니다.

📁 Static Image Hosting

악기 및 연습곡 썸네일 이미지는 다음 위치에 저장합니다:

src/main/resources/static/images


애플리케이션 실행 후 모든 이미지는 다음과 같이 접근할 수 있습니다:

http://localhost:8080/images/<file-name>
예: http://localhost:8080/images/guitar.png


API에서 반환하는 imageUrl 값과 프론트엔드 <img /> 태그에서는
/images/guitar.png 같은 상대 경로를 그대로 사용합니다.

🎵 Practice Song API Endpoints
목록 조회 및 필터링
GET /api/songs
GET /api/practice-songs


지원하는 쿼리 파라미터:

instrumentId

level

tag

q (검색)

상세 조회
GET /api/songs/{id}
GET /api/practice-songs/{id}


응답에는 YouTube URL이 포함되어 프론트엔드에서 링크/임베드 가능합니다.

생성 / 수정 / 삭제 (관리자용)
POST /api/songs
PATCH /api/songs/{id}
DELETE /api/songs/{id}

(동일 alias)
POST /api/practice-songs
PATCH /api/practice-songs/{id}
DELETE /api/practice-songs/{id}


두 base path는 동일하게 동작합니다.

🗂️ Initial Data Seeding

앱 최초 실행 시 다음 파일의 데이터가 MongoDB에 자동 삽입됩니다:

src/main/resources/data/sample-data.json


샘플 데이터는 /images/*.jpg 경로와 YouTube 링크를 이미 포함하고 있어
백엔드와 프론트엔드가 즉시 연동된 상태를 바로 확인할 수 있습니다.

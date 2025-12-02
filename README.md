InstrumentDex Backend

Spring Boot + MongoDB 기반 백엔드로, InstrumentDex 웹 서비스에 필요한 REST API와 정적 이미지 리소스를 제공합니다.

📁 Static Image Hosting

악기 / 연습곡 썸네일 이미지는 다음 위치에 저장합니다:
src/main/resources/static/images

애플리케이션 실행 후 모든 이미지는 다음과 같이 접근할 수 있습니다:

http://localhost:8080/images/<file-name>
예) http://localhost:8080/images/guitar.png


API에서 반환하는 imageUrl 값과 프론트엔드 <img /> 태그는
/images/guitar.png 같은 상대 경로 그대로 사용합니다.

🎵 Practice Song API Endpoints
목적	Method & Path	설명
연습곡 목록 조회 및 필터링	GET /api/songs 또는 GET /api/practice-songs	instrumentId, level, tag, q 등의 필터 지원
연습곡 상세 조회	GET /api/songs/{id} 또는 /api/practice-songs/{id}	YouTube URL 포함(프론트에서 링크/임베드)
연습곡 생성/수정/삭제(관리자용)	POST / PATCH / DELETE (두 base path 동일 동작)	/api/songs와 /api/practice-songs 동일하게 처리
🗂️ Initial Data Seeding

앱 최초 실행 시, 다음 파일의 데이터가 MongoDB에 자동 삽입됩니다:
src/main/resources/data/sample-data.json

이 샘플 데이터는 /images/*.jpg 경로를 이미 사용하고 있으며
YouTube 링크도 포함되어 있어,
백엔드·프론트엔드가 즉시 연동된 UI를 바로 확인할 수 있습니다.

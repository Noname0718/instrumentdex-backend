# InstrumentDex Backend

This Spring Boot + MongoDB backend powers the InstrumentDex web experience. It exposes REST APIs for instruments and practice songs and serves the static assets (instrument / song thumbnails) that the frontend references.

## Static image hosting

- Place instrument or practice song thumbnails under `src/main/resources/static/images` (sample placeholders are already committed).
- Every file becomes available through `http://localhost:8080/images/<file-name>` once the app is running. Example: `http://localhost:8080/images/guitar.jpg`.
- Reference the same relative path (`/images/guitar.jpg`) from the `imageUrl` fields that are returned by the API and consumed by the frontend `<img />` tags.

## Practice song endpoints

| Purpose | Method & Path | Notes |
| --- | --- | --- |
| List or filter songs | `GET /api/songs` or `GET /api/practice-songs` | Supports `instrumentId`, `level`, `tag`, `q` query params. |
| Song detail | `GET /api/songs/{id}` (alias `/api/practice-songs/{id}`) | Returns the YouTube URL so the UI can link/embed it. |
| Admin create/update/delete | `POST/PATCH/DELETE` on either `/api/songs` or `/api/practice-songs` | Both base paths behave identically for compatibility. |

## Local data seeding

On the first boot the app seeds MongoDB with `src/main/resources/data/sample-data.json`. The sample entries already use the `/images/*.jpg` assets and contain YouTube links so the frontend can demonstrate the image + video UX immediately.

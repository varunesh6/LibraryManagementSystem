# College Library Management System

Java 17 + Spring Boot + Spring Security + JWT + refresh-token rotation + Google OAuth2 + MySQL.

## Run
1. Create MySQL database/user:
   - `CREATE DATABASE library_db;`
   - `CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'your-password';`
   - `GRANT ALL PRIVILEGES ON library_db.* TO 'library_user'@'localhost';`
2. Edit `src/main/resources/application.properties`.
3. Run `mvn spring-boot:run`.
4. Register a normal user with `POST /api/auth/register`.
5. For admin testing, temporarily set `app.bootstrap-admin.enabled=true` and change the bootstrap password.
6. Login with `POST /api/auth/login`.
7. Send the access token as `Authorization: Bearer <accessToken>`.

## OAuth2
Start Google login at `/oauth2/authorization/google`. Google returns to `/login/oauth2/code/google`. The success handler creates/fetches a USER and redirects to `/oauth2/success?...`. In a real frontend, replace the redirect with your HTTPS frontend callback URL and never expose tokens in query strings; use a secure one-time handoff or backend-for-frontend pattern.

## Security
Access JWTs are 15 minutes. Refresh tokens are random opaque values stored server-side, expire after 7 days, and rotate on every refresh. Logout revokes the supplied refresh token. CSRF is disabled because this backend is designed as a stateless bearer-token API; do not copy that setting blindly to cookie-authenticated browser sessions.

## Fine
Late return fine is ₹10 per late calendar day. Borrowing gives 5 days. Money uses BigDecimal.

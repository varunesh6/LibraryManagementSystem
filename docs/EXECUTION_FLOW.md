# Top-to-bottom execution flow

## 1. Registration
POST `/api/auth/register` -> `AuthController` -> Bean Validation -> `AuthService.register()` -> email uniqueness -> BCrypt -> `User(role=USER, provider=LOCAL)` -> `UserRepository` -> MySQL.

The request has no role field, so a client cannot register itself as ADMIN.

## 2. Login
POST `/api/auth/login` -> `AuthController` -> `AuthenticationManager` -> `CustomUserDetailsService` -> `UserRepository.findByEmail()` -> Spring Security's password check using BCrypt -> `AuthService.tokens()` -> short-lived JWT + opaque refresh token -> `RefreshTokenRepository` -> response.

## 3. Protected request
`Authorization: Bearer <accessToken>` -> `SecurityFilterChain` -> `JwtAuthenticationFilter` (`OncePerRequestFilter`) -> parse and verify JWT signature/expiry -> extract email -> `CustomUserDetailsService` -> `SecurityContext` -> authorization rules (`hasRole`/`hasAnyRole`) -> controller -> service -> repository -> MySQL.

`filterChain.doFilter(request, response)` passes the request to the next filter/controller chain.

Spring Security's `hasRole("ADMIN")` checks for authority `ROLE_ADMIN`; the `ROLE_` prefix is added by Spring's role-based authorization API.

## 4. Borrow
`POST /api/borrow/book/{bookId}` -> authenticated email from `Authentication` -> user lookup -> pessimistic book lock -> availability check -> create transaction -> `borrowedAt=now`, `dueAt=now+5 days`, `amount=book.amount`, `fine=0`, `status=BORROWED` -> `book.available=false` -> transaction commit.

## 5. Return
`PUT /api/borrow/{transactionId}/return` -> authenticated user -> transaction lookup by `(id,userId)` -> ownership is enforced by the query -> already-returned check -> `returnedAt=now` -> calendar late days -> `fine=lateDays*10` -> `status=RETURNED` -> book available -> commit.

## 6. Overdue
A scheduled job checks BORROWED records every minute by default. If `dueAt` is in the past, the status becomes `OVERDUE`. Change `app.overdue-check-ms` for another interval.

## 7. Refresh
`POST /api/auth/refresh` -> locate opaque refresh token -> reject revoked/expired -> revoke old token -> create a new refresh token -> create a new access JWT -> return both. This is refresh-token rotation.

Refresh tokens are not JWT access tokens. Password-reset tokens are unrelated to authentication.

## 8. Logout
`POST /api/auth/logout` -> revoke the supplied refresh token. The current access JWT is not instantly invalidated; it normally expires within 15 minutes.

## 9. Password recovery
Forgot -> find user -> random one-time token -> 15-minute expiry -> development response returns token. Production should email a reset link. Reset -> validate token -> BCrypt new password -> delete token.

## 10. Google OAuth2
`/oauth2/authorization/google` -> Google -> `/login/oauth2/code/google` -> `OAuth2SuccessHandler` -> find/create user with `provider=GOOGLE`, `role=USER`, no Google password -> generate the same access/refresh tokens -> redirect to the configured success target. For production, use an HTTPS frontend callback and a secure one-time handoff instead of putting tokens in query parameters.

## 11. RBAC
`/api/admin/**` requires `ROLE_ADMIN`. A `ROLE_USER` receives JSON 403. Missing/invalid authentication receives JSON 401.

CSRF is disabled because the API uses stateless bearer Authorization headers rather than browser cookies. If you switch authentication to cookies, revisit CSRF protection.

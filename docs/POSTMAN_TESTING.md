# Postman testing

Base URL: `http://localhost:8080`

## Register
POST `/api/auth/register`
Body:
```json
{"name":"Student One","email":"student@example.com","password":"Student123!"}
```
Expected: `201 No Content`.

## Login
POST `/api/auth/login`
Body:
```json
{"email":"student@example.com","password":"Student123!"}
```
Expected: `200`.
Save `accessToken` and `refreshToken`.

## Protected headers
For protected endpoints:
`Authorization: Bearer {{accessToken}}`

## Get books
GET `/api/books`
Expected: `200`.

## Add book (ADMIN)
POST `/api/admin/books`
```json
{"title":"Clean Code","author":"Robert C. Martin","genre":"Programming","amount":500.00,"available":true}
```
Expected: `200`.

## Borrow
POST `/api/borrow/book/1`
Expected: `200` and status `BORROWED`, dueAt about five days after borrowedAt.

## My history
GET `/api/borrow/my-history`
Expected: `200`.

## My active
GET `/api/borrow/my-active`
Expected: `200`.

## Return
PUT `/api/borrow/1/return`
Expected: `200`. Fine is ₹10 per late calendar day.

## My fines
GET `/api/borrow/my-fines`
Expected: `200`.

## Refresh
POST `/api/auth/refresh`
```json
{"refreshToken":"{{refreshToken}}"}
```
Expected: `200`; replace both saved tokens. Reusing the old refresh token should fail.

## Logout
POST `/api/auth/logout`
```json
{"refreshToken":"{{refreshToken}}"}
```
Expected: `204`.

## Forgot password
POST `/api/auth/forgot-password`
```json
{"email":"student@example.com"}
```
Expected: `200` in this development build with a `resetToken`.

## Reset password
POST `/api/auth/reset-password`
```json
{"token":"<resetToken>","newPassword":"NewStudent123!"}
```
Expected: `200`.

## Admin endpoints
GET `/api/admin/users`
GET `/api/admin/users/1`
GET `/api/admin/borrowings`
GET `/api/admin/borrowings/1`
GET `/api/admin/fines`

PUT `/api/admin/borrowings/1`
```json
{"amount":500.00,"fine":20.00,"status":"RETURNED"}
```

DELETE `/api/admin/books/1`

## Negative tests
- USER -> `/api/admin/users` => `403`.
- No Authorization header -> protected endpoint => `401`.
- Invalid JWT -> protected endpoint => `401`.
- Expired JWT -> protected endpoint => `401`.
- Revoked refresh token -> `/api/auth/refresh` => `400`.
- Expired refresh token -> `/api/auth/refresh` => `400`.
- Borrow unavailable book => `400`.
- Return an already returned transaction => `400`.
- Return another user's transaction => `404` from the ownership-scoped query.
- USER cannot set amount/fine/status because those fields are absent from user-facing request DTOs.

package api.epilogue.wehere.error

import org.springframework.http.HttpStatus

enum class ErrorCause(val status: HttpStatus, val message: String? = null) {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_OWNER(HttpStatus.FORBIDDEN, "cannot access to the resource"),
    UPLOAD_FAILED(HttpStatus.CONFLICT, "file upload failed"),
    AUTH_FAILED(HttpStatus.UNAUTHORIZED),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST)
}
package api.epilogue.wehere.error

import org.springframework.http.HttpStatus

enum class ErrorCause(val status: HttpStatus, val message: String? = null) {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_OWNER(HttpStatus.FORBIDDEN, "cannot access to the resource")
}
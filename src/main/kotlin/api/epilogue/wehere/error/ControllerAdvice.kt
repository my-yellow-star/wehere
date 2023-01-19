package api.epilogue.wehere.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(value = [ApiError::class])
    fun handle(e: ApiError) =
        ResponseEntity(ErrorResponse(e.errorCause.name, e.message), e.errorCause.status)

    data class ErrorResponse(
        val type: String,
        val reason: String?
    )
}
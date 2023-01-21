package api.epilogue.wehere.error

import javax.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(value = [ApiError::class])
    fun handle(e: ApiError) =
        ResponseEntity(ErrorResponse(e.errorCause.name, e.message), e.errorCause.status)

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handle(e: EntityNotFoundException) =
        ResponseEntity(ErrorResponse("ENTITY_NOT_FOUND", "entity not found in database"), HttpStatus.NOT_FOUND)

    data class ErrorResponse(
        val type: String,
        val reason: String?
    )
}
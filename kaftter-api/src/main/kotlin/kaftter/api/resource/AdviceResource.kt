package kaftter.api.resource

import kaftter.api.exception.UserNotFoundException
import kaftter.api.logging.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AdviceResource {
    val logger = logger<AdviceResource>()

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<Any> {
        logger.error("User not found", exception)
        return ResponseEntity.notFound().build<Any>()
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(exception: Exception): ResponseEntity<Any> {
        logger.error("Undexpected exception", exception)
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

}

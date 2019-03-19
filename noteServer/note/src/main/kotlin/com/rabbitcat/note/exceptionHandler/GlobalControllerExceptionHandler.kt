package com.rabbitcat.note.exceptionHandler

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.exception.UnsupportedAuthorizationException
import com.rabbitcat.note.exception.UserIdDuplicatedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerExceptionHandler {
    val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [(UnauthorizedException::class)])
    fun handleUnauthorizedUser(ex : UnauthorizedException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", ex.message), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [(UserIdDuplicatedException::class)])
    fun handleUserIdDuplicated(ex : UserIdDuplicatedException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", ex.message), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [(UnsupportedAuthorizationException::class)])
    fun handleUnsupportedAuthorization(ex : UnsupportedAuthorizationException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", ex.message), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(value = [(HttpRequestMethodNotSupportedException::class)])
    fun handleMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<Any>{
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", ex.message), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(value = [(Exception::class)])
    fun handleBasicException(ex : Exception): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Internal Server error"), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
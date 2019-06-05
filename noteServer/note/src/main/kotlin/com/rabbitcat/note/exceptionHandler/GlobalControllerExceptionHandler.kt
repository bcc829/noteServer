package com.rabbitcat.note.exceptionHandler

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.exception.*
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [(UnauthorizedException::class), (HttpClientErrorException.BadRequest::class)])
    fun handleUnauthorizedUser(ex : UnauthorizedException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Unauthorized"), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [(UserIdDuplicatedException::class)])
    fun handleUserIdDuplicated(ex : UserIdDuplicatedException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "user info is duplicated"), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [(UnsupportedAuthorizationException::class)])
    fun handleUnsupportedAuthorization(ex : UnsupportedAuthorizationException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "file upload fail"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(value = [(InvalidArgumentException::class), (MethodArgumentTypeMismatchException::class)])
    fun handleInvalidArgumentException(ex: InvalidArgumentException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "parameter is invalid"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(FileUploadException::class)])
    fun handleFileUploadException(ex: FileUploadException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "file upload fail"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(FileDownloadException::class)])
    fun handleFileDownloadException(ex: FileDownloadException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "file upload fail"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(FileDeleteException::class)])
    fun handleFileDeleteException(ex: FileDeleteException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "file delete fail"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(HttpRequestMethodNotSupportedException::class)])
    fun handleMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "request method is not supported"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(value = [(DataIntegrityViolationException::class)])
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "data duplication error"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(value = [(Exception::class)])
    fun handleBasicException(ex : Exception): ResponseEntity<Any> {
        logger.error("#######catch exception - {}", ex.message)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Internal Server error"), HttpStatus.INTERNAL_SERVER_ERROR)
    }


}
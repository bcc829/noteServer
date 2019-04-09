package com.rabbitcat.note.exception

class InvalidArgumentException(message: String ?= null): RuntimeException(message) {
    override val message: String?
        get() = if(super.message == null) "parameter is invalid" else super.message
}
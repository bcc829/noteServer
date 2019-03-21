package com.rabbitcat.note.exception

class InvalidArgumentException: RuntimeException() {
    override val message: String?
        get() = "parameter is invalid"
}
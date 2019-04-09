package com.rabbitcat.note.exception

class FileDeleteException(message: String ?= null): RuntimeException(message) {
    override val message: String?
        get() = if(super.message == null) "file delete fail" else super.message
}
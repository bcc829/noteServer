package com.rabbitcat.note.exception

class FileUploadException(message: String ?= null): RuntimeException(message) {
    override val message: String?
        get() = if(super.message == null) "file upload fail" else super.message
}